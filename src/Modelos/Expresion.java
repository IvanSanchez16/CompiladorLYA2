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
        while ( token.getTipo() != Token.DELIMITADOR ){
            tokens.add(token);
            token = S.getToken();
        }
        S.apuntador -= 1;
        if ( tokens.size() == 1 && (tokens.get(0).getTipo() == Token.NUMERO || tokens.get(0).getTipo() == Token.BOLEANO || tokens.get(0).getTipo() == Token.IDENTIFICADOR) )
            return true;
        if ( tokens.size() == 3 ){
            if ( tokens.get(0).getTipo() == Token.IDENTIFICADOR ){
                if ( tokens.get(1).getTipo() == Token.OPERADOR || tokens.get(1).getTipo() == Token.OPERADOR_ARITMETICO ){
                    return tokens.get(0).getTipo() == Token.IDENTIFICADOR;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
