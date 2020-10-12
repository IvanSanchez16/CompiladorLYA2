package Modelos;

import Arbol.Asignacion;
import Arbol.Operacion;
import Arbol.singleExp;
import Scanner.Scanner;

import java.util.ArrayList;

public class Expresion {

    private Scanner S;
    private Token token;

    public Arbol.Programa Arbol;

    public Expresion(Scanner s, Arbol.Programa arbol) {
        Arbol = arbol;
        S = s;
    }

    public Arbol.Expresion validarExpresion(){
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
            singleExp exp = new singleExp();
            exp.setToken(tokens.get(0));
            S.apuntador -= tokens.size()-1;
            return exp;
        }
        if ( tokens.size() == 1 && tokens.get(0).getTipo() == Token.IDENTIFICADOR){
            singleExp exp = new singleExp();
            exp.setToken(tokens.get(0));
            return exp;
        }
        if ( tokens.size() == 3 ){
            Operacion op = new Operacion();
            token = S.getToken();
            if ( token.getTipo() != Token.DELIMITADOR ){
                S.apuntador -= 1;
                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un delimitador");
                return null;
            }
            S.apuntador -= 1;
            if ( tokens.get(0).getTipo() == Token.IDENTIFICADOR ){
                op.setPrimerIdent(tokens.get(0));
                if ( tokens.get(1).getTipo() == Token.OPERADOR || tokens.get(1).getTipo() == Token.OPERADOR_ARITMETICO ){
                    op.setOperador(tokens.get(1));
                    if (tokens.get(0).getTipo() == Token.IDENTIFICADOR) {
                        op.setSegundoIdent(tokens.get(2));
                        return op;
                    }
                    System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un identificador");
                    return null;
                }
                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un operador");
                return null;
            }
            System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba una expresión válida");
            return null;
        }
        System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba una expresión válida");
        return null;
    }
}
