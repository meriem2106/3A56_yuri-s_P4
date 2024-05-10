package Controllers;

import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import Services.FormationCrud;
import Services.ProduitCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import  com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class AjouterProduit {
    @FXML
    private ComboBox<String> FormaCb;


    @FXML
    private TextField nomTF;

    @FXML
    private TextField imgTF;

    @FXML
    private TextField DescTF;

    @FXML
    private TextField OrigTF;


    @FXML
    private ComboBox<String> categTF;
    @FXML
    private TextField MatTF;
    @FXML
    private Button ajouter;


    @FXML
    private Button imgFx;


    private final ProduitCrud pc;
    private final FormationCrud fc;

    public static final String ACCOUNT_SID = "AC0f818c18814338cc09dbf3c64fa9471e";
    public static final String AUTH_TOKEN = "3036143fab402c0cf20cac97825661ae";

    public void sendSMS(String to, String body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber(to), // Numéro de téléphone de destination
                        new PhoneNumber("+12569739597"), // Votre numéro Twilio
                        body) // Corps du message
                .create();

        System.out.println("SMS envoyé avec succès : " + message.getSid());
    }
    List<Integer> formationids =new ArrayList<>();
    public AjouterProduit() throws SQLException {
        pc = new ProduitCrud();
        fc = new FormationCrud();
    }



    @FXML
    public void initialize() {
        // Appeler la méthode ChoisirFormation pour charger les sujets des formations disponibles
        ObservableList<String> sujets = FXCollections.observableArrayList(
                fc.findAll().stream().map(c ->{
                   formationids.add(c.getId()) ;
                   return c.getSujet();
                }).toArray(String[]::new));

        FormaCb.setItems(sujets);
        // Charger les catégories
        ObservableList<String> categories = FXCollections.observableArrayList(
                "ART DE LA TABLE", "VÊTEMENTS", "MAISON & DÉCORATION", "BIJOUX & ACCESSOIRES");
        categTF.setItems(categories);
    }


    @FXML
    public void Ajouter(ActionEvent actionEvent) {
//        try {
//            String nom = nomTF.getText();
//            if (!nom.isEmpty()) { // Vérifie si le champ nom n'est pas vide
//                String categorie = CategCB.getValue();
//
//                if (categorie != null) { // Vérifie si une catégorie est sélectionnée
//                    String imgPath = viewFx.getImage().getUrl();
//                    Produit produit = new Produit(nom, DescTF.getText(), OrigTF.getText(), Produit.Categorie.valueOf(categorie), MatTF.getText() ,imgPath);
//                    pc.create(produit);
//                } else {
//                    System.out.println("Veuillez sélectionner une catégorie.");
//                }
//            } else {
//                System.out.println("Veuillez entrer un nom pour le produit.");
//            }
//        } catch (IllegalArgumentException e) {
//            // Gérer l'exception d'un champ vide ou d'une valeur incorrecte
//            e.printStackTrace();
//        }
        if (nomTF.getText().isEmpty()){
            Alert alertType=new Alert(Alert.AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Nom est vide .Ce champ est obligatoire. Veuillez le remplir");
            alertType.show();
            return;
        }
        if (DescTF.getText().isEmpty()){
            Alert alertType=new Alert(Alert.AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Description est vide.Ce champ est obligatoire. Veuillez le remplir");
            alertType.show();
            return;
        }

        if (OrigTF.getText().isEmpty()){
            Alert alertType=new Alert(Alert.AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("l'origine est vide.Ce champ est obligatoire. Veuillez le remplir");
            alertType.show();
            return;
        }

        if (categTF.getValue() == null ||categTF.getValue().isEmpty() ){
            Alert alertType=new Alert(Alert.AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Categorie est vide.Ce champ est obligatoire. Veuillez le remplir");
            alertType.show();
            return;
        }



        if (MatTF.getText().isEmpty()){
            Alert alertType=new Alert(Alert.AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Matiere est vide.Ce champ est obligatoire. Veuillez le remplir");
            alertType.show();
            return;
        }





        else {
            try {
                Produit e = new Produit();
                e.setNom(nomTF.getText());
                e.setDescription(DescTF.getText());
                e.setOrigine(OrigTF.getText());
                e.setCateg(categTF.getValue());
                e.setMatiere(MatTF.getText());
                e.setImage(imgTF.getText());




                //e.setCategorieEvenement(cbcategorie.getValue());
                //e.setImage(uploadpath);
                ProduitCrud sc = new ProduitCrud();

                if (FormaCb.getSelectionModel().getSelectedIndex()!=-1){
                    e.setFormation(formationids.get(FormaCb.getSelectionModel().getSelectedIndex()));
                }
                sc.create(e);


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Produit ajouté avec succès");
                alert.showAndWait();
                String toPhoneNumber = "+216 53 542 151"; // Numéro de téléphone de destination
                String messageBody = "Nouvel Produit ajouté : " +nomTF.getText(); // Corps du message
                sendSMS(toPhoneNumber, messageBody);

                // Vider les champs
                nomTF.clear();
                DescTF.clear();
                OrigTF.clear();
                MatTF.clear();
                imgTF.clear();


                //tf_logo.clear();
                //cbcategorie.getSelectionModel().clearSelection();

            }

            catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'ajout du produit");
                alert.showAndWait();
                ex.printStackTrace();
            }
        }
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/ListeProduits.fxml"));
            nomTF.getScene().setRoot(root);
            //tf_nbMembres.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }


   }
    @FXML
    private void chargerImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*"));

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Chemin vers le répertoire des images
            String imagesPath = System.getProperty("imagePathforCopy");

            // Créer une copie du fichier sélectionné dans le répertoire des images
            File copiedFile = new File(imagesPath +  selectedFile.getName());
            Files.copy(selectedFile.toPath(),
                    copiedFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            // Charger l'image depuis le fichier sélectionné
           // Image image = new Image(copiedFile.toURI().toString());

            // Afficher l'URL de l'image dans le TextField
            imgTF.setText(copiedFile.getName());

            // Assigner l'image au produit
            // pc.setImage(image);
        }
    }
    @FXML
    void choisirCateg(ActionEvent event) {

    }

    @FXML
    void ChoisirFormation(ActionEvent event) {

    }


}
