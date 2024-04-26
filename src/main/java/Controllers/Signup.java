package Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import Entities.User;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

public class Signup {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker date_pick;

    @FXML
    private TextField email_tf;

    @FXML
    private TextField nom_tf;

    @FXML
    private TextField numtel_tf;

    @FXML
    private TextField password_tf;

    @FXML
    private TextField prenom_tf;

    @FXML
    private ImageView image;

    @FXML
    private ListView<String> rolesListView;

    private String url_image;
    private File selectedFile;

    @FXML
    void initialize() {

        rolesListView.getItems().addAll("Proprietaire", "Organisateur", "Artisan", "Famille d’accueil");
        rolesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        image.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });

        image.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    for (File file : db.getFiles()) {
                        url_image = file.getName();
                        selectedFile = new File(file.getAbsolutePath());
                        System.out.println("Drag and drop file done and path=" + file.getAbsolutePath());//file.getAbsolutePath(:\"C:\Users\X\Desktop\ScreenShot.6.png"
                        image.setImage(new Image("file:" + file.getAbsolutePath()));
                        File destinationFile = new File("C:\\xampp\\htdocs\\user_images\\" + file.getName());
                        try {
                            // Copy the selected file to the destination file
                            Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        image.setImage(new Image("/images/drag-drop.gif"));
    }

    @FXML
    void signup(ActionEvent event) throws IOException {

        UserService userService = new UserService();

        if (userService.test_Email(email_tf.getText()) && userService.test_Tel(numtel_tf.getText()) && userService.test_dateNaissance(date_pick.getValue()))
        {
            List<String> selectedRoles = new ArrayList<>(rolesListView.getSelectionModel().getSelectedItems());
            selectedRoles.add("Touriste");

            LocalDate localDate = date_pick.getValue();
            Date dateNaissance = java.sql.Date.valueOf(localDate);

            User user = new User(nom_tf.getText(), password_tf.getText(), email_tf.getText(), password_tf.getText(), dateNaissance, numtel_tf.getText(), selectedRoles, null, url_image);

            try {
                userService.ajouterUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Ajoutée");
            alert.setContentText("User Ajoutée !");
            alert.show();

            Parent page1 = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            if (!userService.test_Email(email_tf.getText()))
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Verfiy your email address");
                alert.setContentText("Verfiy your email address");
                alert.show();
            } else if (!userService.test_Tel(numtel_tf.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Verfiy your phone number");
                alert.setContentText("Verfiy your phone number");
                alert.show();
            } else if (!userService.test_dateNaissance(date_pick.getValue())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You are under 16, you cant sign up");
                alert.setContentText("You are under 16, you cant sign up");
                alert.show();
            }
        }
    }

    @FXML
    void add_image(MouseEvent event) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fc.setTitle("Veuillez choisir l'image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {

            Image image1 = new Image(selectedFile.toURI().toString());

            image.setImage(image1);

            File destinationFile = new File("C:\\xampp\\htdocs\\user_images\\" + selectedFile.getName());

            url_image = selectedFile.getName();

            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println(e);
            }

        }
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
