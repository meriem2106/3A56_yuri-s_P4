package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import tn.esprit.models.Reclamation;

import tn.esprit.services.ServiceReclamation;

import java.io.IOException;

public class CardUserController {
    @FXML
    private Label cardemail;
    @FXML
    private Label carddesc;
    @FXML
    private Label cardsujet;
    @FXML
    private Label cardnameprename;
    @FXML
    private Pane Card;
    @FXML
    private Label cardspecialite;
    private final ServiceReclamation ReclamationS = new ServiceReclamation();

    int uid;
    String unom, uprenom, uemail, usujet,udesc;

    private String[] colors = {"#CDB4DB", "#FFC8DD", "#FFAFCC", "#BDE0FE", "#A2D2FF",
            "#F4C2D7", "#FFD4E2", "#FFB7D0", "#A6D9FF", "#8BC8FF",
            "#E6A9CB", "#FFBFD3", "#FFA7C1", "#9AC2FF", "#74AFFA",
            "#D8B6D8", "#FFC9D7", "#FFB3C8", "#B0E1FF", "#8DCFFD",
            "#D3AADB", "#FFBEDF", "#FFA9CC", "#AFD5FF", "#93C5FF"};


    public void setData(Reclamation reclamation) {

        cardnameprename.setText(reclamation.getNom() + " " + reclamation.getPrenom());
        cardemail.setText(reclamation.getEmail());
        cardsujet.setText(reclamation.getSujet());
        carddesc.setText(reclamation.getDescription());


        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN}; // Example colors array

        Color randomColor = colors[(int)(Math.random() * colors.length)]; // Get a random color from the array

        BackgroundFill backgroundFill = new BackgroundFill(randomColor, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);

        Card.setBackground(background);
        Card.setStyle("-fx-border-radius: 5px; -fx-border-color: #808080;");


        uprenom = reclamation.getPrenom();
        uid = reclamation.getId();
        unom = reclamation.getNom();
        usujet = reclamation.getSujet();
        udesc= reclamation.getDescription();
        uemail=reclamation.getEmail();

    }
    public void modifuser(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontUser.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            FrontUserController AUC = loader.getController();
            String a = unom;
            String b = uprenom;
            AUC.nomtf.setText(a);
            AUC.prenomtf.setText(b);
            AUC.nomtf.setEditable(false);
            AUC.prenomtf.setEditable(false);




            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
