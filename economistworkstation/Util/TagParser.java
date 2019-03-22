/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package economistworkstation.Util;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author fnajer
 */
public class TagParser {
    public static String convertTag (Cell cell) {
        String cellString = cell.getStringCellValue();
        String resultString = "";
                            
        Pattern pattern = Pattern.compile("<\\w+>");
        Matcher matcher = pattern.matcher(cellString);
        while(matcher.find()) {
            String foundedTag = matcher.group();
            System.out.println(foundedTag);
            if ("<date>".equals(foundedTag)) {
                LocalDate date = LocalDate.now();
                resultString = cellString.replaceAll(foundedTag, date.toString());
                cell.setCellValue(resultString);
                cellString = resultString;
            }
            if ("<numAcc>".equals(foundedTag)) {
                resultString = cellString.replaceAll(foundedTag, "acc");
                cell.setCellValue(resultString);
                cellString = resultString;
            }
            if ("<sumInWords>".equals(foundedTag)) {
                String sumInWords = Money.digits2Text(145.00);
                resultString = cellString.replaceAll(foundedTag, sumInWords);
                cell.setCellValue(resultString);
                cellString = resultString;
            }
        }
        return resultString;
    }
}
