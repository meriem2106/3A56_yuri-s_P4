package Controllers;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import  Services.ProduitCrud ;
import com.itextpdf.kernel.colors.DeviceRgb;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ListeProduits implements Initializable{
    @FXML
    private Button ajouter;
    @FXML
    private ComboBox<String> combo;
    @FXML
    private Button btn_chatgpt;

    @FXML
    private GridPane grid;
    @FXML
    private Button exel;
    @FXML
    private Button pdf;
    private ObservableList<Produit> produitsNonFiltres;

    public ListeProduits() {
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
    void PdfProduit(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(null); // Pas besoin de créer un nouveau Stage

        if (selectedFile != null) {
            try {
                // Initialisez le writer PDF
                PdfWriter writer = new PdfWriter(selectedFile.getAbsolutePath());
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf, PageSize.A4);

                // Ajouter un fond de couleur à la page (Notez que la couleur est en RGB, ici, le rouge pur)
                pdf.addNewPage().getPdfObject().put(PdfName.BackgroundColor, new PdfArray(new float[]{1, 0, 0}));

                // Charger l'image du logo
                ImageData imageData = ImageDataFactory.create(getClass().getResource("/logo.png"));
                com.itextpdf.layout.element.Image image = new Image(imageData);
                image.scaleToFit(90, 90); // Redimensionner l'image pour qu'elle s'adapte dans un rectangle de 90x90 pixels
                image.setFixedPosition(500, 760); // Modifier les coordonnées X et Y selon vos besoins

                // Ajoutez un titre au document
                PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
                document.add(new Paragraph("Liste des produits").setFont(font).setFontSize(16).setTextAlignment(TextAlignment.CENTER));
                document.add(image);

                // Ajoutez une table pour afficher les événements
                Table table = new Table(5);
                table.setWidth(500);
                table.setHorizontalAlignment(HorizontalAlignment.CENTER); // Centrer le tableau horizontalement
                table.setMarginTop(50);


                try {
                    // Importations nécessaires pour les couleurs

                    // Définition de la couleur de fond
                    DeviceRgb headerColor = new DeviceRgb(0, 0, 255); // Bleu en RGB
                    //DeviceRgb rgbColor = new DeviceRgb(headerColor.getColorValue()[0], headerColor.getColorValue()[1], headerColor.getColorValue()[2]);

                    Cell cell;
                    for (String header : new String[]{"Nom", "Categorie", "Description", "Matiere", "Origine"}) {
                        cell = new Cell().add(new Paragraph(header).setFont(font)); // Ajouter le texte

                        cell.setBackgroundColor(headerColor); // Définir la couleur de fond

                        table.addHeaderCell(cell);
                    }
                } catch (Exception e) {
                    // Gérez l'exception ici, par exemple, affichez un message d'erreur ou enregistrez l'erreur dans les logs
                    e.printStackTrace();
                }




                // Récupérez les clubs depuis la base de données
                ProduitCrud produitCrud = new ProduitCrud();
                List<Produit> produits = produitCrud.findAll();

                // Ajoutez les données des clubs à la table
                for (Produit a : produits) {
                    table.addCell(a.getNom());
                    table.addCell(a.getCateg());
                    table.addCell(a.getDescription());
                    table.addCell(a.getMatiere());
                    table.addCell(a.getOrigine());

                }

                // Ajoutez la table au document
                document.add(table);

                // Fermez le document
                document.close();

                // Affichez un message de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Le PDF a été généré avec succès !");
                alert.showAndWait();
            } catch (IOException e) {
                // En cas d'erreur, affichez une alerte
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de la génération du PDF : " + e.getMessage());
                alert.showAndWait();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                Desktop.getDesktop().open(selectedFile); // selectedFile est le fichier PDF généré
            } catch (IOException e) {
                // En cas d'erreur, affichez une alerte ou un message d'erreur
                e.printStackTrace(); // Affichez l'erreur dans la console pour le débogage
            }
        }

    }
    @FXML
    void ExelProduit(ActionEvent event) throws IOException {
        String filePath = "produits.xlsx";

        // Appeler la méthode d'exportation Excel avec la liste complète des clubs et le chemin du fichier Excel
        ExcelExporter.exportToExcel(produits, filePath);

        // Afficher un message de succès à l'utilisateur
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exportation Excel");
        alert.setHeaderText(null);
        alert.setContentText("Les produits ont été exportés vers Excel avec succès !");
        alert.showAndWait();

        if (Desktop.isDesktopSupported()) {
            // Obtenir l'instance de Desktop
            Desktop desktop = Desktop.getDesktop();


            // Ouvrir le fichier avec l'application par défaut associée à son extension
            desktop.open(new File(filePath));


        }
    }
    @FXML
    void naviguerVersChatgpt(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChatGpt.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btn_chatgpt.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

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


