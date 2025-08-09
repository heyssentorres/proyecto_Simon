package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Inicializador {
    public static void inicializar() {
        String crearTablaResultados = """
            CREATE TABLE IF NOT EXISTS resultados (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                modo TEXT,
                ronda_maxima INTEGER,
                resultado TEXT,
                fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """;

        try (Connection conn = ConexionBaseDeDatos.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(crearTablaResultados);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

