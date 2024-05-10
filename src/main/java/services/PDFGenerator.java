//package services;
//
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;
//import entities.Evenement;
//
//import java.io.FileOutputStream;
//
//import static com.itextpdf.kernel.pdf.PdfName.Document;
//
//public class PDFGenerator {
//    public static void generateEventPDF(Evenement event, String filePath) {
//        Document document = new Document();
//        try {
//            PdfWriter.getInstance(document, new FileOutputStream(filePath));
//            document.open();
//            document.add(new Paragraph("Nom de l'événement : " + event.getNom()));
//            document.add(new Paragraph("Contexte : " + event.getContexte()));
//            document.add(new Paragraph("Catégorie : " + event.getCat()));
//            document.add(new Paragraph("Lieu : " + event.getLieu()));
//            document.add(new Paragraph("Transport : " + event.getTransport()));
//            document.add(new Paragraph("Nombre de places maximum : " + event.getNbPlacesMax()));
//            document.add(new Paragraph("Description : " + event.getDescription()));
//            document.add(new Paragraph("Date de début : " + event.getDatedeb()));
//            document.add(new Paragraph("Date de fin : " + event.getDatefin()));
//            document.close();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
