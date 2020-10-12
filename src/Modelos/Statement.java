package Modelos;

import Arbol.Asignacion;
import Arbol.If;
import Arbol.While;
import Arbol.Write;
import Scanner.Scanner;

public class Statement {

    private Scanner S;
    private Token token;
    private Expresion e;

    public Arbol.Programa Arbol;

    public Statement(Scanner s, Arbol.Programa arbol) {
        S = s;
        e = new Expresion(s, arbol);
        Arbol = arbol;
    }

    public Arbol.Statement validarStatement(){
        token = S.getToken();
        switch ( token.getToken() ){
            case "while":
                While state = new While();
                token = S.getToken();
                if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals("(") ){
                    state.setExp( e.validarExpresion() );
                    if ( state.getExp() != null ){
                        token = S.getToken();
                        if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals(")") ){
                            state.setSta( validarStatement() );
                            if ( state.getSta() != null )
                                return state;
                            return null;
                        }
                        System.out.println("Error en la línea "+token.getNumLinea()+" parentesis no cerrado");
                        return null;
                    }
                    return null;
                }
                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un ( despues de la palabra while");
                return null;
            case "if":
                If si = new If();
                token = S.getToken();
                if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals("(") ){
                    si.setExp(e.validarExpresion());
                    if ( si.getExp() != null ){
                        token = S.getToken();
                        if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals(")") ){
                            si.setSta(validarStatement());
                            if ( si.getSta() != null ){
                                token = S.getToken();
                                if ( token.getTipo() == Token.RESERVADA && token.getToken().equals("else") ) {
                                    si.setSta2(validarStatement());
                                    if (si.getSta2() != null)
                                        return si;
                                    return null;
                                }
                                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un else");
                                return null;
                            }
                            return null;
                        }
                        System.out.println("Error en la línea "+token.getNumLinea()+" parentesis no cerrado");
                        return null;
                    }
                    return null;
                }
                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un ( despues de la palabra if");
                return null;
            case "write":
                Write wr = new Write();
                token = S.getToken();
                if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals("(") ){
                    wr.setExp(e.validarExpresion());
                    if ( wr.getExp() != null ){
                        token = S.getToken();
                        if ( token.getTipo() == Token.DELIMITADOR && token.getToken().equals(")") ){
                            token = S.getToken();
                            if (token.getTipo() == Token.DELIMITADOR && token.getToken().equals(";"))
                                return wr;

                            System.out.println("Error en la línea "+token.getNumLinea()+" falta un ;");
                            return null;
                        }
                        System.out.println("Error en la línea "+token.getNumLinea()+" parentesis no cerrado");
                        return null;
                    }
                    return null;
                }
                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un ( despues de la palabra write");
                return null;
            default:
                Asignacion as = new Asignacion();
                if (token.getTipo() == Token.IDENTIFICADOR){
                    as.setPrimIdent(token);
                    token = S.getToken();
                    if ( token.getTipo() == Token.OPERADOR && token.getToken().equals("=") ){
                        as.setIgual(token);
                        as.setExp(e.validarExpresion());
                        if ( as.getExp() != null ){
                            token = S.getToken();
                            if (token.getTipo() == Token.DELIMITADOR && token.getToken().equals(";"))
                                return as;

                            System.out.println("Error en la línea "+token.getNumLinea()+" falta un ;");
                            return null;
                        }
                        return null;
                    }
                    System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un = en el statement");
                    return null;
                }
                System.out.println("Error en la línea "+token.getNumLinea()+" se esperaba un statement");
                return null;
        }
    }
}
