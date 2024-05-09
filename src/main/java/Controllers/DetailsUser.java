package Controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import Entities.User;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetailsUser {

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

    @FXML
    private Button ban_btn;

    private UserService userService = new UserService();

    @FXML
    void delete(ActionEvent event) throws IOException {
        UserService userService = new UserService();
        userService.supprimerUser(ItemUser.u.getId());

        Parent page1 = FXMLLoader.load(getClass().getResource("/dashboardAdmin.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void edit(ActionEvent event) throws IOException {
        int id = ItemUser.u.getId();

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

            user.setImage(ItemUser.u.getImage());
            user.setFile(ItemUser.u.getFile());

            user.setRoles(ItemUser.u.getRoles());


            userService.modifierUser(user, id);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Modifié");
            alert.setContentText("User Modifié !");
            alert.show();

            Parent page1 = FXMLLoader.load(getClass().getResource("/dashboardAdmin.fxml"));
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
    void initialize() {
        String imagePath = "C:\\final\\public\\uploads\\files\\" + ItemUser.u.getFile();

        nom_user.setText(ItemUser.u.getNom());
        prenom_user.setText(ItemUser.u.getPrenom());
        email_user.setText(ItemUser.u.getEmail());
        password_user.setText(String.valueOf(ItemUser.u.getPassword()));
        num_user.setText(ItemUser.u.getNum());
        LocalDate localDate = convertToLocalDate(ItemUser.u.getDateNaissance());
        date_user.setValue(localDate);

        try {
            image_user.setImage(new Image(new FileInputStream(imagePath)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        if (ItemUser.u.getStatus().equals("ACTIF"))
        {
            ban_btn.setText("BAN");
        }
        else
        {
            ban_btn.setText("UNBAN");
        }
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    @FXML
    void retour(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("/dashboardAdmin.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ban(ActionEvent event) throws IOException {
        if (ItemUser.u.getStatus().equals("ACTIF"))
        {
            ItemUser.u.setStatus("INACTIF");
        }
        else
        {
            ItemUser.u.setStatus("ACTIF");
        }

        userService.modifierUser(ItemUser.u, ItemUser.u.getId());

        Parent page1 = FXMLLoader.load(getClass().getResource("/dashboardAdmin.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}
