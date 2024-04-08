package entities;

import java.util.Date;

public class ReservationM {

    private int id,nbAdultes,nbEnfants;

    private String arrangement,repartition;

    private Date dateArrivee, dateDepart;

    private Maison maison;

    private Utilisateur utilisateur;

    public ReservationM() {
    }

    public ReservationM(int id, int nbAdultes, int nbEnfants, String arrangement, String repartition, Date dateArrivee, Date dateDepart, Maison maison, Utilisateur utilisateur) {
        this.id = id;
        this.nbAdultes = nbAdultes;
        this.nbEnfants = nbEnfants;
        this.arrangement = arrangement;
        this.repartition = repartition;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.maison = maison;
        this.utilisateur = utilisateur;
    }

    public ReservationM(int nbAdultes, int nbEnfants, String arrangement, String repartition, Date dateArrivee, Date dateDepart, Maison maison, Utilisateur utilisateur) {
        this.nbAdultes = nbAdultes;
        this.nbEnfants = nbEnfants;
        this.arrangement = arrangement;
        this.repartition = repartition;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.maison = maison;
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbAdultes() {
        return nbAdultes;
    }

    public void setNbAdultes(int nbAdultes) {
        this.nbAdultes = nbAdultes;
    }

    public int getNbEnfants() {
        return nbEnfants;
    }

    public void setNbEnfants(int nbEnfants) {
        this.nbEnfants = nbEnfants;
    }

    public String getArrangement() {
        return arrangement;
    }

    public void setArrangement(String arrangement) {
        this.arrangement = arrangement;
    }

    public String getRepartition() {
        return repartition;
    }

    public void setRepartition(String repartition) {
        this.repartition = repartition;
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Maison getMaison() {
        return maison;
    }

    public void setMaison(Maison maison) {
        this.maison = maison;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public String toString() {
        return "ReservationM{" +
                "id=" + id +
                ", nbAdultes=" + nbAdultes +
                ", nbEnfants=" + nbEnfants +
                ", arrangement='" + arrangement +
                ", repartition='" + repartition +
                ", dateArrivee=" + dateArrivee +
                ", dateDepart=" + dateDepart +
                ", hotel='" + (maison != null ? maison.getNom() : "Aucun") +
                ", utilisateur='" + (utilisateur != null ? utilisateur.getNom() + ' ' + utilisateur.getPrenom() : "Aucun") +
                '}';
    }
}
