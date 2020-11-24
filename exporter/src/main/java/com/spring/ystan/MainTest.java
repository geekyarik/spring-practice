package com.spring.ystan;

import com.spring.ystan.entities.CarData;
import com.spring.ystan.file.ExcelFileExporter;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("context.xml");

        List<CarData> data = new ArrayList<>();
        data.add(CarData.builder().id("32").brand("Audi").model("a4").build());

        ExcelFileExporter exporter = context.getBean(ExcelFileExporter.class);

        Workbook workbook = exporter.fillWorkbook(data, CarData.class, Locale.FRANCE);
        exporter.writeToFile("output.xlsx", workbook);
    }
}
