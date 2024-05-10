package Controllers.FrontOffice;

import entities.Evenement;
import entities.Guide;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.EvenementCrud;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.ResourceBundle;

public class AjouterEvenement implements Initializable {

    ObservableList<String> catList = FXCollections.observableArrayList(
            "Excursion", "Soirée", "Circuit d'hiver", "Circuit d'été", "Séminaire");

    ObservableList<String> conList = FXCollections.observableArrayList(
            "Artistique", "Culturel", "Sportif");

    ObservableList<String> tranList = FXCollections.observableArrayList(
            "véhicule personelle", "bus");

    ObservableList<String> lieuList = FXCollections.observableArrayList(
            "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa", "Jendouba",
            "Kairouan", "Kasserine", "Kébili", "Kef", "Mahdia", "Manouba", "Médenine",
            "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine",
            "Tozeur", "Tunis", "Zaghouan");
    @FXML
    private Button ajouter;

    @FXML
    private ComboBox<String> catComboBox;

    @FXML
    private ComboBox<String> conComboBox;

    @FXML
    private DatePicker debutDatePicker;

    @FXML
    private TextField decTF;

    @FXML
    private DatePicker finDatePicker;

    @FXML
    private Button imgFx;

    @FXML
    private Button acceder;

    @FXML
    private TextField imgTF;

    @FXML
    private ComboBox<String> lieuComboBox;

    @FXML
    private TextField nbTF;

    @FXML
    private TextField nomTF;

    @FXML
    private ComboBox<String> tranComboBox;


    EvenementCrud EC = new EvenementCrud();
    private String imagePath;

    public Guide guide;

    @FXML
    private void ajouter(ActionEvent event) {
        // Vérifier si tous les champs sont remplis
        if (nomTF.getText().isEmpty() || decTF.getText().isEmpty() || imgTF.getText().isEmpty() ||
                debutDatePicker.getValue() == null || finDatePicker.getValue() == null ||
                lieuComboBox.getValue() == null || tranComboBox.getValue() == null ||
                catComboBox.getValue() == null || conComboBox.getValue() == null || nbTF.getText().isEmpty()) {
            showAlert("Champs incomplets", "Vous devez remplir tous les champs.");
            return;
        }

        // Récupérer la valeur saisie dans le champ nbTF
        String nbplaces = nbTF.getText();

        // Vérifier si la valeur saisie est un entier valide
        try {
            int nb = Integer.parseInt(nbplaces);

            // Récupérer les autres valeurs
            String imgPath = imgTF.getText();
            String nom = nomTF.getText();
            String contexte = conComboBox.getValue();
            String cat = catComboBox.getValue();
            String lieu = lieuComboBox.getValue();
            String transport = tranComboBox.getValue();
            String description = decTF.getText();

            // Récupérer les dates sélectionnées
            Date debutDate = java.sql.Date.valueOf(debutDatePicker.getValue());
            Date finDate = java.sql.Date.valueOf(finDatePicker.getValue());

            // Vérifier si la date de début est ultérieure à la date de fin
            if (debutDate.after(finDate)) {
                showAlert("Date invalide", "La date de début ne peut pas être ultérieure à la date de fin.");
                return; // Arrêter l'exécution de la méthode si la condition est vraie
            }

            // Créer l'événement
            Evenement evenement = new Evenement(finDate, debutDate, imgPath, description, nb, transport, lieu, contexte, cat, nom);

            // Ajouter l'événement à la base de données
            EvenementCrud EC = new EvenementCrud();
            EC.create(evenement);

            // Effacer les champs après l'ajout
            clearFields();

            // Afficher un message de succès
            showAlert("Succès", "Événement ajouté avec succès.");
        } catch (NumberFormatException e) {
            // Afficher une alerte si la conversion échoue
            showAlert("Nombre de place invalide", "Veuillez saisir un nombre entier pour le nombre de places.");
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void saveImageToFolder(File file) {
        try {
            // Define the directory path where you want to save the image
            String directoryPath = "src/main/java/images";

            // Create the directory if it doesn't exist
            Path directory = Paths.get(directoryPath);
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
            }

            // Define the destination file path
            String destinationFilePath = directoryPath + File.separator + file.getName();

            // Copy the uploaded image file to the destination folder
            Files.copy(file.toPath(), Paths.get(destinationFilePath));

            System.out.println("Image saved to: " + destinationFilePath);
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
        }
    }

    @FXML
    private void chargerImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));

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
            imgTF.setText(copiedFile.getName());

            // Assigner l'image au produit
            // pc.setImage(image);
        }
    }


    private void clearFields() {
        catComboBox.setValue(null);
        conComboBox.setValue(null);
        decTF.clear();
        imgTF.clear();
        debutDatePicker.setValue(null);
        finDatePicker.setValue(null);
        lieuComboBox.setValue(null);
        nbTF.clear();
        nomTF.clear();
        tranComboBox.setValue(null);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lieuComboBox.setItems(lieuList);
        conComboBox.setItems(conList);
        catComboBox.setItems(catList);
        tranComboBox.setItems(tranList);
    }

    public void saveImageToFolder(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", ".jpeg", ".gif"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // Set the image path in the TextField
            imgTF.setText(file.getAbsolutePath());

            saveImageToFolder(file);
        }
    }

    @FXML
    void acceder(ActionEvent event) throws IOException {
        // Obtenir la scène actuelle
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenement.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);


        stage.show();
    }
    }
