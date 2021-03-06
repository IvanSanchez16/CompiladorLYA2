package Modelos;

public class Token {
    private String token;
    private int tipo;
    private int numLinea;

    public static final int ERROR = -1;
    public static final int RESERVADA = 0;
    public static final int DELIMITADOR = 1;
    public static final int OPERADOR = 2;
    public static final int OPERADOR_ARITMETICO = 3;
    public static final int IDENTIFICADOR = 4;
    public static final int NUMERO = 5;
    public static final int BOLEANO = 6;
    public static final int CADENA = 7;

    public Token(String token, int tipo, int nl) {
        this.token = token;
        this.tipo = tipo;
        numLinea = nl;
    }

    public String getToken() {
        return token;
    }

    public int getTipo() {
        return tipo;
    }

    public int getNumLinea() { return numLinea; }

    @Override
    public String toString() {
        return token+"\t"+tipo;
    }
}
