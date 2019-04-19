/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    
    private static Object calledClass;
    
    public static void setCalledClass(Object calledClass) {
        Util.calledClass = calledClass;
    }
    
    public static void setText(TextField tf, Double value) {
        try {
            String text = Double.toString(value);
            tf.setText(text);
        } catch (NullPointerException e) {
            System.err.println(String.format(
                    "%s: value for %s from db is null", 
                    calledClass.getClass().getSimpleName(),
                    tf.getId()));
            tf.clear();
        }
    }
    public static void setText(Label label, Double value) {
        try {
            String text = Double.toString(value);
            label.setText(text);
        } catch (NullPointerException e) {
            System.err.println(String.format(
                    "%s: value for %s from db is null", 
                    calledClass.getClass().getSimpleName(),
                    label.getId()));
            label.setText("Нет");
        }
    }
    
    public static Double parseField(TextField field) {
        String fieldString = field.getText();
        try {
            double value = Double.parseDouble(fieldString);
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat df = new DecimalFormat("#.##", otherSymbols);
            df.setRoundingMode(RoundingMode.HALF_UP);

            return Double.parseDouble(df.format(value));
        } catch(NumberFormatException e) {
            System.err.println(String.format(
                    "%s: %s value '%s' is not correct. Replaced by null",
                    calledClass.getClass().getSimpleName(),
                    field.getId(),
                    fieldString));
            return null;
        }
    }
    
    public static boolean isExist(Object payment) {
        return payment != null;
    }
    
    public static boolean isFilled(TextField ...tfs) {
        for (TextField tf : tfs) {
            if (tf.getText().length() != 0)
                return true;
        }
        return false;
    }
}
