package Modelos;

public class Token {
    private String token;
    private int tipo;

    public static final int RESERVADA = 0;
    public static final int DELIMITADOR = 1;
    public static final int OPERADOR = 2;
    public static final int IDENTIFICADOR = 3;

    public Token(String token, int tipo) {
        this.token = token;
        this.tipo = tipo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return token+"\t"+tipo;
    }
}
