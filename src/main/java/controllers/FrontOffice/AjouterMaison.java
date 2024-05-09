package controllers.FrontOffice;


import entities.Maison;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import services.ServiceMaison;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjouterMaison implements Initializable {

    ObservableList<String> villeMaisonList = FXCollections.
            observableArrayList("Nabeul", "Sousse", "Ain Drahem", "Djerba");

    ObservableList<String> dispoMaisonList = FXCollections.
            observableArrayList("Disponible", "Complet");


    @FXML
    private TextField PrixMaison;

    @FXML
    private Button ajoutMaison;

    @FXML
    private TextField capaciteMaison;

    @FXML
    private TextArea desMaison;

    @FXML
    private ComboBox<String> dispoMaison;

    @FXML
    private TextField imageMaison;

    @FXML
    private TextField locMaison;

    @FXML
    private TextField mailMaison;

    @FXML
    private TextField nbChambresMaison;

    @FXML
    private TextField nomMaison;

    @FXML
    private ComboBox<String> villeMaison;

    @FXML
    private Button upload;

    ServiceMaison sm = new ServiceMaison();
    private String imagePath;

    @FXML
    void AjouterMaison(ActionEvent event) {
        try {
            if (!isValidEmail(mailMaison.getText())) {
                // Affichez une alerte si l'e-mail n'est pas valide
                showInvalidEmailAlert();
                return; // Arrêtez l'exécution de la méthode
            }

            String texteNbChambres = nbChambresMaison.getText();
            int nbChambres = Integer.parseInt(texteNbChambres);

            sm.ajouter(new Maison(nbChambres, nomMaison.getText(), mailMaison.getText(), capaciteMaison.getText(), locMaison.getText(), villeMaison.getValue(), dispoMaison.getValue(), desMaison.getText(), imageMaison.getText(), PrixMaison.getText()));
        } catch (NumberFormatException e) {
            // Gérer l'erreur de conversion de la chaîne en entier
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    private boolean isValidEmail(String email) {
        // Expression régulière pour valider une adresse e-mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Méthode pour afficher une alerte si l'adresse e-mail est invalide
    private void showInvalidEmailAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Adresse e-mail invalide");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez saisir une adresse e-mail valide.");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the ComboBox with the list of locations
        villeMaison.setItems(villeMaisonList);
        dispoMaison.setItems(dispoMaisonList);


    }

    @FXML
    void upload(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // Set the image path in the TextField
            imageMaison.setText(file.getAbsolutePath());

            saveImageToFolder(file);
        }
    }

    private void saveImageToFolder(File file) {
        try {
            // Define the directory path where you want to save the image
            String directoryPath = "src/main/resources/images";

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


}
