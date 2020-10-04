package Modelos;

import Scanner.Scanner;

public class DeclaracionVar {

    private Scanner S;
    private Token token;

    public DeclaracionVar(Scanner s) {
        S = s;
    }

    public boolean validarDeclaracion(){
        token = S.getToken();
        if ( token.getTipo() == Token.RESERVADA && esType() ){
            token = S.getToken();
            if ( token.getTipo() == Token.IDENTIFICADOR ){
                token = S.getToken();
                return token.getTipo() == Token.DELIMITADOR && token.getToken().equals(";");
            }
            return false;
        }
        return false;
    }

    private boolean esType(){
        return token.getToken().equals("boolean") || token.getToken().equals("integer");
    }
}
