package controllers.FrontOffice;

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

import java.io.File;
import java.io.IOException;

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


}
