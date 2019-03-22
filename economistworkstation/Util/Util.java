/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Util;

/**
 *
 * @author fnajer
 */
public class Util {
    enum State {
        Оплачено,
        Неоплачено
    };
    
    public static String boolToString(boolean isTrue) {
       if (isTrue) {
           return State.Оплачено.toString();
       } else {
           return State.Неоплачено.toString();
       }
    }
    
    public static boolean stringToBool(String paid) {
        return "Оплачено".equals(paid);
    }
}
