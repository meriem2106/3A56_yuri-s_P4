package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import tn.esprit.models.Reclamation;

import tn.esprit.models.Reponse;
import tn.esprit.services.ServiceReclamation;
import tn.esprit.services.ServiceReponse;
import tn.esprit.utils.MyDataBase;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ResourceBundle;

public class FrontUserController implements Initializable {
    @FXML
    public TextField emailtf;
    @FXML
    public TextField nommedecin;

    @FXML
    private Tab gusertab;

    @FXML
    public TextField idtf;

    @FXML
    private ImageView imagepdp;

    @FXML
    private Tab listusertab;

    @FXML
    public TextField nomtf;

    @FXML
    public TextField prenomtf;

    @FXML
    public TextField specialitetf;
    @FXML
    private DatePicker datetf;

    @FXML
    public TextField contenuetf;

    @FXML
    private Label uinfolabel;

    @FXML
    private GridPane userContainer;

    @FXML
    private TextField usersearch;

    @FXML
    private TabPane usertp;
    private final ServiceReclamation ReclamationS = new ServiceReclamation();
    private final ServiceReponse ReponseS = new ServiceReponse();
    private Connection cnx;
    public void initialize(URL url, ResourceBundle rb) {

        load();
    }


    public void load() {
        int column = 0;
        int row = 1;
        try {
            for (Reclamation reclamation : ReclamationS.afficher()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser1.fxml"));
                Pane userBox = fxmlLoader.load();
                CardUserrController cardC = fxmlLoader.getController();
                cardC.setData(reclamation);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void RechercheNom(ActionEvent event) {
        int column = 0;
        int row = 1;
        String recherche = usersearch.getText();
        try {
            userContainer.getChildren().clear();
            for (Reclamation reclamation : ReclamationS.Rechreche(recherche)){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser1.fxml"));
                Pane userBox = fxmlLoader.load();
                CardUserrController cardC = fxmlLoader.getController();
                cardC.setData(reclamation);
                if (column == 3) {
                    column = 0;
                    ++row;
                }
                userContainer.add(userBox, column++, row);
                GridPane.setMargin(userBox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void refresh(ActionEvent event) {
        load();
    }
    @FXML
    void ajouterRV(ActionEvent event)throws SQLException{


        String NOM = nomtf.getText();
        String PRENOM = prenomtf.getText();
        String CONTENUE = contenuetf.getText();





            ReponseS.Add(new Reponse(NOM,PRENOM,CONTENUE));
            uinfolabel.setText("Ajout Effectue");



    }





    @FXML
    void Deconnection(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Close the stage (which effectively closes the scene)
        stage.close();
    }
}
