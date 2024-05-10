package Controllers.FrontOffice;

import entities.Hotel;
import entities.Maison;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Services.ServiceHotel;
import Services.ServiceMaison;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Home implements Initializable {

    @FXML
    private HBox cardHotel;

    @FXML
    private HBox cardMaison;

    ServiceHotel sh = new ServiceHotel();

    ServiceMaison sm = new ServiceMaison();

    public Home() throws SQLException {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            for (Hotel hotel : sh.afficher()) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FrontOffice/HotelCard.fxml"));
                VBox cardBox = fxmlLoader.load();
                HotelCard cardController = fxmlLoader.getController();
                cardController.setData(hotel);
                cardHotel.getChildren().add(cardBox);
            }
            for (Maison maison : sm.afficher()) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FrontOffice/MaisonCard.fxml"));
                VBox cardBoxM = fxmlLoader.load();
                MaisonCard cardController = fxmlLoader.getController();
                cardController.setData(maison);
                cardMaison.getChildren().add(cardBoxM);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
