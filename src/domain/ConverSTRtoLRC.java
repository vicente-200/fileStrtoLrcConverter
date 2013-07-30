/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import gui.MainGui;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class ConverSTRtoLRC {

    public static MainGui mainGui;

    private static String leerArchivo(File file) throws IOException {
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
                linea = ConverSTRtoLRC.replaceWithRegEx(ComRegEx.FIRST_BLANCK_SP, linea, "");

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
//                pattern = "^(?:[\t ]*(?:\r?\n|\r))+";
//                pattern = ComRegEx.BLANCK_LINES;
                linea = ConverSTRtoLRC.replaceWithRegEx(ComRegEx.BLANCK_LINES, linea, "");

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
        return fileString;
    }

    /**
     * replaces text from a input string using a regular expresion usage
     * exemple:
     * ConverSTRtoLRC.replaceWithRegEx("([0-9]{1,}:[0-9]{1,})[.|,]([0-9]{1,})",
     * linea, "[$1.$2]"); converts "00:01:02,570" into "01:02.570"
     *
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
        if (!inputStrFile.exists() || !inputStrFile.canRead()) {
            throw new Exception("The file is not accesible.");
        }
        String input = ConverSTRtoLRC.leerArchivo(inputStrFile);
        System.out.println(input);
        String pattern = ComRegEx.FILE_EXT;
        System.out.println("pattern =" + pattern);
        System.out.println("fileName=" + inputStrFile.getName());
        String newName = inputStrFile.getName().replaceAll(pattern, Config.Ext.LRC);
        System.out.println("newfilename = " + newName);
        File outputFile = new File(inputStrFile.getParent() + Config.Files.FileSeparator + newName);

        Integer codConfirm = null;
        if (outputFile.exists()) {
            codConfirm = ConverSTRtoLRC.mainGui.askPane("Already exists a file with the same name\nDo you want to overwrite it?", "Info");
        }
        System.out.println("!!codConfirm =" + codConfirm);
        if (codConfirm == null || codConfirm ==MainGui.CONFIRM_YES) {
            ConverSTRtoLRC.writeFile(input, outputFile);
        } else {
            throw new Exception("Has cancelado la operación");
        }
        System.out.println("writing into" + outputFile.getAbsolutePath());



    }

    public static void writeFile(String content, File outputFile) throws IOException, Exception {
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter(outputFile);
            printWriter = new PrintWriter(fileWriter);
            printWriter.println(content);
        } finally {

            if (null != fileWriter) {
                fileWriter.close();
            }
        }

        System.out.println("the file has been created");

    }

    public MainGui getMainGui() {
        return mainGui;
    }

    public void setMainGui(MainGui mainGui) {
        this.mainGui = mainGui;
    }
}