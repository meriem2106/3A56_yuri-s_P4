package Controllers;

import entities.Hebergement;
import services.HebergementCrud;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CardHebergement {

    public CardHebergement(){
        Vnom=new Label();
        Vemail=new Label();
        Vpass=new Label();
        Vloc=new Label();
        Vprix=new Label();
        Vtele=new Label();
    }

    @FXML
    private ImageView IVimage;

    @FXML
    private ImageView IVimage2;

    @FXML
    private Label Vemail;

    @FXML
    private Label Vloc;

    @FXML
    private Label Vnom;

    @FXML
    private Label Vpass;

    @FXML
    private Label Vprix;

    @FXML
    private Label Vtele;

    @FXML
    private Button resbtn;



    private Hebergement hebergement;

    public void setData(Hebergement hebergement){
        this.hebergement=hebergement;
        Vnom.setText(hebergement.getNom());
        Vemail.setText(hebergement.getEmail());
        Vpass.setText(hebergement.getPassword());
        Vloc.setText(hebergement.getLocalisation());
        Vprix.setText(hebergement.getPrix());
        Vtele.setText(hebergement.getTelephone());

        Image image = new Image(System.getProperty("imagesPaths") + hebergement.getImage());
        IVimage.setImage(image);
    }
    @FXML
    public void DeleteHebergement(ActionEvent event) throws SQLException, IOException {


    }

    public void DeleteHebergement(javafx.event.ActionEvent actionEvent) throws SQLException, IOException {
        HebergementCrud service = new HebergementCrud();

        // Confirmation de la suppression
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer ce Produit ?");

        // Attend la réponse de l'utilisateur
        Optional<ButtonType> result = alert.showAndWait();

        // Si l'utilisateur a confirmé la suppression
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Obtenez l'instance du club que vous souhaitez supprimer (club doit être défini)
            //Club club = sc; // Définir club avec l'instance du club à supprimer

            // Supprimer le club
            /*ServiceAffiliation serviceAffiliation=new ServiceAffiliation();
            serviceAffiliation.delete6(club.getId());
            ServiceEquipement serviceEquipement=new ServiceEquipement();
            serviceEquipement.delete5(club.getId());*/
            service.delete(hebergement.getId());
            // Recharger la vue des clubs
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/ListeHebergemnets.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
    }

//    @FXML
//    void UpdateProduit(ActionEvent event) throws IOException {
//        UpdateHebergement.hebergement= hebergement;
//        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/UpdateHebergement.fxml"));
//        Scene tableViewScene = new Scene(tableViewParent);
//        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        window.setScene(tableViewScene);
//        window.show();
//    }

    public void UpdateProduit(javafx.event.ActionEvent actionEvent) throws IOException {
        UpdateHebergement.hebergement= hebergement;
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/UpdateHebergement.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }


    public void ajouterForm(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterInvitation.fxml"));
            Parent root = loader.load();
            Scene nouvelleScene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(nouvelleScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void ajouterForm(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterInvitation.fxml"));
        Parent tableViewParent = loader.load();
        AjouterInvitation controller = loader.getController();
        controller.setEmailDeHebergement(hebergement.getEmail());
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
