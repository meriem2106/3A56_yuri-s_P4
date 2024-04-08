package entities;

public class Maison {

    private int id,nbChambres;
    private int likes=0;

    private String nom,email,capacite,localisation,ville,disponibilite,description,image,prix;

    public Maison(int id, int nbChambres, String nom, String email, String capacite, String localisation, String ville, String disponibilite, String description, String image, String prix) {
        this.id = id;
        this.nbChambres = nbChambres;
        this.nom = nom;
        this.email = email;
        this.capacite = capacite;
        this.localisation = localisation;
        this.ville = ville;
        this.disponibilite = disponibilite;
        this.description = description;
        this.image = image;
        this.prix = prix;
    }

    public Maison() {
    }

    public Maison(int nbChambres, String nom, String email, String capacite, String localisation, String ville, String disponibilite, String description, String image, String prix) {
        this.nbChambres = nbChambres;
        this.nom = nom;
        this.email = email;
        this.capacite = capacite;
        this.localisation = localisation;
        this.ville = ville;
        this.disponibilite = disponibilite;
        this.description = description;
        this.image = image;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getNbChambres() {
        return nbChambres;
    }

    public void setNbChambres(int nbChambres) {
        this.nbChambres = nbChambres;
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

    public String getCapacite() {
        return capacite;
    }

    public void setCapacite(String capacite) {
        this.capacite = capacite;
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
        return "Maison{" +
                "id=" + id +
                ", nbChambres=" + nbChambres +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", capacite='" + capacite + '\'' +
                ", localisation='" + localisation + '\'' +
                ", ville='" + ville + '\'' +
                ", disponibilite='" + disponibilite + '\'' +
                ", description='" + description + '\'' +
                ", prix='" + prix + '\'' +
                '}';
    }
}
