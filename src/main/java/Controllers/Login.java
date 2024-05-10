package Controllers;

import Entities.User;
import services.UserService;
import utils.DataSource;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField email_tf;

    @FXML
    private PasswordField password_tf;

    @FXML
    private FontAwesomeIconView ViewPassword;

    static int id;
    static User user = new User();
    private int i = 0;
    private String status;
    private UserService userService = new UserService();
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN = "";

    @FXML
    void initialize() {
    }

    @FXML
    void login(ActionEvent event) {
        String email = email_tf.getText();
        String password = password_tf.getText();

        Connection connection = DataSource.getInstance().getCnx();

        try {
            String query = "SELECT * FROM utilisateur WHERE email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                status = resultSet.getString("status");

                try {  // Handle FXML loading exceptions
                    if (email.equals("admin@tuniworld.tn")) {
                        user.setNom(resultSet.getString("nom"));
                        Parent page1 = FXMLLoader.load(getClass().getResource("/dashboardAdmin.fxml"));
                        Scene scene = new Scene(page1);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } else if (resultSet.getString("status").equals("ACTIF")){
                        id = resultSet.getInt("id");
                        Parent page1 = FXMLLoader.load(getClass().getResource("/Home.fxml"));
                        Scene scene = new Scene(page1);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    }else
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("BANNED");
                        alert.setContentText("This User is Banned");
                        alert.show();
                    }
                } catch (IOException e) {  // Catch FXML loading exceptions
                    System.out.println("Error loading FXML: " + e.getMessage());
                }
            } else {  // User not found
                if (i==3)
                {
                    User user1 = userService.rechercheUserByEmail(email);
                    userService.banUser(user1.getId());

                    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                    Message message = Message.creator(
                                new com.twilio.type.PhoneNumber("+21624746196"),
                                new com.twilio.type.PhoneNumber("+17273085273"),
                                "Tuniworld : You are Banned (3 failed attempts to login)")
                        .create();
                }
                System.out.println("Invalid credentials.");
                i++;

            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    @FXML
    void signup(ActionEvent event) throws IOException {
        Parent page = FXMLLoader.load(getClass().getResource("/signup.fxml"));
        Scene scene = new Scene(page);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void forget_password(MouseEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("/forgotPassword.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void ViewPassword(MouseEvent event) {
        if (password_tf.isVisible()) {

            String realPassword = password_tf.getText();
            TextField textField = new TextField(realPassword);
            textField.setPrefHeight(password_tf.getHeight());
            textField.setPrefWidth(password_tf.getWidth());
            textField.setLayoutX(password_tf.getLayoutX());
            textField.setLayoutY(password_tf.getLayoutY());
            textField.setId("password_tf");
            textField.setOnAction(password_tf.getOnAction());
            textField.setOnKeyPressed(password_tf.getOnKeyPressed());
            textField.setOnKeyReleased(password_tf.getOnKeyReleased());
            textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {
                    password_tf.setText(textField.getText());
                    ((Pane)password_tf.getParent()).getChildren().remove(textField);
                    password_tf.setVisible(true);
                }
            });


            ((Pane)password_tf.getParent()).getChildren().add(textField);
            password_tf.setVisible(false); // Hide the password field
        } else {

            password_tf.setVisible(true);
            ((Pane)password_tf.getParent()).getChildren().removeIf(node -> node.getId() != null && node.getId().equals("password_tf"));
        }
    }
}
