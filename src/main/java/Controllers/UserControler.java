package Controllers;

import Entities.Role;
import Entities.User;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
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
    void SaveUser(ActionEvent event) {
        UserService us = new UserService();
        User user1 =  new User(nom.getText(), prenom.getText(), Integer.parseInt(numtel.getText()),email.getText(),password.getText(),Role.CLIENT);
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
    }

}
