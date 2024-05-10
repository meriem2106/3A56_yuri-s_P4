package Controllers.BackOffice;

import Controllers.FrontOffice.ShowEvent;
import entities.Evenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.EvenementCrud;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AfficherEvenement {
    @FXML
    private TextField rechevent;

    @FXML
    private Button afficher;

    @FXML
    private ListView<String> eventlist;

    @FXML
    private Button supprimer;

    @FXML
    private Button retourner;

    @FXML
    private Button Tri;

    private List<Evenement> evenements ;

    EvenementCrud evC = new EvenementCrud();

    Evenement evenementSelectionne = null;





    public void refreshTable() {
        List<Evenement> evenements;
        try {
            evenements = evC.findAll();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des événements: " + ex.getMessage());
            ex.printStackTrace();
            return;
        }

        ObservableList<String> eventNames = FXCollections.observableArrayList();
        for (Evenement e : evenements) {
            // Créer une représentation textuelle personnalisée pour chaque événement
            String eventDetails = e.getNom() + " - " + e.getDatedeb() + " - " + e.getLieu();
            eventNames.add(eventDetails);
        }
        eventlist.setItems(eventNames);
    }

    @FXML
    void initialized() {
        rechevent.textProperty().addListener((observable, oldValue, newValue) -> {
            rechevent(null);
        });
    }

    private void afficherResultats(List<Evenement> resultats) {
        ObservableList<String> eventDetails = FXCollections.observableArrayList();
        for (Evenement e : resultats) {
            String eventDetail = "Votre Evénement :"+ e.getNom() + "                                  Du:" + e.getDatedeb() +   "Au:"+e.getDatefin()+"                                    à" + e.getLieu();
            eventDetails.add(eventDetail);
        }
        eventlist.setItems(eventDetails);
    }


    @FXML
    public void afficher(ActionEvent event) {
        try {
            List<Evenement> evenements = evC.findAll();
            ObservableList<String> eventNames = FXCollections.observableArrayList();
            for (Evenement e : evenements) {
                // Créer une représentation textuelle personnalisée pour chaque événement
                String eventDetails = e.getNom() + " - " + e.getDatedeb() + " - " + e.getLieu();
                eventNames.add(eventDetails);
            }
            eventlist.setItems(eventNames);

            eventlist.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {
                    int selectedIndex = eventlist.getSelectionModel().getSelectedIndex();
                    if (selectedIndex != -1) {
                        Evenement selectedEvent = evenements.get(selectedIndex);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ShowEvent.fxml"));
                        Parent root = null;
                        try {
                            root = loader.load();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        ShowEvent controller = loader.getController();
                        controller.setData(selectedEvent);
                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    }
                }else{
                    if(eventlist.getSelectionModel().getSelectedIndex() != -1){
                        evenementSelectionne = evenements.get(eventlist.getSelectionModel().getSelectedIndex());
                    }
                }

            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void triParDate(ActionEvent event) throws SQLException {
        if (evenements != null) {
            List<Evenement> evenementsTries = evenements.stream()
                    .sorted(Comparator.comparing(Evenement::getDatedeb))
                    .collect(Collectors.toList());

            ObservableList<String> eventNames = FXCollections.observableArrayList();
            for (Evenement e : evenementsTries) {
                // Créer une représentation textuelle personnalisée pour chaque événement
                String eventDetails = e.getNom() + " - " + e.getDatedeb() + " - " + e.getLieu();
                eventNames.add(eventDetails);
            }
            eventlist.setItems(eventNames);
        } else {
            // Handle the case when evenements is null
            showAlert("aucun évenement n'est sélectionné", "Veuillez sélectionner les événements à trier.");
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }




    @FXML
    void supprimerEvenement(ActionEvent event) {

        if (evenementSelectionne != null) {
            evC.deleteByNom(evenementSelectionne.getNom());
            refreshTable();
        } else {
            System.out.println("Veuillez sélectionner un événement à supprimer.");
        }
    }

    public void initialize() throws SQLException {
        evenements = evC.findAll();
        afficher(null); // Vous pouvez également appeler afficher ici si nécessaire
    }

    @FXML
    void retourner(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/AjouterEvenement.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) retourner.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void rechevent(ActionEvent event) {
        String texteRecherche;
        TextField textField = (TextField) event.getSource();
        if (textField != null) {
            texteRecherche = textField.getText();
        } else {
            texteRecherche = "";
        }

        List<Evenement> evenements;
        try {
            evenements = evC.findAll();
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des événements: " + ex.getMessage());
            ex.printStackTrace();
            return;
        }
        List<Evenement> resultats = evenements.stream()
                .filter(e ->
                                e.getNom().toLowerCase().contains(texteRecherche.toLowerCase()) ||
                                        e.getDatedeb().toString().toLowerCase().contains(texteRecherche.toLowerCase()) ||
                                        e.getLieu().toLowerCase().contains(texteRecherche.toLowerCase()) ||
                                        e.getCat().toLowerCase().contains(texteRecherche))
                        // Ajoutez ici d'autres attributs à rechercher
                .collect(Collectors.toList());
        afficherResultats(resultats);
    }
}

