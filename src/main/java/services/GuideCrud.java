package services;

import entities.Guide;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuideCrud implements Crud<Guide> {
    Connection connection;

    public GuideCrud() {
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public Guide create(Guide guide) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO guide (nom, prenom, ville, langueparlee, description) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, guide.getNom());
            preparedStatement.setString(2, guide.getPrenom());
            preparedStatement.setString(3, guide.getVille());
            preparedStatement.setString(4, guide.getLangueParlee());
            preparedStatement.setString(5, guide.getDescription());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Guide update(Guide guide) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE guide SET nom=?, prenom=?, ville=?, langueparlee=?, description=? WHERE id=?");
            preparedStatement.setString(1, guide.getNom());
            preparedStatement.setString(2, guide.getPrenom());
            preparedStatement.setString(3, guide.getVille());
            preparedStatement.setString(4, guide.getLangueParlee());
            preparedStatement.setString(5, guide.getDescription());
            preparedStatement.setInt(6, guide.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return guide;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM guide WHERE id=?");
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Guide exists(Guide guide) {
        return null;
    }

    @Override
    public List<Guide> findAll() {
        List<Guide> guides = new ArrayList<>();
        try {
            String req = "SELECT * FROM guide";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                Guide guide = new Guide(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("ville"),
                        rs.getString("langueparlee"),
                        rs.getString("description")
                );
                guide.setId(rs.getInt("id"));
                guides.add(guide);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guides;
    }

    @Override
    public Guide findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM guide WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Guide(
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("ville"),
                        resultSet.getString("langueparlee"),
                        resultSet.getString("description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
