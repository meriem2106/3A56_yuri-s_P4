package Controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Admin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView image_user;

    @FXML
    private Label nom_user;

    @FXML
    private Label prenom_user;

    private List<User> UserDataList = FXCollections.observableArrayList();
    private UserService userService = new UserService();
    private ItemUser.MyListener myListener;

    @FXML
    void delete(ActionEvent event) throws IOException {
        UserService userService = new UserService();
        userService.supprimerUser(ItemUser.u.getId());

        Parent page1 = FXMLLoader.load(getClass().getResource("/admin.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void details(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("/detailsUser.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void initialize() {
        UserDataList.addAll(userService.afficherUser());
        System.out.println("load data");
        if (UserDataList.size() > 0) {
            setChosenRec(UserDataList.get(0));
            myListener = new ItemUser.MyListener() {

                @Override
                public void onClick(User user) {
                    System.out.println("mouse clicked");
                    setChosenRec(user);
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

                if (column == 2) {
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

    private void setChosenRec(User u) {

        nom_user.setText(ItemUser.u.getNom());
        prenom_user.setText(ItemUser.u.getPrenom());
        String imagePath = "C:/final/public/uploads/files/" + ItemUser.u.getFile();
        try {
            image_user.setImage(new Image(new FileInputStream(imagePath)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

    }

}
