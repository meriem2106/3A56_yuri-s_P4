package services;

import entities.Hotel;
import entities.ReservationH;
import entities.Utilisateur;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservationH implements IService<ReservationH>{

    Connection connection;

    public ServiceReservationH() {
        connection = MyDatabase.getInstance().getConnection();

    }

    @Override
    public void ajouter(ReservationH reservationH) throws SQLException {
        String req = "INSERT INTO reservation_h (nb_adultes, nb_enfants, repartition, arrangement, datedepart, datearrivee, hotel_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setInt(1, reservationH.getNbAdultes());
            preparedStatement.setInt(2, reservationH.getNbEnfants());
            preparedStatement.setString(3, reservationH.getRepartition());
            preparedStatement.setString(4, reservationH.getArrangement());
            preparedStatement.setDate(5, java.sql.Date.valueOf(reservationH.getDateDepart()));
            preparedStatement.setDate(6, java.sql.Date.valueOf(reservationH.getDateArrivee()));
            preparedStatement.setInt(7, reservationH.getHotel().getId());
            preparedStatement.setInt(8, reservationH.getUtilisateur().getId());

            preparedStatement.executeUpdate();
            System.out.println("La réservation d'hôtel a été ajoutée avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void modifier(ReservationH reservationH) throws SQLException {
        String req="update reservation_h set nb_adultes=?,nb_enfants=?,repartition=?,arrangement=?,datedepart=?,datearrivee=?  where id=?";
        PreparedStatement preparedStatement= connection.prepareStatement(req);
        preparedStatement.setInt(1,reservationH.getNbAdultes());
        preparedStatement.setInt(2,reservationH.getNbEnfants());
        preparedStatement.setString(3,reservationH.getRepartition());
        preparedStatement.setString(4,reservationH.getArrangement());
        preparedStatement.setDate(5,java.sql.Date.valueOf(reservationH.getDateDepart()));
        preparedStatement.setDate(6, java.sql.Date.valueOf(reservationH.getDateArrivee()));
        preparedStatement.setInt(7,reservationH.getId());
        preparedStatement.executeUpdate();


    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM reservation_h WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setInt(1, id);
            int rowCount = preparedStatement.executeUpdate();

            if (rowCount > 0) {
                System.out.println("La reservation d'hotel avec l'ID " + id + " a été supprimée avec succès.");
            } else {
                System.out.println("Aucune reservation d'hotel avec l'ID " + id + " n'a été trouvé.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression de la reservation d'hotel avec l'ID " + id + ": " + ex.getMessage());
        }
    }

    @Override
    public List<ReservationH> afficher() throws SQLException {
        List<ReservationH> reservationsH = new ArrayList<>();
        String req = "SELECT rh.*, h.nom AS hotel_nom, u.prenom AS utilisateur_prenom, u.nom AS utilisateur_nom " +
                "FROM reservation_h rh " +
                "JOIN hotel h ON rh.hotel_id = h.id " +
                "JOIN utilisateur u ON rh.user_id = u.id";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(req);
        while (rs.next()) {
            ReservationH reservationH = new ReservationH();
            reservationH.setNbAdultes(rs.getInt("nb_adultes"));
            reservationH.setNbEnfants(rs.getInt("nb_enfants"));
            reservationH.setArrangement(rs.getString("arrangement"));
            reservationH.setRepartition(rs.getString("repartition"));
            reservationH.setDateDepart(rs.getDate("datedepart").toLocalDate());
            reservationH.setDateArrivee(rs.getDate("datearrivee").toLocalDate());
            reservationH.setId(rs.getInt("id"));

            // Ajouter le nom de l'hôtel
            Hotel hotel = new Hotel();
            hotel.setNom(rs.getString("hotel_nom"));
            reservationH.setHotel(hotel);

            // Ajouter le nom de l'utilisateur
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(rs.getString("utilisateur_nom"));
            utilisateur.setPrenom(rs.getString("utilisateur_prenom"));
            reservationH.setUtilisateur(utilisateur);

            reservationsH.add(reservationH);
        }

        return reservationsH;
    }


    public ReservationH chercher(Date datedepart) {
        String req = "SELECT * FROM reservation_h WHERE datedepart LIKE ?";

        ReservationH reservationHTrouve = null;

        try {
            PreparedStatement prepStat = connection.prepareStatement(req);

            prepStat.setString(1, "%" + datedepart + "%");

            ResultSet result = prepStat.executeQuery();

            if (result.next()) {
                reservationHTrouve = new ReservationH();
                reservationHTrouve.setId(result.getInt("id"));
                reservationHTrouve.setNbAdultes(result.getInt("nb_adultes"));
                reservationHTrouve.setNbEnfants(result.getInt("nb_enfants"));
                reservationHTrouve.setArrangement(result.getString("arrangement"));
                reservationHTrouve.setRepartition(result.getString("repartition"));
                reservationHTrouve.setDateDepart(result.getDate("datedepart").toLocalDate());
                reservationHTrouve.setDateArrivee(result.getDate("datearrivee").toLocalDate());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return reservationHTrouve ;
    }
}
