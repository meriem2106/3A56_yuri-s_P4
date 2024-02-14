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


}
