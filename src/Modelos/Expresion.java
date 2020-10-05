package Modelos;

import Scanner.Scanner;

import java.util.ArrayList;

public class Expresion {

    private Scanner S;
    private Token token;

    public Expresion(Scanner s) {
        S = s;
    }

    public boolean validarExpresion(){
        ArrayList<Token> tokens = new ArrayList<>();
        token = S.getToken();
        int cont = 0;
        while ( token.getTipo() != Token.DELIMITADOR && cont<3 ){
            tokens.add(token);
            cont++;
            token = S.getToken();
        }
        S.apuntador -= 1;
        if ( tokens.get(0).getTipo() == Token.NUMERO || tokens.get(0).getTipo() == Token.BOLEANO || tokens.get(0).getTipo() == Token.CADENA ){
            S.apuntador -= tokens.size()-1;
            return true;
        }
        if ( tokens.size() == 1 && tokens.get(0).getTipo() == Token.IDENTIFICADOR)
            return true;
        if ( tokens.size() == 3 ){
            token = S.getToken();
            if ( token.getTipo() != Token.DELIMITADOR ){
                S.apuntador -= 1;
                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un delimitador");
                return false;
            }
            S.apuntador -= 1;
            if ( tokens.get(0).getTipo() == Token.IDENTIFICADOR ){
                if ( tokens.get(1).getTipo() == Token.OPERADOR || tokens.get(1).getTipo() == Token.OPERADOR_ARITMETICO ){
                    if (tokens.get(0).getTipo() == Token.IDENTIFICADOR)
                        return true;
                    System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un identificador");
                    return false;
                }
                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un operador");
                return false;
            }
            System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba una expresión válida");
            return false;
        }
        System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba una expresión válida");
        return false;
    }
}
