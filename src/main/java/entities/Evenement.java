package entities;

import java.util.Date;


public class Evenement {


    private Integer ref;

    private String nom;

    private String contexte;

    private String cat;

    private String lieu;

    private String transport;


    private Integer nbPlacesMax;


    private String description;


    private String image;


    private Date datedeb;

    private Date datefin;

    private Guide guide;

    // Constructors, getters, setters, toString method
    public Evenement(int id, String text, String contexteTFText, String catTFText, String lieuTFText, String transportTFText, String descriptionTFText) {
    }

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }



    public Evenement(Guide guide, Date datefin, Date datedeb, String image, String description, Integer nbPlacesMax, String transport, String cat, String lieu, String contexte, String nom) {
        this.guide= guide;
        this.datefin = datefin;
        this.datedeb = datedeb;
        this.image = image;
        this.description = description;
        this.nbPlacesMax = nbPlacesMax;
        this.transport = transport;
        this.cat = cat;
        this.lieu = lieu;
        this.contexte = contexte;
        this.nom = nom;

    }

    public Evenement(Date datefin, Date datedeb, String image, String description, Integer nbPlacesMax, String transport, String lieu, String contexte, String cat, String nom) {
        this.datefin = datefin;
        this.datedeb = datedeb;
        this.image = image;
        this.description = description;
        this.nbPlacesMax = nbPlacesMax;
        this.transport = transport;
        this.lieu = lieu;
        this.contexte = contexte;
        this.cat = cat;
        this.nom = nom;
    }

    public Evenement(Integer id, String nom, String contexte, String cat, String lieu, String transport, Integer nbPlacesMax, String description, String image, Date datedeb, Date datefin) {
        this.ref = ref;
        this.nom = nom;
        this.contexte = contexte;
        this.cat = cat;
        this.lieu = lieu;
        this.transport = transport;
        this.nbPlacesMax = nbPlacesMax;
        this.description = description;
        this.image = image;
        this.datedeb = datedeb;
        this.datefin = datefin;
    }

    public Evenement() {

    }

    // Getters and setters
    public Integer getId() {return ref;}

    public void setId(Integer id) {this.ref = id;}

    public String getImage() {return image;}

    public void setImage(String image) {this.image = image;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getTransport() {return transport;}

    public void setTransport(String transport) {this.transport = transport;}

    public Integer getNbPlacesMax() {return nbPlacesMax;}

    public void setNbPlacesMax(Integer nbPlacesMax) {this.nbPlacesMax = nbPlacesMax;}

    public String getLieu() {return lieu;}

    public void setLieu(String lieu) {this.lieu = lieu;}

    public String getCat() {return cat;}

    public void setCat(String cat) {this.cat = cat;}

    public String getContexte() {
        return contexte;
    }

    public void setContexte(String contexte) {
        this.contexte = contexte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDatedeb() {
        return datedeb;
    }

    public void setDatedeb(Date datedeb) {
        this.datedeb = datedeb;
    }

    public Date getDatefin() {
        return datefin;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "ref=" + ref +
                ", nom='" + nom + '\'' +
                ", contexte='" + contexte + '\'' +
                ", cat='" + cat + '\'' +
                ", lieu='" + lieu + '\'' +
                ", transport='" + transport + '\'' +
                ", nbPlacesMax=" + nbPlacesMax +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", datedeb=" + datedeb +
                ", datefin=" + datefin +
                '}';
    }
}
