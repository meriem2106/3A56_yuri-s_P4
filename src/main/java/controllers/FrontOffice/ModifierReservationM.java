package controllers.FrontOffice;

import entities.ReservationH;
import entities.ReservationM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceReservationH;
import services.ServiceReservationM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ModifierReservationM {

    @FXML
    private TextField id_arrangement;

    @FXML
    private TextField id_dateArrivee;

    @FXML
    private TextField id_dateDepart;

    @FXML
    private TextField id_nb_adultes;

    @FXML
    private TextField id_nb_enfants;

    @FXML
    private TextField id_repartition;

    private ReservationM oldReservationM;

    @FXML
    void modify_and_return(ActionEvent event) {
        try {
            if (id_nb_adultes.getText() != null && id_nb_enfants.getText() != null && id_repartition.getText() != null && id_arrangement.getText() != null && id_dateArrivee.getText() != null && id_dateDepart.getText() != null ) {
                // Create Hotel object with updated information
                ReservationM modifiedReservation = new ReservationM(
                        oldReservationM.getId(),
                        Integer.parseInt(id_nb_adultes.getText()),
                        Integer.parseInt(id_nb_enfants.getText()),
                        id_repartition.getText(),
                        id_arrangement.getText(),
                        LocalDate.parse(id_dateArrivee.getText()),
                        LocalDate.parse(id_dateDepart.getText())

                );

                // Call the modifier method from your service class
                ServiceReservationM srm = new ServiceReservationM();
                srm.modifier(modifiedReservation);

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Reservation de la maison d'hote modifiée avec succès");
                alert.showAndWait();

                // Return to Home page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/Home.fxml"));
                Parent root = loader.load();
                id_nb_adultes.getScene().setRoot(root);

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
            alert.setContentText("Erreur lors de la modification de la reservation : " + e.getMessage());
            alert.showAndWait();
        }
    }


    public void setData(ReservationM r) {


        id_nb_adultes.setText(String.valueOf(r.getNbAdultes()));
        id_nb_enfants.setText(String.valueOf(r.getNbEnfants()));
        id_repartition.setText(r.getRepartition());
        id_arrangement.setText(r.getArrangement());
        id_dateArrivee.setText(r.getDateArrivee().toString());
        id_dateDepart.setText(r.getDateDepart().toString());


        oldReservationM = r;
    }

}
