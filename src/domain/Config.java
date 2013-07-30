/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 * Main aplication configuration
 *
 * @author vvazquez
 */
public class Config {

    /**
     * File extensions
     */
    public static class Ext {

        public static String LRC = ".lrc", SRT = ".srt";
    }
    public static class Files{
        public static String FileSeparator = System.getProperty("file.separator");
    }
}
