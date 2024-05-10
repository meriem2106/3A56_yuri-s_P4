package entities;

import java.util.Objects;

public class Invitation {
    private int id ;
    private int nbrAdulte;

    public Invitation() {

    }

    public int getNbrAdulte() {
        return nbrAdulte;
    }

    public void setNbrAdulte(int nbrAdulte) {
        this.nbrAdulte = nbrAdulte;
    }

    public int getNbrMaj() {
        return nbrMaj;
    }

    public void setNbrMaj(int nbrMaj) {
        this.nbrMaj = nbrMaj;
    }

    private int nbrMaj;
    private String date_acceuil;
    private String date_retour;
    private String Description;

    public Invitation(int id, int nbrAdulte, int nbrMaj, String date_acceuil, String date_retour, String description) {
        this.id = id;
        this.nbrAdulte = nbrAdulte;
        this.nbrMaj = nbrMaj;
        this.date_acceuil = date_acceuil;
        this.date_retour = date_retour;
        this.Description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_acceuil() {
        return date_acceuil;
    }

    public void setDate_acceuil(String date_acceuil) {
        this.date_acceuil = date_acceuil;
    }

    public String getDate_retour() {
        return date_retour;
    }

    public void setDate_retour(String date_retour) {
        this.date_retour = date_retour;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invitation that)) return false;
        return id == that.id && Objects.equals(date_acceuil, that.date_acceuil) && Objects.equals(date_retour, that.date_retour) && Objects.equals(Description, that.Description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date_acceuil, date_retour, Description);
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "id=" + id +
                ", date_acceuil=" + date_acceuil +
                ", date_retour=" + date_retour +
                ", Description='" + Description + '\'' +
                '}';
    }
}
