package Controllers;

import entities.Formation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.SQLException;

public class DetailsFormation {

    @FXML
    private Button btn_retour;

    @FXML
    private Label Vdate;

    @FXML
    private Label Vheure;

    @FXML
    private Label Vlieu;

    @FXML
    private Label Vsujet;

    private Formation formation ;
    public DetailsFormation() throws SQLException {
        // Initialisation des éléments graphiques
    }
    public void setData(Formation formation) throws SQLException {
        this.formation = formation;

        Vsujet.setText(formation.getSujet());
        Vdate.setText(String.valueOf(formation.getDate()));
        Vheure.setText(String.valueOf(formation.getHeure()));
        Vlieu.setText(formation.getLieu());


    }
    @FXML
    void retourner(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeFormations.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btn_retour.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

    }

}