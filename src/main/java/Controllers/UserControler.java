package Controllers;

import Entities.Role;
import Entities.User;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class   UserControler{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bttn;

    @FXML
    private TextField email;

    @FXML
    private TextField nom;

    @FXML
    private TextField numtel;

    @FXML
    private TextField password;

    @FXML
    private TextField prenom;

    @FXML
    private ImageView image_user;

    public String url_image;
    private String path;
    File selectedFile;

    @FXML
    void SaveUser(ActionEvent event) {
        UserService us = new UserService();
        User user1 =  new User(nom.getText(), prenom.getText(), Integer.parseInt(numtel.getText()),email.getText(),password.getText(),Role.CLIENT, url_image);
        try {
            us.ajouterUser(user1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Ajoutée");
        alert.setContentText("User Ajoutée !");
        alert.show();
    }
    @FXML
    void initialize() {

        image_user.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });

        image_user.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    path = null;
                    for (File file : db.getFiles()) {
                        path = file.getName();
                        selectedFile = new File(file.getAbsolutePath());
                        System.out.println("Drag and drop file done and path=" + file.getAbsolutePath());//file.getAbsolutePath()="C:\Users\X\Desktop\ScreenShot.6.png"
                        image_user.setImage(new Image("file:" + file.getAbsolutePath()));
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        image_user.setImage(new Image("file:C:\\Users\\user\\Desktop\\drag-drop.gif"));

    }

    @FXML
    void image_add(MouseEvent event) {

        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.home") + "\\Desktop"));
        fc.setTitle("Veuillez choisir l'image");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {

            // Load the selected image into the image view
            Image image1 = new Image(selectedFile.toURI().toString());

            //url_image = file.toURI().toString();
            System.out.println(selectedFile.toURI().toString());
            image_user.setImage(image1);

            // Create a new file in the destination directory
            File destinationFile = new File("C:\\xampp\\htdocs\\user_images\\" + selectedFile.getName());
            // url_image = "C:\\xampp\\htdocs\\image_trippie_cov\\" + file.getName();
            url_image = selectedFile.getName();

            try {
                // Copy the selected file to the destination file
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.err.println(e);
            }

        }

    }

}
