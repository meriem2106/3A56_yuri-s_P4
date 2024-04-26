package services;

import entities.Hotel;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceHotel implements IService<Hotel> {

    Connection connection;

    public ServiceHotel() {
        connection = MyDatabase.getInstance().getConnection();

    }

    @Override
    public void ajouter(Hotel hotel) throws SQLException {

        if (hotel.getNom() == null || hotel.getNom().isEmpty() ||
                hotel.getEmail() == null || hotel.getEmail().isEmpty() ||
                hotel.getTelephone() == null || hotel.getTelephone().isEmpty() ||
                hotel.getLocalisation() == null || hotel.getLocalisation().isEmpty() ||
                hotel.getVille() == null || hotel.getVille().isEmpty() ||
                hotel.getDisponibilite() == null || hotel.getDisponibilite().isEmpty()) {
            throw new IllegalArgumentException("Tous les champs obligatoires doivent être remplis.");
        }
        String req = "INSERT INTO hotel (nb_etoiles,prix,nom,email,telephone,localisation,ville,disponibilite,description,image)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?);";
        try{

            PreparedStatement preparedStatement= connection.prepareStatement(req);


            preparedStatement.setInt(1,hotel.getNbEtoiles());
            preparedStatement.setString(2,hotel.getPrix());
            preparedStatement.setString(3,hotel.getNom());
            preparedStatement.setString(4,hotel.getEmail());
            preparedStatement.setString(5,hotel.getTelephone());
            preparedStatement.setString(6,hotel.getLocalisation());
            preparedStatement.setString(7,hotel.getVille());
            preparedStatement.setString(8,hotel.getDisponibilite());
            preparedStatement.setString(9,hotel.getDescription());
            preparedStatement.setString(10,hotel.getImage());

            preparedStatement.executeUpdate();
            System.out.println("L'hotel a été ajouté avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifier(Hotel hotel) throws SQLException {
        String req="update hotel set nom=?,nb_etoiles=?,localisation=?,ville=?,prix=?,email=?,telephone=?,description=?,disponibilite=?,image=?  where id=?";
        PreparedStatement preparedStatement= connection.prepareStatement(req);
        preparedStatement.setString(1,hotel.getNom());
        preparedStatement.setInt(2,hotel.getNbEtoiles());
        preparedStatement.setString(3,hotel.getLocalisation());
        preparedStatement.setString(4,hotel.getVille());
        preparedStatement.setString(5,hotel.getPrix());
        preparedStatement.setString(6,hotel.getEmail());
        preparedStatement.setString(7,hotel.getTelephone());
        preparedStatement.setString(8,hotel.getDescription());
        preparedStatement.setString(9,hotel.getDisponibilite());
        preparedStatement.setString(10,hotel.getImage());
        preparedStatement.setInt(11,hotel.getId());
        preparedStatement.executeUpdate();


    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM hotel WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setInt(1, id);
            int rowCount = preparedStatement.executeUpdate();

            if (rowCount > 0) {
                System.out.println("L'hôtel avec l'ID " + id + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun hôtel avec l'ID " + id + " n'a été trouvé.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression de l'hôtel avec l'ID " + id + ": " + ex.getMessage());
        }
    }

    @Override
    public List<Hotel> afficher() throws SQLException {
        List<Hotel> hotels= new ArrayList<>();
        String req="select * from hotel";
        Statement statement= connection.createStatement();

        ResultSet rs= statement.executeQuery(req);
        while (rs.next()){
            Hotel hotel= new Hotel();
            hotel.setNom(rs.getString("nom"));
            hotel.setNbEtoiles(rs.getInt("nb_etoiles"));
            hotel.setLocalisation(rs.getString("localisation"));
            hotel.setVille(rs.getString("ville"));
            hotel.setPrix(rs.getString("prix"));
            hotel.setEmail(rs.getString("email"));
            hotel.setTelephone(rs.getString("telephone"));
            hotel.setDescription(rs.getString("description"));
            hotel.setDisponibilite(rs.getString("disponibilite"));
            hotel.setImage(rs.getString("image"));
            hotel.setId(rs.getInt("id"));

            hotels.add(hotel);
        }


        return hotels;
    }

    public Hotel chercher(String nomHotel) {
        String req = "SELECT * FROM hotel WHERE nom LIKE ?";

        Hotel hotelTrouve = null;

        try {
            PreparedStatement prepStat = connection.prepareStatement(req);

            prepStat.setString(1, "%" + nomHotel + "%");

            ResultSet result = prepStat.executeQuery();

            if (result.next()) {
                hotelTrouve = new Hotel();
                hotelTrouve.setId(result.getInt("id"));
                hotelTrouve.setNom(result.getString("nom"));
                hotelTrouve.setNbEtoiles(result.getInt("nb_etoiles"));
                hotelTrouve.setPrix(result.getString("prix"));
                hotelTrouve.setEmail(result.getString("email"));
                hotelTrouve.setTelephone(result.getString("telephone"));
                hotelTrouve.setLocalisation(result.getString("localisation"));
                hotelTrouve.setVille(result.getString("ville"));
                hotelTrouve.setDisponibilite(result.getString("disponibilite"));
                hotelTrouve.setDescription(result.getString("description"));
                hotelTrouve.setImage(result.getString("image"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return hotelTrouve;
    }

    public Hotel voirDetails(int id) {
        String req = "SELECT * FROM hotel WHERE id = ?";
        Hotel hotelDetails = null;
        PreparedStatement prepStat = null;
        ResultSet result = null;

        try {
            prepStat = connection.prepareStatement(req);
            prepStat.setInt(1, id);
            result = prepStat.executeQuery();

            if (result.next()) {
                hotelDetails = new Hotel();
                hotelDetails.setId(result.getInt("id"));
                hotelDetails.setNom(result.getString("nom"));
                hotelDetails.setNbEtoiles(result.getInt("nb_etoiles"));
                hotelDetails.setPrix(result.getString("prix"));
                hotelDetails.setEmail(result.getString("email"));
                hotelDetails.setTelephone(result.getString("telephone"));
                hotelDetails.setLocalisation(result.getString("localisation"));
                hotelDetails.setVille(result.getString("ville"));
                hotelDetails.setDisponibilite(result.getString("disponibilite"));
                hotelDetails.setDescription(result.getString("description"));
                hotelDetails.setImage(result.getString("image"));
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des détails de l'hôtel : " + ex.getMessage());
        } finally {
            // Fermeture des ressources JDBC dans un bloc finally pour garantir la libération des ressources
            try {
                if (result != null) {
                    result.close();
                }
                if (prepStat != null) {
                    prepStat.close();
                }
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la fermeture des ressources JDBC : " + ex.getMessage());
            }
        }

        return hotelDetails;
    }


}


