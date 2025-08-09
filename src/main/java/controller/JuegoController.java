package controller;

import database.Resultado;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class JuegoController implements Reinicio {

    @FXML
    private AnchorPane panel;

    @FXML
    private Label etiquetaRonda;

    @FXML
    private Label etiquetaRecord;

    @FXML
    private Label etiquetaAviso;

    @FXML
    private Button amarillo;

    @FXML
    private Button rojo;

    @FXML
    private Button azul;

    @FXML
    private Button verde;

    @FXML
    private HBox velocidadBox;

    @FXML
    private Button botonFacil;

    @FXML
    private Button botonNormal;

    @FXML
    private Button botonDificil;

    @FXML
    private Button botonReiniciarRecord;

    private final List<Button> secuenciaMaquina = new ArrayList<>();
    private final List<Button> secuenciaJugador = new ArrayList<>();
    private final Random aleatorio = new Random();

    private String dificultad = "Fácil";
    private int velocidadMs = 600;
    private int rondasParaGanar = 10;
    private boolean modoInfinito = false;

    private int rondaActual = 1;
    private int record = 0;

    @FXML
    public void initialize() {
        nuevaRonda();
    }

    @FXML
    private void manejarColor(javafx.event.ActionEvent event) {
        Button botonPresionado = (Button) event.getSource();
        secuenciaJugador.add(botonPresionado);

        reproducirSonido(botonPresionado);
        resaltarBoton(botonPresionado);


        int index = secuenciaJugador.size() - 1;
        if (secuenciaJugador.get(index) != secuenciaMaquina.get(index)) {
            ActualizarRecord();
            etiquetaAviso.setText("");
            mostrarDialogo("¡Has perdido! Ronda alcanzada: " + rondaActual);
            Resultado.guardarResultado( dificultad, rondaActual, "Perdió");

            return;
        }

        if (secuenciaJugador.size() == secuenciaMaquina.size()) {
            rondaActual++;

            if (!modoInfinito && rondaActual > rondasParaGanar) {
                etiquetaAviso.setText("");
                mostrarDialogo("Completaste todas las rondas del modo: " + dificultad);
                Resultado.guardarResultado( dificultad, rondaActual, "Ganó");
                return;
            }

            nuevaRonda();
        }
    }

    private void nuevaRonda() {
        etiquetaRonda.setText("Ronda: " + rondaActual);
        secuenciaJugador.clear();
        agregarColorAleatorio();
        mostrarSecuencia();
    }

    private void agregarColorAleatorio() {
        int opcion = aleatorio.nextInt(4);
        switch (opcion) {
            case 0 -> secuenciaMaquina.add(amarillo);
            case 1 -> secuenciaMaquina.add(rojo);
            case 2 -> secuenciaMaquina.add(azul);
            case 3 -> secuenciaMaquina.add(verde);
        }
    }

    private void mostrarSecuencia() {
        List<Button> secuenciaCopia = new ArrayList<>(secuenciaMaquina);
        Platform.runLater(() -> {
            etiquetaAviso.setText("Observa");
            bloquearBotones(true);
        });
        new Thread(() -> {
            try {
                Thread.sleep(1500);
                for (Button boton : secuenciaCopia) {
                    javafx.application.Platform.runLater(() -> resaltarBoton(boton));
                    Thread.sleep(velocidadMs);
                }

                Platform.runLater(() -> {
                    etiquetaAviso.setText("Tu turno");
                    bloquearBotones(false);
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void resaltarBoton(Button boton) {
        javafx.application.Platform.runLater(() -> {
            InnerShadow sombra = new InnerShadow();
            sombra.setColor(Color.web("#ffffff"));
            sombra.setRadius(20);
            sombra.setChoke(0.2);
            sombra.setWidth(30);
            sombra.setHeight(30);
            boton.setEffect(sombra);
         reproducirSonido(boton);
            PauseTransition pausa = new PauseTransition(Duration.millis(200));
            pausa.setOnFinished(e -> boton.setEffect(null));
            pausa.play();
        });
    }

    private void reproducirSonido(Button boton) {
        String nombre = switch (boton.getId()) {
            case "amarillo" -> "amarillo";
            case "rojo" -> "rojo";
            case "azul" -> "azul";
            case "verde" -> "verde";
            default -> "";
        };

        try {
            String ruta = getClass().getResource("/sonidos/" + nombre + ".wav").toString();
            AudioClip clip = new AudioClip(ruta);
            clip.play();
        } catch (Exception e) {
            System.out.println("Error al cargar sonido: " + e.getMessage());
        }
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;

        switch (dificultad) {
            case "Fácil" -> {
                velocidadMs = 800;
                rondasParaGanar = 10;
            }
            case "Normal" -> {
                velocidadMs = 600;
                rondasParaGanar = 15;
            }
            case "Difícil" -> {
                velocidadMs = 400;
                rondasParaGanar = 25;
            }
            case "Hardcore" -> {
                velocidadMs = 250;
                rondasParaGanar = 30;
            }
            case "Infinito" -> {
                velocidadMs = 600;
                modoInfinito = true;
            }
        }
        if (modoInfinito) {
            velocidadBox.setVisible(true);
            velocidadBox.setManaged(true);
            etiquetaRecord.setVisible(true);
            botonReiniciarRecord.setVisible(true);
            etiquetaRecord.setText("Récord: " + record);
        } else {
            velocidadBox.setVisible(false);
            velocidadBox.setManaged(false);
            etiquetaRecord.setVisible(false);
            botonReiniciarRecord.setVisible(false);
        }
    }

    private void ActualizarRecord() {
        if (modoInfinito && rondaActual - 1 > record) {
            record = rondaActual - 1;
            etiquetaRecord.setText("Récord: " + record);
        }
    }

    @FXML
    private void ReiniciarRecord(ActionEvent event) {
        if (modoInfinito) {
            record = 0;
            etiquetaRecord.setText("Récord: 0");
        }
    }

    @FXML
    private void ReiniciarJuego() {
        rondaActual = 1;
        secuenciaMaquina.clear();
        secuenciaJugador.clear();
        nuevaRonda();
    }

    private void bloquearBotones(boolean estado) {
        amarillo.setMouseTransparent(estado);
        rojo.setMouseTransparent(estado);
        azul.setMouseTransparent(estado);
        verde.setMouseTransparent(estado);
    }

    private void mostrarDialogo(String mensaje) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dialogos/finalPartida.fxml"));
            Parent root = loader.load();

            FinalPartidaController controller = loader.getController();
            controller.setMensaje(mensaje);
            controller.setControladorReiniciable(this);

            Stage stage = new Stage();
            stage.setTitle("Fin del juego");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println("Error al cargar el dialogo");
        }
    }

    public void reiniciarDesdeDialogo() {
        ReiniciarJuego();
    }

    public void volverMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dialogos/app.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) panel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Error al cargar el dialogo");
        }
    }

    public void salirApp() {
        System.exit(0);
    }

    @FXML
    private void CambiarFacil() {
        velocidadMs = 1000;
    }

    @FXML
    private void CambiarNormal() {
        velocidadMs = 800;
    }

    @FXML
    private void CambiarDificil() {
        velocidadMs = 400;
    }


}
