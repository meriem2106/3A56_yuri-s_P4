package controllers.FrontOffice;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import entities.ReservationM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class ShowReservationM {

    @FXML
    private Label arrangement;

    @FXML
    private Label dateArrivee;

    @FXML
    private Label maison;

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

    private ReservationM reservationM;

    public void setData(ReservationM reservationM) {
        this.reservationM = reservationM;
        maison.setText("Maison d'hote: " +reservationM.getMaison().getNom());
        user.setText("Touriste: " + reservationM.getUtilisateur().getPrenom()+ " " + reservationM.getUtilisateur().getNom());
        nbAdultes.setText("Nombre d'adultes: " + reservationM.getNbAdultes());
        nbEnfants.setText("Nombre d'enfants: " + reservationM.getNbEnfants());
        repartition.setText("Repartition " + reservationM.getRepartition());
        arrangement.setText("Arrangement: " + reservationM.getArrangement());
        dateArrivee.setText("Date d'arrivee: " + reservationM.getDateArrivee());
        dateDepart.setText("Date de depart: " + reservationM.getDateDepart());

        GenerateQR();
    }





    @FXML
    void modifier(ActionEvent event) {
        try {
            // Load the view ModifierReservationH.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ModifierReservationM.fxml"));
            Parent root = loader.load();

            // Get the controller of ModifierReservationH
            ModifierReservationM controller = loader.getController();

            // Pass the reservation data to the ModifierReservationH controller
            controller.setData(reservationM);

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

    //QR
    @FXML
    private ImageView Qr_id;

    public void GenerateQR() {
        try {
            String data = reservationM.toString();
            String path = "QrGen.jpg";
            BitMatrix matrix = null;
            matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 150, 150);
            MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));

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
    @FXML
    private Button goback_butt;

    @FXML
    void GoBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/Home.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = goback_butt.getScene();
        scene.setRoot(loginSuccessRoot);
    }

}
