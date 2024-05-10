package Services;

import entities.Formation;
import entities.Produit;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormationCrud implements Crud<Formation> {
    Connection connection = MyDatabase.getConnection();

    public FormationCrud() throws SQLException {
    }

    @Override
    public Formation create(Formation formation) {
        try {
            // Create a PreparedStatement for the INSERT statement
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO formation (sujet, date, heure, lieu) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            // Set the values for the parameters
            preparedStatement.setString(1, formation.getSujet());
            preparedStatement.setDate(2, Date.valueOf(formation.getDate()));
            preparedStatement.setTime(3, Time.valueOf(formation.getHeure()));
            preparedStatement.setString(4, formation.getLieu());
            //preparedStatement.setInt(5, formation.getProduit().getId()); // Assuming Produit has getId()

            // Execute the INSERT statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                // Get the auto-generated key (if any)
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    formation.setId(generatedKeys.getInt(1));
                }
                return formation;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return null; // Return null if insertion failed
    }


    @Override
    public Formation update(Formation formation) {
        try {
            // Create a PreparedStatement for the UPDATE statement
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE formation SET sujet=?, date=?, heure=?, lieu=? WHERE id=?");

            // Set the values for the parameters
            preparedStatement.setString(1, formation.getSujet());
            preparedStatement.setDate(2, Date.valueOf(formation.getDate()));
            preparedStatement.setTime(3, Time.valueOf(formation.getHeure()));
            preparedStatement.setString(4, formation.getLieu());
            preparedStatement.setInt(5, formation.getId());

            // Execute the UPDATE statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the update was successful
            if (rowsAffected > 0) {
                return formation;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return null; // Return null if update failed
    }

    @Override
    public boolean delete(int id) {
        try {
            // Create a PreparedStatement for the DELETE statement
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM formation WHERE id=?");

            // Set the value for the parameter
            preparedStatement.setInt(1, id);

            // Execute the DELETE statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the deletion was successful
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
            return false;
        }
    }

    @Override
    public Formation exists(Formation formation) {
        try {
            // Create a PreparedStatement for the SELECT statement
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM formation WHERE id=?");

            // Set the value for the parameter
            preparedStatement.setInt(1, formation.getId());

            // Execute the SELECT statement
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a formation with the given ID exists
            if (resultSet.next()) {
                // Construct and return a Formation object with the retrieved data
                Formation existingFormation = new Formation();
                existingFormation.setId(resultSet.getInt("id"));
                existingFormation.setSujet(resultSet.getString("sujet"));
                existingFormation.setDate(resultSet.getDate("date").toLocalDate());
                existingFormation.setHeure(resultSet.getTime("heure").toLocalTime());
                existingFormation.setLieu(resultSet.getString("lieu"));
                return existingFormation;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return null; // Return null if no formation with the given ID exists
    }
    @Override
    public List<Formation> findAll() {
        List<Formation> output = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM formation");

            while (resultSet.next()) {
                Formation formation = new Formation();
                formation.setId(resultSet.getInt("id"));
                formation.setSujet(resultSet.getString("sujet"));
                formation.setDate(resultSet.getDate("date").toLocalDate());
                formation.setHeure(resultSet.getTime("heure").toLocalTime());
                formation.setLieu(resultSet.getString("lieu"));

                // Assuming Produit has a separate retrieval method based on ID
                int produitId = resultSet.getInt("produit_id");
                formation.setProduit(findProduitById(produitId)); // Replace with your logic to retrieve Produit

                output.add(formation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }

    @Override
    public Formation findById(int id) {
        try {
            // Create a PreparedStatement for the SELECT statement
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM formation WHERE id=?");

            // Set the value for the parameter
            preparedStatement.setInt(1, id);

            // Execute the SELECT statement
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a formation with the given ID exists
            if (resultSet.next()) {
                Formation formation = new Formation();
                formation.setId(resultSet.getInt("id"));
                formation.setSujet(resultSet.getString("sujet"));
                formation.setDate(resultSet.getDate("date").toLocalDate());
                formation.setHeure(resultSet.getTime("heure").toLocalTime());
                formation.setLieu(resultSet.getString("lieu"));

                // Assuming Produit has a separate retrieval method based on ID
                int produitId = resultSet.getInt("produit_id");
                formation.setProduit(findProduitById(produitId)); // Replace with your logic to retrieve Produit

                return formation;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return null; // Return null if no formation found or any exception occurs
    }

    private Produit findProduitById(int id) throws SQLException {
        ProduitCrud p=new ProduitCrud();
        return p.findById(id);
    }
}
