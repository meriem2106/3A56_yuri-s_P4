package Controllers.BackOffice;

import entities.Hotel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ServiceHotel;
import javafx.geometry.Insets;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AfficherHotel implements Initializable {

    @FXML
    private Circle circle;

    @FXML
    private HBox collectBtn;

    @FXML
    private HBox collectBtn1;

    @FXML
    private HBox collectBtn2;

    @FXML
    private HBox collectBtn3;

    @FXML
    private HBox collectBtn4;

    @FXML
    private HBox collectBtn5;

    @FXML
    private Label collectText2;

    @FXML
    private Label collectText3;

    @FXML
    private Label collectText4;

    @FXML
    private Label collectText5;

    @FXML
    private HBox commandsBtn1;

    @FXML
    private Pane content_area;

    @FXML
    private HBox dashboardBtn;

    @FXML
    private ImageView dashboardIcon;

    @FXML
    private Label dashboardText;

    @FXML
    private Button delete;

    @FXML
    private HBox fundrisingBtn;

    @FXML
    private Button hotels;

    @FXML
    private ListView<Hotel> listHotel;

    @FXML
    private Button maisons;

    @FXML
    private HBox navBarLogout;

    @FXML
    private Text navFullname;

    @FXML
    private HBox productsBtn;

    @FXML
    private Button reservationsH;

    @FXML
    private Button reservationsM;

    @FXML
    private Button show;

    @FXML
    private HBox sideBarLogout;

    @FXML
    private HBox usersBtn;

    @FXML
    private Label usersText;




    ServiceHotel hs = new ServiceHotel();



    @FXML
    void delete(ActionEvent event) {
        Hotel selectedHotel = listHotel.getSelectionModel().getSelectedItem();
        if (selectedHotel != null) {
            try {
                hs.supprimer(selectedHotel.getId());
                refreshTable();
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la suppression de l'hôtel: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {

            System.out.println("Veuillez sélectionner un hôtel à supprimer.");
        }
    }

    @FXML
    public void showHotel(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ShowHotelB.fxml"));
            Parent root = loader.load();


            ShowHotelB controller = loader.getController();


            // Assuming you have a selected hotel from your list view
            Hotel selectedHotel = listHotel.getSelectionModel().getSelectedItem();

// Pass the selected hotel to setData method
            controller.setData(selectedHotel);
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshTable();


    }

    public void refreshTable() {
        // Implémentez votre code pour rafraîchir la ListView ici

        ObservableList<Hotel> HList;
        try {
            HList = FXCollections.observableArrayList(hs.afficher());
        } catch (SQLException ex) {
            HList = FXCollections.observableArrayList();
            System.out.println("Erreur lors de la récupération des hotels: " + ex.getMessage());
            ex.printStackTrace(); // Affichez les détails de l'exception pour un débogage supplémentaire si nécessaire
        }

        listHotel.setCellFactory(param -> new ListCell<Hotel>() {
            private final VBox vbox = new VBox();
            private final Label nomLabel = new Label();
            private final Label emailLabel = new Label();
            private final Label telephoneLabel = new Label();
            private final Label etoilesLabel = new Label();
            private final Label localisationLabel = new Label();

            {
                // Personnalisez l'apparence des étiquettes (labels)
                nomLabel.setStyle("-fx-font-weight: bold;");
                emailLabel.setStyle("-fx-text-fill: #666666;");
                telephoneLabel.setStyle("-fx-text-fill: #666666;");
                etoilesLabel.setStyle("-fx-font-style: italic;");
                localisationLabel.setStyle("-fx-font-style: italic;");

                // Ajoutez les étiquettes à la boîte verticale (VBox)
                vbox.getChildren().addAll(nomLabel, emailLabel, telephoneLabel, etoilesLabel, localisationLabel);

                // Personnalisez le remplissage et l'espacement de la boîte verticale
                vbox.setSpacing(5);
                vbox.setPadding(new Insets(5));
            }

            @Override
            protected void updateItem(Hotel hotel, boolean empty) {
                super.updateItem(hotel, empty);

                if (empty || hotel == null) {
                    setGraphic(null);
                } else {
                    // Mettez à jour le texte des étiquettes avec les données de l'hôtel
                    nomLabel.setText("Nom : " + hotel.getNom());
                    emailLabel.setText("Email : " + hotel.getEmail());
                    telephoneLabel.setText("Téléphone : " + hotel.getTelephone());
                    etoilesLabel.setText("Étoiles : " + hotel.getNbEtoiles());
                    localisationLabel.setText("Localisation : " + hotel.getLocalisation());

                    // Affichez la VBox contenant les étiquettes
                    setGraphic(vbox);
                }
            }
        });

        // Définissez les éléments de la ListView avec la liste observable
        listHotel.setItems(HList);
    }

    @FXML
    void hotels(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/AfficherHotel.fxml"));
            Parent root = loader.load();


            AfficherHotel controller = loader.getController();



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
    void maisons(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/AfficherMaison.fxml"));
            Parent root = loader.load();


            AfficherMaison controller = loader.getController();



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
    void reservationsH(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/AfficherReservationH.fxml"));
            Parent root = loader.load();


            AfficherReservationH controller = loader.getController();



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
    void reservationsM(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/AfficherReservationM.fxml"));
            Parent root = loader.load();


            AfficherReservationM controller = loader.getController();



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














