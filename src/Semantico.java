import Arbol.DefVar;
import Arbol.Expresion;
import Arbol.Programa;
import Arbol.Statement;
import Modelos.Simbolo;
import Modelos.Token;

import java.util.ArrayList;

public class Semantico {

    public static ArrayList<Simbolo> crearTabla(Programa arbol){
        ArrayList<DefVar> defVar = arbol.getVariables();
        ArrayList<Simbolo> tablaSimbolos = new ArrayList<>();
        Simbolo s;
        for (DefVar df:defVar) {
            s = new Simbolo( df.getNombre().getToken() , df.getTipo().getToken() );
            tablaSimbolos.add(s);
        }
        return tablaSimbolos;
    }

    public static boolean analisis(Programa arbol, ArrayList<Simbolo> tablaS){
        Statement s = arbol.getStatment();
        return analizarStatement(s,tablaS);
    }

    public static boolean analizarStatement(Statement stat, ArrayList<Simbolo> tablaS){
        Class<? extends Statement> clase = stat.getClass();
        String tipoClase = clase.getSimpleName();
        switch (tipoClase){
            case "If":
                String exp = analizarExpresion(stat.getExp(),tablaS);
                if (exp.equals("error"))
                    return false;
                if (!exp.equals("boolean")){
                    System.out.println("Error es necesario un valor booleano para la expresión del If");
                    return false;
                }
                return analizarStatement(stat.getSta(),tablaS) & analizarStatement(stat.getSta2(),tablaS);
            case "Asignacion":
                String tokenId = stat.getPrimIdent().getToken();
                String tipoId = "";
                for (Simbolo s:tablaS)
                    if (s.getIdentificador().equals(tokenId)) {
                        tipoId = s.getTipo();
                        break;
                    }
                if (tipoId.equals("")){
                    System.out.println("Error en la línea "+stat.getPrimIdent().getNumLinea()+" - "+tokenId+" no ha sido declarado");
                    return false;
                }
                String tipoExp = analizarExpresion(stat.getExp(),tablaS);
                if (!tipoId.equals(tipoExp)){
                    System.out.println("Error en la línea "+stat.getPrimIdent().getNumLinea()+" tipos no compatibles");
                    return false;
                }
                return true;
            case "While":
                String exp2 = analizarExpresion(stat.getExp(),tablaS);
                if (exp2.equals("error"))
                    return false;
                if (!exp2.equals("boolean")){
                    System.out.println("Error es necesario un valor booleano para la expresión del While");
                    return false;
                }
                return analizarStatement(stat.getSta(),tablaS);
            case "Write":
                return !analizarExpresion(stat.getExp(),tablaS).equals("error");
        }
        return false;
    }

    public static String analizarExpresion(Expresion exp, ArrayList<Simbolo> tablaS){
        Class<? extends Expresion> clase = exp.getClass();
        String tipoClase = clase.getSimpleName();
        switch (tipoClase){
            case "singleExp":
                Token t = exp.getToken();
                if (t.getTipo() == Token.BOLEANO)
                    return "boolean";
                if (t.getTipo() == Token.NUMERO)
                    return "integer";
                for (Simbolo s:tablaS)
                    if (t.getToken().equals(s.getIdentificador()))
                        return s.getTipo();
                 System.out.println("Error en la línea "+t.getNumLinea()+" - "+t.getToken()+" no ha sido declarado");
                 return "error";
            case "Operacion":
                break;
        }
        return "";
    }
}
