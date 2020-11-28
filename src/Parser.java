import Modelos.Programa;
import Modelos.Simbolo;
import Scanner.Scanner;

import java.util.ArrayList;

public class Parser {

    public Scanner S;
    public Arbol.Programa Arbol;

    public Parser() {
        S = new Scanner("Codigo");
        Arbol = new Arbol.Programa();
    }

    public Arbol.Programa parser(){
        Programa p = new Programa(S,Arbol);
        boolean band = p.validarPrograma();
        if (band)
            return Arbol;
        return null;
    }
}
