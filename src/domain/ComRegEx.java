/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 * Common regular expresions
 */
public enum ComRegEx {

    BLANCK_LINES("^(?:[\t ]*(?:\r?\n|\r))+"), SRT(".lrc");
    private String ext;

    private ComRegEx(String c) {
        ext = c;
    }

    public String getExt() {
        return ext;
    }
}
