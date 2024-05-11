package Controllers;

import entities.Hebergement;
import services.HebergementCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

public class AjouterHebergement {

    @FXML
    private Button ajouter;


    @FXML
    private TextField emailTF;

    @FXML
    private Button imFX1;

    @FXML
    private Button imFX2;

    @FXML
    private TextField imaQr;

    @FXML
    private TextField imaTF;

    @FXML
    private TextField locTF;

    @FXML
    private TextField nomTF;
    @FXML
    private TextField teleTF;
    @FXML
    private TextField passwordTF;

    private final HebergementCrud pc;
    @FXML
    private TextField prixTF;
    public AjouterHebergement() throws SQLException {
        pc = new HebergementCrud();
        String imagesDirectory = "C:\\Users\\Amine\\tzzt\\public\\public\\uploads\\images\\HImage";
        System.setProperty("imagePathforCopy", imagesDirectory);
    }



    @FXML
    public void initialize() {
//        // Créer une liste observable pour les catégories
//        ObservableList<String> categories = FXCollections.observableArrayList(Produit.Categorie.BIJOUX_ET_ACCESSOIRES.toString(),Produit.Categorie.ART_DE_LA_TABLE.toString(),
//                Produit.Categorie.MAISON_ET_DECORATION.toString(),
//                Produit.Categorie.VETEMENTS.toString());
//
//        // Ajouter les catégories à la liste déroulante (ComboBox)
//        CategCB.setItems(categories);

    }


    @FXML
    public void Ajouter(ActionEvent actionEvent) {
//
        if (nomTF.getText().isEmpty()){
            Alert alertType=new Alert(Alert.AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Nom est vide .Ce champ est obligatoire. Veuillez le remplir");
            alertType.show();
            return;
        }
        if (emailTF.getText().isEmpty()){
            Alert alertType=new Alert(Alert.AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("email est vide.Ce champ est obligatoire. Veuillez le remplir");
            alertType.show();
            return;
        }

        if (passwordTF.getText().isEmpty()){
            Alert alertType=new Alert(Alert.AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Ce champ est obligatoire. Veuillez le remplir");
            alertType.show();
            return;
        }

        if (locTF.getText().isEmpty()){
            Alert alertType=new Alert(Alert.AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Ce champ est obligatoire. Veuillez le remplir");
            alertType.show();
            return;
        }



        if (prixTF.getText().isEmpty()){
            Alert alertType=new Alert(Alert.AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Ce champ est obligatoire. Veuillez le remplir");
            alertType.show();
            return;
        }
        if (teleTF.getText().isEmpty()){
            Alert alertType=new Alert(Alert.AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Ce champ est obligatoire. Veuillez le remplir");
            alertType.show();
            return;
        }





        else {
            try {
                Hebergement e = new Hebergement();
                e.setNom(nomTF.getText());
                e.setEmail(emailTF.getText());
                e.setPassword(passwordTF.getText());
                e.setLocalisation(locTF.getText());
                e.setPrix(prixTF.getText());
                e.setTelephone(teleTF.getText());
                e.setImage(imaTF.getText());
                e.setImage2(imaQr.getText());

                //e.setCategorieEvenement(cbcategorie.getValue());
                //e.setImage(uploadpath);
                HebergementCrud sc = new HebergementCrud();
                sc.create(e);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Produit ajouté avec succès");
                alert.showAndWait();

                // Vider les champs
                nomTF.clear();
                emailTF.clear();
                passwordTF.clear();
                locTF.clear();
                prixTF.clear();
                teleTF.clear();
                imaTF.clear();
                imaQr.clear();

                //tf_logo.clear();
                //cbcategorie.getSelectionModel().clearSelection();

            }

            catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'ajout du Hebergementt");
                alert.showAndWait();
                ex.printStackTrace();
            }
        }
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/ListeHebergemnets.fxml"));
            nomTF.getScene().setRoot(root);
            //tf_nbMembres.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


    }
    @FXML
    private void chargerImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers image", ".png", ".jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "."));

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {

            String newImagesPath = "image/";


            File copiedFile = new File(newImagesPath +  selectedFile.getName());
            Files.copy(selectedFile.toPath(),
                    copiedFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            imaTF.setText(copiedFile.getName());


        }
    }




    @FXML
    void chargerQRImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers image", ".png", ".jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "."));

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Chemin vers le répertoire des images
            String imagesPath = System.getProperty("imagePathforCopy");

            // Créer une copie du fichier sélectionné dans le répertoire des images
            File copiedFile = new File(imagesPath + selectedFile.getName());
            Files.copy(selectedFile.toPath(),
                    copiedFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            // Charger l'image depuis le fichier sélectionné
            // Image image = new Image(copiedFile.toURI().toString());

            // Afficher l'URL de l'image dans le TextField
            imaQr.setText(copiedFile.getName());


        }

    }

    public void modifierProduit(ActionEvent actionEvent) {
    }
}
