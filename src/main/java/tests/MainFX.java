package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontOffice/AjouterHotel.fxml"));

        Parent root= loader.load();
        primaryStage.setScene(new Scene (root,1280,720));
        primaryStage.setTitle("test");
        primaryStage.show();
    }
}
