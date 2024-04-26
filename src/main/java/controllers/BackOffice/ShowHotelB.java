package controllers.BackOffice;

import entities.Hotel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;

public class ShowHotelB {

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
    private Button goback_butt;

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
    void GoBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/AfficherHotel.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = goback_butt.getScene();
        scene.setRoot(loginSuccessRoot);
    }

}
