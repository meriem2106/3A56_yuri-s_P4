package Controllers.FrontOffice;

import entities.Hotel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import Services.ServiceHotel;
import javafx.stage.Stage;


import javafx.scene.input.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.Node;


public class AjouterHotel implements Initializable {

    ObservableList<String> villeHotelList = FXCollections.
            observableArrayList("Nabeul", "Sousse", "Ain Drahem", "Djerba");

    ObservableList<String> dispoHotelList = FXCollections.
            observableArrayList("Disponible", "Complet");

    ObservableList<Integer> nbetoilesHotelList = FXCollections.
            observableArrayList(3, 4, 5);


    @FXML
    private TextField PrixHotel;

    @FXML
    private Button ajoutHotel;

    @FXML
    private TextArea desHotel;


    @FXML
    private TextField imageHotel;

    @FXML
    private ComboBox<String> villeHotel;

    @FXML
    private ComboBox<Integer> nbetoilesHotel;

    @FXML
    private ComboBox<String> dispoHotel;

    @FXML
    private TextField mailHotel;


    @FXML
    private TextField nomHotel;

    @FXML
    private TextField telHotel;

    @FXML
    private Button upload;

    ServiceHotel sh = new ServiceHotel();
    private String imagePath;

    @FXML
    private TextField locHotel;

    public AjouterHotel() throws SQLException {
    }

    @FXML
    void AjouterHotel(ActionEvent event) {
        try {
            // Vérifiez d'abord si l'adresse e-mail est valide
            if (!isValidEmail(mailHotel.getText())) {
                // Affichez une alerte si l'e-mail n'est pas valide
                showInvalidEmailAlert();
                return; // Arrêtez l'exécution de la méthode
            }

            // Le reste de votre logique d'ajout d'hôtel ici
            sh.ajouter(new Hotel(nbetoilesHotel.getValue(), PrixHotel.getText(), nomHotel.getText(), mailHotel.getText(), telHotel.getText(), locHotel.getText(), villeHotel.getValue(), dispoHotel.getValue(), desHotel.getText(), imageHotel.getText()));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/Home.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (NumberFormatException e) {
            // Gérer l'erreur de conversion de la chaîne en entier
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Méthode pour valider l'adresse e-mail avec une expression régulière
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
        villeHotel.setItems(villeHotelList);
        dispoHotel.setItems(dispoHotelList);
        nbetoilesHotel.setItems(nbetoilesHotelList);
        imagePath="";

    }

    @FXML
    void upload(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // Extraire uniquement le nom de fichier sans le chemin complet
            String fileName = file.getName();

            // Set the image file name in the TextField
            imageHotel.setText(fileName);

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


