package Services;

import Entities.Role;
import Entities.User;
import Utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService{

    Connection conn = DataSource.getInstance().getCnx();
    @Override
    public void ajouterUser(User user) {
        try {
            String query = "INSERT INTO user (nom, prenom, numtel,email,password,role) VALUES (?, ?, ?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setInt(3, user.getNumtel());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getRole().toString());
            preparedStatement.executeUpdate();
            System.out.println("user ajouté");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    @Override
    public void modifierUser(User user, int id) {
        try {
            String query = "UPDATE `user` SET `nom` = '" + user.getNom() + "', `prenom` = '" + user.getPrenom() + "', `numtel` = '" + user.getNumtel()  + "', `email` = '" + user.getEmail()  + "', `password` = '" + user.getPassword()  +   "' WHERE `user`.`id` = " + id;
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            System.out.println("user updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerUser(int id) {
        try {
            String req = "DELETE FROM `user` WHERE id = " + id;
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("user deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<User> afficherUser() {
        List<User> list = new ArrayList<>();
        try {
            String query = "Select * from user";
            Statement st = conn.createStatement();

            ResultSet RS = st.executeQuery(query);
            while (RS.next()) {
                User user = new User();
                user.setId(RS.getInt(1));
                user.setNom(RS.getString(2));
                user.setPrenom(RS.getString(3));
                user.setNumtel(RS.getInt(4));
                user.setEmail(RS.getString(5));
                user.setPassword(RS.getString(6));
                user.setRole(Role.valueOf(RS.getString(7)));

                list.add(user);
                System.out.println(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }
    @Override
    public boolean test_num_telephonique(String numtel) {
        int i;
        String[] tab = {"0", "1", "4", "6", "8"};
        for (i = 0; i < tab.length; i++) {
            if (numtel.charAt(0) == tab[i].charAt(0)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean test_Tel(String numtel) {
        int i;

        if (numtel.length() != 8) {
            return false;
        }

        for (i = 0; i < numtel.length(); i++) {

            if ((!(numtel.charAt(i) >= '0' && numtel.charAt(i) <= '9')) || (test_num_telephonique(numtel) == false)) {
                return false;
            }

        }
        return true;
    }

    @Override
    public boolean test_Email(String mail) {
        int test = 0;
        int position = 0;
        int test2 = 0;
        String[] tab = {"/", ";", ",", ":", "'", "&", "=", ">", "-", "_", "+", " ","!"};

        for (int i = 0; i < mail.length(); i++) {
            if (mail.charAt(i) == "@".charAt(0)) {
                test++;
                position = i;
            }

        }
        for (int k = 0; k < mail.length(); k++) {

            for (String tab1 : tab) {
                if (mail.charAt(k) == tab1.charAt(0)) {
                    return false;
                }
            }
        }
        for (int i = 0; i < mail.length(); i++) {
            if ((test == 1) && (mail.charAt(i) == ".".charAt(0))) {

                if (((mail.length() > i + 2) && (i > position + 4))) {
                    for (int j = position; j < mail.length(); j++) {
                        if (mail.charAt(j) == ".".charAt(0)) {
                            test2++;

                        }
                    }
                    if (test2 > 1) {
                        return false;
                    }

                    return true;
                }

            }

        }
        return false;
    }

}
