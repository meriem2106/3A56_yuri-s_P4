package Controllers.BackOffice;


import entities.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.EvenementCrud;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ModifierEvenement {
    public static Evenement ev;


    @FXML
    private TextField catup;
    @FXML
    private TextField conup;
    @FXML
    private DatePicker debup;
    @FXML
    private TextField decup;
    @FXML
    private DatePicker  finup;
    @FXML
    private ImageView imgup;
    @FXML
    private TextField lieuup;
    @FXML
    private TextField nomup;
    @FXML
    private TextField tranup;
    @FXML
    private TextField up;
    @FXML
    private Button modifier;
    @FXML
    private Button retour;

    private Evenement evenement;

    private EvenementCrud evC = new EvenementCrud();


    private Integer evenementId;

    String image ;

//    public void setData(Evenement evenement) {
//        this.evenement = evenement;
//        // Afficher les données de l'événement dans les champs correspondants
//        nomup.setText(evenement.getNom());
//        lieuup.setText(evenement.getLieu());
//        conup.setText(evenement.getContexte());
//        catup.setText(evenement.getCat());
//        // et ainsi de suite pour les autres champs...
//    }

    public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

public void initialize() {
    if (evenement != null) {
        debup.setValue(LOCAL_DATE(evenement.getDatedeb().toString()));
        nomup.setText(evenement.getNom());
        conup.setText(evenement.getContexte());
        catup.setText(evenement.getCat());
        finup.setValue(LOCAL_DATE(evenement.getDatefin().toString()));
        lieuup.setText(evenement.getLieu());
        tranup.setText(evenement.getTransport());
        decup.setText(evenement.getDescription());
        up.setText(evenement.getNbPlacesMax().toString());
        imgup.setImage(new Image(System.getProperty("imagesPaths") + evenement.getImage()));
//        Image image = new Image()
//        imgup.setImage(evenement.getImage());
        this.evenementId = evenement.getId();
    }
}
public Date convertLocalDateToDate(LocalDate date){
    ZoneId defaultZoneId = ZoneId.systemDefault();

    //local date + atStartOfDay() + default time zone + toInstant() = Date
    Date returnedDate = Date.from(date.atStartOfDay(defaultZoneId).toInstant());
    return returnedDate;
}
    @FXML
    void modifier(ActionEvent event) {
        String Nom = nomup.getText();
        String Contexte = conup.getText();
        String Catégorie = catup.getText();
        Date Date_de_début = convertLocalDateToDate(debup.getValue()) ;
        Date Date_de_fin = convertLocalDateToDate(finup.getValue());
        String Lieu = lieuup.getText();
        String Transport = tranup.getText();
        String Description = decup.getText();
        Integer Nombre_de_places = 0;
        try{
            Nombre_de_places = Integer.parseInt(up.getText());
        }catch(NumberFormatException e){
            showAlert(Alert.AlertType.ERROR,"Erreur" , "Veuillez saisir un nombre dans le champs du nombre de places");
        }


        if (Nombre_de_places<1 || Nom.isEmpty() || Description.isEmpty() || Contexte.isEmpty() || Catégorie.isEmpty() || Lieu.isEmpty() || Transport.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs!");
        } else {
            try {
                Evenement c = new Evenement();
                c.setContexte(Contexte);
                c.setNom(Nom);
                c.setDescription(Description);
                c.setTransport(Transport);
                c.setCat(Catégorie);
                c.setLieu(Lieu);
                c.setNbPlacesMax(Nombre_de_places);
                c.setDatedeb(Date_de_début);
                c.setDatefin(Date_de_fin);
                c.setId(evenementId);
                c.setImage(image);

//                c.setId(evenement.getId());
                EvenementCrud sc = new EvenementCrud();
                sc.update(c);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Evenement modifié avec succès");
                clearFields();
                loadListeProduitsFXML(event);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la modification de l'événement");
                ex.printStackTrace();
            }
        }

    }
    private void clearFields() {
        nomup.clear();
        conup.clear();
        lieuup.clear();
        catup.clear();

    }
    private void loadListeProduitsFXML(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherEvenement.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    private void showAlert(Alert.AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void setData(Evenement evenement) {
        this.evenement = evenement;
        nomup.setText(evenement.getNom());
        conup.setText(evenement.getContexte());
        catup.setText(evenement.getCat());
        debup.setValue(LOCAL_DATE(evenement.getDatedeb().toString()));
        finup.setValue(LOCAL_DATE(evenement.getDatefin().toString()));
        lieuup.setText(evenement.getLieu());
        tranup.setText(evenement.getTransport());
        decup.setText(evenement.getDescription());
        evenementId = evenement.getId();

        imgup.setImage(new Image(System.getProperty("imagesPaths") + evenement.getImage()));
        image = evenement.getImage();
        // Vérifier si getNbPlacesMax() est différent de null avant de l'assigner à up
        Integer nbPlacesMax = evenement.getNbPlacesMax();
        if (nbPlacesMax != null) {
            up.setText(Integer.toString(nbPlacesMax.intValue()));
        } else {
            up.setText("dd"); // ou une valeur par défaut
        }
    }


    public void retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/ShowEvent.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) retour.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void imgup(MouseEvent mouseEvent) {
    }
//        // Mettre à jour les attributs de l'événement en utilisant la méthode update de EvenementCrud
//        EvenementCrud evenementCrud = new EvenementCrud();
////        evenementCrud.update(evenement.getId(), nomup.getText(), lieuup.getText(), conup.getText(), catup.getText(),debup.getText(),finup.getText(),decup.getText(),tranup.getText());
//        // Mettre à jour l'affichage ou effectuer d'autres actions nécessaires après la modification
    }

//    @FXML
//    void retour(ActionEvent event) {
//        // Ajoutez ici le code pour retourner à la vue précédente
//    }
//}








