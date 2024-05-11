package Controllers;

import entities.Hebergement;
import services.HebergementCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

public class UpdateHebergement {

    public static Hebergement hebergement;

    @FXML
    private TextField nomTF;

    @FXML
    private TextField emailTF;

    @FXML
    private Button imFX1;

    @FXML
    private Button imFX2;

    @FXML
    private TextField imaQr;

    @FXML
    private TextField prixTF;

    @FXML
    private TextField imaTF;

    @FXML
    private TextField locTF;

    @FXML
    private TextField teleTF;

    @FXML
    private TextField passwordTF;


    @FXML
    private Button modifier;

    private final HebergementCrud pc;

    public UpdateHebergement() throws SQLException {
        pc = new HebergementCrud();
    }

 void initialize() {
        if (hebergement != null) {
            nomTF.setText(hebergement.getNom());
            emailTF.setText(hebergement.getEmail());
            passwordTF.setText(hebergement.getPassword());
            teleTF.setText(hebergement.getTelephone());
            locTF.setText(hebergement.getLocalisation());
            prixTF.setText(hebergement.getPrix());
            imaTF.setText(hebergement.getImage());
            imaQr.setText(hebergement.getImage2());
        }
    }

    @FXML
    void modifierProduit(ActionEvent event) {
        String nom = nomTF.getText();
        String email = emailTF.getText();
        String password = passwordTF.getText();
        String localisation = locTF.getText();
        String prix = prixTF.getText();
        String telephone = teleTF.getText();
        String image = imaTF.getText();
        String image2 = imaQr.getText();

        if (nom.isEmpty() || email.isEmpty() || password.isEmpty() || localisation.isEmpty() || prix.isEmpty() || telephone.isEmpty() || image.isEmpty() || image2.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs!");
        } else {
            try {
                Hebergement hebergement = new Hebergement();
                hebergement.setNom(nom);
                hebergement.setEmail(email);
                hebergement.setPassword(password);
                hebergement.setLocalisation(localisation);
                hebergement.setPrix(prix);
                hebergement.setTelephone(telephone);
                hebergement.setImage(image);
                hebergement.setImage2(image2);
                hebergement.setId(hebergement.getId()); // Assurez-vous de définir l'identifiant correctement

                pc.update(hebergement);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit modifié avec succès");
                clearFields();
                loadListeHebergementsFXML(event);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la modification du produit");
                ex.printStackTrace();
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void clearFields() {
        nomTF.clear();
        emailTF.clear();
        passwordTF.clear();
        locTF.clear();
        prixTF.clear();
        teleTF.clear();
        imaTF.clear();
        imaQr.clear();
    }

    private void loadListeHebergementsFXML(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListeHebergements.fxml"));
            Scene scene = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void RechargerImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            File copiedFile = new File(System.getProperty("imagePathforCopy") + selectedFile.getName());
            try {
                Files.copy(selectedFile.toPath(), copiedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                imaTF.setText(copiedFile.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
