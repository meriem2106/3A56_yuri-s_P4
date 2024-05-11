package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {

    public static void main(String[] args) {
        System.setProperty("imagesPaths","File:///C:\\Users\\Amine\\tzzt\\public\\public\\uploads\\images\\HImage");
        System.setProperty("imagePathforCopy","C:\\Users\\Amine\\tzzt\\public\\public\\uploads\\images\\HImage");
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {
<<<<<<< Updated upstream
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
=======
>>>>>>> Stashed changes

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Gestion Hebergement");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}