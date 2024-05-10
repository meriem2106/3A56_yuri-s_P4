package services;

import entities.*;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservationM implements IService<ReservationM>{

    Connection connection;

    public ServiceReservationM() {
        connection = MyDatabase.getInstance().getConnection();

    }

    @Override
    public void ajouter(ReservationM reservationM) throws SQLException {


        String req = "INSERT INTO reservation_m (nb_adultes, nb_enfants, repartition, arrangement, datedepart, datearrivee, maison_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try{

            PreparedStatement preparedStatement= connection.prepareStatement(req);


            preparedStatement.setInt(1,reservationM.getNbAdultes());
            preparedStatement.setInt(2,reservationM.getNbEnfants());
            preparedStatement.setString(3,reservationM.getRepartition());
            preparedStatement.setString(4,reservationM.getArrangement());
            preparedStatement.setDate(5, java.sql.Date.valueOf(reservationM.getDateDepart()));
            preparedStatement.setDate(6, java.sql.Date.valueOf(reservationM.getDateArrivee()));
            preparedStatement.setInt(7, reservationM.getMaison().getId());
            preparedStatement.setInt(8, reservationM.getUtilisateur().getId());



            preparedStatement.executeUpdate();
            System.out.println("La reservation de maison d'hote a été ajoutée avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifier(ReservationM reservationM) throws SQLException {
        String req="update reservation_m set nb_adultes=?,nb_enfants=?,repartition=?,arrangement=?,datedepart=?,datearrivee=?  where id=?";
        PreparedStatement preparedStatement= connection.prepareStatement(req);
        preparedStatement.setInt(1,reservationM.getNbAdultes());
        preparedStatement.setInt(2,reservationM.getNbEnfants());
        preparedStatement.setString(3,reservationM.getRepartition());
        preparedStatement.setString(4,reservationM.getArrangement());
        preparedStatement.setDate(5, java.sql.Date.valueOf(reservationM.getDateDepart()));
        preparedStatement.setDate(6, java.sql.Date.valueOf(reservationM.getDateArrivee()));
        preparedStatement.setInt(7,reservationM.getId());
        preparedStatement.executeUpdate();


    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM reservation_m WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setInt(1, id);
            int rowCount = preparedStatement.executeUpdate();

            if (rowCount > 0) {
                System.out.println("La reservation de maison d'hote avec l'ID " + id + " a été supprimée avec succès.");
            } else {
                System.out.println("Aucune reservation de maison d'hote avec l'ID " + id + " n'a été trouvé.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression de la reservation de maison d'hote avec l'ID " + id + ": " + ex.getMessage());
        }
    }

    @Override
    public List<ReservationM> afficher() throws SQLException {
        List<ReservationM> reservationsM = new ArrayList<>();
        String req = "SELECT rm.*, m.nom AS maison_nom, u.prenom AS utilisateur_prenom, u.nom AS utilisateur_nom " +
                "FROM reservation_m rm " +
                "JOIN maison m ON rm.maison_id = m.id " +
                "JOIN utilisateur u ON rm.user_id = u.id";
        Statement statement = connection.createStatement();

        ResultSet rs = statement.executeQuery(req);
        while (rs.next()) {
            ReservationM reservationM = new ReservationM();
            reservationM.setNbAdultes(rs.getInt("nb_adultes"));
            reservationM.setNbEnfants(rs.getInt("nb_enfants"));
            reservationM.setArrangement(rs.getString("arrangement"));
            reservationM.setRepartition(rs.getString("repartition"));
            reservationM.setDateDepart(rs.getDate("datedepart").toLocalDate());
            reservationM.setDateArrivee(rs.getDate("datearrivee").toLocalDate());
            reservationM.setId(rs.getInt("id"));

            // Ajouter le nom de l'hôtel
            Maison maison = new Maison();
            maison.setNom(rs.getString("maison_nom"));
            reservationM.setMaison(maison);

            // Ajouter le nom de l'utilisateur
            User utilisateur = new User();
            utilisateur.setNom(rs.getString("utilisateur_nom"));
            utilisateur.setPrenom(rs.getString("utilisateur_prenom"));
            reservationM.setUtilisateur(utilisateur);

            reservationsM.add(reservationM);
        }

        return reservationsM;
    }
    /*public List<ReservationM> afficher() throws SQLException {
        List<ReservationM> reservationsM= new ArrayList<>();
        String req="select * from reservation_m";
        Statement statement= connection.createStatement();

        ResultSet rs= statement.executeQuery(req);
        while (rs.next()){
            ReservationM reservationM= new ReservationM();
            reservationM.setNbAdultes(rs.getInt("nb_adultes"));
            reservationM.setNbEnfants(rs.getInt("nb_enfants"));
            reservationM.setArrangement(rs.getString("arrangement"));
            reservationM.setRepartition(rs.getString("repartition"));
            reservationM.setDateDepart(rs.getDate("datedepart"));
            reservationM.setDateArrivee(rs.getDate("datearrivee"));
            reservationM.setId(rs.getInt("id"));

            reservationsM.add(reservationM);
        }


        return reservationsM;
    }*/

    public ReservationM chercher(Date datedepart) {
        String req = "SELECT * FROM reservation_m WHERE datedepart LIKE ?";

        ReservationM reservationMTrouve = null;

        try {
            PreparedStatement prepStat = connection.prepareStatement(req);

            prepStat.setString(1, "%" + datedepart + "%");

            ResultSet result = prepStat.executeQuery();

            if (result.next()) {
                reservationMTrouve = new ReservationM();
                reservationMTrouve.setId(result.getInt("id"));
                reservationMTrouve.setNbAdultes(result.getInt("nb_adultes"));
                reservationMTrouve.setNbEnfants(result.getInt("nb_enfants"));
                reservationMTrouve.setArrangement(result.getString("arrangement"));
                reservationMTrouve.setRepartition(result.getString("repartition"));
                reservationMTrouve.setDateDepart(result.getDate("datedepart").toLocalDate());
                reservationMTrouve.setDateArrivee(result.getDate("datearrivee").toLocalDate());

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return reservationMTrouve ;
    }
}
