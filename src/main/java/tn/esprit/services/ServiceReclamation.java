package tn.esprit.services;

import tn.esprit.interfaces.IReclamation;
import tn.esprit.models.Reclamation;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReclamation implements IReclamation<Reclamation> {
    private Connection cnx;
    public ServiceReclamation() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void Add(Reclamation reclamation) {
        String qry = "INSERT INTO `reclamation`(`nom`,`prenom`, `sujet`, `description`, `email`,`date`) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, reclamation.getNom());
            stm.setString(2, reclamation.getPrenom());
            stm.setString(3, reclamation.getSujet());
            stm.setString(4, reclamation.getDescription());
            stm.setString(5, reclamation.getEmail());
            stm.setDate(6, reclamation.getDate());



            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public ArrayList<Reclamation> getAll() {
        return null;
    }

    @Override
    public List<Reclamation> afficher() {
        List<Reclamation> reclamations = new ArrayList<>();
        String sql = "SELECT `id`,`nom`,`prenom`, `sujet`, `description`, `email`,`date` FROM `reclamation`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setNom(rs.getString("nom"));
                reclamation.setPrenom(rs.getString("prenom"));
                reclamation.setEmail(rs.getString("email"));
                reclamation.setSujet(rs.getString("sujet"));

                reclamation.setDescription(rs.getString("description"));
                reclamation.setDate(rs.getDate("date"));
                reclamations.add(reclamation);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reclamations;
    }

    @Override
    public List<Reclamation> TriparNom() {
        List<Reclamation> reclamations = new ArrayList<>();
        String sql = "SELECT `id`,`nom`,`prenom`, `sujet`, `description`, `email`,`date` FROM `reclamation` ORDER BY `nom`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setNom(rs.getString("nom"));
                reclamation.setPrenom(rs.getString("prenom"));
                reclamation.setSujet(rs.getString("sujet"));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setEmail(rs.getString("email"));
                reclamation.setDate(rs.getDate("date"));
                reclamations.add(reclamation);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reclamations;
    }

    @Override
    public List<Reclamation> TriparEmail() {
        List<Reclamation> reclamations = new ArrayList<>();
        String sql = "SELECT `id`,`nom`,`prenom`, `sujet`, `description`, `email`,`date` FROM `reclamation` ORDER BY `email`";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setNom(rs.getString("nom"));
                reclamation.setPrenom(rs.getString("prenom"));
                reclamation.setSujet(rs.getString("sujet"));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setEmail(rs.getString("email"));
                reclamation.setDate(rs.getDate("date"));
                reclamations.add(reclamation);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reclamations;
    }

    @Override
    public List<Reclamation> Rechreche(String recherche) {
        List<Reclamation> reclamations = new ArrayList<>();
        String sql = "SELECT `id`,`nom`,`prenom`, `sujet`, `description`,`email`,`date` FROM `reclamation` WHERE `nom` LIKE '%" + recherche + "%' OR `prenom` LIKE '%" + recherche + "%' OR `sujet`LIKE '%" + recherche + "%'";
        try {
            Statement ste = cnx.createStatement();
            ResultSet rs = ste.executeQuery(sql);
            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt("id"));
                reclamation.setNom(rs.getString("nom"));
                reclamation.setPrenom(rs.getString("prenom"));
                reclamation.setSujet(rs.getString("sujet"));
                reclamation.setDescription(rs.getString("description"));
                reclamation.setEmail(rs.getString("email"));
                reclamation.setDate(rs.getDate("date"));
                reclamations.add(reclamation);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reclamations;
    }

    @Override
    public void Update(Reclamation reclamation) {

    }

    @Override
    public void Delete(Reclamation reclamation) {
        try {
            String qry = "DELETE FROM `reclamation` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, reclamation.getId());
            smt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void DeleteByID(int id) {
        try {
            String qry = "DELETE FROM `reclamation` WHERE id=?";
            PreparedStatement smt = cnx.prepareStatement(qry);
            smt.setInt(1, id);
            smt.executeUpdate();
            System.out.println("Suppression Effectué");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
