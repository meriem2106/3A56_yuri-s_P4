package Services;

import Entities.User;
import Utils.DataSource;
import com.google.gson.Gson;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService implements UserInterface{

    Connection conn = DataSource.getInstance().getCnx();

    @Override
    public List<User> afficherUser() {
        List<User> list = new ArrayList<>();
        try {
            String query = "Select * from utilisateur";
            Statement st = conn.createStatement();

            ResultSet RS = st.executeQuery(query);
            while (RS.next()) {
                User user = new User();
                user.setId(RS.getInt("id"));
                user.setNom(RS.getString("nom"));
                user.setPrenom(RS.getString("prenom"));
                user.setEmail(RS.getString("email"));
                user.setPassword(RS.getString("password"));
                user.setNum(RS.getString("num_de_telephone"));
                user.setDateNaissance(RS.getDate("datedenaissance"));
                String rolesString = RS.getString("roles");
                if (rolesString != null && !rolesString.isEmpty()) {
                    // Convert the roles string to a list of strings
                    List<String> rolesList = Arrays.asList(rolesString.split(","));
                    user.setRoles(rolesList);
                }
                user.setImage(RS.getString("image"));
                user.setFile(RS.getString("file"));
                user.setStatus(RS.getString("status"));

                list.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public void ajouterUser(User user) throws SQLException {
        java.util.Date utilDate = user.getDateNaissance();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        String roles = new Gson().toJson(user.getRoles());

        String query = "INSERT INTO utilisateur (nom, roles, password, prenom, num_de_telephone, email, datedenaissance, file, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, user.getNom());
        preparedStatement.setString(2, roles);
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setString(4, user.getPrenom());
        preparedStatement.setString(5, user.getNum());
        preparedStatement.setString(6, user.getEmail());
        preparedStatement.setDate(7, sqlDate);
        preparedStatement.setString(8, user.getFile());
        preparedStatement.setString(9, user.getStatus());
        preparedStatement.executeUpdate();
        System.out.println("user ajouté");
    }

    @Override
    public void modifierUser(User user, int id) {
        java.util.Date utilDate = user.getDateNaissance();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try {

            String rolesString = user.getRoles().toString();

            // Remove square brackets from the beginning and end of rolesString
            rolesString = rolesString.substring(1, rolesString.length() - 1);

            String query = "UPDATE utilisateur SET nom=?, prenom=?, num_de_telephone=?, email=?, password=?, datedenaissance=?, file=?, roles=?, status=? WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getNum());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setDate(6, sqlDate);
            preparedStatement.setString(7, user.getFile());
            preparedStatement.setString(8, rolesString);
            preparedStatement.setString(9, user.getStatus());
            preparedStatement.setInt(10, id);

            // Execute the update
            preparedStatement.executeUpdate();

            System.out.println("User updated!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }



    @Override
    public void supprimerUser(int id) {
        try {
            String req = "DELETE FROM `utilisateur` WHERE id = " + id;
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("user deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
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

    @Override
    public boolean test_Tel(String numtel) {
        int i;

        if (numtel.length() != 8) {
            return false;
        }

        for (i = 0; i < numtel.length(); i++) {

            if ((!(numtel.charAt(i) >= '0' && numtel.charAt(i) <= '9')) || (!test_num_telephonique(numtel))) {
                return false;
            }

        }
        return true;
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
    public boolean test_dateNaissance(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(date, currentDate);
        return period.getYears() >= 16;
    }

    @Override
    public boolean verfier_mail(String mail) {
        Statement stm = null;
        ResultSet rst = null;

        try {
            stm = conn.createStatement();
            String query = "SELECT * FROM utilisateur WHERE email='" + mail + "'";
            rst = stm.executeQuery(query);
            if (rst.next()) {
                return true;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

        return false;
    }

    @Override
    public User rechercheUserByEmail(String email) {
        User user = new User();
        String req = "SELECT * FROM `utilisateur` WHERE email= ?";

        try (PreparedStatement ps = conn.prepareStatement(req)) {
            ps.setString(1, email);
            ResultSet RS = ps.executeQuery();

            if (RS.next()) {
                user.setId(RS.getInt("id"));
                user.setNom(RS.getString("nom"));
                user.setPrenom(RS.getString("prenom"));
                user.setNum(RS.getString("num_de_telephone"));
                user.setEmail(RS.getString("email"));
                user.setPassword(RS.getString("password"));
                user.setFile(RS.getString("file"));
                user.setStatus(RS.getString("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public void modifier_password(int id, String password) {
        Statement stm;
        try {
            stm = conn.createStatement();

            String query = "UPDATE utilisateur SET password='" + password + "' WHERE id= " + id;
            stm.executeUpdate(query);
            System.out.println("password updated");

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<User> rechercheUser(int id) {
        List<User> list = new ArrayList<>();
        try {
            String req = "SELECT * FROM `utilisateur` WHERE id= " + id;
            Statement st = conn.createStatement();
            ResultSet RS = st.executeQuery(req);
            while (RS.next()) {
                User user = new User();
                user.setId(RS.getInt("id"));
                user.setNom(RS.getString("nom"));
                user.setPrenom(RS.getString("prenom"));
                user.setEmail(RS.getString("email"));
                user.setPassword(RS.getString("password"));
                user.setNum(RS.getString("num_de_telephone"));
                user.setDateNaissance(RS.getDate("datedenaissance"));
                String rolesString = RS.getString("roles");
                if (rolesString != null && !rolesString.isEmpty()) {
                    // Convert the roles string to a list of strings
                    List<String> rolesList = Arrays.asList(rolesString.split(","));
                    user.setRoles(rolesList);
                }
                user.setImage(RS.getString("image"));
                user.setFile(RS.getString("file"));
                user.setStatus(RS.getString("status"));

                System.out.println(user);
                list.add(user);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return list;
    }

    public void banUser(int id) {

        try {
            String query = "UPDATE utilisateur SET status=? WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, "INACTIF");
            preparedStatement.setInt(2, id);

            // Execute the update
            preparedStatement.executeUpdate();

            System.out.println("User banned!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int countByRole(String role) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM utilisateur WHERE roles LIKE ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + role + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                }
            }
        }
        return 0;
    }

}
