package Modelos;

import Scanner.Scanner;

public class Programa {

    private Scanner S;
    private Token token;
    private DeclaracionVar dv;
    private Statement s;

    public Programa(Scanner s) {
        S = s;
        dv = new DeclaracionVar(s);
        this.s = new Statement(s);
    }

    public boolean validarPrograma(){
        token = S.getToken();
        if ( token.getTipo() == Token.RESERVADA && token.getToken().equals("class") ){
            token = S.getToken();
            if ( token.getTipo() == Token.IDENTIFICADOR ){
                int apuntaux = S.apuntador;
                while( dv.validarDeclaracion() )
                    apuntaux = S.apuntador;
                S.apuntador = apuntaux;
                token = S.getToken();
                if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals("{") ){
                    if ( s.validarStatement() ){
                        token = S.getToken();
                        return token.getTipo() == Token.DELIMITADOR && token.getToken().equals("}");
                    }
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
