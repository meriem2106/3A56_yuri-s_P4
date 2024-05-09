package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Services.UserService;
import Utils.SendMail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ForgotPassword {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField email_tf;

    private boolean verification = false;
    static String emailaddress;

    @FXML
    void initialize() {
    }

    @FXML
    void send_code(ActionEvent event) throws IOException, InterruptedException {
        UserService su = new UserService();

        SendMail sm = new SendMail();
        String code_random = "";

        String email = this.email_tf.getText();
        emailaddress = this.email_tf.getText();
        String resultat = "";


        if (email.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs!");

        } else if (!su.verfier_mail(email)) {
            System.out.println("Verifier Vos Données");

        } else { //email and id sonts correct
            code_random = code_random();

            sm.envoyerMail(email, "Mail Pour Verification", "Voice Votre Code de Verification : " + code_random);
            resultat = affichage_box_code(code_random);

            if ("true".equals(resultat)) {

                information_Box("Code Correct", "Votre Code est Correct Veuillez modifier votre mot de passe");
                this.verification = true;
                Parent page1 = FXMLLoader.load(getClass().getResource("/changePassword.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } else if (!"close".equals(resultat)) {
                alert_Box("Code Incorrect", "Vous avez atteint toutes vos tentaives,Ressayez Plus Tard");

                this.verification = false;
            }

        }
    }

    private String code_random() {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();

    }

    public void alert_Box(String title, String message) {
        Alert dg = new Alert(Alert.AlertType.WARNING);
        dg.setTitle(title);
        dg.setContentText(message);
        dg.show();
    }

    public boolean alert_Box_verif_code(String title, String message) throws InterruptedException {

        boolean sortie = false;
        Alert.AlertType Type = Alert.AlertType.WARNING;

        Alert alert = new Alert(Type, "");
        alert.setTitle(title);
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            sortie = false;
        }

        return sortie;

    }


    private String affichage_box_code(String code_random) throws InterruptedException {
        int i = 0;
        boolean test = false;

        while (i <= 2 && !test) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Code Verification");
            dialog.setContentText("Un code de verification est envoyé a votre email");
            String code_saisie;

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {

                code_saisie = result.get();

                if (code_saisie.equals(code_random)) {
                    return "true";

                } else if (!code_saisie.equals(code_random) && i < 2) {
                    if (i == 0) {
                        test = true;
                        test = alert_Box_verif_code("Code Incorrect", "Il vous reste deux tentatives");

                    } else if (i == 1) {
                        test = true;

                        test = alert_Box_verif_code("Code Incorrect", "Il vous reste une seule  tentative");
                    }

                    i++;

                } else {

                    return "cancel";
                }

            } else {
                return "close";
            }
        }
        return "cancel";

    }

    public void information_Box(String title, String message) {
        Alert dg = new Alert(Alert.AlertType.INFORMATION);
        dg.setTitle(title);
        dg.setContentText(message);
        dg.show();
    }

}
