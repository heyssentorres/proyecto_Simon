package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Resultado {
    public static void guardarResultado(String modo, int rondaMaxima, String resultado) {
        String sql = "INSERT INTO resultados (modo, ronda_maxima, resultado) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBaseDeDatos.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, modo);
            stmt.setInt(2, rondaMaxima);
            stmt.setString(3, resultado);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
