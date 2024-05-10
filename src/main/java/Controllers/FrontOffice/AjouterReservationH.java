package Controllers.FrontOffice;

import entities.Hotel;
import entities.ReservationH;
import entities.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Services.ServiceReservationH;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

public class AjouterReservationH implements Initializable {

    @FXML
    private ComboBox<String> arrangement;

    @FXML
    private DatePicker dateArrivee;

    @FXML
    private DatePicker dateDepart;

    @FXML
    private TextField nbAdultes;

    @FXML
    private TextField nbEnfants;

    @FXML
    private ComboBox<String> repartition;

    @FXML
    private Button reserver;



    public Hotel hotelSelectionne;

    ServiceReservationH srh = new ServiceReservationH();

    Utilisateur u = new Utilisateur(3,"abid","amina","meriem@gmail.com","hehehe",new Date(100, 6, 11),"", Arrays.asList("proprietaire"),"","");


    ObservableList<String> repartitionList = FXCollections.
            observableArrayList("All Inclusive", "Pension complete", "Demi Pension", "Logement Petit Dejeuner","Logement Simple");
    ObservableList<String> arrangementList = FXCollections.
            observableArrayList("Single", "Double", "Triple", "Quadruple","Appartement","Bungalow","Suite Luxe");

    public AjouterReservationH() throws SQLException {
    }


    public void setHotelSelectionne(Hotel hotel) {
        this.hotelSelectionne = hotel;
    }

    public boolean check_date(LocalDate dateDepart , LocalDate dateArrivee)
    {

        if (dateArrivee.compareTo(dateDepart)<0 ) return true;
        else return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the ComboBox with the list of locations
        arrangement.setItems(arrangementList);
        repartition.setItems(repartitionList);
    }



    @FXML
    void ReserverH(ActionEvent event) {

        try {

            String texteNbAdultes = nbAdultes.getText();
            int nbAdultes = Integer.parseInt(texteNbAdultes);

            String texteNbEnfants = nbEnfants.getText();
            int nbEnfants = Integer.parseInt(texteNbEnfants);

            if (!texteNbAdultes.matches("\\d+") || !texteNbEnfants.matches("\\d+")) {
                // Afficher une alerte d'erreur
                throw new SQLException("Le nombre doit être positif");
            }

            if(check_date(dateDepart.getValue(), dateArrivee.getValue())) {
                ReservationH reservation = new ReservationH(nbAdultes, nbEnfants, arrangement.getValue(), repartition.getValue(), dateArrivee.getValue(), dateDepart.getValue(), hotelSelectionne, u);
                srh.ajouter(reservation);

                // Afficher un message de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Reservation ajoutée avec succès");
                alert.showAndWait();

                // Charger la vue des détails de la réservation
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ShowReservationH.fxml"));
                Parent root = loader.load();

                // Passer la réservation à la vue des détails de la réservation
                ShowReservationH controller = loader.getController();
                controller.setData(reservation);

                // Obtenir la scène actuelle et la remplacer par la nouvelle vue
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } else {
                // Afficher un message d'erreur pour les dates invalides
                throw new SQLException("Les dates de départ et d'arrivée ne sont pas valides.");
            }
        } catch (SQLException | IOException e) {
            // Gérer les erreurs
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Erreur lors de la réservation : " + e.getMessage());
            alert.showAndWait();
        }
    }





}




