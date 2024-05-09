package controllers.FrontOffice;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.stripe.param.checkout.SessionCreateParams;
import com.twilio.rest.taskrouter.v1.workspace.task.Reservation;
import entities.Hotel;
import entities.ReservationH;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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


import javafx.embed.swing.SwingFXUtils;
import net.glxn.qrgen.javase.QRCode;
import javafx.scene.image.WritableImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import java.nio.file.Paths;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import java.net.URISyntaxException;
import java.net.URI;
import java.awt.Desktop;
import java.sql.SQLException;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import services.ServiceReservationH;


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
    private Button payer;

    @FXML
    private Label prix;

    @FXML
    private Button exporter;

    private ReservationH reservationH;

    ServiceReservationH srh= new ServiceReservationH();

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
        prix.setText("Le prix a payer est egale a" +reservationH.getHotel().getPrix());
        GenerateQR();

    }

    private static void addReservationContent(Document document, ReservationH reservation) throws IOException {
        // Définir la police et la taille du texte
        PdfFont font = PdfFontFactory.createFont();

        // Ajouter le titre
        Paragraph title = new Paragraph("Réservation d'Hôtel").setFont(font).setFontSize(20).setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        document.add(new Paragraph("Hôtel: " + reservation.getHotel().getImage()));
        document.add(new Paragraph("Hôtel: " + reservation.getHotel().getNom()).setFont(font));
        document.add(new Paragraph("Touriste: " + reservation.getUtilisateur().getPrenom() + " " + reservation.getUtilisateur().getNom()).setFont(font));
        document.add(new Paragraph("Nombre d'adultes: " + reservation.getNbAdultes()).setFont(font));
        document.add(new Paragraph("Nombre d'enfants: " + reservation.getNbEnfants()).setFont(font));
        document.add(new Paragraph("Repartition: " + reservation.getRepartition()).setFont(font));
        document.add(new Paragraph("Arrangement: " + reservation.getArrangement()).setFont(font));
        document.add(new Paragraph("Date d'arrivée: " + reservation.getDateArrivee().toString()).setFont(font));
        document.add(new Paragraph("Date de départ: " + reservation.getDateDepart().toString()).setFont(font));
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


    @FXML
    void payer(ActionEvent event) {
        String prixString = reservationH.getHotel().getPrix();
        if (prixString == null) {
            // Gérer le cas où le prix est null
            System.err.println("Le prix de l'hôtel est null.");
            return;
        }

        try {
            Double m = Double.parseDouble(prixString);
            Double montant = m*reservationH.getNbAdultes()+(reservationH.getNbEnfants()*(0.5*m));
            Stripe.apiKey = "sk_test_51Oop0OBQCHJCIBnOp9sP9YNzRUVOleVW4d5FgsXox60XUClnwh8ZiMMamUtpz8LgHkIfYzQw8q40ocGEf1fVkj7G00Qk2ILK7A";

            SessionCreateParams params = SessionCreateParams.builder()
                    .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://ruperhat.com/wp-content/uploads/2020/06/Paymentsuccessful21.png")
                    .setCancelUrl("https://hypixel.net/attachments/1690923493412-png.3230490/")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("usd")
                                                    .setUnitAmount((long) (montant * 100)) // Stripe expects the amount in cents
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("test")

                                                                    .setDescription("TUNI'WORLD, SEE THROUGH MY EYE")
                                                                    .build()
                                                    )
                                                    .build()
                                    )
                                    .build()
                    )
                    .build();

            Session session = Session.create(params);
            URI checkoutUri = new URI(session.getUrl());
            //loadCheckoutPage(checkoutUri.toString());

            // Ouvre l'URL dans le navigateur par défaut
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(checkoutUri);
            } else {
                // Gérer le cas où l'ouverture de l'URL n'est pas supportée
                System.err.println("Ouverture de l'URL non supportée.");
            }
        } catch (NumberFormatException e) {
            // Gérer le cas où le prix n'est pas un nombre valide
            System.err.println("Le prix de l'hôtel n'est pas un nombre valide : " + prixString);
            e.printStackTrace();
        } catch (StripeException e) {
            System.err.println("Error creating Checkout Session: " + e.getMessage());
            e.printStackTrace();
            // Handle the error, display a message to the user, etc.
        } catch (Exception ex) {
            System.err.println("Error redirecting to Stripe Checkout: " + ex.getMessage());
            ex.printStackTrace();
            // Handle the error, display a message to the user, etc.
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

    //QR
    @FXML
    private ImageView Qr_id;

    public void GenerateQR()
    {
        try {
            String data=reservationH.toString();
            String path="QrGen.jpg";
            BitMatrix matrix = null;
            matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE,150,150);
            MatrixToImageWriter.writeToPath(matrix,"jpg", Paths.get(path));

            // Convert BitMatrix to BufferedImage
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "JPG", outputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            javafx.scene.image.Image image = new Image(inputStream);
            Qr_id.setImage(image);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }


    }
    public void SetQr()
    {
        Image img = new Image("QrGen.jpg");
        Qr_id.setImage(img);
    }

}
