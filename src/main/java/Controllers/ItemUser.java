package Controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import Entities.User;
import Services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
    void Click(MouseEvent event) {
        UserService userService = new UserService();
        List<User> L = new ArrayList<>();
        myListener.onClick(user);
        //covt.setId_co(Integer.parseInt(id_co.getText()));
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
    }

    public void setData(int id, String nom, String prenom, String email, String password, Date dateNaissance, String num, List<String> roles, String image, String file, MyListener myListener) {

        this.id = id;
        this.myListener = myListener;
        nom_prenom.setText(nom+" "+prenom);
        this.roles.setText(roles.toString());

        String fullurl = "C:\\xampp\\htdocs\\user_images\\" + file;
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
