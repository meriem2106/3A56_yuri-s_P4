package Controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import Entities.User;
import Services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ItemUser {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView image_user;

    @FXML
    private Label nom_prenom;

    @FXML
    private Label roles;

    private MyListener myListener;
    private User user = new User();
    public static User u = new User();
    private int id;

    @FXML
    void initialize() {
    }

    @FXML
    void Click(MouseEvent event) throws IOException {
        UserService userService = new UserService();
        List<User> L = new ArrayList<>();
        myListener.onClick(user);
        L = userService.rechercheUser(id);
        u.setId(L.get(0).getId());
        u.setNom(L.get(0).getNom());
        u.setPrenom(L.get(0).getPrenom());
        u.setNum(L.get(0).getNum());
        u.setEmail(L.get(0).getEmail());
        u.setPassword(L.get(0).getPassword());
        u.setDateNaissance(L.get(0).getDateNaissance());
        u.setRoles(L.get(0).getRoles());
        u.setFile(L.get(0).getFile());
        u.setImage(L.get(0).getImage());
        u.setStatus(L.get(0).getStatus());

        Parent page1 = FXMLLoader.load(getClass().getResource("/detailsUser.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void setData(int id, String nom, String prenom, String email, String password, Date dateNaissance, String num, List<String> roles, String image, String file, String status, MyListener myListener) {

        this.id = id;
        this.myListener = myListener;
        nom_prenom.setText(nom+" "+prenom);
        this.roles.setText(status);

        String fullurl = "C:\\final\\public\\uploads\\files\\" + file;
        System.out.println("full url " + fullurl);

        try {
            image_user.setImage(new Image(new FileInputStream(fullurl)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e);
        }
    }

    public interface MyListener {
        void onClick(User user);
    }

}
