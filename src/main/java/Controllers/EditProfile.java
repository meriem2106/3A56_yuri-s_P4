package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import entities.User;
import services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class EditProfile {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker date_user;

    @FXML
    private TextField email_user;

    @FXML
    private ImageView image_user;

    @FXML
    private TextField nom_user;

    @FXML
    private TextField num_user;

    @FXML
    private TextField password_user;

    @FXML
    private TextField prenom_user;
    int id = Login.id;

    @FXML
    void edit(ActionEvent event) throws IOException {
        UserService userService = new UserService();

        if (userService.test_Email(email_user.getText()) && userService.test_Tel(num_user.getText()) && userService.test_dateNaissance(date_user.getValue()))
        {
            User user = new User();
            user.setNom(nom_user.getText());
            user.setPrenom(prenom_user.getText());
            user.setNum(num_user.getText());
            user.setEmail(email_user.getText());
            user.setPassword(password_user.getText());
            LocalDate localDate = date_user.getValue();
            Date dateNaissance = java.sql.Date.valueOf(localDate);
            user.setDateNaissance(dateNaissance);

            User user1 = userService.rechercheUser(id).get(0);

            user.setImage(user1.getImage());
            user.setFile(user1.getFile());

            user.setRoles(user1.getRoles());


            userService.modifierUser(user, id);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Modifié");
            alert.setContentText("User Modifié !");
            alert.show();

            Parent page1 = FXMLLoader.load(getClass().getResource("/FrontOffice/Home.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            if (!userService.test_Email(email_user.getText()))
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Verfiy your email address");
                alert.setContentText("Verfiy your email address");
                alert.show();
            } else if (!userService.test_Tel(num_user.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Verfiy your phone number");
                alert.setContentText("Verfiy your phone number");
                alert.show();
            } else if (!userService.test_dateNaissance(date_user.getValue())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("You are under 16, you cant sign up");
                alert.setContentText("You are under 16, you cant sign up");
                alert.show();
            }
        }
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("/FrontOffice/Home.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void initialize() {
        UserService userService = new UserService();
        User user = userService.rechercheUser(id).get(0);

        String imagePath = "C:\\Users\\THINKPAD\\Desktop\\finalaa\\public\\uploads\\files\\" + user.getFile();

        nom_user.setText(user.getNom());
        prenom_user.setText(user.getPrenom());
        email_user.setText(user.getEmail());
        password_user.setText(String.valueOf(user.getPassword()));
        num_user.setText(user.getNum());
        LocalDate localDate = convertToLocalDate(user.getDateNaissance());
        date_user.setValue(localDate);

        try {
            image_user.setImage(new Image(new FileInputStream(imagePath)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

}
