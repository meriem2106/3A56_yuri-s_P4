package Controllers;

import entities.Invitation;
import entities.PdfGenerator;
import services.InvitationCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.sql.SQLException;
import java.util.Properties;

public class AjouterInvitation {
    @FXML
    private Button EnvoyerFormulaire;
    @FXML
    private Button retoure;

    @FXML
    private TextField dateacTF;

    @FXML
    private TextField datereTF;

    @FXML
    private TextField descTF;

    @FXML
    private TextField nbradulteTF;

    @FXML
    private TextField nbrenfantTF;
    @FXML
    private Label emaillab;



    private final InvitationCrud pc;
    private String emailDeHebergement;

    public AjouterInvitation() throws SQLException {
        pc = new InvitationCrud();

    }
    @FXML
    public void initialize() {
//
    }
    @FXML
    public void EnvoyerFormulaire (ActionEvent event) {

        if (nbradulteTF.getText().isEmpty() || nbrenfantTF.getText().isEmpty() || dateacTF.getText().isEmpty() ||
                datereTF.getText().isEmpty() || descTF.getText().isEmpty()) {
            Alert alertType = new Alert(Alert.AlertType.ERROR);
            alertType.setTitle("Error");
            alertType.setHeaderText("Tous les champs du formulaire sont obligatoires. Veuillez les remplir.");
            alertType.show();
            return;
        }

        try {

            Invitation i = new Invitation();
            i.setNbrAdulte(Integer.parseInt(nbradulteTF.getText()));
            i.setNbrMaj(Integer.parseInt(nbrenfantTF.getText()));
            i.setDate_acceuil(dateacTF.getText());
            i.setDate_retour(datereTF.getText());
            i.setDescription(descTF.getText());


            InvitationCrud sc = new InvitationCrud();
            sc.create(i);


            PdfGenerator pdfGenerator = new PdfGenerator();
            pdfGenerator.generatePDF(nbradulteTF.getText(), nbrenfantTF.getText(), dateacTF.getText(), datereTF.getText(), descTF.getText());



            sendEmail(emaillab.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Votre demande a été bien traitée.");
            alert.showAndWait();

            nbradulteTF.clear();
            nbrenfantTF.clear();
            dateacTF.clear();
            datereTF.clear();
            descTF.clear();

        } catch (NumberFormatException ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer des nombres valides pour le nombre d'adultes et d'enfants.");
            alert.showAndWait();
        } catch (Exception ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors du traitement de votre demande.");
            alert.showAndWait();
            ex.printStackTrace();
        }

    }

    public void setEmailDeHebergement(String email) {
        this.emailDeHebergement = email;
        emaillab.setText(email);
    }
    @FXML
    void retourner(ActionEvent event) {

    }
    private void sendEmail(String recipientEmail) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myemail="mohamedaminejelijli@gmail.com";
        String password="zaxh scmq ncxr likl";


        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myemail, password);
            }
        });

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myemail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Nouvelle demande d'Acceuil");

            // Créer une partie du message pour le contenu de l'email
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Contenu de l'email");

            // Créer une partie du message pour le PDF en pièce jointe
            MimeBodyPart attachmentPart = new MimeBodyPart();
            String cheminDuPDF = "C:\\Users\\Amine\\Downloads\\pidev\\formulaire.pdf";
            FileDataSource sourceDeFichier = new FileDataSource(cheminDuPDF);
            attachmentPart.setDataHandler(new DataHandler(sourceDeFichier));
            attachmentPart.setFileName("nom-du-pdf.pdf");

            // Créer un conteneur pour les parties du message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            // Ajouter les parties du message au message principal
            message.setContent(multipart);

            // Envoyer le message
            Transport.send(message);
            System.out.println("Email envoyé avec succès.");

        } catch (MessagingException e) {
            throw new MessagingException("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }


}
