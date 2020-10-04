import Modelos.Programa;
import Scanner.Scanner;

public class Parser {

    public Scanner S;

    public Parser() {
        S = new Scanner("Codigo");
        Programa p = new Programa(S);
        boolean band = p.validarPrograma();
        System.out.println(band);
    }
}
