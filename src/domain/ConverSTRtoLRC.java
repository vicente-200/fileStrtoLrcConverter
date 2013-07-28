/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author klebber
 */
public class ConverSTRtoLRC {

    private static void leerArchivo(File file) throws IOException {
        FileReader fr = null;
        BufferedReader br = null;
        //Cadena de texto donde se guardara el contenido del archivo
        String fileString = "";
        try {


            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String linea;
            //skip first line
            br.readLine();
            //Obtenemos el contenido del archivo linea por linea
            while ((linea = br.readLine()) != null) {
                String pattern;
                //removes first blanck spaces in each line
                pattern = "^\\s+";
                Pattern regex = Pattern.compile(pattern);
                Matcher matcher = regex.matcher(linea);
                linea = matcher.replaceAll("");

    
                /*
                 * eg:
                 * converts  "--> 00:01:02,570" 
                 * into  "00:01:02,570" 
                 * 
                 */

                pattern = "-{1,}>\\s+";
                regex = Pattern.compile(pattern);
                matcher = regex.matcher(linea);
                linea = matcher.replaceAll("");

                /*
                 * eg:
                 * converts  "00:01:02,570" 
                 * into  "01:02,570"  
                 * 
                 */
                pattern = "(^[0-9]{1,}:)";
                regex = Pattern.compile(pattern);
                matcher = regex.matcher(linea);
                linea = matcher.replaceAll("");
                /*
                 * eg:
                 * converts  "00:01:02,570" 
                 * into  "01:02.570"  
                 * 
                 */
                pattern = "([0-9]{1,}:[0-9]{1,})[.|,]([0-9]{1,})";
                regex = Pattern.compile(pattern);
                matcher = regex.matcher(linea);
                linea = matcher.replaceAll("[$1.$2]");



                //remove numeration lines
                pattern = "^[0-9]+$";
                regex = Pattern.compile(pattern);
                matcher = regex.matcher(linea);
                linea = matcher.replaceAll("");

                //removes the second timmestamp
                pattern = "^(\\[.+\\]).+";
                regex = Pattern.compile(pattern);
                matcher = regex.matcher(linea);
                linea = matcher.replaceFirst("$1");
// 

 
                pattern = "\\[[0-9]{1,}:[0-9]{1,}[.|,][0-9]{1,}\\]";
                regex = Pattern.compile(pattern);
                matcher = regex.matcher(linea);
                if (!matcher.matches()) {
                    linea += "\n";
                }
 

                //remove blanck lines
                pattern = "^(?:[\t ]*(?:\r?\n|\r))+";
                regex = Pattern.compile(pattern);
                matcher = regex.matcher(linea);
                linea = matcher.replaceAll("");

                fileString += linea;
            }
        } //finally se utiliza para que si todo ocurre correctamente o si ocurre
        //algun error se cierre el archivo que anteriormente abrimos
        finally {
            br.close();

        }

        //TEST
        //Se imprime el contenido
        System.out.println("FILE RESULT--------------------\n--------------------");
        System.out.println(fileString);
    }

    public static void convertStrtoLRC(File inputStrFile) throws Exception {
        ConverSTRtoLRC.leerArchivo(inputStrFile);
    }
}