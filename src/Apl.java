import Arbol.Programa;
import CodIntermedio.Cuadruple;
import CodIntermedio.Generador;
import Modelos.Simbolo;
import java.util.ArrayList;

public class Apl {

    public static ArrayList<Simbolo> tablaSimbolos;
    public static ArrayList<Cuadruple> cuadruples;
    public static Programa Arbol;

    public static void main(String[] args){
        boolean band = true;
        //Scanner y parser - parte 1
        Parser p = new Parser();
        Arbol = p.parser();
        band = Arbol != null;
        //Analisis Semantico y cod intermedio - parte 2
        if (band){
            tablaSimbolos = Semantico.crearTabla(Arbol);
            band = Semantico.analisis(Arbol, tablaSimbolos);
            //Generacion de codigo intermedio
            if (band){
                cuadruples = Generador.generarCod(Arbol);
                System.out.println("Compilado correctamente");
            }
        }
    }
}