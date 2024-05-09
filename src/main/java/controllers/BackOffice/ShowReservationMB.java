package controllers.BackOffice;

import entities.Maison;
import entities.ReservationH;
import entities.ReservationM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;

public class ShowReservationMB {

    @FXML
    private Label arrangement;

    @FXML
    private Label dateArrivee;

    @FXML
    private Label dateDepart;

    @FXML
    private Label maison;

    @FXML
    private Label nbAdultes;

    @FXML
    private Label nbEnfants;

    @FXML
    private Label repartition;

    @FXML
    private Label user;


    private ReservationM r;


    public void setData(ReservationM r) {
        this.r = r;
        maison.setText("Maison: " +r.getMaison().getNom());
        user.setText("Touriste: " + r.getUtilisateur().getPrenom()+ " " + r.getUtilisateur().getNom());
        nbAdultes.setText("Nombre d'adultes: " + r.getNbAdultes());
        nbEnfants.setText("Nombre d'enfants: " + r.getNbEnfants());
        repartition.setText("Repartition: " + r.getRepartition());
        arrangement.setText("Arrangement: " + r.getArrangement());
        dateArrivee.setText("Date d'arrivee: " + r.getDateArrivee());
        dateDepart.setText("Date de depart: " + r.getDateDepart());


    }

    @FXML
    private Button goback_butt;
    @FXML
    void GoBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/AfficherReservationM.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = goback_butt.getScene();
        scene.setRoot(loginSuccessRoot);
    }
}
