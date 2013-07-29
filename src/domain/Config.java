/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.HashMap;

/**
 * Main aplication configuration
 *
 * @author vvazquez
 */
public class Config {

    /**
     * File extensions
     */
    public enum Ext {

        LRC(".lrc"), SRT(".srt");
        private String ext;

        private Ext(String c) {
            ext = c;
        }

        public String getExt() {
            return ext;
        }
    }

    
}
