package controllers.BackOffice;

import controllers.FrontOffice.ShowReservationH;
import entities.Maison;
import entities.ReservationH;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import services.ServiceReservationH;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AfficherReservationH implements Initializable {


    @FXML
    private Circle circle;
    @FXML
    private Button utilisateurs;
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
    private ListView<ReservationH> listReservationH;

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


    ServiceReservationH srh = new ServiceReservationH();

    public ReservationH r;

    @FXML
    void delete(ActionEvent event) {
        ReservationH selectedreservation = listReservationH.getSelectionModel().getSelectedItem(); // Récupérer l'hôtel sélectionné
        if (selectedreservation != null) {
            try {
                srh.supprimer(selectedreservation.getId()); // Supprimer l'hôtel à partir du service
                refreshTable(); // Rafraîchir la table après la suppression
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la suppression de la reservation: " + ex.getMessage());
                ex.printStackTrace(); // Affichez les détails de l'exception pour un débogage supplémentaire si nécessaire
            }
        } else {
            // Afficher un message d'erreur si aucun hôtel n'est sélectionné
            System.out.println("Veuillez sélectionner une reservation à supprimer.");
        }
    }


    @FXML
    void show(ActionEvent event) {
        try {
            // Load the view ShowHotelB.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ShowReservationHB.fxml"));
            Parent root = loader.load();

            // Get the controller of ShowHotel
            ShowReservationHB controller = loader.getController();

            ReservationH selectedreservation = listReservationH.getSelectionModel().getSelectedItem();

            // Pass the selected hotel to setData method
            controller.setData(selectedreservation);

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
                System.err.println("Error: Loading ShowReservationH.fxml failed.");
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

        ObservableList<ReservationH> RHList;
        try {
            RHList = FXCollections.observableArrayList(srh.afficher());
        } catch (SQLException ex) {
            RHList = FXCollections.observableArrayList();
            System.out.println("Erreur lors de la récupération des hotels: " + ex.getMessage());
            ex.printStackTrace(); // Affichez les détails de l'exception pour un débogage supplémentaire si nécessaire
        }

        listReservationH.setCellFactory(param -> new ListCell<ReservationH>() {
            private final VBox vbox = new VBox();
            private final Label HotelLabel = new Label();
            private final Label UserLabel = new Label();
            private final Label nbAdultesLabel = new Label();
            private final Label nbEnfantsLabel = new Label();
            private final Label arrangementLabel = new Label();
            private final Label repartitionLabel = new Label();
            private final Label dateDepartLabel = new Label();
            private final Label dateArriveeLabel = new Label();


            {
                // Personnalisez l'apparence des étiquettes (labels)
                nbAdultesLabel.setStyle("-fx-font-style: italic;");
                nbEnfantsLabel.setStyle("-fx-font-style: italic;");
                arrangementLabel.setStyle("-fx-font-style: italic;");
                repartitionLabel.setStyle("-fx-font-style: italic;");
                dateDepartLabel.setStyle("-fx-font-style: italic;");
                dateArriveeLabel.setStyle("-fx-font-style: italic;");
                HotelLabel.setStyle("-fx-font-style: italic,bold;");
                UserLabel.setStyle("-fx-font-style: italic,bold;");

                // Ajoutez les étiquettes à la boîte verticale (VBox)
                vbox.getChildren().addAll(HotelLabel,UserLabel,nbAdultesLabel, nbEnfantsLabel, arrangementLabel, repartitionLabel, dateDepartLabel,dateArriveeLabel);

                // Personnalisez le remplissage et l'espacement de la boîte verticale
                vbox.setSpacing(5);
                vbox.setPadding(new Insets(5));
            }

            @Override
            protected void updateItem(ReservationH reservationH, boolean empty) {
                super.updateItem(reservationH, empty);

                if (empty || reservationH == null) {
                    setGraphic(null);
                } else {
                    // Mettez à jour le texte des étiquettes avec les données de l'hôtel
                    HotelLabel.setText("Hotel : " + reservationH.getHotel().getNom());
                    UserLabel.setText("User : " + reservationH.getUtilisateur().getPrenom() +" "+ reservationH.getUtilisateur().getNom());
                    nbAdultesLabel.setText("Nombre Adultes : " + reservationH.getNbAdultes());
                    nbEnfantsLabel.setText("Nombre Enfants : " + reservationH.getNbEnfants());
                    arrangementLabel.setText("Arrangement : " + reservationH.getArrangement());
                    repartitionLabel.setText("Repartition : " + reservationH.getRepartition());
                    dateDepartLabel.setText("DateDepart : " + reservationH.getDateDepart());
                    dateArriveeLabel.setText("DateArrivee : " + reservationH.getDateArrivee());

                    // Affichez la VBox contenant les étiquettes
                    setGraphic(vbox);
                }
            }
        });

        // Définissez les éléments de la ListView avec la liste observable
        listReservationH.setItems(RHList);
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
    @FXML
    private Button reclamations;

    @FXML
    void reclamations(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontUser.fxml"));
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

