package Controllers;

import entities.Formation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Services.FormationCrud;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UpdateFormation {

    public static Formation formation;

    @FXML
    private DatePicker MyDatepicker;

    @FXML
    private TextField UdateTF;

    @FXML
    private TextField UheureTF;

    @FXML
    private TextField UlieuTF;

    @FXML
    private TextField UsujetTF;

    @FXML
    private Button modifier;
    private final FormationCrud fc;

    public UpdateFormation() throws SQLException {
        fc = new FormationCrud();
    }
    @FXML
    void getDate(ActionEvent event) {
        LocalDate myDate = MyDatepicker.getValue();
        if (myDate != null) {
            String myFormattedDate = myDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            UdateTF.setText(myFormattedDate);
        } else {
            UdateTF.clear();
        }
    }
    public void initialize() {
        if (formation != null) {
            UsujetTF.setText(formation.getSujet());
         UdateTF.setText(formation.getDate().toString());
           UheureTF.setText(formation.getHeure().toString());
            UlieuTF.setText(formation.getLieu());

        }
    }

    @FXML
    void modifierFormation(ActionEvent event) throws IOException {
        String Sujet = UsujetTF.getText();
        LocalDate Date = LocalDate.parse(UdateTF.getText());
        LocalTime Heure = LocalTime.parse(UheureTF.getText());
        String Lieu = UlieuTF.getText();


        if (Sujet.isEmpty() ||  Lieu.isEmpty() ) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs!");
        } else {
            try {
                Formation c = new Formation();
                c.setSujet(Sujet);
                c.setDate(Date);
                c.setHeure(Heure);
                c.setLieu(Lieu);
                c.setId(formation.getId());
                FormationCrud sc = new FormationCrud();
                sc.update(c);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Formation modifié avec succès");
                clearFields();
                loadListeFomationsFXML(event);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la modification du Formation");
                ex.printStackTrace();
            }
        }
    }

    private void loadListeFomationsFXML(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ListeFormations.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void clearFields() {
        UsujetTF.clear();
        UheureTF.clear();
        UdateTF.clear();
        UlieuTF.clear();

    }




}