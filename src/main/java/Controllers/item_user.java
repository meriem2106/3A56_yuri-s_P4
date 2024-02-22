/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import Entities.Role;
import Entities.User;
import Services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author guerf
 */
public class item_user implements Initializable {

    @FXML
    private ImageView img;

    private MyListener myListener;
    @FXML
    private Label nom_item;
    @FXML
    private Label prenom_item;
    @FXML
    private Label role_item;

    private User user = new User();
    public static User u = new User();
    private int id;

    public void setData(int idU, String nom, String prenom, Role role, int numtel, String email, String password, String url, MyListener myListener) {

        this.id = idU;
        this.myListener = myListener;
        nom_item.setText(nom);
        prenom_item.setText(prenom);
        role_item.setText(role.toString());
        String fullurl = "C:\\xampp\\htdocs\\user_images\\" + url;
        System.out.println("full url " + fullurl);

        try {
            img.setImage(new Image(new FileInputStream(fullurl)));
        } catch (FileNotFoundException e) {
            System.err.println("Error loading image: " + e);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void onClick() {
        myListener.onClick(user);
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
    }

    @FXML
    private void Click(MouseEvent event) {
        UserService userService = new UserService();
        List<User> L = new ArrayList<>();
        myListener.onClick(user);
        //covt.setId_co(Integer.parseInt(id_co.getText()));
        L = userService.rechercheUser(id);
        u.setId(L.get(0).getId());
        u.setNom(L.get(0).getNom());
        u.setPrenom(L.get(0).getPrenom());
        u.setNumtel(L.get(0).getNumtel());
        u.setEmail(L.get(0).getEmail());
        u.setPassword(L.get(0).getPassword());
        u.setRole(Role.valueOf(L.get(0).getRole().toString()));
        u.setImage(L.get(0).getImage());
    }

    public interface MyListener {

        void onClick(User user);
    }

}
