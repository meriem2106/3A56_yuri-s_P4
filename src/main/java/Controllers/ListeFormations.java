package Controllers;

import entities.Formation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Services.FormationCrud;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ListeFormations implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private TextField searchField;

    @FXML
    private Button Tri;

    @FXML
    private Button calendrier;

    private List<Formation> formations;

    public ListeFormations() {
        // Initialisation des éléments graphiques
        grid = new GridPane();
        // Autres initialisations...
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int rows = 3;
        int columns = 0;
        FormationCrud fc;
        try {
            fc = new FormationCrud();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        formations = fc.findAll();
        try {

            for (Formation f : formations) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardFormation.fxml"));
                AnchorPane cardBox = fxmlLoader.load();
                cardBox.setOpacity(10); // Définit un espacement de 10 pixels entre les éléments enfants du HBox

                CardFormation cardFormation = fxmlLoader.getController();
                cardFormation.setData(f);
                if (columns == 2) {
                    columns = 0;
                    rows++;
                }
                grid.add(cardBox, columns++, rows);
                GridPane.setMargin(cardBox, new Insets(10));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                rechercherFormation(newValue.trim());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            rechercherFormation("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void afficherFormations(List<Formation> formations) {
        int rows = 3;
        int columns = 0;
        grid.getChildren().clear(); // Effacer les éléments précédents

        try {
            for (Formation f : formations) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardFormation.fxml"));
                AnchorPane cardBox = fxmlLoader.load();
                cardBox.setOpacity(10); // Définit un espacement de 10 pixels entre les éléments enfants du HBox

                CardFormation formationItem = fxmlLoader.getController();
                formationItem.setData(f);
                if (columns == 2) {
                    columns = 0;
                    rows++;
                }
                grid.add(cardBox, columns++, rows);
                GridPane.setMargin(cardBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void rechercherFormation(String motCle) throws SQLException {
        FormationCrud service = new FormationCrud();
        List<Formation> formations = service.findAll();

        if (!motCle.isEmpty()) {
            formations = formations.stream()
                    .filter(f -> f.getSujet().toLowerCase().contains(motCle.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Mettre à jour l'affichage des formations filtrées dans la grille
        afficherFormations(formations);
    }

    @FXML
    void TriParDate(ActionEvent event) {

        List<Formation> formationsTries= formations.stream()
                .sorted(Comparator.comparing(Formation::getDate))
                .collect(Collectors.toList());

        afficherFormations(formationsTries);

    }

    @FXML
    void afficherCalendrier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Calendar.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) calendrier.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

    }
}
