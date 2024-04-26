package entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hotel {


    private int id,nbEtoiles;


    private String nom,email,telephone,localisation,ville,disponibilite,description,image,prix;

    public Hotel() {
    }

    public Hotel(int id, int nbEtoiles, String prix, String nom, String email, String telephone, String localisation, String ville, String disponibilite, String description, String image) {
        this.id = id;
        this.nbEtoiles = nbEtoiles;
        this.prix = prix;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.localisation = localisation;
        this.ville = ville;
        this.disponibilite = disponibilite;
        this.description = description;
        this.image = image;
    }


    public Hotel(int nbEtoiles, String prix, String nom, String email, String telephone, String localisation, String ville, String disponibilite, String description, String image) {
        this.nbEtoiles = nbEtoiles;
        this.prix = prix;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
        this.localisation = localisation;
        this.ville = ville;
        this.disponibilite = disponibilite;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbEtoiles() {
        return nbEtoiles;
    }

    public void setNbEtoiles(int nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", nbEtoiles=" + nbEtoiles +
                ", prix=" + prix +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", localisation='" + localisation + '\'' +
                ", ville='" + ville + '\'' +
                ", disponibilite='" + disponibilite + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
