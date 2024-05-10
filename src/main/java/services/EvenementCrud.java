package services;

import entities.Evenement;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EvenementCrud implements Crud <Evenement>{
    Connection connection ;


    public EvenementCrud()  {
        connection=MyDatabase.getInstance().getConnection();
    }

    @Override
    public Evenement create(Evenement evenement) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO evenement (nom, contexte, cat, lieu, nbplacemax, transport, description, image, datedeb, datefin) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, evenement.getNom());
            preparedStatement.setString(2, evenement.getContexte());
            preparedStatement.setString(3, evenement.getCat());
            preparedStatement.setString(4, evenement.getLieu());
            preparedStatement.setInt(5, evenement.getNbPlacesMax());
            preparedStatement.setString(6, evenement.getTransport());
            preparedStatement.setString(7, evenement.getDescription());
            preparedStatement.setString(8, evenement.getImage());
            preparedStatement.setDate(9, new Date(evenement.getDatedeb().getTime()));
            preparedStatement.setDate(10, new Date(evenement.getDatefin().getTime()));

           preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void deleteByNom(String nom) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM evenement WHERE nom=?");
            preparedStatement.setString(1, nom);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Evenement update(Evenement evenement) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE evenement SET nom=?, contexte=?, cat=?, lieu=?, nbplacemax=?, transport=?, description=?, image=?, datedeb=?, datefin=? WHERE ref=?");
            preparedStatement.setString(1, evenement.getNom());
            preparedStatement.setString(2, evenement.getContexte());
            preparedStatement.setString(3, evenement.getCat());
            preparedStatement.setString(4, evenement.getLieu());
            preparedStatement.setInt(5, evenement.getNbPlacesMax());
            preparedStatement.setString(6, evenement.getTransport());
            preparedStatement.setString(7, evenement.getDescription());
            preparedStatement.setString(8, evenement.getImage());
            preparedStatement.setDate(9, new Date(evenement.getDatedeb().getTime()));
            preparedStatement.setDate(10, new Date(evenement.getDatefin().getTime()));
            preparedStatement.setInt(11, evenement.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                return evenement;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Evenement> rechercheAvancee(String lettre) {
        try {
            List<Evenement> evenements = findAll();

            return evenements.stream()
                    .filter(e -> e.getNom().toLowerCase().startsWith(lettre.toLowerCase()) ||
                            e.getCat().toLowerCase().startsWith(lettre.toLowerCase()) ||
                            e.getContexte().toLowerCase().startsWith(lettre.toLowerCase()))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(int ref) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM evenement WHERE ref=?");
            preparedStatement.setInt(1, ref);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Evenement exists(Evenement o) {
        return null;
    }


    @Override
    public List<Evenement> findAll() throws SQLException {
        List<Evenement> events= new ArrayList<>();
        String req="select * from evenement";
        Statement statement= connection.createStatement();

        ResultSet rs= statement.executeQuery(req);
        while (rs.next()){
            Evenement event= new Evenement();
            event.setNom(rs.getString("nom"));
            event.setContexte(rs.getString("contexte"));
            event.setDatedeb(rs.getDate("datedeb"));
            event.setDatefin(rs.getDate("datefin"));
            event.setCat(rs.getString("cat"));
            event.setLieu(rs.getString("lieu"));
            event.setNbPlacesMax(rs.getInt("nbplacemax"));
            event.setDescription(rs.getString("description"));
            event.setTransport(rs.getString("transport"));
            event.setImage(rs.getString("image"));
            event.setId(rs.getInt("ref"));

            events.add(event);
        }


        return events;
    }

    @Override
    public Evenement findById(int ref) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM evenement WHERE ref=?");
            preparedStatement.setInt(1, ref);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Evenement(
                        resultSet.getInt("ref"),
                        resultSet.getString("nom"),
                        resultSet.getString("contexte"),
                        resultSet.getString("cat"),
                        resultSet.getString("lieu"),
                        resultSet.getString("transport"),
                        resultSet.getInt("nbplacemax"),
                        resultSet.getString("description"),
                        resultSet.getString("image"),
                        resultSet.getDate("datedeb"),
                        resultSet.getDate("datefin")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(String nom) {
    }
}


