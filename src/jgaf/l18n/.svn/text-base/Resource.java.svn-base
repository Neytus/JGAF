/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jgaf.l18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author hanis
 */
public class Resource {

    private Resource() {
    }

    public static String getValue(String key) {

        //// System.out.println(System.getProperties().getProperty("java.class.path").toString());//.getAbsoluteFile());//.getParent() + File.separator;
       // System.out.println(Resource.class.getResource("/config/localization/localization.properties"));
        return ResourceBundle.getBundle("jgaf/l18n/localization", Locale.getDefault()).getString(key);
    }
}
