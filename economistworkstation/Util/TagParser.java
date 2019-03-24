/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author fnajer
 */
public class TagParser {
    
    public static void convertTag (Cell cell) {
        String cellString = cell.getStringCellValue();
                            
        Pattern pattern = Pattern.compile("<\\w+>");
        Matcher matcher = pattern.matcher(cellString);
        while(matcher.find()) {
            String resultString;
            String newValue = "<Tag not founded>";
            String foundedTag = matcher.group();
            System.out.println(foundedTag);
            
            if ("<date>".equals(foundedTag)) {
                LocalDate date = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedString = date.format(formatter);
                newValue = formattedString;
            }
            if ("<numAcc>".equals(foundedTag)) {
                newValue = "acc";
            }
            if ("<sumInWords>".equals(foundedTag)) {
                String sumInWords = Money.digits2Text(145.00);
                newValue = sumInWords;
            }
            
            resultString = cellString.replaceAll(foundedTag, newValue);
            cell.setCellValue(resultString);
            cellString = resultString;
        }
    }
}
