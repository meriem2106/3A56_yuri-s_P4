package controllers.FrontOffice;

import entities.Maison;
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

public class MaisonCard extends Node {

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

    public Maison maison;


    public void setData(Maison maison) {
        this.nom.setText(maison.getNom());
        this.ville.setText(maison.getVille());
//        this.image.setImage(hotel.getImage());
        this.maison = maison;

        String path=maison.getImage();

        if (path!= null) {
            // Check if the path is not an absolute path (doesn't start with C:\)
            if (!path.startsWith("Users\\THINKPAD\\Desktop\\MODIFIED_PROJECT\\pidev\\src\\main\\resources\\images")) {
                // Assuming you have a base directory for your images, replace "YOUR_BASE_DIRECTORY" with your actual base directory
                String baseDirectory = "C:\\Users\\THINKPAD\\Desktop\\MODIFIED_PROJECT\\pidev\\src\\main\\resources\\images";
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/AjouterReservationM.fxml"));
            Parent root = loader.load();
            AjouterReservationM controller = loader.getController();
            controller.setMaisonSelectionne(maison);

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
                System.err.println("Erreur : Chargement de la vue AjouterReservationM.fxml a échoué.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void show(ActionEvent event) {
        try {
            // Load the view ShowHotelB.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ShowMaison.fxml"));
            Parent root = loader.load();

            // Get the controller of ShowHotel
            ShowMaison controller = loader.getController();

            // Pass the hotel data to the ShowHotel controller
            controller.setData(maison);

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
                System.err.println("Error: Loading ShowMaison.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }




