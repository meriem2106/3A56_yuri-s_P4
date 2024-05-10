package entities;


import java.util.ArrayList;
import java.util.List;

public class Guide {

    private Integer id;
    private String nom;
    private String prenom;
    private String ville;
    private String langueParlee;
    private String description;
    private Evenement evenement;
    private List<Evenement> evenements;

    // Constructors, getters, setters, toString method

    public Guide(String nom, String prenom, String ville, String langueParlee, String description) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.ville = ville;
        this.langueParlee = langueParlee;
        this.description = description;
    }
    public Evenement getEvenement(){
        return evenement;
    }
    public void setEvenement(Evenement evenement){

    }

    public List<Evenement> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<Evenement> evenements) {
        this.evenements = evenements;
    }

    public void addEvenement(Evenement evenement) {
        if (evenements == null) {
            evenements = new ArrayList<>();
        }
        evenements.add(evenement);
    }
    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getLangueParlee() {
        return langueParlee;
    }

    public void setLangueParlee(String langueParlee) {
        this.langueParlee = langueParlee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Guide{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", ville='" + ville + '\'' +
                ", langueParlee='" + langueParlee + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

