package tn.esprit.services;

import tn.esprit.interfaces.IReponse;
import tn.esprit.models.Reponse;
import tn.esprit.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServiceReponse implements IReponse<Reponse> {
    private Connection cnx;
    public ServiceReponse() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    @Override
    public void Add(Reponse reponse) {
        String qry = "INSERT INTO `reponse`(`nom`,`prenom`, `contenue`) VALUES (?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1, reponse.getNom());
            stm.setString(2, reponse.getPrenom());
            stm.setString(3, reponse.getContenue());




            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
