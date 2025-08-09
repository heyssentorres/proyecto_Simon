package controller;

import database.Resultado;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ResultadoConstructor;

public class HistorialController {

    @FXML
    private TableView<Resultado> tablaResultados;

    @FXML
    private TableColumn<Resultado, String> colFecha;

    @FXML
    private TableColumn<Resultado, String> colModo;

    @FXML
    private TableColumn<Resultado, Integer> colRondas;

    @FXML
    private TableColumn<Resultado, String> colResultado;

    @FXML
    public void initialize() {
        database.Inicializador.inicializar();

        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colModo.setCellValueFactory(new PropertyValueFactory<>("modo"));
        colRondas.setCellValueFactory(new PropertyValueFactory<>("rondaMaxima"));
        colResultado.setCellValueFactory(new PropertyValueFactory<>("resultado"));

        tablaResultados.setItems(obtenerResultados());
    }

    private ObservableList<Resultado> obtenerResultados() {
        ObservableList<Resultado> lista = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:datos_juego.db");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT fecha, modo, ronda_maxima, resultado FROM resultados ORDER BY fecha DESC"))
        {

            while (rs.next()) {
                ResultadoConstructor res = new ResultadoConstructor(
                        rs.getString("fecha"),
                        rs.getString("modo"),
                        rs.getInt("ronda_maxima"),
                        rs.getString("resultado")
                );
                lista.add(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
