package services;

import entities.Invitation;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InvitationCrud implements Crud<Invitation>{
    Connection connection = MyDatabase.getInstance().getConnection();

    public InvitationCrud() throws SQLException {
    }

    @Override
    public Invitation create(Invitation o) {
         try {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `invitation`( `nbrAdulte`, `nbrMaj`, `date_acceuil`, `date_retour`, `description`) VALUES (?,?,?,?,?)");

            // Set the values for the parameters
            preparedStatement.setInt(1,o.getNbrAdulte());
            preparedStatement.setInt(2,o.getNbrMaj());
            preparedStatement.setString(3,o.getDate_acceuil());
            preparedStatement.setString(4, o.getDate_retour());
            preparedStatement.setString(5, o.getDescription());


            // Execute the INSERT statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Invitation update(Invitation o) {
        return null;
    }

    @Override
    public boolean delete(int o) {
        return false;
    }

    @Override
    public Invitation exists(Invitation o) {
        return null;
    }

    @Override
    public List<Invitation> findAll() {
        return List.of();
    }

    @Override
    public Invitation findById(int Id) {
        return null;
    }
}
