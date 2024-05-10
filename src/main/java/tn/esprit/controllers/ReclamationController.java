package tn.esprit.controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.esprit.models.Reclamation;
import tn.esprit.services.ServiceReclamation;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class ReclamationController implements Initializable {


    @FXML
    private Tab gusertab;

    @FXML
    public TextField idtf;
    @FXML
    private DatePicker datetf;
    @FXML
    private ImageView imagepdp;

    @FXML
    private Tab listusertab;

    @FXML
    public TextField nomtf;

    @FXML
    public TextField prenomtf;
    @FXML
    public TextField emailtf;
    @FXML
    public TextField desctf;
    @FXML
    public TextField sujettf ;

    @FXML
    private Label uinfolabel;

    @FXML
    private GridPane userContainer;

    @FXML
    private TextField usersearch;

    @FXML
    private TabPane usertp;

    private final ServiceReclamation ReclamationS = new ServiceReclamation();
    private Connection cnx;


    public void initialize(URL url, ResourceBundle rb) {
        load();
    }


    @FXML
    void AjouterUser(ActionEvent event) throws SQLException {
        String NOM = nomtf.getText();
        String PRENOM = prenomtf.getText();
        String SUJET = sujettf.getText();
        String DESCRIPTION = desctf.getText();
        String EMAIL = emailtf.getText();
        Date DATE = Date.valueOf(datetf.getValue());

        if (EMAIL.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@(esprit\\.tn|gmail\\.com)$")) {


            ReclamationS.Add(new Reclamation(NOM,PRENOM,SUJET,DESCRIPTION,EMAIL,DATE));
                uinfolabel.setText("Ajout Effectue");
            String msg = "Votre reclamation a été enregistré.\n\n" +
                    "Détails du reclamation :\n" +
                    "Date : " + DATE.toString() + "\n" +
                    "Sujet : " + SUJET.toString() + "\n" +
                    "Description : " + DESCRIPTION.toString() + "\n\n" +

                    "Nous avons hâte de vous repondre bientot !";

            // Envoi de l'email avec le message contenant les détails du rendez-vous
            sendEmail(EMAIL, msg);
        }
        else {
            uinfolabel.setText("Email est invalide");
        }
    }
    private void sendEmail(String toEmail, String msg) {
        final String username = "waves.esprit@gmail.com";
        final String password = "tgao tbqg wudl aluo";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("waves.esprit@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Reclamation En Succes");
            message.setText(msg);
            Transport.send(message);
            System.out.println("OTP email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void Deconnection(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Close the stage (which effectively closes the scene)
        stage.close();
    }

    @FXML
    void Menu1(ActionEvent event) {

    }

    @FXML
    void ModifierUser(ActionEvent event) {
        int ID = Integer.parseInt(idtf.getText());
        String NOM = nomtf.getText();
        String PRENOM = prenomtf.getText();
        String SUJET = sujettf.getText();
        String DESCRIPTION = desctf.getText();
        String EMAIL = emailtf.getText();
        Date DATE = Date.valueOf(datetf.getValue());

        if (EMAIL.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@(esprit\\.tn|gmail\\.com)$")) {
            ReclamationS.Update(new Reclamation(ID,NOM,PRENOM,SUJET,DESCRIPTION,EMAIL,DATE));
            uinfolabel.setText("Modification Effectue");
        }
        else {
            uinfolabel.setText("Email est invalide");
        }
    }
    public void load() {
        int column = 0;
        int row = 1;
        try {
            for (Reclamation reclamation : ReclamationS.afficher()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser.fxml"));
                Pane userBox = fxmlLoader.load();
                CardUserController cardC = fxmlLoader.getController();
                cardC.setData(reclamation);
                if (column == 2) {
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
                fxmlLoader.setLocation(getClass().getResource("/CardUser.fxml"));
                Pane userBox = fxmlLoader.load();
                CardUserController cardC = fxmlLoader.getController();
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
    void TriEmail(ActionEvent event) {
        int column = 0;
        int row = 1;
        try {
            for (Reclamation reclamation : ReclamationS.TriparEmail()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser.fxml"));
                Pane userBox = fxmlLoader.load();
                CardUserController cardC = fxmlLoader.getController();
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
    void TriNom(ActionEvent event) {
        int column = 0;
        int row = 1;
        try {
            for (Reclamation reclamation : ReclamationS.TriparNom()) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/CardUser.fxml"));
                Pane userBox = fxmlLoader.load();
                CardUserController cardC = fxmlLoader.getController();
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
    void extract(ActionEvent event) {
        try {
            generatePDF();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void generatePDF() throws FileNotFoundException {
        // Get the path to the Downloads directory
        String downloadsDir = System.getProperty("user.home") + "/Downloads/";

        // Create a PDF file in the Downloads directory
        File file = new File(downloadsDir + "reclamations.pdf");
        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdf = new PdfDocument(writer);

        // Create a document
        Document document = new Document(pdf);
//logo houni

        // Add content to the document
        for (Reclamation reclamation : ReclamationS.afficher()) {
            document.add(new Paragraph("Nom:       " + reclamation.getNom()));
            document.add(new Paragraph("Prénom:    " + reclamation.getPrenom()));
            document.add(new Paragraph("Email:     " + reclamation.getEmail()));
            document.add(new Paragraph("Descritpion: " + reclamation.getDescription()));
            document.add(new Paragraph("\n")); // Add a blank line between users
        }
        // Close the document
        document.close();

        System.out.println("PDF file generated successfully at: " + file.getAbsolutePath());
    }


    @FXML
    private Button goback_butt;

    @FXML
    void GoBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/Home.fxml"));
        Parent loginSuccessRoot = loader.load();
        Scene scene = goback_butt.getScene();
        scene.setRoot(loginSuccessRoot);
    }
}


