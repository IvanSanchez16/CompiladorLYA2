package Scanner;

import Modelos.Token;

import javax.swing.*;
import java.io.IOException;

public class Scanner {

    private LectorArchivo lector;
    private String lineaAct;
    private boolean delBand, opBand;
    private final char[] delimitadores = {
            '(',
            ')',
            ';',
            '{',
            '}'
    };
    private final String[] operadores = {
            "=",
            "==",
            "<",
            ">",
            ">=",
            "<=",
            "!",
            "!="
    };
    private final String[] palabrasReservadas = {
            "class",
            "boolean",
            "integer",
            "while",
            "if",
            "true",
            "false",
            "else"
    };

    public Scanner(String nombreArch) {
        delBand = false;
        lector = new LectorArchivo(nombreArch);
        try {
            lineaAct = lector.leerSigLinea();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"El archivo está vacío","Compilado terminado",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public Token getToken(){
        lineaAct = lineaAct.trim();
        //Valido que no se haya terminado la linea
        if ( lineaAct.equals("") ){
            try {
                lineaAct = lector.leerSigLinea();
            } catch (IOException e) {
                return null;
            }
        }
        //EOF
        if (lineaAct == null)
            return null;
        //Construyo el siguiente token
        String token = "";
        //Valido que anteriormente no se haya encontrado un delimitador
        if (delBand){
            token = lineaAct.charAt(0)+"";
            lineaAct = lineaAct.substring(1);
            delBand = false;
            return new Token(token, Token.DELIMITADOR);
        }
        char aux;
        //Valido que anteriormente no se haya encontrado un operador
        if (opBand){
            aux = lineaAct.charAt(0);
            char aux2 = lineaAct.charAt(1);
            if ( esOperador(aux2) ){
                token = aux + "" + aux2;
                lineaAct = lineaAct.substring(2);
                return new Token(token, Token.OPERADOR);
            }
            token = lineaAct.charAt(0) + "";
            lineaAct = lineaAct.substring(1);
            return new Token(token, Token.OPERADOR);
        }
        int i;
        for (i = 0; i < lineaAct.length(); i++) {
            aux = lineaAct.charAt(i);
            if ( esCaracterEspecial(aux) ) { //No cumple A-Z a-z 0-9
                if ( esDelimitador(aux) ) {
                    if (i == 0) {
                        token = lineaAct.charAt(0) + "";
                        lineaAct = lineaAct.substring(1);
                        return new Token(token, Token.DELIMITADOR);
                    }
                    delBand = true;
                    lineaAct = lineaAct.substring(i);
                    return new Token(token, comprobarTipo(token));
                }
                if (aux == ' ') {
                    lineaAct = lineaAct.substring(i + 1);
                    return new Token(token, comprobarTipo(token));
                }
                if (aux == 9){ //\t
                    lineaAct = lineaAct.substring(1);
                    i--;
                    continue;
                }
                if ( esOperador(aux) ){
                    if (i == 0) {
                        char aux2 = lineaAct.charAt(1);
                        if ( esOperador(aux2) ){
                            token = aux + "" + aux2;
                            lineaAct = lineaAct.substring(2);
                            return new Token(token, Token.OPERADOR);
                        }
                        token = lineaAct.charAt(0) + "";
                        lineaAct = lineaAct.substring(1);
                        return new Token(token, Token.OPERADOR);
                    }
                    opBand = true;
                    lineaAct = lineaAct.substring(i);
                    return new Token(token, comprobarTipo(token));
                }
                //Caracter no identificado
            }
            token += aux;
        }
        //Fin de la linea
        lineaAct = "";
        return new Token(token, comprobarTipo(token));
    }
    private boolean esCaracterEspecial(char c){
        int code = c;
        return !((code>47 && code<58) || ( code>96  && code<123) || (code>64 && code<91));
    }

    private int comprobarTipo(String token){
        if( esReservada(token) )
            return Token.RESERVADA;
        return Token.IDENTIFICADOR;
    }

    private boolean esDelimitador(char caracter){
        for (char delimitador : delimitadores)
            if (caracter == delimitador)
                return true;
        return false;
    }
    private boolean esReservada(String token){
        for (String palabra : palabrasReservadas)
            if (palabra.equals( token ))
                return true;
        return false;
    }

    private boolean esOperador(char op){
        return op == 33 || (op>59 && op<63);
    }
}
