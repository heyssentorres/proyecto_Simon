package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FinalPartidaController {

    @FXML
    private Label lblMensaje;

    private Reinicio controlador;

    public void setMensaje(String mensaje) {
        lblMensaje.setText(mensaje);
    }

    public void setControladorReiniciable(Reinicio controlador) {
        this.controlador = controlador;
    }

    @FXML
    void continuar(ActionEvent event) {
        cerrarVentana(event);
        if (controlador != null) {
            controlador.reiniciarDesdeDialogo();
        }
    }

    @FXML
    void volverMenu(ActionEvent event) {
        cerrarVentana(event);
        if (controlador != null) {
            controlador.volverMenu();
        }
    }

    @FXML
    void cerrar(ActionEvent event) {
        if (controlador != null) {
            controlador.salirApp();
        }
        cerrarVentana(event);
    }

    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
