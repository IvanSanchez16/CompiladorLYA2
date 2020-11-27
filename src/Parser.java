import Modelos.Programa;
import Modelos.Simbolo;
import Scanner.Scanner;

import java.util.ArrayList;

public class Parser {

    public Scanner S;
    public Arbol.Programa Arbol;
    public ArrayList<Simbolo> tablaSimbolos;

    public Parser() {
        S = new Scanner("Codigo");
        Arbol = new Arbol.Programa();
        //Scanner y parser - parte 1
        Programa p = new Programa(S,Arbol);
        boolean band = p.validarPrograma();
        //Analisis Semantico y cod intermedio - parte 2
        if (band){
            tablaSimbolos = Semantico.crearTabla(Arbol);
            band = Semantico.analisis(Arbol, tablaSimbolos);
            System.out.println(band ? "Compilado correctamente" : "");
        }
    }
}
