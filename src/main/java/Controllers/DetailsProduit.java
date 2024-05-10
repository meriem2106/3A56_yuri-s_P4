package Controllers;

import entities.Formation;
import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import Services.FormationCrud;

import java.sql.SQLException;

public class DetailsProduit {

    @FXML
    private Rating prating;


    @FXML
    private ImageView IVimage;

    @FXML
    private Label SujetF;

    @FXML
    private Label Vcat;

    @FXML
    private Label Vdesc;

    @FXML
    private Label Vmatie;

    @FXML
    private Label Vnom;

    @FXML
    private Label Vorig;

    @FXML
    private Button btn_retour;

    private Produit produit;

    private Formation formation;
    private FormationCrud formationService = new FormationCrud();

    public DetailsProduit() throws SQLException {
        // Initialisation des éléments graphiques
    }

    public void setData(Produit produit) throws SQLException {
        this.produit = produit;

        Vnom.setText(produit.getNom());
        Vdesc.setText(produit.getDescription());
        Vorig.setText(produit.getOrigine());
        Vcat.setText(produit.getCateg());
        Vmatie.setText(produit.getMatiere());

        Image image = new Image("File:///C:\\Users\\ASUS\\Desktop\\3A56_yuri-s_P3-main\\3A56_yuri-s_P3-main\\public\\pictures\\" + produit.getImage());
        IVimage.setImage(image);

        if (produit.getFormation() != 0) {
            Formation formation = formationService.findById(produit.getFormation());
            this.formation = formation;
            SujetF.setText(formation.getSujet());
        } else {
            SujetF.setText("Formation non trouvée");
        }
    }

    @FXML
    void retourner(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeProduits.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btn_retour.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void detailsForma(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsFormation.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de la vue DetailsProduit
            DetailsFormation detailsController = loader.getController();

            // Transmettre l'objet Produit au contrôleur DetailsProduit
            detailsController.setData(formation);
            Scene scene = new Scene(root);
            Stage   primaryStage = new Stage();
            primaryStage.setTitle("Gestion Artisanale");
            primaryStage.setScene(scene);
            primaryStage.show();
//            Stage stage = (Stage) .getScene().getWindow();
//            stage.setScene(new Scene(root));

        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void produitRating(MouseEvent event) {
        produit.setRating((int) prating.getRating());

    }

}