package com.spring.ystan.file;

import com.spring.ystan.entities.CarData;
import com.spring.ystan.processor.ClassExportPreProcessor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class ExcelFileExporter {

    @Autowired
    ClassExportPreProcessor processor;

    private Workbook workbook;

    private Workbook createWorkbook() {
        workbook = new XSSFWorkbook();
        return workbook;
    }

    /**
     * Requires header with created cells.
     *
     * @param header
     */
    private void styleHeader(Row header) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Iterator<Cell> cellIterator = header.cellIterator();
        while (cellIterator.hasNext()) {
            Cell next = cellIterator.next();
            next.setCellStyle(headerStyle);
        }

    }

    @SneakyThrows
    public Workbook fillWorkbook(List<CarData> data, Class carDataClass, Locale locale) {
        workbook = createWorkbook();
        Sheet sheet = workbook.createSheet("Exported Data");
        int rowNumber = 0;

        Row headerRow = sheet.createRow(rowNumber++);

        int headerCellNumber = 0;
        Map<String, Field> fields = processor.getFields(carDataClass, locale);
        for (String fieldName : fields.keySet()) {
            Cell cell = headerRow.createCell(headerCellNumber++);
            cell.setCellValue(fieldName);
        }


        for (CarData carData : data) {
            Row row = sheet.createRow(rowNumber++);
            int cellNumber = 0;

            for (Field field : fields.values()) {
                field.setAccessible(true);
                String value = (String) field.get(carData);
                Cell cell = row.createCell(cellNumber++);
                cell.setCellValue(value);
            }
        }

        return workbook;
    }

    @SneakyThrows
    public void writeToFile(String fileName, Workbook workbook) {
        File file = new File(".");
        String absolutePath = file.getAbsolutePath();
        String absoluteFileName = absolutePath.substring(0, absolutePath.length() - 1) + fileName;

        FileOutputStream fileOutputStream = new FileOutputStream(absoluteFileName);
        workbook.write(fileOutputStream);
    }

}
