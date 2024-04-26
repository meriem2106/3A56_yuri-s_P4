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

public class HotelCard  {

    @FXML
    public ImageView image;

    @FXML
    public Label nom;

    @FXML
    public Button reserver;

    @FXML
    public Button show;

    @FXML
    public Label ville;

    public Hotel hotel;


    public void setData(Hotel hotel) {
        this.nom.setText(hotel.getNom());
        this.ville.setText(hotel.getVille());
//        this.image.setImage(hotel.getImage());
        this.hotel = hotel;

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
    void reserver(ActionEvent event) {
        try {


            // Charger la vue ShowHotelB.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/AjouterReservationH.fxml"));
            Parent root = loader.load();
            AjouterReservationH controller = loader.getController();
            controller.setHotelSelectionne(hotel);
            // Vérifier si le chargement a réussi
            if (root != null) {
                // Créer une nouvelle scène avec la vue chargée
                Scene scene = new Scene(root);

                // Obtenir la fenêtre principale (stage) à partir de l'événement
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Fermer la fenêtre précédente
                stage.close();

                // Définir la nouvelle scène sur le stage
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Erreur : Chargement de la vue AjouterReservationH.fxml a échoué.");
            }
        } catch (IOException e) {
            e.printStackTrace();
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




    @FXML
    void ShowHotel(ActionEvent event) {
        try {
            // Load the view ShowHotelB.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ShowHotel.fxml"));
            Parent root = loader.load();

            // Get the controller of ShowHotel
            ShowHotel controller = loader.getController();

            // Pass the hotel data to the ShowHotel controller
            controller.setData(hotel);

            // Check if the loading was successful
            if (root != null) {
                // Create a new scene with the loaded view
                Scene scene = new Scene(root);

                // Get the main stage from the event
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Error: Loading ShowHotelB.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

