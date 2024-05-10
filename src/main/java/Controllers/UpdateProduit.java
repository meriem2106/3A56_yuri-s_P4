package Controllers;

import entities.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Services.ProduitCrud;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

public class UpdateProduit {

    public static Produit produit;
    @FXML
    private TextField nomTF;


    @FXML
    private ComboBox<String> categTF;

    @FXML
    private TextField DescTF;

    @FXML
    private TextField OrigTF;

    @FXML
    private TextField ImgTF;

    @FXML
    private TextField MatTF;

    @FXML
    private Button imgFx;

    @FXML
    private Button modifier;

    private final ProduitCrud pc;

    public UpdateProduit() throws SQLException {
        pc = new ProduitCrud();
    }

    public void initialize() {

            ObservableList<String> categories = FXCollections.observableArrayList(
                    "ART DE LA TABLE", "VÊTEMENTS", "MAISON & DÉCORATION", "BIJOUX & ACCESSOIRES");
            categTF.setItems(categories);

        if (produit != null) {
            nomTF.setText(produit.getNom());
            DescTF.setText(produit.getDescription());
            OrigTF.setText(produit.getOrigine());
            MatTF.setText(produit.getMatiere());
            categTF.getValue();
            ImgTF.setText(produit.getImage());
        }

    }

    @FXML
    void modifierProduit(ActionEvent event) throws IOException {
        String Nom = nomTF.getText();
        String Description = DescTF.getText();
        String Origine = OrigTF.getText();
        String Categorie = categTF.getValue();
        String Matiere = MatTF.getText();
        String Image = ImgTF.getText();

        if (Nom.isEmpty() || Description.isEmpty() || Origine.isEmpty() || Categorie.isEmpty() || Matiere.isEmpty() || Image.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs!");
        } else {
            try {
                Produit c = new Produit();
                c.setNom(Nom);
                c.setDescription(Description);
                c.setOrigine(Origine);
                c.setCateg(Categorie);
                c.setMatiere(Matiere);
                c.setImage(Image);
                c.setId(produit.getId());
                ProduitCrud sc = new ProduitCrud();
                sc.update(c);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit modifié avec succès");
                clearFields();
                loadListeProduitsFXML(event);
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
        DescTF.clear();
        OrigTF.clear();
        MatTF.clear();
        ImgTF.clear();
    }

    private void loadListeProduitsFXML(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ListeProduits.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void RechargerImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            File copiedFile = new File(System.getProperty("imagePathforCopy") + selectedFile.getName());
            Files.copy(selectedFile.toPath(), copiedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            ImgTF.setText(copiedFile.getName());
        }

    }
    @FXML
    void choisirCateg(ActionEvent event) {

    }

}
