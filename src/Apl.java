import Modelos.Token;
import Scanner.Scanner;

import java.util.ArrayList;

public class Apl {

    public static void main(String[] args){
        Scanner S = new Scanner("Codigo");
        ArrayList<Token> tokens = new ArrayList<>();
        Token nextToken;
        do{
            nextToken = S.getToken();
            tokens.add(nextToken);
        }while (nextToken != null);

        for (int i = 0; i < tokens.size(); i++) {
            System.out.println(tokens.get(i));
        }
    }
}