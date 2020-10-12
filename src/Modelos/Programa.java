package Modelos;

import Arbol.Asignacion;
import Scanner.Scanner;
public class Programa {

    private Scanner S;
    private Token token;
    private DeclaracionVar dv;
    private Statement s;

    public Arbol.Programa Arbol;

    public Programa(Scanner s, Arbol.Programa Arbol) {
        S = s;
        dv = new DeclaracionVar(s, Arbol);
        this.s = new Statement(s, Arbol);
        this.Arbol = Arbol;
    }

    public boolean validarPrograma(){
        token = S.getToken();
        if ( token.getTipo() == Token.RESERVADA && token.getToken().equals("class") ){
            Arbol.setClase(token);
            token = S.getToken();
            if ( token.getTipo() == Token.IDENTIFICADOR ){
                Arbol.setNombreClase(token);
                int apuntaux = S.apuntador;
                while( dv.validarDeclaracion() )
                    apuntaux = S.apuntador;
                S.apuntador = apuntaux;
                token = S.getToken();
                if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals("{") ){
                    Arbol.setStatement(s.validarStatement());
                    if ( Arbol.getStatment() != null ){
                        token = S.getToken();
                        return token.getTipo() == Token.DELIMITADOR && token.getToken().equals("}");
                    }
                    return false;
                }
                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba una llave que abre ¨{¨ ");
                return false;
            }
            System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un identificador despues del class");
            return false;
        }
        System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba la palabra class");
        return false;
    }
}
