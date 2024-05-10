package entities;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "formation")
public class Formation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @Column(name = "sujet", length = 255)
    private String sujet;

    @Future(message = "La date est invalide.")
    @Column(name = "date")
    private LocalDate date;

    @NotNull
    @Column(name = "heure")
    private LocalTime heure;

    @NotBlank
    @Column(name = "lieu", length = 255)
    private String lieu;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produit_id", referencedColumnName = "id")
    private Produit produit;


    // Constructors, getters, setters, toString method
    public Formation() {
    }


    public Formation(String sujet, LocalDate date, LocalTime heure, String lieu,Produit produit) {

        this.sujet = sujet;
        this.date = date;
        this.heure = heure;
        this.lieu = lieu;
        this.produit=produit;


    }

    // Getters and setters
    // getId(), setId(), getSujet(), setSujet(), getDate(), setDate(),
    // getHeure(), setHeure(), getLieu(), setLieu(), getProduit(), setProduit()

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public @Future(message = "La date est invalide.") LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public @NotNull LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "Formation{" +
                "id=" + id +
                ", sujet='" + sujet + '\'' +
                ", date=" + date +
                ", heure=" + heure +
                ", lieu='" + lieu + '\'' +
                ", produit=" + produit +
                '}';
    }
}
