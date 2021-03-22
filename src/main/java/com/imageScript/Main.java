package com.imageScript;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;


public class Main {


    public static void main(String args []) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Scan batch Name:");
        String scanBatchName = scanner.next();
        scanner.close();
        ImageScan imageScan = new ImageScan("src/main/resources/sampleSheets");
        ImageSlicer imageSlicer = new ImageSlicer(imageScan.getImages());

        ScanResult scanResult = new ScanResult(scanBatchName);
        while (imageSlicer.hasNext()) {

            ImageSlicer.Sheet sheet = imageSlicer.nextSheet();
            SheetResult currentSheet = new SheetResult(sheet.qrCode);

            for (int i = 0; i < sheet.imageList.size(); i++) {
                File temp = new File("temp.tif");
                ImageIO.write(sheet.imageList.get(i), "tif", temp);
                ImageJScript imageJScript = new ImageJScript();
                ImageJScript.ParticleAnalyzerResult result = imageJScript.calcResult();
                float area = result.area;
                int particleAmount =  result.particleAmount;
                currentSheet.addSquare(new Square(i, area, particleAmount));
                System.out.println(area);

            }
            scanResult.addSheetResult(currentSheet);
        }
        saveAsCSV(scanResult);
        System.out.println("Scan Succesfull");
    }

    private static void saveAsCSV(ScanResult scanResult){
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet spreadsheet = workbook.createSheet(scanResult.name);
        Row row  ;

        Map<String, Object[]>  sheetData = new TreeMap<>();

        sheetData.put("1", new Object[] {
                "QrCode",
                "Square 1",
                "Square 2",
                "Square 3",
                "Square 4",
                "Square 5",
                "Square 6",
                "Square 7",
                "Square 8",
                "Square 9",
                "Square 10",
                "Square 11",
                "Square 12",
                "Square 13",
                "Square 14",
                "Square 15",
                "Square 16",
        });
        int index = 2;
        for (SheetResult sheet : scanResult.sheetResultList ) {
            sheetData.put(String.valueOf(index ), new Object[]{
                    sheet.QrName,
                    String.valueOf(sheet.square.get(0).area),
                    String.valueOf(sheet.square.get(1).area),
                    String.valueOf(sheet.square.get(2).area),
                    String.valueOf(sheet.square.get(3).area),
                    String.valueOf(sheet.square.get(4).area),
                    String.valueOf(sheet.square.get(5).area),
                    String.valueOf(sheet.square.get(6).area),
                    String.valueOf(sheet.square.get(7).area),
                    String.valueOf(sheet.square.get(8).area),
                    String.valueOf(sheet.square.get(9).area),
                    String.valueOf(sheet.square.get(10).area),
                    String.valueOf(sheet.square.get(11).area),
                    String.valueOf(sheet.square.get(12).area),
                    String.valueOf(sheet.square.get(13).area),
                    String.valueOf(sheet.square.get(14).area),
                    String.valueOf(sheet.square.get(15).area),
            });
            index++;
            sheetData.put(String.valueOf(index ), new Object[]{
                    "Particle Count",
                    String.valueOf(sheet.square.get(0).particleAmount),
                    String.valueOf(sheet.square.get(1).particleAmount),
                    String.valueOf(sheet.square.get(2).particleAmount),
                    String.valueOf(sheet.square.get(3).particleAmount),
                    String.valueOf(sheet.square.get(4).particleAmount),
                    String.valueOf(sheet.square.get(5).particleAmount),
                    String.valueOf(sheet.square.get(6).particleAmount),
                    String.valueOf(sheet.square.get(7).particleAmount),
                    String.valueOf(sheet.square.get(8).particleAmount),
                    String.valueOf(sheet.square.get(9).particleAmount),
                    String.valueOf(sheet.square.get(10).particleAmount),
                    String.valueOf(sheet.square.get(11).particleAmount),
                    String.valueOf(sheet.square.get(12).particleAmount),
                    String.valueOf(sheet.square.get(13).particleAmount),
                    String.valueOf(sheet.square.get(14).particleAmount),
                    String.valueOf(sheet.square.get(15).particleAmount),
            });


            index++;
        }

        Set<String> keyId = sheetData.keySet();

        int rowId = 1;
        for (String key: keyId) {
            row = spreadsheet.createRow(rowId++);
            Object[] objectArr = sheetData.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(new File("src/main/resources/Result.xlsx"));
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}