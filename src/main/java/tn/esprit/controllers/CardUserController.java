package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
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
    private Label cardnameprename;
    @FXML
    private Pane Card;
    @FXML
    private Label cardsujet;
    @FXML
    private Label carddesc;

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
        carddesc.setText(reclamation.getDescription());
        cardemail.setText(reclamation.getEmail());
        cardsujet.setText(reclamation.getSujet());


        Card.setBackground(Background.fill(Color.web(colors[(int)(Math.random()* colors.length)])));
        Card.setStyle("-fx-border-radius: 5px;-fx-border-color:#808080");

        uprenom = reclamation.getPrenom();
        uid = reclamation.getId();
        unom = reclamation.getNom();
        uemail = reclamation.getEmail();
        usujet= reclamation.getSujet();
        udesc= reclamation.getDescription();
    }

    public void suppuser(ActionEvent actionEvent) throws IOException {
        ReclamationS.DeleteByID(uid);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUser.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        ReclamationController AUC = loader.getController();
        stage.setScene(scene);
        stage.show();
    }

    public void modifuser(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminUser.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            ReclamationController AUC = loader.getController();
            AUC.idtf.setText(String.valueOf(uid));
            AUC.nomtf.setText(unom);
            AUC.prenomtf.setText(uprenom);
            AUC.emailtf.setText(uemail);
            AUC.sujettf.setText(usujet);
            AUC.desctf.setText(udesc);



            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
