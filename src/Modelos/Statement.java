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
                    }
                }
                break;
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
                            }
                        }
                    }
                }
                break;
            case "write":
                token = S.getToken();
                if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals("(") ){
                    if ( e.validarExpresion() ){
                        token = S.getToken();
                        if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals(")") ){
                            token = S.getToken();
                            return token.getTipo() == Token.DELIMITADOR && token.getToken().equals(";");
                        }
                    }
                }
                break;
            default:
                if (token.getTipo() == Token.IDENTIFICADOR){
                    token = S.getToken();
                    if ( token.getTipo() == Token.OPERADOR && token.getToken().equals("=") ){
                        if ( e.validarExpresion() ){
                            token = S.getToken();
                            return token.getTipo() == Token.DELIMITADOR && token.getToken().equals(";");
                        }
                    }
                }
        }
        return false;
    }
}
