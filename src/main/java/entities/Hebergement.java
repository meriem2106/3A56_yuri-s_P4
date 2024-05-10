package entities;

import java.util.Objects;

public class Hebergement {

    private int id;
    private String nom;
    private String email;
    private String password;
    private String localisation ;
    private String prix ;
    private String telephone;
    private String image;
    private String image2;



    public Hebergement(int id, String nom, String email, String password, String localisation, String prix,String telephone, String image, String image2) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.localisation = localisation;
        this.prix = prix;
        this.telephone = telephone;
        this.image = image;
        this.image2 = image2;
    }

    public Hebergement() {

    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getLocalisation() {
        return localisation;
    }

    public String getPrix() {
        return prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage2() {
        return image2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }


    public void setImage2(String image2) {
        this.image2 = image2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hebergement that)) return false;
        return id == that.id && Objects.equals(nom, that.nom) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(localisation, that.localisation) && Objects.equals(prix, that.prix) && Objects.equals(image, that.image) && Objects.equals(image2, that.image2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, email, password, localisation, prix, image, image2);
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Hebergement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", localisation='" + localisation + '\'' +
                ", prix='" + prix + '\'' +
                ", telephone='" + telephone + '\'' +
                ", image='" + image + '\'' +
                ", image2='" + image2 + '\'' +
                '}';
    }
}
