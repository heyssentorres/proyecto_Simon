package model;


import database.Resultado;

public class ResultadoConstructor extends Resultado {
    private String fecha;
    private String modo;
    private int rondaMaxima;
    private String resultado;

    public ResultadoConstructor(String fecha, String modo, int rondaMaxima, String resultado) {
        this.fecha = fecha;
        this.modo = modo;
        this.rondaMaxima = rondaMaxima;
        this.resultado = resultado;
    }

    public String getFecha() {
        return fecha;
    }

    public String getModo() {
        return modo;
    }

    public int getRondaMaxima() {
        return rondaMaxima;
    }

    public String getResultado() {
        return resultado;
    }
}
