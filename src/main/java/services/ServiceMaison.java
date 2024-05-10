package services;


import entities.Maison;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMaison implements IService<Maison>{

    Connection connection;

    public ServiceMaison() {
        connection = MyDatabase.getInstance().getConnection();

    }

    @Override
    public void ajouter(Maison maison) throws SQLException {

        String req = "INSERT INTO maison (nb_chambres,nom,email,capacite,localisation,ville,disponibilite,description,image,prix)" + " VALUES (?,?,?,?,?,?,?,?,?,?);";
        try{

            PreparedStatement preparedStatement= connection.prepareStatement(req);


            preparedStatement.setInt(1,maison.getNbChambres());
            preparedStatement.setString(2,maison.getNom());
            preparedStatement.setString(3,maison.getEmail());
            preparedStatement.setString(4,maison.getCapacite());
            preparedStatement.setString(5,maison.getLocalisation());
            preparedStatement.setString(6,maison.getVille());
            preparedStatement.setString(7,maison.getDisponibilite());
            preparedStatement.setString(8,maison.getDescription());
            preparedStatement.setString(9,maison.getImage());
            preparedStatement.setString(10,maison.getPrix());

            preparedStatement.executeUpdate();
            System.out.println("La maison d'hote a été ajoutée avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void modifier(Maison maison) throws SQLException {
        String req="update maison set nom=?,nb_chambres=?,localisation=?,ville=?,prix=?,email=?,capacite=?,description=?,disponibilite=?,image=?  where id=?";
        PreparedStatement preparedStatement= connection.prepareStatement(req);
        preparedStatement.setString(1,maison.getNom());
        preparedStatement.setInt(2,maison.getNbChambres());
        preparedStatement.setString(3,maison.getLocalisation());
        preparedStatement.setString(4,maison.getVille());
        preparedStatement.setString(5,maison.getPrix());
        preparedStatement.setString(6,maison.getEmail());
        preparedStatement.setString(7,maison.getCapacite());
        preparedStatement.setString(8,maison.getDescription());
        preparedStatement.setString(9,maison.getDisponibilite());
        preparedStatement.setString(10,maison.getImage());
        preparedStatement.setInt(11,maison.getId());
        preparedStatement.executeUpdate();


    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM maison WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            preparedStatement.setInt(1, id);
            int rowCount = preparedStatement.executeUpdate();

            if (rowCount > 0) {
                System.out.println("La maison d'hote avec l'ID " + id + " a été supprimée avec succès.");
            } else {
                System.out.println("Aucune maison d'hote avec l'ID " + id + " n'a été trouvé.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression de la maison d'hote avec l'ID " + id + ": " + ex.getMessage());
        }
    }

    @Override
    public List<Maison> afficher() throws SQLException {
        List<Maison> maisons= new ArrayList<>();
        String req="select * from maison";
        Statement statement= connection.createStatement();

        ResultSet rs= statement.executeQuery(req);
        while (rs.next()){
            Maison maison= new Maison();
            maison.setNom(rs.getString("nom"));
            maison.setNbChambres(rs.getInt("nb_chambres"));
            maison.setLocalisation(rs.getString("localisation"));
            maison.setVille(rs.getString("ville"));
            maison.setPrix(rs.getString("prix"));
            maison.setEmail(rs.getString("email"));
            maison.setCapacite(rs.getString("capacite"));
            maison.setDescription(rs.getString("description"));
            maison.setDisponibilite(rs.getString("disponibilite"));
            maison.setImage(rs.getString("image"));
            maison.setId(rs.getInt("id"));

            maisons.add(maison);
        }


        return maisons;
    }

    public Maison chercher(String nomMaison) {
        String req = "SELECT * FROM maison WHERE nom LIKE ?";

        Maison maisonTrouve = null;

        try {
            PreparedStatement prepStat = connection.prepareStatement(req);

            prepStat.setString(1, "%" + nomMaison + "%");

            ResultSet result = prepStat.executeQuery();

            if (result.next()) {
                maisonTrouve = new Maison();
                maisonTrouve.setId(result.getInt("id"));
                maisonTrouve.setNom(result.getString("nom"));
                maisonTrouve.setNbChambres(result.getInt("nb_chambres"));
                maisonTrouve.setPrix(result.getString("prix"));
                maisonTrouve.setEmail(result.getString("email"));
                maisonTrouve.setCapacite(result.getString("capacite"));
                maisonTrouve.setLocalisation(result.getString("localisation"));
                maisonTrouve.setVille(result.getString("ville"));
                maisonTrouve.setDisponibilite(result.getString("disponibilite"));
                maisonTrouve.setDescription(result.getString("description"));
                maisonTrouve.setImage(result.getString("image"));
                // Vous pouvez ajouter d'autres attributs si nécessaire
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return maisonTrouve;
    }
}
