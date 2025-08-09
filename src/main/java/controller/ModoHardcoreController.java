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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

public class ModoHardcoreController implements Reinicio{

    @FXML
    private AnchorPane panel;

    @FXML
    private Label etiquetaRonda;

    @FXML
    private Label etiquetaAviso;

    @FXML
    private Button rojo, verde, azul, amarillo, naranja, morado, cian, rosa;

    @FXML
    private Button botonReiniciar;

    private final List<Button> botones = new ArrayList<>();
    private final List<Button> secuenciaMaquina = new ArrayList<>();
    private final List<Button> secuenciaJugador = new ArrayList<>();
    private final Random aleatorio = new Random();

    private final int TOTAL_RONDAS = 20;
    private int rondaActual = 1;
    private int velocidadMs = 300;

    @FXML
    public void initialize() {
        Collections.addAll(botones, rojo, verde, azul, amarillo, naranja, morado, cian, rosa);
        nuevaRonda();
    }

    @FXML
    private void manejarColor(ActionEvent event) {
        Button botonPresionado = (Button) event.getSource();
        secuenciaJugador.add(botonPresionado);

        resaltarBoton(botonPresionado);

        int index = secuenciaJugador.size() - 1;
        if (secuenciaJugador.get(index) != secuenciaMaquina.get(index)) {
            mostrarDialogo("Has perdido", "Ronda alcanzada: " + rondaActual);
            Resultado.guardarResultado( "Hardcore", rondaActual, "Perdió");

            return;
        }

        if (secuenciaJugador.size() == secuenciaMaquina.size()) {
            if (rondaActual == TOTAL_RONDAS) {
                mostrarDialogo("¡Victoria!", "Completaste las 20 rondas del modo Hardcore");
                Resultado.guardarResultado( "Hardcore", rondaActual, "Ganó");
            } else {
                rondaActual++;
                nuevaRonda();
            }
        }
    }

    private void nuevaRonda() {
        etiquetaRonda.setText("Ronda: " + rondaActual);
        secuenciaJugador.clear();
        agregarColorAleatorio();
        mostrarSecuencia();
    }

    private void agregarColorAleatorio() {
        secuenciaMaquina.add(botones.get(aleatorio.nextInt(botones.size())));
    }

    private void mostrarSecuencia() {
        List<Button> copiaSecuencia = new ArrayList<>(secuenciaMaquina);
        Platform.runLater(() -> {
            etiquetaAviso.setText("Observa");
            bloquearBotones(true);
        });

        new Thread(() -> {
            try {
                Thread.sleep(800);
                for (Button boton : copiaSecuencia) {
                    Platform.runLater(() -> resaltarBoton(boton));
                    Thread.sleep(velocidadMs + 200);
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
        InnerShadow efecto = new InnerShadow();
        efecto.setColor(Color.WHITE);
        efecto.setRadius(20);
        boton.setEffect(efecto);

        PauseTransition pausa = new PauseTransition(Duration.millis(velocidadMs));
        pausa.setOnFinished(e -> boton.setEffect(null));
        pausa.play();
    }

    @FXML
    private void ReiniciarJuego() {
        rondaActual = 1;
        secuenciaMaquina.clear();
        secuenciaJugador.clear();
        nuevaRonda();
    }

    private void bloquearBotones(boolean estado) {
        for (Button boton : botones) {
            boton.setMouseTransparent(estado);
        }
    }

    private void mostrarDialogo(String titulo, String mensaje) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dialogos/finalPartida.fxml"));
            Parent root = loader.load();

            FinalPartidaController controller = loader.getController();
            controller.setMensaje(mensaje);
            controller.setControladorReiniciable(this);

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
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
            System.out.println("Error al cargar la pantalla");
        }
    }

    public void salirApp() {
        System.exit(0);
    }
}
