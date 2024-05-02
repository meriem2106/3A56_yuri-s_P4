package controllers.FrontOffice;

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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import entities.Hotel;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;



public class ShowHotel {

    @FXML
    private ImageView image;

    @FXML
    private Label nom;

    @FXML
    private Label ville;

    @FXML
    private Label tel;

    @FXML
    private Label mail;

    @FXML
    private Label nbEtoiles;

    @FXML
    private Label loc;

    @FXML
    private Label dispo;

    @FXML
    private Label prix;

    @FXML
    private Label des;

    @FXML
    private Button reserver;

    @FXML
    private Button modifier;

    private Hotel hotel;

    public void setData(Hotel hotel) {
        this.hotel = hotel;
        nom.setText(hotel.getNom());
        ville.setText("Ville: " + hotel.getVille());
        tel.setText("Telephone: " + hotel.getTelephone());
        mail.setText("Email: " + hotel.getEmail());
        nbEtoiles.setText("Nombres d'etoiles: " + hotel.getNbEtoiles());
        loc.setText("Localisation: " + hotel.getLocalisation());
        dispo.setText("Disponibilité: " + hotel.getDisponibilite());
        prix.setText("Prix: " + hotel.getPrix());
        des.setText("Description: " + hotel.getDescription());





        String path=hotel.getImage();

        if (path!= null) {
            // Check if the path is not an absolute path (doesn't start with C:\)
            if (!path.startsWith("Users\\THINKPAD\\IdeaProjects\\pidev\\src\\main\\resources\\images")) {
                // Assuming you have a base directory for your images, replace "YOUR_BASE_DIRECTORY" with your actual base directory
                String baseDirectory = "C:\\Users\\THINKPAD\\IdeaProjects\\pidev\\src\\main\\resources\\images";
                path = baseDirectory + "\\" + path;
                System.out.println(path);
            }

            File imageFile = new File(path);
            Image image = new Image(imageFile.toURI().toString(),200,117,false,true);

            this.image.setImage(image);

        }
        GenerateQR();
    }





    @FXML
    void modifier(ActionEvent event) {
        try {
            // Load the view ModifierHotel.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ModifierHotel.fxml"));
            Parent root = loader.load();

            // Get the controller of ModifierHotel
            ModifierHotel controller = loader.getController();

            // Pass the hotel data to the ModifierHotel controller
            controller.setData(hotel);

            // Check if the loading was successful
            if (root != null) {
                // Create a new scene with the loaded view
                Scene scene = new Scene(root);

                // Get the main stage from the event
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Close the previous window
                stage.close();

                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Error: Loading ModifierHotel.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //QR
    @FXML
    private ImageView Qr_id;

    public void GenerateQR()
    {
        try {
            String data=hotel.toString();
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
