package controllers.BackOffice;

import entities.Maison;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceMaison;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AfficherMaison implements Initializable {


    @FXML
    private Button delete;

    @FXML
    private ListView<Maison> listMaison;

    @FXML
    private Button show;


    ServiceMaison sm = new ServiceMaison();

    public Maison maison;

    @FXML
    void delete(ActionEvent event) {
        Maison selectedMaison = listMaison.getSelectionModel().getSelectedItem(); // Récupérer l'hôtel sélectionné
        if (selectedMaison != null) {
            try {
                sm.supprimer(selectedMaison.getId()); // Supprimer l'hôtel à partir du service
                refreshTable(); // Rafraîchir la table après la suppression
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la suppression de la maison d'hote: " + ex.getMessage());
                ex.printStackTrace(); // Affichez les détails de l'exception pour un débogage supplémentaire si nécessaire
            }
        } else {
            // Afficher un message d'erreur si aucun hôtel n'est sélectionné
            System.out.println("Veuillez sélectionner une maison d'hote à supprimer.");
        }
    }


    @FXML
    void show(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BackOffice/ShowMaisonB.fxml"));
            Parent root = loader.load();


            ShowMaisonB controller = loader.getController();


            // Assuming you have a selected hotel from your list view
            Maison selectedMaison = listMaison.getSelectionModel().getSelectedItem();

// Pass the selected hotel to setData method
            controller.setData(selectedMaison);

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
        ObservableList<Maison> MList;
        try {
            MList = FXCollections.observableArrayList(sm.afficher());
        } catch (SQLException ex) {
            MList = FXCollections.observableArrayList();
            System.out.println("Erreur lors de la récupération des maisons: " + ex.getMessage());
            ex.printStackTrace(); // Affichez les détails de l'exception pour un débogage supplémentaire si nécessaire
        }

        listMaison.setCellFactory(param -> new ListCell<Maison>() {
            private final VBox vbox = new VBox();
            private final Label imageLabel = new Label();
            private final Label nomLabel = new Label();
            private final Label emailLabel = new Label();
            private final Label capaciteLabel = new Label();
            private final Label nbChambresLabel = new Label();
            private final Label localisationLabel = new Label();
            private final Label villeLabel = new Label();
            private final Label disponibiliteLabel = new Label();
            private final Label descriptionLabel = new Label();
            private final Label prixLabel = new Label();


            {
                // Personnalisez l'apparence des étiquettes (labels)

                nomLabel.setStyle("-fx-font-style: italic;");
                emailLabel.setStyle("-fx-font-style: italic;");
                capaciteLabel.setStyle("-fx-font-style: italic;");
                nbChambresLabel.setStyle("-fx-font-style: italic;");
                localisationLabel.setStyle("-fx-font-style: italic;");
                villeLabel.setStyle("-fx-font-style: italic;");
                disponibiliteLabel.setStyle("-fx-font-style: italic;");
                descriptionLabel.setStyle("-fx-font-style: italic;");
                prixLabel.setStyle("-fx-font-style: italic;");


                // Ajoutez les étiquettes à la boîte verticale (VBox)
                vbox.getChildren().addAll( nomLabel,nbChambresLabel, emailLabel,  capaciteLabel, localisationLabel, villeLabel, disponibiliteLabel, descriptionLabel, prixLabel);

                // Personnalisez le remplissage et l'espacement de la boîte verticale
                vbox.setSpacing(5);
                vbox.setPadding(new Insets(5));
            }

            @Override
            protected void updateItem(Maison maison, boolean empty) {
                super.updateItem(maison, empty);

                if (empty || maison == null) {
                    setGraphic(null);
                } else {
                    // Mettez à jour le texte des étiquettes avec les données de l'hôtel

                    nomLabel.setText("Nom : " + maison.getNom());
                    nbChambresLabel.setText("Nombre Chambres : " + maison.getNbChambres());
                    emailLabel.setText("Email : " + maison.getEmail());
                    capaciteLabel.setText("Capacité : " + maison.getCapacite());
                    localisationLabel.setText("Localisation : " + maison.getLocalisation());
                    villeLabel.setText("Villes : " + maison.getVille());
                    disponibiliteLabel.setText("Disponibilité : " + maison.getDisponibilite());
                    descriptionLabel.setText("Description : " + maison.getDescription());
                    prixLabel.setText("Prix : " + maison.getPrix());


                    // Affichez la VBox contenant les étiquettes
                    setGraphic(vbox);
                }
            }
        });

        // Définissez les éléments de la ListView avec la liste observable
        listMaison.setItems(MList);
    }
}