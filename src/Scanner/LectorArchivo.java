package Scanner;

import java.io.*;

public class LectorArchivo {
    private File arch;
    private FileReader fr;
    private BufferedReader br;

    public LectorArchivo(String nom){
        try {
            arch=new File(nom+".txt");
            fr = new FileReader(arch);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void reiniciarApuntador() throws IOException {
        fr = new FileReader(arch);
        br = new BufferedReader(fr);
    }

    public String leerSigLinea() throws IOException {
        String linea=br.readLine();
        if(linea==null)
            return null;
        return linea;
    }
}
