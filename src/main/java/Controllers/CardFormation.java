package Controllers;


import entities.Formation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Services.FormationCrud;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CardFormation {

    public CardFormation() {
        // Initialisation des éléments graphiques
        Vsujet = new Label();
        Vheure = new Label();
        Vlieu = new Label();
        Vheure = new Label();


        // Autres initialisations...
    }
    @FXML
    private Button details;
    @FXML
    private Button DeleteFormation;

    @FXML
    private Button UpdateFormation;

    @FXML
    private Label Vdate;

    @FXML
    private Label Vheure;

    @FXML
    private Label Vlieu;

    @FXML
    private Label Vsujet;

    //public static Formation formation;
    
    private Formation formation;
    public void setData(Formation formation) {

        this.formation = formation;

        Vsujet.setText(formation.getSujet());
        Vdate.setText(String.valueOf(formation.getDate()));
        Vheure.setText(String.valueOf(formation.getHeure()));
        Vlieu.setText(formation.getLieu());
    }

    private String toString(String heure) {
        return "CardFormation{" +
                "Vdate=" + Vdate +
                ", Vheure=" + Vheure +
                '}';
    }


    @FXML
    void DeleteFormation(ActionEvent event) throws SQLException, IOException {
        FormationCrud service = new FormationCrud();

        // Confirmation de la suppression
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous vraiment supprimer cette Formation ?");

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
            service.delete(formation.getId());
            // Recharger la vue des clubs
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/ListeFormations.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }

    }



//    @FXML
//    void UpdateFormation(ActionEvent event) throws IOException, SQLException {
//        UpdateFormation UpdateFormationController = new UpdateFormation();
//        UpdateFormationController.formation = formation; // Assurez-vous que votre classe UpdateFormationController possède une variable statique nommée "formation"
//        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/UpdateFormation.fxml"));
//        Scene tableViewScene = new Scene(tableViewParent);
//        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        window.setScene(tableViewScene);
//        window.show();
//    }

    @FXML
    void updateFormation(ActionEvent event) throws IOException {
        Controllers.UpdateFormation.formation=formation;
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/UpdateFormation.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
    @FXML
    void VoirDetails(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsFormation.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de la vue DetailsProduit
            DetailsFormation detailsController = loader.getController();

            // Transmettre l'objet Produit au contrôleur DetailsProduit
            detailsController.setData(formation);

            Stage stage = (Stage) details.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}