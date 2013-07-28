/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author klebber
 */
public class ConverSTRtoLRC {

    private static void leerArchivo(File file) throws IOException {
        FileReader fr = null;
        BufferedReader br = null;
        //Cadena de texto donde se guardara el contenido del archivo
        String contenido = "";
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String linea;
            //Obtenemos el contenido del archivo linea por linea
            while ((linea = br.readLine()) != null) {
                contenido += linea + "\n";
            }
        } //finally se utiliza para que si todo ocurre correctamente o si ocurre
        //algun error se cierre el archivo que anteriormente abrimos
        finally {
            br.close();

        }
        //Se imprime el contenido
        System.out.println(contenido);
    }

    public static void convertStrtoLRC(File inputStrFile) throws Exception {
         ConverSTRtoLRC.leerArchivo(inputStrFile);
    }
}