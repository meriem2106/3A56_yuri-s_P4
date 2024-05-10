package entities;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class PdfGenerator {
    public void generatePDF(String nbrAdulte, String nbrEnfant, String dateAcceuil, String dateRetour, String description) {
        String dest = "formulaire.pdf";

        try {
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Créer un tableau
            Table table = new Table(2); // 2 colonnes

            // Ajouter le contenu au tableau
            table.addCell("Nombre d'adultes:");
            table.addCell(nbrAdulte);
            table.addCell("Nombre d'enfants:");
            table.addCell(nbrEnfant);
            table.addCell("Date d'accueil:");
            table.addCell(dateAcceuil);
            table.addCell("Date de retour:");
            table.addCell(dateRetour);
            table.addCell("Description:");
            table.addCell(description);

            // Ajouter le logo à gauche de la page
            Image logo = new Image(ImageDataFactory.create("src/main/resources/image/5fdaa49c-c09d-45cf-9d66-2bffa1540ad7.jpg"));
            logo.setWidth(100); // Ajuster la largeur du logo selon vos besoins
            document.add(logo);

            // Ajouter le tableau au centre de la page
            document.add(table);

            // Ajouter la signature en bas à droite de la page
            Image signature = new Image(ImageDataFactory.create("src/main/resources/image/signature.png"));
            signature.setWidth(100); // Ajuster la largeur de la signature selon vos besoins
            document.add(signature);

            document.close();

            System.out.println("PDF généré avec succès.");
        } catch (FileNotFoundException | MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
