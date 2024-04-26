package controllers.BackOffice;

import entities.Maison;
import entities.ReservationH;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.io.File;

public class ShowReservationHB {

    @FXML
    private Label arrangement;

    @FXML
    private Label dateArrivee;

    @FXML
    private Label dateDepart;

    @FXML
    private Label hotel;

    @FXML
    private Label nbAdultes;

    @FXML
    private Label nbEnfants;

    @FXML
    private Label repartition;

    @FXML
    private Label user;


    private ReservationH r;


    public void setData(ReservationH r) {
        this.r = r;
        hotel.setText("Hotel: " +r.getHotel().getNom());
        user.setText("Touriste: " + r.getUtilisateur().getPrenom()+ " " + r.getUtilisateur().getNom());
        nbAdultes.setText("Nombre d'adultes: " + r.getNbAdultes());
        nbEnfants.setText("Nombre d'enfants: " + r.getNbEnfants());
        repartition.setText("Repartition " + r.getRepartition());
        arrangement.setText("Arrangement: " + r.getArrangement());
        dateArrivee.setText("Date d'arrivee: " + r.getDateArrivee());
        dateDepart.setText("Date de depart: " + r.getDateDepart());


    }

}
