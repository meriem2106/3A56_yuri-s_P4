package Controllers;
import entities.Formation;
import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import Services.FormationCrud;
import Services.ProduitCrud;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CardProduit {

    public CardProduit() throws SQLException {
        // Initialisation des éléments graphiques
        Vnom = new Label();
        Vdesc = new Label();
        Vorig = new Label();
        Vcat = new Label();
        Vmatie = new Label();

        // Autres initialisations...
    }

    @FXML
    private Button updateButton;
    @FXML
    private Button DeleteButton;

    @FXML
    private Label Vcat;

    @FXML
    private ImageView IVimage;

    @FXML
    private VBox VBoximg;


    @FXML
    private Label rateLabel;

    @FXML
    private Rating rateStars;

    @FXML
    private Label Vdesc;

    @FXML
    private Label Vmatie;

    @FXML
    private Label Vnom;

    @FXML
    private Label Vorig;

    @FXML
    private Label SujetF;

    @FXML
    private Button details;

    private Produit produit;
    FormationCrud formationService = new FormationCrud();

    public void setData(Produit produit) throws SQLException {

        this.produit=produit;

        Vnom.setText(produit.getNom());
        Vdesc.setText(produit.getDescription());
        Vorig.setText(produit.getOrigine());
        Vcat.setText(produit.getCateg());
        Vmatie.setText(produit.getMatiere());

       Image image = new Image(System.getProperty("imagesPaths") + produit.getImage());
       IVimage.setImage(image);



           if( produit.getFormation()!= 0){
                // Supposons que vous avez un service pour gérer les formations
               Formation formation = formationService.findById(produit.getFormation()); // Adapter cette méthode selon votre logique
               SujetF.setText(formation.getSujet());
           } else {
               SujetF.setText("Formation non trouvée"); // Gérer le cas où aucune formation n'est associée
           }
            rateLabel.setText("Rating :  "  +produit.getRating().toString());
            rateStars.setRating(produit.getRating());

        // Rechercher la formation correspondante à partir de l'identifiant (vous devez adapter cette partie à votre logique métier)





        // Afficher le sujet de la formation associée (supposons que la formation a un attribut sujet)




        /*Path urrRelativePath = Paths.get("");
        String currAbsolutePathString = currRelativePath.toAbsolutePath().toString();*/
    }

    @FXML
    void DeleteProduit(ActionEvent event) throws SQLException, IOException {
        ProduitCrud service = new ProduitCrud();

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
            service.delete(produit.getId());
            // Recharger la vue des clubs
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/ListeProduits.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }

    }

    @FXML
    void UpdateProduit(ActionEvent event) throws IOException {
        UpdateProduit.produit = produit;
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/UpdateProduit.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
    @FXML
    void VoirDetails(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsProduit.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de la vue DetailsProduit
            DetailsProduit detailsController = loader.getController();

            // Transmettre l'objet Produit au contrôleur DetailsProduit
            detailsController.setData(produit);

            Stage stage = (Stage) details.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    }

