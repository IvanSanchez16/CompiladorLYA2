import Modelos.Programa;
import Scanner.Scanner;

public class Parser {

    public Scanner S;
    public Arbol.Programa Arbol;

    public Parser() {
        S = new Scanner("Codigo");
        Arbol = new Arbol.Programa();
        Programa p = new Programa(S,Arbol);
        boolean band = p.validarPrograma();
        System.out.println(band ? "Compilado correctamente" : "");
    }
}
