package Modelos;

import Scanner.Scanner;

public class Statement {

    private Scanner S;
    private Token token;
    private Expresion e;

    public Statement(Scanner s) {
        S = s;
        e = new Expresion(s);
    }

    public boolean validarStatement(){
        token = S.getToken();
        switch ( token.getToken() ){
            case "while":
                token = S.getToken();
                if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals("(") ){
                    if ( e.validarExpresion() ){
                        token = S.getToken();
                        if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals(")") ){
                            return validarStatement();
                        }
                        System.out.println("Error en la línea "+token.getNumLinea()+" parentesis no cerrado");
                        return false;
                    }
                    return false;
                }
                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un ( despues de la palabra while");
                return false;
            case "if":
                token = S.getToken();
                if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals("(") ){
                    if ( e.validarExpresion() ){
                        token = S.getToken();
                        if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals(")") ){
                            if ( validarStatement() ){
                                token = S.getToken();
                                if ( token.getTipo() == Token.RESERVADA && token.getToken().equals("else") )
                                    return validarStatement();
                                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un else");
                                return false;
                            }
                            return false;
                        }
                        System.out.println("Error en la línea "+token.getNumLinea()+" parentesis no cerrado");
                        return false;
                    }
                    return false;
                }
                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un ( despues de la palabra if");
                return false;
            case "write":
                token = S.getToken();
                if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals("(") ){
                    if ( e.validarExpresion() ){
                        token = S.getToken();
                        if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals(")") ){
                            token = S.getToken();
                            if (token.getTipo() == Token.DELIMITADOR && token.getToken().equals(";"))
                                return true;
                            System.out.println("Error en la línea "+token.getNumLinea()+" falta un ;");
                            return false;
                        }
                        System.out.println("Error en la línea "+token.getNumLinea()+" parentesis no cerrado");
                        return false;
                    }
                    return false;
                }
                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un ( despues de la palabra write");
                return false;
            default:
                if (token.getTipo() == Token.IDENTIFICADOR){
                    token = S.getToken();
                    if ( token.getTipo() == Token.OPERADOR && token.getToken().equals("=") ){
                        if ( e.validarExpresion() ){
                            token = S.getToken();
                            if (token.getTipo() == Token.DELIMITADOR && token.getToken().equals(";"))
                                return true;
                            System.out.println("Error en la línea "+token.getNumLinea()+" falta un ;");
                            return false;
                        }
                        return false;
                    }
                    System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un = en el statement");
                    return false;
                }
                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un statement");
                return false;
        }
    }
}
