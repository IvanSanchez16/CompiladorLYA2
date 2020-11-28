package CodIntermedio;
import Arbol.Expresion;
import Arbol.Programa;
import Arbol.Statement;

import java.util.ArrayList;

public class Generador {

    static ArrayList<Cuadruple> Cuadruples;
    static int contT = 0;

    public static ArrayList<Cuadruple> generarCod(Programa arbol){
        Cuadruples = new ArrayList<>();
        generarStat(arbol.getStatment());
        Cuadruples.add( new Cuadruple("End","","","") );
        return Cuadruples;
    }

    public static void generarStat(Statement stat){
        Class<? extends Statement> clase = stat.getClass();
        String tipoClase = clase.getSimpleName();
        switch (tipoClase){
            case "If":
                String op = "";
                String arg = generarExp(stat.getExp());
                if (!arg.startsWith("t"))
                    op = "Jz";
                int apuntador = Cuadruples.size();
                Cuadruples.add( new Cuadruple(op,arg,"","") );
                generarStat(stat.getSta());
                Cuadruples.add( new Cuadruple("Jp","","","") );
                Cuadruples.get(apuntador).setResultado( (Cuadruples.size()+1)+"" );
                apuntador = Cuadruples.size()-1;
                generarStat(stat.getSta2());
                Cuadruples.get(apuntador).setResultado( (Cuadruples.size()+1)+"" );
                return;
            case "Asignacion":
                String op2 = "=";
                String arg21 = generarExp(stat.getExp());
                String re = stat.getPrimIdent().getToken();
                Cuadruples.add( new Cuadruple(op2,arg21,"",re) );
                return;
            case "While":
                String op3 = "";
                String arg31 = generarExp(stat.getExp());
                if (!arg31.startsWith("t"))
                    op3 = "Jz";
                int apuntador2 = Cuadruples.size();
                Cuadruples.add( new Cuadruple(op3,arg31,"","") );
                generarStat(stat.getSta());
                Cuadruples.add( new Cuadruple("Jp","","",(apuntador2+1)+"") );
                Cuadruples.get(apuntador2).setResultado( (Cuadruples.size()+1)+"" );
                return;
            case "Write":
                String op4 = "Write";
                String re4 = generarExp(stat.getExp());
                Cuadruples.add( new Cuadruple(op4,"","",re4) );
        }
    }

    public static String generarExp(Expresion exp){
        Class<? extends Expresion> clase = exp.getClass();
        String tipoClase = clase.getSimpleName();
        switch (tipoClase){
            case "singleExp":
                return exp.getToken().getToken();
            case "Operacion":
                String op = exp.getOperador().getToken();
                String arg1 = exp.getPrimerIdent().getToken();
                String arg2 = exp.getSegundoIdent().getToken();
                String res = "t"+(++contT);
                Cuadruples.add( new Cuadruple(op,arg1,arg2,res) );
                return res;
        }
        return "";
    }

}
