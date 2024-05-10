package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainFx extends Application {

    public static void main(String[] args) {
        System.setProperty("imagesPaths","File:///C:\\Users\\ASUS\\Desktop\\3A56_yuri-s_P3-main\\3A56_yuri-s_P3-main\\public\\pictures\\");
        System.setProperty("imagePathforCopy","C:\\Users\\ASUS\\Desktop\\3A56_yuri-s_P3-main\\3A56_yuri-s_P3-main\\public\\pictures\\");
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {
        // Chargement du fichier FXMLf
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeProduits.fxml"));
        Parent root = loader.load();

        // Création de la scène
        Scene scene = new Scene(root);

        // Configuration de la fenêtre principale
        primaryStage.setTitle("Gestion Artisanale");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

