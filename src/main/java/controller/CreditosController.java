package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;



public class CreditosController {

    @FXML
    private VBox panel;

    public void volverMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dialogos/app.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) panel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
