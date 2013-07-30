/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 * Common regular expresions
 */
public class ComRegEx {

    public static String BLANCK_LINES = "^(?:[\t ]*(?:\r?\n|\r))+";
    public static String FILE_EXT = "[.].+$";
    public static String FIRST_BLANCK_SP = "^\\s+";   //match first blanck spaces in each line 
}
