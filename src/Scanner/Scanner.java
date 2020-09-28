package Scanner;

import Modelos.Token;

import javax.swing.*;
import java.io.IOException;

public class Scanner {

    private LectorArchivo lector;
    private int numLinea;
    private String lineaAct;
    private boolean delBand, opBand;
    private final char[] delimitadores = {
            '(',
            ')',
            ';',
            '{',
            '}'
    };
    private final char[] opAritmeticos = {
            '+',
            '-',
            '*',
            '/'
    };
    private final String[] palabrasReservadas = {
            "class",
            "boolean",
            "integer",
            "while",
            "if",
            "true",
            "false",
            "else",
            "write"
    };

    public Scanner(String nombreArch) {
        delBand = false;
        opBand = false;
        numLinea = 1;
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
                numLinea++;
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
        char caracter;
        //Valido que anteriormente no se haya encontrado un operador
        if (opBand){
            caracter = lineaAct.charAt(0);
            char aux2 = lineaAct.charAt(1);
            if ( esOperador(aux2) ){
                token = caracter + "" + aux2;
                lineaAct = lineaAct.substring(2);
                opBand = false;
                return new Token(token, Token.OPERADOR);
            }
            token = lineaAct.charAt(0) + "";
            lineaAct = lineaAct.substring(1);
            opBand = false;
            return new Token(token, Token.OPERADOR);
        }
        int i;
        for (i = 0; i < lineaAct.length(); i++) {
            caracter = lineaAct.charAt(i);
            if ( esCaracterEspecial(caracter) ) { //No cumple A-Z a-z 0-9
                if (esDelimitador(caracter)) {
                    if (i == 0) {
                        token = lineaAct.charAt(0) + "";
                        lineaAct = lineaAct.substring(1);
                        return new Token(token, Token.DELIMITADOR);
                    }
                    delBand = true;
                    lineaAct = lineaAct.substring(i);
                    return new Token(token, comprobarTipo(token));
                }
                if (caracter == ' ') {
                    lineaAct = lineaAct.substring(i + 1);
                    return new Token(token, comprobarTipo(token));
                }
                if (caracter == 9) { //\t
                    lineaAct = lineaAct.substring(1);
                    i--;
                    continue;
                }
                if (esOperador(caracter)) {
                    if (i == 0) {
                        char aux2 = lineaAct.charAt(1);
                        if (esOperador(aux2)) {
                            token = caracter + "" + aux2;
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
                if ( esOpAritmetico(caracter) ){
                    token = lineaAct.charAt(0) + "";
                    lineaAct = lineaAct.substring(i + 1);
                    return new Token(token, Token.OPERADOR_ARITMETICO);
                }
                if (caracter == 34){ // "
                    int primerComilla = i;
                    if (i == lineaAct.length()-1 ){ //Faltaba una comilla inicial
                        System.out.println("Error en la linea "+numLinea+" faltó definir la primera comilla");
                        continue;
                    }
                    do {
                        i++;
                        caracter = lineaAct.charAt(i);
                    }while( caracter != 34 && i < lineaAct.length()-1);
                    if (i == lineaAct.length()-1 ){ //Faltaba una comilla final
                        System.out.println("Error en la linea "+numLinea+" faltó definir la segunda comilla");
                        return null;
                    }
                    token = lineaAct.substring(primerComilla, i+1);
                    lineaAct = lineaAct.substring( i == lineaAct.length()-1 ? i : i+1);
                    return new Token(token, Token.CADENA);
                }
                //Caracter no identificado
                System.out.println("Error en la linea "+numLinea+" se encontró un carácter inválido");
                continue;
            }
            token += caracter;
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
        if ( token.equals("true") || token.equals("false"))
            return Token.BOLEANO;
        
        try {
            Integer.parseInt(token);
        } catch (NumberFormatException e) {
            char primerCaracter = token.charAt(0);
            if (( primerCaracter>96  && primerCaracter<123) || (primerCaracter>64 && primerCaracter<91) )
                return Token.IDENTIFICADOR;
            //Error
        }
        return Token.NUMERO;
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

    private boolean esOpAritmetico(char op){
        for (char operador : opAritmeticos)
            if (operador == op)
                return true;
        return false;
    }
}
