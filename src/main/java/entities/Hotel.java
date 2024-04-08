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
        if(nbEtoiles >= 1 && nbEtoiles <= 5) {
            this.nbEtoiles = nbEtoiles;
        } else {
            throw new IllegalArgumentException("Le nombre d'étoiles doit être compris entre 1 et 5.");
        }
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
        // Définir le pattern de validation de l'adresse e-mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        // Vérifier si l'adresse e-mail correspond au pattern
        if (matcher.matches()) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("L'adresse e-mail n'est pas valide.");
        }
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        // Vérifier si le numéro de téléphone contient uniquement des chiffres et a une longueur valide
        if (telephone.matches("\\d{8}")) { // Vérifie si le numéro de téléphone a 10 chiffres
            this.telephone = telephone;
        } else {
            throw new IllegalArgumentException("Le numéro de téléphone doit contenir exactement 10 chiffres.");
        }
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
        // Tableau des villes valides
        String[] villesValides = {"Nabeul", "Sousse", "Djerba", "Aindraham"};

        // Vérifier si la ville entrée par l'utilisateur est valide
        boolean valide = false;
        for (String v : villesValides) {
            if (v.equalsIgnoreCase(ville)) {
                valide = true;
                break;
            }
        }

        // Si la ville est valide, l'assigner à la variable ville, sinon lancer une exception
        if (valide) {
            this.ville = ville;
        } else {
            throw new IllegalArgumentException("Veuillez entrer une ville valide parmi : Nabeul, Sousse, Djerba, Aindraham.");
        }
    }


    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        // Vérifier si la disponibilité entrée par l'utilisateur est valide
        if (disponibilite.equalsIgnoreCase("complet") || disponibilite.equalsIgnoreCase("disponible")) {
            this.disponibilite = disponibilite;
        } else {
            throw new IllegalArgumentException("La disponibilité doit être soit 'complet' ou 'disponible'.");
        }
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        try {
            double prixValue = Double.parseDouble(prix);
            if (prixValue >= 0) {
                this.prix = prix;
            } else {
                throw new IllegalArgumentException("Le prix doit être un nombre positif.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Le prix doit être un nombre valide.");
        }
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
