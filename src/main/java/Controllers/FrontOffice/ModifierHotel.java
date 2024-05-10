package Controllers.FrontOffice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import entities.Hotel;

import services.ServiceHotel;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierHotel {

    @FXML
    private TextField id_nom_hotel;

    @FXML
    private TextField id_nb_etoiles;

    @FXML
    private TextField id_prix;

    @FXML
    private TextField id_email;

    @FXML
    private TextField id_telephone;

    @FXML
    private TextField id_localisation;

    @FXML
    private TextField id_ville;

    @FXML
        private TextField id_disponibilite;

    @FXML
    private TextField id_description;

    @FXML
    private TextField id_image;

    @FXML
    private Button id_update;

    private Hotel oldHotel;

    @FXML
    void modify_and_return(ActionEvent event) {
        try {
            if (id_nb_etoiles.getText() != null && id_prix.getText() != null && id_nom_hotel.getText() != null && id_email.getText() != null && id_telephone.getText() != null && id_localisation.getText() != null && id_ville.getText() != null && id_disponibilite.getText() != null && id_description.getText() != null && id_image.getText() != null) {
                // Create Hotel object with updated information
                Hotel modifiedHotel = new Hotel(
                        oldHotel.getId(),
                        Integer.parseInt(id_nb_etoiles.getText()),
                        id_prix.getText(),
                        id_nom_hotel.getText(),
                        id_email.getText(),
                        id_telephone.getText(),
                        id_localisation.getText(),
                        id_ville.getText(),
                        id_disponibilite.getText(),
                        id_description.getText(),
                        id_image.getText()
                );

                // Call the modifier method from your service class
                ServiceHotel sh = new ServiceHotel();
                sh.modifier(modifiedHotel);

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Hôtel modifié avec succès");
                alert.showAndWait();

                // Return to Home page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/Home.fxml"));
                Parent root = loader.load();
                id_nom_hotel.getScene().setRoot(root);
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


    public void setData(Hotel hotel) {
        id_nom_hotel.setText(hotel.getNom());
        id_nb_etoiles.setText(String.valueOf(hotel.getNbEtoiles()));
        id_prix.setText(hotel.getPrix());
        id_email.setText(hotel.getEmail());
        id_telephone.setText(hotel.getTelephone());
        id_localisation.setText(hotel.getLocalisation());
        id_ville.setText(hotel.getVille());
        id_disponibilite.setText(hotel.getDisponibilite());
        id_description.setText(hotel.getDescription());
        id_image.setText(hotel.getImage());

        oldHotel = hotel;
    }
}
