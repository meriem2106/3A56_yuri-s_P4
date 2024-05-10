package Controllers;

import entities.Produit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {
    private static List<Produit> produits;
    private static String filePath;


    public static void exportToExcel(List<Produit> produits, String filePath) {
        ExcelExporter.produits = produits;
        ExcelExporter.filePath = filePath;
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Produits");
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Nom du Produit", "Description", "Categorie", "Matiere", "Origine"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            int rowNum = 1; // Commencer à la deuxième ligne après les titres des colonnes
            for (Produit produit : produits) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                //

            /*int rowNum = 0;
            for (Club club : clubs) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;*/
                row.createCell(colNum++).setCellValue(produit.getNom());
                row.createCell(colNum++).setCellValue(produit.getDescription());
                row.createCell(colNum++).setCellValue(produit.getCateg());
                row.createCell(colNum++).setCellValue(produit.getMatiere());
                row.createCell(colNum++).setCellValue(produit.getOrigine());

            }

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
