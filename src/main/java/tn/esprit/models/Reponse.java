package tn.esprit.models;

import java.sql.Date;

public class Reponse {
    private int id;
    private String nom , prenom, contenue;

    public static Reponse reponse;
    public Reponse() {}
    public Reponse(int ID, String NOM, String PRENOM, String CONTENUE) {
        this.id = ID;
        this.nom = NOM;
        this.prenom = PRENOM;
        this.contenue = CONTENUE;

    }
    public Reponse( String NOM, String PRENOM, String CONTENUE) {

        this.nom = NOM;
        this.prenom = PRENOM;
        this.contenue = CONTENUE;

    }
    public Reponse(String CONTENUE) {

        this.contenue = CONTENUE;

    }


    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}
    public String getPrenom() {return prenom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}
    public String getContenue() {return contenue;}
    public void setContenue(String contenue) {this.contenue = contenue;}


    public static Reponse getReponse() {return reponse;}
    public static void setReponse(Reponse reponse) {Reponse.reponse = reponse;}
    @Override
    public String toString() {
        return "Reponse{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", contenue='" + contenue + '\'' +
                '}';
    }

}
