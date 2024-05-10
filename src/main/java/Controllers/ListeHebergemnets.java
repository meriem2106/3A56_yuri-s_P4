package Controllers;

import entities.Hebergement;
import services.HebergementCrud;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ListeHebergemnets implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private TextField searchField;

    private List<Hebergement> hebergements;

    public ListeHebergemnets() {
        // Initialisation des éléments graphiques
        grid = new GridPane();
        // Autres initialisations...
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int rows = 3;
        int columns = 0;
        HebergementCrud fc;
        try {
            fc = new HebergementCrud();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        hebergements = fc.findAll();
        try {

            for (Hebergement f : hebergements) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardHebergement.fxml"));
                AnchorPane cardBox = fxmlLoader.load();
                cardBox.setOpacity(10); // Définit un espacement de 10 pixels entre les éléments enfants du HBox

                CardHebergement cardHebergement = fxmlLoader.getController();
                cardHebergement.setData(f);
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

    private void afficherFormations(List<Hebergement> hebergements) {
        int rows = 3;
        int columns = 0;
        grid.getChildren().clear();

        try {
            for (Hebergement f : hebergements) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardHebergement.fxml"));
                AnchorPane cardBox = fxmlLoader.load();
                cardBox.setOpacity(10);

                CardHebergement HebergementItem = fxmlLoader.getController();
                HebergementItem.setData(f);
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
        HebergementCrud service = new HebergementCrud();
        List<Hebergement> hebergements1 = service.findAll();

        if (!motCle.isEmpty()) {
            hebergements1 = hebergements1.stream()
                    .filter(f -> f.getNom().toLowerCase().contains(motCle.toLowerCase()))
                    .collect(Collectors.toList());
        }


        afficherFormations(hebergements1);
    }
     void rechercherParPrix(double prixMin, double prixMax) throws SQLException {
        HebergementCrud service = new HebergementCrud();
        List<Hebergement> hebergements = service.findAll();


        List<Hebergement> hebergementsFiltres = hebergements.stream()
                .filter(f -> {
                    double prix = Double.parseDouble(f.getPrix());
                    return prix >= prixMin && prix <= prixMax;
                })
                .collect(Collectors.toList());


        afficherFormations(hebergementsFiltres);

     }

}