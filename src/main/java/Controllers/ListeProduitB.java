package Controllers;

import entities.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import  Services.ProduitCrud ;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ListeProduitB implements Initializable{

    @FXML
    private ComboBox<String> combo;


    @FXML
    private GridPane grid;

    private ObservableList<Produit> produitsNonFiltres;

    public void ListeProduits() {
        // Initialisation des éléments graphiques
        grid = new GridPane();
        // Autres initialisations...
    }

    @FXML
    private TextField searchField;
    private List<Produit> produits;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int rows = 3;
        int columns = 0;
        ProduitCrud sc;
        try {
            sc = new ProduitCrud();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        produits = sc.findAll();
        try {

            for (Produit c : produits) {
                //System.out.println(c);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardProduit.fxml"));
                AnchorPane cardBox = fxmlLoader.load();
                cardBox.setOpacity(10); // Définit un espacement de 10 pixels entre les éléments enfants du HBox

                CardProduit cardProduit = fxmlLoader.getController();
                cardProduit.setData(c);
                if (columns == 2) {
                    columns = 0;
                    rows++;
                }
                grid.add(cardBox, columns++, rows);
                GridPane.setMargin(cardBox, new Insets(5));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                rechercherProduit(newValue.trim());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            rechercherProduit("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        List<String> categoriesList = new ArrayList<>();
//        categoriesList.add("All");
//        produits.forEach(produit -> {
//            if(!categoriesList.contains(produit.getCateg())){
//                categoriesList.add(produit.getCateg());
//            }
//        });
//
//        ObservableList<String> categories = FXCollections.observableArrayList(categoriesList.toArray(new String[0]));

        ObservableList<String> categories = FXCollections.observableArrayList(
                "All", "ART DE LA TABLE", "VÊTEMENTS", "MAISON & DÉCORATION", "BIJOUX & ACCESSOIRES");

        // Ajouter les catégories au ComboBox
        combo.setItems(categories);

    }
    private void afficherProduits(List<Produit> produits) {
        int rows = 3;
        int columns = 0;
        grid.getChildren().clear(); // Effacer les éléments précédents

        //resetRowsColumns(); // Réinitialiser les lignes et colonnes
        try {
            for (Produit c : produits) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardProduit.fxml"));
                AnchorPane cardBox = fxmlLoader.load();
                cardBox.setOpacity(10); // Définit un espacement de 10 pixels entre les éléments enfants du HBox
//tt
                CardProduit produitItem = fxmlLoader.getController();
                produitItem.setData(c);
                if (columns == 2) {
                    columns = 0;
                    rows++;
                }
                grid.add(cardBox, columns++, rows);
                GridPane.setMargin(cardBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    void rechercherProduit(String motCle) throws SQLException {


        ProduitCrud service = new ProduitCrud();
        List<Produit> produits = service.findAll();

        if (!motCle.isEmpty()) {
            produits = produits.stream()
                    .filter(p -> p.getNom().toLowerCase().contains(motCle.toLowerCase()))
                    .collect(Collectors.toList());
        }


        afficherProduits(produits);

    }
    @FXML
    private void handleAjouterProduit(ActionEvent event) throws IOException {
        // Charger le fichier FXML de la page d'ajout de produit
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
        Parent ajoutProduitParent = loader.load();

        // Obtenir le contrôleur de la page d'ajout de produit
        AjouterProduit ajoutProduitController = loader.getController();

        // Créer une nouvelle scène avec la page d'ajout de produit
        Scene ajoutProduitScene = new Scene(ajoutProduitParent);

        // Obtenir la fenêtre actuelle
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Afficher la scène de la page d'ajout de produit dans la fenêtre
        window.setScene(ajoutProduitScene);
        window.show();
    }




    @FXML
    void FiltrerParCateg(ActionEvent event) {
        // Récupérer la catégorie sélectionnée depuis le ComboBox
        String categorieSelectionnee = combo.getValue();
        if (    categorieSelectionnee.equals("All")){
            grid.getChildren().clear();
            afficherProduits(produits);

        } else  {
            // Créer une liste pour stocker les produits filtrés
            List<Produit> produitsFiltres = new ArrayList<>();

            // Parcourir tous les produits non filtrés
            for (Produit produit : produits) {
                // Vérifier si la catégorie du produit correspond à la catégorie sélectionnée
                if (produit.getCateg().equals(categorieSelectionnee)) {
                    // Ajouter le produit à la liste des produits filtrés
                    produitsFiltres.add(produit);
                }
            }

            // Effacer les éléments précédents de la grille
            grid.getChildren().clear();

            // Afficher les produits filtrés dans la grille
            afficherProduits(produitsFiltres);
        }
    }


}


