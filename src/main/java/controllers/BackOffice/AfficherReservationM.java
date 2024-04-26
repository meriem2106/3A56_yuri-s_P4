package controllers.BackOffice;

import entities.ReservationH;
import entities.ReservationM;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceReservationM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AfficherReservationM implements Initializable  {

    @FXML
    private Button delete;

    @FXML
    private ListView<ReservationM> listReservationM;

    @FXML
    private Button show;

    ServiceReservationM srm = new ServiceReservationM();

    @FXML
    void delete(ActionEvent event) {
        ReservationM selectedreservation = listReservationM.getSelectionModel().getSelectedItem(); // Récupérer l'hôtel sélectionné
        if (selectedreservation != null) {
            try {
                srm.supprimer(selectedreservation.getId()); // Supprimer l'hôtel à partir du service
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ShowReservationMB.fxml"));
            Parent root = loader.load();

            // Get the controller of ShowHotel
            ShowReservationMB controller = loader.getController();

            ReservationM selectedreservation = listReservationM.getSelectionModel().getSelectedItem();

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
                System.err.println("Error: Loading ShowReservationM.fxml failed.");
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

        ObservableList<ReservationM> RMList;
        try {
            RMList = FXCollections.observableArrayList(srm.afficher());
        } catch (SQLException ex) {
            RMList = FXCollections.observableArrayList();
            System.out.println("Erreur lors de la récupération des hotels: " + ex.getMessage());
            ex.printStackTrace(); // Affichez les détails de l'exception pour un débogage supplémentaire si nécessaire
        }

        listReservationM.setCellFactory(param -> new ListCell<ReservationM>() {
            private final VBox vbox = new VBox();
            private final Label nbAdultesLabel = new Label();
            private final Label nbEnfantsLabel = new Label();
            private final Label arrangementLabel = new Label();
            private final Label repartitionLabel = new Label();
            private final Label dateDepartLabel = new Label();
            private final Label dateArriveeLabel = new Label();
            private final Label MaisonLabel = new Label();
            private final Label UserLabel = new Label();

            {
                // Personnalisez l'apparence des étiquettes (labels)
                nbAdultesLabel.setStyle("-fx-font-style: italic;");
                nbEnfantsLabel.setStyle("-fx-font-style: italic;");
                arrangementLabel.setStyle("-fx-font-style: italic;");
                repartitionLabel.setStyle("-fx-font-style: italic;");
                dateDepartLabel.setStyle("-fx-font-style: italic;");
                dateArriveeLabel.setStyle("-fx-font-style: italic;");
                MaisonLabel.setStyle("-fx-font-style: italic;");
                UserLabel.setStyle("-fx-font-style: italic;");

                // Ajoutez les étiquettes à la boîte verticale (VBox)
                vbox.getChildren().addAll(MaisonLabel,UserLabel,nbAdultesLabel, nbEnfantsLabel, arrangementLabel, repartitionLabel, dateDepartLabel,dateArriveeLabel);

                // Personnalisez le remplissage et l'espacement de la boîte verticale
                vbox.setSpacing(5);
                vbox.setPadding(new Insets(5));
            }

            @Override
            protected void updateItem(ReservationM reservationM, boolean empty) {
                super.updateItem(reservationM, empty);

                if (empty || reservationM == null) {
                    setGraphic(null);
                } else {
                    // Mettez à jour le texte des étiquettes avec les données de l'hôtel
                    nbAdultesLabel.setText("Nombre Adultes : " + reservationM.getNbAdultes());
                    nbEnfantsLabel.setText("Nombre Enfants : " + reservationM.getNbEnfants());
                    arrangementLabel.setText("Arrangement : " + reservationM.getArrangement());
                    repartitionLabel.setText("Repartition : " + reservationM.getRepartition());
                    dateDepartLabel.setText("DateDepart : " + reservationM.getDateDepart());
                    dateArriveeLabel.setText("DateArrivee : " + reservationM.getDateArrivee());
                    MaisonLabel.setText("Maison : " + reservationM.getMaison().getNom());
                    UserLabel.setText("User : " + reservationM.getUtilisateur().getNom() +" "+ reservationM.getUtilisateur().getPrenom());

                    // Affichez la VBox contenant les étiquettes
                    setGraphic(vbox);
                }
            }
        });

        // Définissez les éléments de la ListView avec la liste observable
        listReservationM.setItems(RMList);
    }


}
