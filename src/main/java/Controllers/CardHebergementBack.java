package Controllers;

import entities.Hebergement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;


public class CardHebergementBack {
    public CardHebergementBack(){
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
    @FXML
    private Button delTF;

    @FXML
    private Button upTF;




    @FXML
    void updatefonction(ActionEvent event) {

    }



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

//    @FXML
//    public void DeleteFonction(javafx.event.ActionEvent event) throws SQLException, IOException {
//        HebergementCrud service = new HebergementCrud();
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Confirmation");
//        alert.setHeaderText("Voulez-vous vraiment supprimer ce Hebergement ?");
//        Optional<ButtonType> result = alert.showAndWait();
//        if (result.isPresent() && result.get() == ButtonType.OK) {
//            service.delete(hebergement.getId());
//            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/ListeHebergemnets.fxml"));
//            Scene tableViewScene = new Scene(tableViewParent);
//            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            window.setScene(tableViewScene);
//            window.show();
//        }
//    }

    public void updatefonction(javafx.event.ActionEvent event) {
    }
}
