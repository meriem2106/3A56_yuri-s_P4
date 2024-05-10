
package Controllers.FrontOffice;

import entities.Guide;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import services.GuideCrud;

import java.net.URL;
import java.util.ResourceBundle;

public class AjouterGuide implements Initializable {

//    ObservableList<String> villeList = FXCollections.observableArrayList(
//            "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa", "Jendouba",
//            "Kairouan", "Kasserine", "Kébili", "Kef", "Mahdia", "Manouba", "Médenine",
//            "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine",
//            "Tozeur", "Tunis", "Zaghouan");
//    ObservableList<String> langueList = FXCollections.observableArrayList(
//            "Arabe","Français", "Anglais", "Allemand", "Espagnol", "Italien");

    @FXML
    private Button ajoutguide;

    @FXML
    private TextField decGTF;

    @FXML
    private ChoiceBox<String> lgChoiceBox;

    @FXML
    private TextField nomGTF;

    @FXML
    private TextField prenomGTF;

    @FXML
    private ComboBox<String> villeComboBox;

    @FXML
    void ajouterguide(ActionEvent event) {
        String nom = nomGTF.getText();
        String prenom = prenomGTF.getText();
        String ville = villeComboBox.getValue();
        String langueParlee = lgChoiceBox.getValue();
        String description = decGTF.getText();

        // Créer une instance de Guide avec les valeurs récupérées
        Guide guide = new Guide(nom, prenom, ville, langueParlee, description);
        GuideCrud GC = new GuideCrud();
        GC.create(guide);
        clearFieldsGuide();
    }
    @FXML
    private void clearFieldsGuide() {
        nomGTF.clear();
        villeComboBox.setValue(null);
        lgChoiceBox.setValue(null);
        decGTF.clear();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> villeList = FXCollections.observableArrayList(
                "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa", "Jendouba",
                "Kairouan", "Kasserine", "Kébili", "Kef", "Mahdia", "Manouba", "Médenine",
                "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine",
                "Tozeur", "Tunis", "Zaghouan");
        ObservableList<String> langueList = FXCollections.observableArrayList(
                "Arabe","Français", "Anglais", "Allemand", "Espagnol", "Italien");

      villeComboBox.setItems(villeList);
       lgChoiceBox.setItems(langueList);
    }
}
