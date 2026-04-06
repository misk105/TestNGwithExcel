package loginTest;

import org.apache.poi.xssf.usermodel.*;
import java.io.*;

public class ExcelUtile {

    private static XSSFSheet sheet;
    private static XSSFWorkbook workbook;
    private static String filePath;

    
    public static void openFile(String path, String sheetName) throws Exception {
        filePath = path;
        workbook = new XSSFWorkbook(new FileInputStream(path));
        sheet    = workbook.getSheet(sheetName);
    }

    public static String read(int row, int col) {
        try {
            return sheet.getRow(row).getCell(col).getStringCellValue();
        } catch (Exception e) {
            return "";
        }
    }

   
    public static void write(String value, int row, int col) throws Exception {
        XSSFRow r = sheet.getRow(row);
        XSSFCell c = r.getCell(col);
        if (c == null) c = r.createCell(col);
        c.setCellValue(value);
        FileOutputStream out = new FileOutputStream(filePath);
        workbook.write(out);
        out.close();
    }
}