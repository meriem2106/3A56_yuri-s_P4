package Controllers;

import entities.Formation;
import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import Services.FormationCrud;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AjouterFormation {

    @FXML
    private DatePicker MyDatepicker;

    @FXML
    private TextField dateTF;

    @FXML
    private TextField heureTF;

    @FXML
    private TextField lieuTF;

    @FXML
    private TextField sujetTF;

    @FXML
    void Ajouter(ActionEvent event) {
        try {
            // Récupération des valeurs des champs
            String sujet = sujetTF.getText();
            LocalDate date = MyDatepicker.getValue();
            LocalTime heure = LocalTime.parse(heureTF.getText());
            String lieu = lieuTF.getText();

            // Validation des champs
            if (sujet.isEmpty() || date == null || heure == null || lieu.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;
            }

            // Création de l'objet Formation
            Formation formation = new Formation(sujet, date, heure, lieu,new Produit());

            // Utilisation d'une méthode de service pour enregistrer la formation
            FormationCrud formationCrud = new FormationCrud();
            formationCrud.create(formation);

            // Affichage d'un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Formation ajoutée avec succès.");
            alert.showAndWait();

            // Effacer les champs après ajout
            sujetTF.clear();
            MyDatepicker.getEditor().clear();
            heureTF.clear();
            lieuTF.clear();

        } catch (Exception e) {
            // Gestion des exceptions
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de l'ajout de la formation.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }


    @FXML
    void getDate(ActionEvent event) {
        LocalDate myDate = MyDatepicker.getValue();
        if (myDate != null) {
            String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
            dateTF.setText(myFormattedDate);
        } else {
            dateTF.clear();
        }
    }
}
