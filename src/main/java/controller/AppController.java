package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox root;
    @FXML
    private Button creditos;

    @FXML
    private MenuButton dificultad;

    @FXML
    private Button jugar;

    @FXML
    private Button salir;

    @FXML
    private Label titulo;

    @FXML
    private MenuItem facil;

    @FXML
    private MenuItem intermedio;

    @FXML
    private MenuItem dificil;

    @FXML
    private MenuItem infinito;

    private String dificultadActual = "Fácil";


    @FXML
    private void initialize() {

        dificultad.setText("Dificultad: " + dificultadActual);
    }

    @FXML
    private void Jugar(ActionEvent event) {
        try {
            FXMLLoader loader;

            if (dificultadActual.equals("Hardcore")) {
                loader = new FXMLLoader(getClass().getResource("/dialogos/modoHardcore.fxml"));
                Parent root = loader.load();

                // No es necesario pasar dificultad si el controlador ya lo tiene configurado fijo
                Stage stage = (Stage) jugar.getScene().getWindow();
                stage.setScene(new Scene(root));

            } else {
                loader = new FXMLLoader(getClass().getResource("/dialogos/juego.fxml"));
                Parent root = loader.load();

                JuegoController juegoController = loader.getController();
                juegoController.setDificultad(dificultadActual);

                Stage stage = (Stage) jugar.getScene().getWindow();
                stage.setScene(new Scene(root));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar la pantalla");
        }
    }

    @FXML
    private void Creditos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dialogos/creditos.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) creditos.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Error al cargar la pantalla");
        }
    }

    @FXML
    private void MostrarHistorial() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dialogos/historial.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Historial de Resultados");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void Salir(ActionEvent event) {
        Stage stage = (Stage) salir.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void DificultadFacil(ActionEvent event) {
        dificultadActual = "Fácil";
        dificultad.setText("Dificultad: " + dificultadActual);
    }

    @FXML
    private void DificultadIntermedio(ActionEvent event) {
        dificultadActual = "Normal";
        dificultad.setText("Dificultad: " + dificultadActual);
    }

    @FXML
    private void DificultadDificil(ActionEvent event) {
        dificultadActual = "Difícil";
        dificultad.setText("Dificultad: " + dificultadActual);
    }

    @FXML
    private void DificultadHardcore(ActionEvent actionEvent) {
        dificultadActual = "Hardcore";
        dificultad.setText("Dificultad: " +dificultadActual);
    }

    @FXML
    private void DificultadInfinito(ActionEvent actionEvent) {
        dificultadActual = "Infinito";
        dificultad.setText("Dificultad: " +dificultadActual);
    }

    public void CambiarDificultad(ActionEvent actionEvent) {
    }
}

