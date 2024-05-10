package Services;

import entities.Produit;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitCrud implements Crud<Produit> {
    Connection connection= MyDatabase.getConnection();
    //private Connection cnx;

    public ProduitCrud() throws SQLException {
        //cnx= MyDatabase.getConnection();
    }

    @Override
    public Produit create(Produit produit) {
        // Vérifie si le nom du produit est valide avant d'effectuer l'insertion
        try {
            produit.setNom(produit.getNom()); // Assure que le nom est valide, sinon une exception est levée
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur lors de la création du produit : " + e.getMessage());
            return null; // Retourne null si le nom du produit n'est pas valide
        }

        try {
            // Create a PreparedStatement for the INSERT statement

            PreparedStatement preparedStatement = produit.getFormation() != null?
            connection.prepareStatement("INSERT INTO produit (nom, description, origine, categ, matiere, image,formation_id) VALUES (?, ?, ?, ?, ?,?,?)")
            :connection.prepareStatement("INSERT INTO produit (nom, description, origine, categ, matiere, image) VALUES (?, ?, ?, ?, ?,?)");

            // Set the values for the parameters
            preparedStatement.setString(1, produit.getNom());
            preparedStatement.setString(2, produit.getDescription());
            preparedStatement.setString(3, produit.getOrigine());
            preparedStatement.setString(4, produit.getCateg());
            preparedStatement.setString(5, produit.getMatiere());
            preparedStatement.setString(6, produit.getImage());
           if (produit.getFormation()!=null) {
               preparedStatement.setInt(7, produit.getFormation());
           }
            // Execute the INSERT statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                return produit;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return null; // Return null if insertion failed
    }

    @Override
    public Produit update(Produit produit) {
        try {
            // Create a PreparedStatement for the UPDATE statement
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE produit SET nom=?, description=?, origine=?, categ=?, matiere=?, image=? WHERE id=?");

            // Set the values for the parameters
            preparedStatement.setString(1, produit.getNom());
            preparedStatement.setString(2, produit.getDescription());
            preparedStatement.setString(3, produit.getOrigine());
            preparedStatement.setString(4, produit.getCateg());
            preparedStatement.setString(5, produit.getMatiere());
            preparedStatement.setString(6, produit.getImage());
            preparedStatement.setInt(7, produit.getId());

            // Execute the UPDATE statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if the update was successful
            if (rowsAffected > 0) {
                return produit;
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
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM produit WHERE id=?");

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
    public Produit exists(Produit produit) {
        try {
            // Create a PreparedStatement for the SELECT statement
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM produit WHERE id=?");

            // Set the value for the parameter
            preparedStatement.setInt(1, produit.getId());

            // Execute the SELECT statement
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a produit with the given ID exists
            if (resultSet.next()) {
                // Construct and return a Produit object with the retrieved data
                Produit existingProduit = new Produit(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("description"), resultSet.getString("origine"), resultSet.getString("categ"), resultSet.getString("matiere"), resultSet.getString("image"));
                return existingProduit;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return null; // Return null if no produit with the given ID exists
    }



    @Override
    public List<Produit> findAll() {
      List<Produit> output = new ArrayList<>();
       try {
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM `produit` WHERE 1");

           while (resultSet.next()) {
               int id = resultSet.getInt("id");
               String nom  = resultSet.getString("nom");
               String description = resultSet.getString("description");
                String origine = resultSet.getString("origine");
               String categ = resultSet.getString("categ");
                String matiere  = resultSet.getString("matiere");
               String image = resultSet.getString("image");// exp to get string from column "name"
               int formation_id =resultSet.getInt("formation_id");
               Produit temp = new Produit( id,  nom, description, origine, categ,  matiere,  image );
               temp.setFormation(formation_id);
                output.add(temp);
           }
       } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();

        }
        return output;

    }

    @Override
    public Produit findById(int id) {
        try {
            // Create a PreparedStatement for the SELECT statement
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM produit WHERE id=?");

            // Set the value for the parameter
            preparedStatement.setInt(1, id);

            // Execute the SELECT statement
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a product with the given ID exists
            if (resultSet.next()) {
                // Construct and return a Produit object with the retrieved data
                Produit produit = new Produit(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("description"), resultSet.getString("origine"), resultSet.getString("categ"), resultSet.getString("matiere"), resultSet.getString("image"));
                produit.setFormation(resultSet.getInt("formation_id"));

                return produit;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return null; // Return null if no product with the given ID exists
    }

}
