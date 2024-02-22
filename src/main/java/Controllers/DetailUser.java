package Controllers;

import Entities.User;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailUser {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField email_detail;

    @FXML
    private ImageView image_detail;

    @FXML
    private TextField nom_detail;

    @FXML
    private TextField numtel_detail;

    @FXML
    private TextField password_detail;

    @FXML
    private TextField prenom_detail;

    @FXML
    private TextField role_detail;

    @FXML
    void Modifier(ActionEvent event) {
        try {
            int id = item_user.u.getId();
            String nom = nom_detail.getText();
            String prenom = prenom_detail.getText();
            int numtel = Integer.parseInt(numtel_detail.getText());
            String email = email_detail.getText();
            String password = password_detail.getText();

            User user = new User(nom,prenom,numtel,email,password,item_user.u.getRole(),item_user.u.getImage());

            UserService userService = new UserService();

            userService.modifierUser(user, id);

            Parent page1 = FXMLLoader.load(getClass().getResource("/Affichage.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void Supprimer(ActionEvent event) {
        try {
            UserService userService = new UserService();

            userService.supprimerUser(item_user.u.getId());

            Parent page1 = FXMLLoader.load(getClass().getResource("/Affichage.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    void initialize() {
        String imagePath = "C:\\xampp\\htdocs\\user_images\\" + item_user.u.getImage();
        System.out.println(imagePath);
        //id_co_TF.setText(String.valueOf(Afficher_CovController.id_co));
        nom_detail.setText(String.valueOf(item_user.u.getNom()));
        prenom_detail.setText(String.valueOf(item_user.u.getPrenom()));
        role_detail.setText(item_user.u.getRole().toString());
        email_detail.setText(String.valueOf(item_user.u.getEmail()));
        password_detail.setText(String.valueOf(item_user.u.getPassword()));
        numtel_detail.setText(String.valueOf(item_user.u.getNumtel()));
        try {
            image_detail.setImage(new Image(new FileInputStream(imagePath)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }
}
