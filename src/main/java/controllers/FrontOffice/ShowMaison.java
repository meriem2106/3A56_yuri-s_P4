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

public class ShowMaison {

    @FXML
    private Label capacite;

    @FXML
    private Label des;

    @FXML
    private Label dispo;

    @FXML
    private ImageView image;

    @FXML
    private Label loc;

    @FXML
    private Label mail;

    @FXML
    private Button modifier;

    @FXML
    private Label nbChambres;

    @FXML
    private Label nom;

    @FXML
    private Label prix;

    @FXML
    private Button reserver;

    @FXML
    private Label ville;

    private Maison maison;

    public void setData(Maison maison) {
        this.maison = maison;
        nom.setText(maison.getNom());
        ville.setText("Ville: " + maison.getVille());
        capacite.setText("Capacite: " + maison.getCapacite());
        mail.setText("Email: " + maison.getEmail());
        nbChambres.setText("Nombres de Chambres: " + maison.getNbChambres());
        loc.setText("Localisation: " + maison.getLocalisation());
        dispo.setText("Disponibilité: " + maison.getDisponibilite());
        prix.setText("Prix: " + maison.getPrix());
        des.setText("Description: " + maison.getDescription());





        String path=maison.getImage();

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ModifierMaison.fxml"));
            Parent root = loader.load();

            // Get the controller of ModifierHotel
            ModifierMaison controller = loader.getController();

            // Pass the hotel data to the ModifierHotel controller
            controller.setData(maison);

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
                System.err.println("Error: Loading ModifierMaison.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
