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
                linea = ConverSTRtoLRC.replaceWithRegEx("^\\s+", linea, "");

                /*
                 * eg:
                 * converts  "--> 00:01:02,570" 
                 * into  "00:01:02,570" 
                 * 
                 */
                linea = ConverSTRtoLRC.replaceWithRegEx("-{1,}>\\s+", linea, "");

                /*
                 * eg:
                 * converts  "00:01:02,570" 
                 * into  "01:02,570"  
                 * 
                 */
                pattern = "(^[0-9]{1,}:)";
                linea = ConverSTRtoLRC.replaceWithRegEx("(^[0-9]{1,}:)", linea, "");
                /*
                 * eg:
                 * converts  "00:01:02,570" 
                 * into  "01:02.570"  
                 * 
                 */
                pattern = "([0-9]{1,}:[0-9]{1,})[.|,]([0-9]{1,})";
                linea = ConverSTRtoLRC.replaceWithRegEx("([0-9]{1,}:[0-9]{1,})[.|,]([0-9]{1,})", linea, "[$1.$2]");


                //remove numeration lines
                pattern = "^[0-9]+$";
                linea = ConverSTRtoLRC.replaceWithRegEx("^[0-9]+$", linea, "");

                //removes the second timmestamp
                pattern = "^(\\[.+\\]).+";
                linea = ConverSTRtoLRC.replaceWithRegEx("^(\\[.+\\]).+", linea, "$1");

                //puts the timing line with its phrase in the same line
                pattern = "\\[[0-9]{1,}:[0-9]{1,}[.|,][0-9]{1,}\\]";
                Pattern regex = Pattern.compile(pattern);
                Matcher matcher = regex.matcher(linea);
                if (!matcher.matches()) {
                    linea += "\n";
                }


                //remove blanck lines
                pattern = "^(?:[\t ]*(?:\r?\n|\r))+";
                linea = ConverSTRtoLRC.replaceWithRegEx("^(?:[\t ]*(?:\r?\n|\r))+", linea, "");

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

    /**
     * replaces text from a input string using a regular expresion usage
     * exemple:
     * ConverSTRtoLRC.replaceWithRegEx("([0-9]{1,}:[0-9]{1,})[.|,]([0-9]{1,})",
     * linea, "[$1.$2]"); 
     * converts "00:01:02,570" 
     * into "01:02.570"     
     * @param pattern
     * @param input
     * @param replaceText
     * @return
     */
    private static String replaceWithRegEx(String pattern, String input, String replaceText) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        return matcher.replaceAll(replaceText);
    }

    public static void convertStrtoLRC(File inputStrFile) throws Exception {
        ConverSTRtoLRC.leerArchivo(inputStrFile);
    }
}