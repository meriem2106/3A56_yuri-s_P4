package controllers.FrontOffice;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import entities.ReservationH;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ShowReservationH {

    @FXML
    private Label arrangement;

    @FXML
    private Label dateArrivee;

    private Window window;

    @FXML
    private Label hotel;

    @FXML
    private Label user;

    @FXML
    private Label dateDepart;

    @FXML
    private Button modifier;

    @FXML
    private Label nbAdultes;

    @FXML
    private Label nbEnfants;

    @FXML
    private Label repartition;

    @FXML
    private Button exporter;

    private ReservationH reservationH;

    public void setData(ReservationH reservationH) {
        this.reservationH = reservationH;
        hotel.setText("Hotel: " +reservationH.getHotel().getNom());
        user.setText("Touriste: " + reservationH.getUtilisateur().getPrenom()+ " " + reservationH.getUtilisateur().getNom());
        nbAdultes.setText("Nombre d'adultes: " + reservationH.getNbAdultes());
        nbEnfants.setText("Nombre d'enfants: " + reservationH.getNbEnfants());
        repartition.setText("Repartition " + reservationH.getRepartition());
        arrangement.setText("Arrangement: " + reservationH.getArrangement());
        dateArrivee.setText("Date d'arrivee: " + reservationH.getDateArrivee());
        dateDepart.setText("Date de depart: " + reservationH.getDateDepart());

    }

    private static void addReservationContent(Document document, ReservationH reservation) throws IOException {
        // Définir la police et la taille du texte
        PdfFont font = PdfFontFactory.createFont();

        // Ajouter le titre
        Paragraph title = new Paragraph("Réservation d'Hôtel").setFont(font).setFontSize(20).setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        // Ajouter les détails de la réservation dans un tableau
        Table table = new Table(2);
        table.addCell(new Paragraph("Hôtel: ").setFont(font));
        table.addCell(new Paragraph(reservation.getHotel().getNom()).setFont(font));
        table.addCell(new Paragraph("Touriste: ").setFont(font));
        table.addCell(new Paragraph(reservation.getUtilisateur().getPrenom() + " " + reservation.getUtilisateur().getNom()).setFont(font));
        table.addCell(new Paragraph("Nombre d'adultes: ").setFont(font));
        table.addCell(new Paragraph(String.valueOf(reservation.getNbAdultes())).setFont(font));
        table.addCell(new Paragraph("Nombre d'enfants: ").setFont(font));
        table.addCell(new Paragraph(String.valueOf(reservation.getNbEnfants())).setFont(font));
        table.addCell(new Paragraph("Repartition: ").setFont(font));
        table.addCell(new Paragraph(reservation.getRepartition()).setFont(font));
        table.addCell(new Paragraph("Arrangement: ").setFont(font));
        table.addCell(new Paragraph(reservation.getArrangement()).setFont(font));
        table.addCell(new Paragraph("Date d'arrivée: ").setFont(font));
        table.addCell(new Paragraph(reservation.getDateArrivee().toString()).setFont(font));
        table.addCell(new Paragraph("Date de départ: ").setFont(font));
        table.addCell(new Paragraph(reservation.getDateDepart().toString()).setFont(font));

        document.add(table);
    }

    @FXML
    void exporter(ActionEvent event) {
        try {
            // Créer un writer pour écrire dans un fichier PDF
            PdfWriter writer = new PdfWriter(new File("reservation.pdf"));

            // Créer un document PDF
            PdfDocument pdf = new PdfDocument(writer);

            // Créer un document iText associé au document PDF
            Document document = new Document(pdf, PageSize.A4);

            // Ajouter le contenu de la réservation à la page PDF
            addReservationContent(document, reservationH);

            // Fermer le document
            document.close();

            // Télécharger le fichier PDF
            downloadPDF(new File("reservation.pdf"), window);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors de la génération du fichier PDF : " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void modifier(ActionEvent event) {
        try {
            // Load the view ModifierReservationH.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ModifierReservationH.fxml"));
            Parent root = loader.load();

            // Get the controller of ModifierReservationH
            ModifierReservationH controller = loader.getController();

            // Pass the reservation data to the ModifierReservationH controller
            controller.setData(reservationH);

            // Create a new scene with the loaded view
            Scene scene = new Scene(root);

            // Get the main stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadPDF(File file, Window window) {
        // Créer un FileChooser pour sélectionner l'emplacement de téléchargement
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier PDF", "*.pdf"));
        fileChooser.setInitialFileName("reservation.pdf");

        // Afficher la boîte de dialogue de sauvegarde
        File selectedFile = fileChooser.showSaveDialog(window);

        // Copier le fichier PDF dans l'emplacement sélectionné
        if (selectedFile != null) {
            try {
                Files.copy(file.toPath(), selectedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setContentText("Le fichier PDF a été téléchargé avec succès.");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Une erreur s'est produite lors du téléchargement du fichier PDF : " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

}
