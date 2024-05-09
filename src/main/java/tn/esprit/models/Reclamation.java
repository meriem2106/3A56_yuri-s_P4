package tn.esprit.models;

import java.sql.Date;

public class Reclamation {
    private int id;
    private String nom , prenom, sujet,description,email,img;
    private Date date;

    public static Reclamation reclamation;

    public Reclamation() {}
    public Reclamation(int ID, String NOM, String PRENOM, String SUJET, String DESCRIPTION, String EMAIL,Date DATE,String IMG) {
        this.id = ID;
        this.nom = NOM;
        this.prenom = PRENOM;
        this.sujet = SUJET;
        this.description = DESCRIPTION;
        this.email = EMAIL;
        this.date= DATE;
        this.img = IMG;
    }
    public Reclamation( String NOM, String PRENOM, String SUJET, String DESCRIPTION, String EMAIL,Date DATE,String IMG) {

        this.nom = NOM;
        this.prenom = PRENOM;
        this.sujet = SUJET;
        this.description = DESCRIPTION;
        this.email = EMAIL;
        this.date= DATE;
        this.img = IMG;
    }
    public Reclamation(int ID, String NOM, String PRENOM, String SUJET, String DESCRIPTION, String EMAIL,Date DATE) {
        this.id = ID;
        this.nom = NOM;
        this.prenom = PRENOM;
        this.sujet = SUJET;
        this.description = DESCRIPTION;
        this.email = EMAIL;
        this.date= DATE;

    }
    public Reclamation( String NOM, String PRENOM, String SUJET, String DESCRIPTION, String EMAIL,Date DATE) {

        this.nom = NOM;
        this.prenom = PRENOM;
        this.sujet = SUJET;
        this.description = DESCRIPTION;
        this.email = EMAIL;
        this.date= DATE;

    }
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getNom() {return nom;}
    public void setNom(String nom) {this.nom = nom;}
    public String getPrenom() {return prenom;}
    public void setPrenom(String prenom) {this.prenom = prenom;}
    public String getSujet() {return sujet;}
    public void setSujet(String sujet) {this.sujet = sujet;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}
    public String getImg() {return img;}
    public void setImg(String img) {this.img = img;}
    public static Reclamation getReclamation() {return reclamation;}
    public static void setPatient(Reclamation reclamation) {Reclamation.reclamation = reclamation;}
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", sujet='" + sujet + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", date='" + date + '\'' +
                ", img='" + img + '\'' +
                '}';
    }


}
