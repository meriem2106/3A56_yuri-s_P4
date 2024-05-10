package Controllers.FrontOffice;

import entities.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class ShowEvent {
    @FXML
    private Button retour;
    @FXML
    private Label catevent;

    @FXML
    private Label conevent;

    @FXML
    private Label debevent;

    @FXML
    private Label decevent;

    @FXML
    private Label finevent;

    @FXML
    private ImageView imgevent;

    @FXML
    private Label lieuevent;

    @FXML
    private Button modifierevent;

    @FXML
    private Label nbevent;

    @FXML
    private Label nomevent;

    @FXML
    private Label tranevent;



    private Evenement evenement;


    public void setData(Evenement evenement) {


        this.evenement = evenement;
        nomevent.setText("Nom:                " + evenement.getNom());
        lieuevent.setText("Lieu:              " + evenement.getLieu());
        conevent.setText("Contexte:           " + evenement.getContexte());
        catevent.setText("Catégorie:          " + evenement.getCat());
        nbevent.setText("Nombres de place disponibles: " + evenement.getNbPlacesMax());
        debevent.setText("Date de Début:      " + evenement.getDatedeb());
        finevent.setText("Date de Fin:        " + evenement.getDatefin());
        decevent.setText("Description:        " + evenement.getDescription());
        tranevent.setText("Transport:         " + evenement.getTransport());
        imgevent.setImage(new Image(System.getProperty("imagesPaths") + evenement.getImage()));


//        String path = evenement.getImage();
//
//        if (path != null) {
//            // Check if the path is not an absolute path (doesn't start with C:\)
//            if (!path.startsWith("C:\\USERS\\user\\OneDrive\\Desktop\\mme chaima li nekhdem aalih\\main\\java\\images")) {
//                // Assuming you have a base directory for your images, replace "YOUR_BASE_DIRECTORY" with your actual base directory
//                String baseDirectory = "C:\\USERS\\user\\OneDrive\\Desktop\\mme chaima li nekhdem aalih\\main\\java\\images";
//                path = baseDirectory + "\\" + path;
//                System.out.println(path);
//            }
//
//            File imageFile = new File(path);
//            Image image = new Image(imageFile.toURI().toString(), 200, 117, false, true);
//
//            this.imgevent.setImage(image);
//
//
//        }



    }
    @FXML
    void modifierevent(ActionEvent event) throws IOException {
        // Pass the evenement object to the ModifierEvenement controller
        Controllers.BackOffice.ModifierEvenement.ev = evenement;

        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Frontoffice/ModifierEvenement.fxml"));
        Parent modifierEvenementParent = loader.load();

        // Access the controller and set the evenement object
        Controllers.BackOffice.ModifierEvenement modifierEvenementController = loader.getController();
        modifierEvenementController.setData(evenement); // Assuming you have a setData method

        // Set the scene
        Scene modifierEvenementScene = new Scene(modifierEvenementParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(modifierEvenementScene);
        window.show();
    }

    @FXML
    <MouseEvent>
    void AfficherMap(MouseEvent event) {

        // Créer une WebView
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Récupérer la localisation de l'événement
        String lieu = evenement.getLieu();

        // Charger l'URL de Google Maps avec la localisation spécifique
        webEngine.load("https://www.google.com/maps/place/" + lieu);

        // Afficher la WebView dans une nouvelle fenêtre
        Stage mapStage = new Stage();
        mapStage.setScene(new Scene(webView, 800, 600));
        mapStage.show();

    }
    @FXML
    void retourner(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenement.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) retour.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void AfficherMap(MouseEvent mouseEvent) {
        WebView webView = new WebView();
        webView.getEngine().load("https://www.google.com/maps");

        Scene scene = new Scene(webView, 800, 600);

        Stage primaryStage = null;
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void pdf(ActionEvent event) {
    }

    // public static void main(String[] args) {
      //  launch(args);
    //

    }








//    public void setData(Evenement evenement) {
//        this.evenement = evenement;
//        nomevent.setText("Nom: " +evenement.getNom());
//        lieuevent.setText("Lieu: " + evenement.getLieu());
//        conevent.setText("Contexte: " + evenement.getContexte());
//        catevent.setText("Catégorie: " + evenement.getCat());
//        nbevent.setText("Nombres de place disponibles: " + evenement.getNbPlacesMax());
//        debevent.setText("Date de Début: " + evenement.getDatedeb());
//        finevent.setText("Date de Fin: " + evenement.getDatefin());
//        decevent.setText("Description: " + evenement.getDescription());
//        tranevent.setText("Transport: " + evenement.getTransport());
//
//
//
//
//
//        String path=evenement.getImage();
//
//        if (path!= null) {
//            // Check if the path is not an absolute path (doesn't start with C:\)
//            if (!path.startsWith("C:\\USERS\\user\\OneDrive\\Desktop\\mme chaima li nekhdem aalih\\main\\java\\images")) {
//                // Assuming you have a base directory for your images, replace "YOUR_BASE_DIRECTORY" with your actual base directory
//                String baseDirectory = "C:\\USERS\\user\\OneDrive\\Desktop\\mme chaima li nekhdem aalih\\main\\java\\images";
//                path = baseDirectory + "\\" + path;
//                System.out.println(path);
//            }
//
//            File imageFile = new File(path);
//            Image image = new Image(imageFile.toURI().toString(),200,117,false,true);
//
//            this.imgevent.setImage(image);
//
//        }
//
//
//    }
//    @FXML
//    void modifierevent(ActionEvent event) {
//
//    }

