package controllers.FrontOffice;

import controllers.BackOffice.AfficherReservationM;
import controllers.DashboardAdmin;
import controllers.Login;
import entities.Hotel;
import entities.Maison;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceHotel;
import services.ServiceMaison;
import tn.esprit.controllers.ReclamationController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Home  {

    @FXML
    private HBox cardHotel;

    @FXML
    private HBox cardMaison;

    ServiceHotel sh = new ServiceHotel();

    ServiceMaison sm = new ServiceMaison();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView image_user;

    @FXML
    private Label nom_user;

    private int userID;

    public void setData(int id)
    {
        this.userID =id;
    }
    @FXML
    void edit_profile(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("/editProfile.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void hotel(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/AjouterHotel.fxml"));
            Parent root = loader.load();


            AjouterHotel controller = loader.getController();



            // Check if the loading was successful
            if (root != null) {
                // Create a new scene with the loaded view
                Scene scene = new Scene(root);

                // Get the main stage from the event
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Error: Loading ShowHotelB.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void maison(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/AjouterMaison.fxml"));
            Parent root = loader.load();


            AjouterMaison controller = loader.getController();



            // Check if the loading was successful
            if (root != null) {
                // Create a new scene with the loaded view
                Scene scene = new Scene(root);

                // Get the main stage from the event
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Error: Loading ShowHotelB.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() throws FileNotFoundException {

        services.UserService userService = new services.UserService();

        // Check if the user list is not empty before accessing its elements
        List<User> userList = userService.rechercheUser(Login.id);
        if (!userList.isEmpty()) {
            entities.User user = userList.get(0);
            System.out.println(Login.id);
            nom_user.setText(user.getNom());
            try {
                // Load hotels
                for (Hotel hotel : sh.afficher()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FrontOffice/HotelCard.fxml"));
                    VBox cardBox = fxmlLoader.load();
                    HotelCard cardController = fxmlLoader.getController();
                    cardController.setData(hotel);
                    cardHotel.getChildren().add(cardBox);
                }
                // Load houses
                for (Maison maison : sm.afficher()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FrontOffice/MaisonCard.fxml"));
                    VBox cardBoxM = fxmlLoader.load();
                    MaisonCard cardController = fxmlLoader.getController();
                    cardController.setData(maison);
                    cardMaison.getChildren().add(cardBoxM);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Handle the case where the user list is empty, perhaps by logging an error or displaying a message to the user.
            System.out.println("User list is empty.");
        }
    }
    @FXML
    void reclamation(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUser.fxml"));
            Parent root = loader.load();


            ReclamationController controller = loader.getController();



            // Check if the loading was successful
            if (root != null) {
                // Create a new scene with the loaded view
                Scene scene = new Scene(root);

                // Get the main stage from the event
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Set the new scene on the stage
                stage.setScene(scene);
                stage.show();
            } else {
                System.err.println("Error: Loading ShowHotelB.fxml failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
