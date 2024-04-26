package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangePassword {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField new_pass;

    @FXML
    private TextField rt_new_pass;

    @FXML
    void change_password(ActionEvent event) throws IOException {
        UserService su = new UserService();
        int id = su.rechercheUserByEmail(ForgotPassword.emailaddress).getId();

        String password = new_pass.getText();
        String c_password = rt_new_pass.getText();


        if(password.equals(c_password))
        {
            su.modifier_password(id,password);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Password Changed !!");
            alert.setContentText("Password Changed !!");
            alert.show();

            Parent page1 = FXMLLoader.load(getClass().getResource("/Login.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
            System.out.println("verifier votre mdp");
    }

    @FXML
    void initialize() {
    }

}
