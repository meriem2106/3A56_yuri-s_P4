package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import Entities.User;
import Services.UserService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DashboardAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Circle circle;

    @FXML
    private HBox collectBtn;

    @FXML
    private HBox collectBtn1;

    @FXML
    private HBox collectBtn2;

    @FXML
    private HBox collectBtn3;

    @FXML
    private HBox collectBtn4;

    @FXML
    private HBox collectBtn5;

    @FXML
    private Label collectText;

    @FXML
    private Label collectText1;

    @FXML
    private Label collectText2;

    @FXML
    private Label collectText3;

    @FXML
    private Label collectText4;

    @FXML
    private Label collectText5;

    @FXML
    private HBox commandsBtn1;

    @FXML
    private Pane content_area;

    @FXML
    private HBox dashboardBtn;

    @FXML
    private ImageView dashboardIcon;

    @FXML
    private Label dashboardText;

    @FXML
    private Button delete;

    @FXML
    private HBox fundrisingBtn;

    @FXML
    private Label fundrisingText;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField search_tf;

    @FXML
    private HBox navBarLogout;

    @FXML
    private Text navFullname;

    @FXML
    private HBox productsBtn;

    @FXML
    private Label productsText;

    @FXML
    private Button show;

    @FXML
    private HBox sideBarLogout;

    @FXML
    private HBox usersBtn;

    @FXML
    private Label usersText;

    @FXML
    private ChoiceBox<String> tri_choice;

    private List<User> UserDataList = FXCollections.observableArrayList();
    private UserService userService = new UserService();
    private ItemUser.MyListener myListener;
    static User user;

    @FXML
    void delete(ActionEvent event) {
    }

    @FXML
    void stats(ActionEvent event) {
        try {
            int proprietaireCount = userService.countByRole("Proprietaire");
            int organisateurCount = userService.countByRole("Organisateur");
            int touristeCount = userService.countByRole("Touriste");
            int artisanCount = userService.countByRole("Artisan");
            int familleCount = userService.countByRole("Famille d’accueil");

            // Create a PieChart to display the counts with pastel colors
            PieChart pieChart = new PieChart();
            pieChart.setTitle("Utilisateurs par rôle");
            pieChart.getData().addAll(
                    new PieChart.Data("Proprietaire", proprietaireCount),
                    new PieChart.Data("Organisateur", organisateurCount),
                    new PieChart.Data("Touriste", touristeCount),
                    new PieChart.Data("Artisan", artisanCount),
                    new PieChart.Data("Famille d’accueil", familleCount)
            );

            // Create a new stage for the popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(((Node) event.getSource()).getScene().getWindow()); // Using event to get the source
            popupStage.setTitle("Utilisateurs par rôle");

            // Add the PieChart to the scene and show the stage
            popupStage.setScene(new Scene(pieChart));
            popupStage.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

        tri_choice.getItems().addAll("Nom", "Prenom");

        // Listen for changes in the selected item of the choice box
        tri_choice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals("Nom")) {
                    sortUsersByNom();
                } else if (newValue.equals("Prenom")) {
                    sortUsersByPrenom();
                }
            }
        });

        navFullname.setText(Login.user.getNom());

        search_tf.textProperty().addListener((observable, oldValue, newValue) -> {
            filterUsers(newValue);
        });

        UserDataList.addAll(userService.afficherUser());

        if (UserDataList.size() > 0) {
            myListener = new ItemUser.MyListener() {

                @Override
                public void onClick(User user) {
                    System.out.println("mouse clicked");
                }
            };
        }

        int column = 0;
        int row = 3;
        for (int i = 0; i < UserDataList.size(); i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ItemUser.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemUser item = fxmlLoader.getController();
                System.out.println("user details name " + UserDataList.get(i).getNom() + " url : C:/final/public/uploads/files/" + UserDataList.get(i).getFile());
                item.setData(UserDataList.get(i).getId(),UserDataList.get(i).getNom(), UserDataList.get(i).getPrenom(),  UserDataList.get(i).getEmail(), UserDataList.get(i).getPassword(), UserDataList.get(i).getDateNaissance(), UserDataList.get(i).getNum(), UserDataList.get(i).getRoles(), UserDataList.get(i).getImage(), UserDataList.get(i).getFile(), UserDataList.get(i).getStatus(), myListener);

                if (column == 5) {
                    column = 0;
                    row++;
                }

                gridPane.add(anchorPane, column++, row);
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);
                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException e) {
                System.out.println("problem");
            }
        }
    }

    private void filterUsers(String query) {

        gridPane.getChildren().clear();

        if (query == null || query.isEmpty()) {
            displayUsers(UserDataList);
        } else {

            List<User> filteredList = UserDataList.stream()
                    .filter(user -> user.getNom().toLowerCase().contains(query.toLowerCase())
                            || user.getPrenom().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
            displayUsers(filteredList);
        }
    }

    private void displayUsers(List<User> userList) {
        int column = 0;
        int row = 3;
        for (User user : userList) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ItemUser.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemUser item = fxmlLoader.getController();
                item.setData(user.getId(), user.getNom(), user.getPrenom(), user.getEmail(), user.getPassword(), user.getDateNaissance(), user.getNum(), user.getRoles(), user.getImage(), user.getFile(), user.getStatus(), myListener);

                if (column == 5) {
                    column = 0;
                    row++;
                }

                gridPane.add(anchorPane, column++, row);
                gridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxWidth(Region.USE_PREF_SIZE);
                gridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setPrefHeight(Region.USE_COMPUTED_SIZE);
                gridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sortUsersByNom() {
        UserDataList.sort(Comparator.comparing(User::getNom));
        displayUsers(UserDataList);
    }

    private void sortUsersByPrenom() {
        UserDataList.sort(Comparator.comparing(User::getPrenom));
        displayUsers(UserDataList);
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("/login.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
