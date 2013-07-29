/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author klebber
 */
public class FileFilterStr extends FileFilter {
    //Type of file that should be display in JFileChooser will be set here  
    //We choose to display only directory and text file 
    

    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(Config.Ext.SRT.getExt());
    }

    //Set description for the type of file that should be display  
    @Override
    public String getDescription() {
        return ".str files";
    }
}
