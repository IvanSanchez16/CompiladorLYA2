package Modelos;

import Arbol.DefVar;
import Scanner.Scanner;

import java.util.ArrayList;

public class DeclaracionVar {

    private Scanner S;
    private Token token;

    public Arbol.Programa Arbol;

    public DeclaracionVar(Scanner s,Arbol.Programa arbol) {
        S = s;
        Arbol = arbol;
    }

    public boolean validarDeclaracion(){
        token = S.getToken();
        if ( token.getTipo() == Token.RESERVADA && esType() ){
            Arbol.addDefvariables(new DefVar());
            ArrayList<DefVar> df = Arbol.getVariables();
            DefVar aux = df.get(df.size()-1);
            aux.setTipo(token);

            token = S.getToken();
            if ( token.getTipo() == Token.IDENTIFICADOR ){
                aux.setNombre(token);

                token = S.getToken();
                if (token.getTipo() == Token.DELIMITADOR && token.getToken().equals(";"))
                    return true;
                System.out.println("Error en la línea "+ (token.getNumLinea()-1) +" falta un ;");
                return false;
            }
            System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un identificador");
            return false;
        }
        return false;
    }

    private boolean esType(){
        return token.getToken().equals("boolean") || token.getToken().equals("integer");
    }
}
