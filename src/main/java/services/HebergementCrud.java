package services;

import entities.Hebergement;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HebergementCrud implements Crud<Hebergement> {
    Connection connection = MyDatabase.getInstance().getConnection();

    public HebergementCrud() throws SQLException {
    }

    @Override
    public Hebergement create(Hebergement hebergement) {
        try {
            // Create a PreparedStatement for the INSERT statement
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO hebergement (nom, email, password, localisation, prix,telephone, image, image2) VALUES (?, ?, ?,?, ?, ?, ?, ?)");

            // Set the values for the parameters
            preparedStatement.setString(1, hebergement.getNom());
            preparedStatement.setString(2, hebergement.getEmail());
            preparedStatement.setString(3, hebergement.getPassword());
            preparedStatement.setString(4, hebergement.getLocalisation());
            preparedStatement.setString(5, hebergement.getPrix());
            preparedStatement.setString(6, hebergement.getTelephone());
            preparedStatement.setString(7, hebergement.getImage());
            preparedStatement.setString(8, hebergement.getImage2());

            // Execute the INSERT statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                return hebergement;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return null; // Return null if insertion failed
    }

    @Override
    public Hebergement update(Hebergement hebergement) {
        try {
            // Create a PreparedStatement for the UPDATE statement
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE hebergement SET nom=?, email=?, password=?, localisation=?, prix=?, image=?, image2=? WHERE id=?");

            // Set the values for the parameters
            preparedStatement.setString(1, hebergement.getNom());
            preparedStatement.setString(2, hebergement.getEmail());
            preparedStatement.setString(3, hebergement.getPassword());
            preparedStatement.setString(4, hebergement.getLocalisation());
            preparedStatement.setString(5, hebergement.getPrix());
            preparedStatement.setString(6, hebergement.getImage());
            preparedStatement.setString(7, hebergement.getImage2());
            preparedStatement.setInt(8, hebergement.getId());

            // Execute the UPDATE statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the update was successful
            if (rowsAffected > 0) {
                return hebergement;
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
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM hebergement WHERE id=?");

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
    public Hebergement exists(Hebergement o) {
        return null;
    }

    @Override
    public Hebergement findById(int id) {
        try {
            // Create a PreparedStatement for the SELECT statement
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM hebergement WHERE id=?");

            // Set the value for the parameter
            preparedStatement.setInt(1, id);

            // Execute the SELECT statement
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a hebergement with the given ID exists
            if (resultSet.next()) {
                // Construct and return a Hebergement object with the retrieved data
                Hebergement hebergement = new Hebergement(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("localisation"), resultSet.getString("prix"), resultSet.getString("telephone"), resultSet.getString("image"), resultSet.getString("image2"));
                return hebergement;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return null; // Return null if no hebergement with the given ID exists
    }

    @Override
    public List<Hebergement> findAll() {
        List<Hebergement> output = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM hebergement");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String localisation = resultSet.getString("localisation");
                String prix = resultSet.getString("prix");
                String telephone = resultSet.getString("telephone");
                String image = resultSet.getString("image");
                String image2 = resultSet.getString("image2");
                Hebergement temp = new Hebergement(id, nom, email, password, localisation, prix, telephone ,image, image2);
                output.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }
}

