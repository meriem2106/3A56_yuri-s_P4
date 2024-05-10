package Controllers;

import com.plexpt.chatgpt.ChatGPT;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Chatgpt implements Initializable {

    @FXML
    private Button bt_chat;

    @FXML
    private TextArea label_rep;

    @FXML
    private Button btn_retour;

    @FXML
    private TextField tf_question;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bt_chat.setOnAction(event -> {
            String response = sendChat(tf_question.getText());
            label_rep.setText(response);
            //tf_question.clear();
        });
    }
    public String sendChat(String message){
        ChatGPT chatgpt = ChatGPT.builder().apiKey("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")
                .build()
                .init();
        tf_question.clear();
        return chatgpt.chat(message);


    }

    @FXML
    void retourner(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeProduits.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btn_retour.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
}
