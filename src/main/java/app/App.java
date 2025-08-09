package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/dialogos/app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 377, 323);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        database.Inicializador.inicializar();
        launch();
    }
}