package Controllers.BackOffice;

import entities.Maison;
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

public class ShowMaisonB {
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
    private Label nbChambres;

    @FXML
    private Label nom;

    @FXML
    private Label prix;

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
    private Button goback_butt;

    @FXML
    void GoBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/AfficherMaison.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = goback_butt.getScene();
        scene.setRoot(loginSuccessRoot);
    }

}
