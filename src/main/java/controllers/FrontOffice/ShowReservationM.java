package controllers.FrontOffice;

import entities.ReservationH;
import entities.ReservationM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowReservationM {

    @FXML
    private Label arrangement;

    @FXML
    private Label dateArrivee;

    @FXML
    private Label maison;

    @FXML
    private Label user;

    @FXML
    private Label dateDepart;

    @FXML
    private Button modifier;

    @FXML
    private Label nbAdultes;

    @FXML
    private Label nbEnfants;

    @FXML
    private Label repartition;

    private ReservationM reservationM;

    public void setData(ReservationM reservationM) {
        this.reservationM = reservationM;
        maison.setText("Maison d'hote: " +reservationM.getMaison().getNom());
        user.setText("Touriste: " + reservationM.getUtilisateur().getPrenom()+ " " + reservationM.getUtilisateur().getNom());
        nbAdultes.setText("Nombre d'adultes: " + reservationM.getNbAdultes());
        nbEnfants.setText("Nombre d'enfants: " + reservationM.getNbEnfants());
        repartition.setText("Repartition " + reservationM.getRepartition());
        arrangement.setText("Arrangement: " + reservationM.getArrangement());
        dateArrivee.setText("Date d'arrivee: " + reservationM.getDateArrivee());
        dateDepart.setText("Date de depart: " + reservationM.getDateDepart());

    }





    @FXML
    void modifier(ActionEvent event) {
        try {
            // Load the view ModifierReservationH.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ModifierReservationM.fxml"));
            Parent root = loader.load();

            // Get the controller of ModifierReservationH
            ModifierReservationM controller = loader.getController();

            // Pass the reservation data to the ModifierReservationH controller
            controller.setData(reservationM);

            // Create a new scene with the loaded view
            Scene scene = new Scene(root);

            // Get the main stage from the event
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
