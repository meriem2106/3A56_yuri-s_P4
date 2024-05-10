package Controllers.FrontOffice;

import entities.Maison;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.ServiceMaison;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierMaison {

    @FXML
    private TextField id_capacite;

    @FXML
    private TextField id_description;

    @FXML
    private TextField id_disponibilite;

    @FXML
    private TextField id_email;

    @FXML
    private TextField id_image;

    @FXML
    private TextField id_localisation;

    @FXML
    private TextField id_nb_chambres;

    @FXML
    private TextField id_nom;

    @FXML
    private TextField id_prix;

    @FXML
    private TextField id_ville;

    private Maison old;

    @FXML
    void modify_and_return(ActionEvent event) {
        try {
            if (id_nb_chambres.getText() != null && id_prix.getText() != null && id_nom.getText() != null && id_email.getText() != null && id_capacite.getText() != null && id_localisation.getText() != null && id_ville.getText() != null && id_disponibilite.getText() != null && id_description.getText() != null && id_image.getText() != null) {
                // Create Hotel object with updated information
                Maison modified = new Maison(
                        old.getId(),
                        Integer.parseInt(id_nb_chambres.getText()),
                        id_nom.getText(),
                        id_email.getText(),
                        id_capacite.getText(),
                        id_localisation.getText(),
                        id_ville.getText(),
                        id_disponibilite.getText(),
                        id_description.getText(),
                        id_image.getText(),
                        id_prix.getText()

                );

                // Call the modifier method from your service class
                ServiceMaison sm = new ServiceMaison();
                sm.modifier(modified);

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Hôtel modifié avec succès");
                alert.showAndWait();

                // Return to Home page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/Home.fxml"));
                Parent root = loader.load();
                id_nom.getScene().setRoot(root);
            } else {
                // Handle the case where any of the text fields are null
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Tous les champs doivent être remplis.");
                alert.showAndWait();
            }
        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Erreur lors de la modification de l'hôtel : " + e.getMessage());
            alert.showAndWait();
        }
    }


    public void setData(Maison maison) {
        id_nom.setText(maison.getNom());
        id_nb_chambres.setText(String.valueOf(maison.getNbChambres()));
        id_prix.setText(maison.getPrix());
        id_email.setText(maison.getEmail());
        id_capacite.setText(maison.getCapacite());
        id_localisation.setText(maison.getLocalisation());
        id_ville.setText(maison.getVille());
        id_disponibilite.setText(maison.getDisponibilite());
        id_description.setText(maison.getDescription());
        id_image.setText(maison.getImage());

        old = maison;
    }

}
