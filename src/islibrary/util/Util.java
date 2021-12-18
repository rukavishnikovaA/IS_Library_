/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package islibrary.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Util {

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String longToDateString(long Date) {
        Date date = new Date(Date);
        SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.yyyy");
        String dateText = df2.format(date);
        return dateText;
    }

    public static boolean stringIsLetters(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
    
    public static boolean stringIsNumberPhone(String s) {
        String regexStr = "^(1\\-)?[0-9]{3}\\-?[0-9]{3}\\-?[0-9]{4}$";
        return s.matches(regexStr);
    }
    
        
    public static long getCurrentDate() {
        return System.currentTimeMillis();
    }
}
