package entities;

import java.time.LocalDate;
import java.util.Objects;

public class ReservationH {

    private int id,nbAdultes,nbEnfants;

    private String arrangement,repartition;

    private LocalDate dateArrivee, dateDepart;

    private Hotel hotel;

    private User utilisateur;

    public ReservationH() {
    }

    public ReservationH(int nbAdultes, int nbEnfants, String arrangement, String repartition, LocalDate dateArrivee, LocalDate dateDepart) {
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.repartition = repartition;
        this.arrangement = arrangement;
        this.nbEnfants = nbEnfants;
        this.nbAdultes = nbAdultes;
    }

    public ReservationH(int nbAdultes, int nbEnfants, String arrangement, String repartition, LocalDate dateArrivee, LocalDate dateDepart, Hotel hotel, User utilisateur) {
        this.nbAdultes = nbAdultes;
        this.nbEnfants = nbEnfants;
        this.arrangement = arrangement;
        this.repartition = repartition;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.hotel = hotel;
        this.utilisateur = utilisateur;
    }

    public ReservationH(int id, int nbAdultes, int nbEnfants, String arrangement, String repartition, LocalDate dateArrivee, LocalDate dateDepart, Hotel hotel, User utilisateur) {
        this.id = id;
        this.nbAdultes = nbAdultes;
        this.nbEnfants = nbEnfants;
        this.arrangement = arrangement;
        this.repartition = repartition;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.hotel = hotel;
        this.utilisateur = utilisateur;
    }

    public ReservationH(int id, int nbAdultes, int nbEnfants, String arrangement, String repartition, LocalDate dateArrivee, LocalDate dateDepart) {
        this.id = id;
        this.nbAdultes = nbAdultes;
        this.nbEnfants = nbEnfants;
        this.arrangement = arrangement;
        this.repartition = repartition;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
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

    public LocalDate getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(LocalDate dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public LocalDate getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(LocalDate dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public User getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(User utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationH that = (ReservationH) o;
        return id == that.id && nbAdultes == that.nbAdultes && nbEnfants == that.nbEnfants && Objects.equals(arrangement, that.arrangement) && Objects.equals(repartition, that.repartition) && Objects.equals(dateArrivee, that.dateArrivee) && Objects.equals(dateDepart, that.dateDepart) && Objects.equals(hotel, that.hotel) && Objects.equals(utilisateur, that.utilisateur);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "ReservationH{" +
                "id=" + id +
                ", nbAdultes=" + nbAdultes +
                ", nbEnfants=" + nbEnfants +
                ", arrangement='" + arrangement + '\'' +
                ", repartition='" + repartition + '\'' +
                ", dateArrivee=" + dateArrivee +
                ", dateDepart=" + dateDepart +
                ", hotel='" + (hotel != null ? hotel.getNom() : "Aucun") + '\'' + // Afficher le nom de l'hôtel, ou "Aucun" s'il est nul
                ", utilisateur='" + (utilisateur != null ? utilisateur.getNom() + ' ' + utilisateur.getPrenom() : "Aucun") + '\'' + // Afficher le nom de l'utilisateur, ou "Aucun" s'il est nul
                '}';
    }

}
