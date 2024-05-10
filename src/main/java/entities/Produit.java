package entities;

import javafx.util.Pair;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "produit")
public class Produit {


    /*public enum Categorie {
        ART_DE_LA_TABLE,
        MAISON_ET_DECORATION,
        VETEMENTS,
        BIJOUX_ET_ACCESSOIRES
    }*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private static Map<Integer, Pair<Integer,Integer>> rating = new HashMap<>();
    private String nom;

    @NotBlank(message = "La description ne peut pas être vide")
    @Column(name = "description", length = 255)
    private String description;

    @NotBlank(message = "L'origine'est obligatoire")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "L'origine ne peut contenir que des lettres de l'alphabet")
    @Column(name = "origine", length = 255)
    private String origine;


    //private Categorie categ;

    @NotBlank(message = "matiere du produit est obligatoire")
    @Column(name = "matiere", length = 255)
    private String matiere;

    @Column(name = "image", length = 255)
    private String image;
    private String categ;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "formation_id", referencedColumnName = "id")
    private Integer formation;

    // Constructors, getters, setters, toString method
    public Produit() {
    }

    public  Double getRating (){
        if (rating.get(id)==null){
            rating.put(id,new Pair<>(0,0));

        }
        if(rating.get(getId()).getKey() == 0){
            return 0.0;
        }else{







            return (double)rating.get(getId()).getValue() / (double)rating.get(getId()).getKey();
        }
    }
    public  Map<Integer,Pair<Integer,Integer>> test (){

        return  rating;
    }
public void setRating(Integer rate){
    if (rating.get(id)==null){
        rating.put(id,new Pair<>(0,0));

    }

        rating.put(getId(),new Pair<>(rating.get(getId()).getKey() + 1,rate+rating.get(getId()).getValue()));



}

    public Produit(Integer id, String nom, String description, String origine, String categ, String matiere, String image) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.origine = origine;
        this.categ = categ;
        this.matiere = matiere;
        this.image = image;
        if (rating.get(id)==null){
            rating.put(id,new Pair<>(0,0));

        }
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
//
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getCateg() {
        return categ;
    }

    public void setCateg(String categ) {
        this.categ = categ;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getImage() {
        return image;

    }


    public void setImage(String image) {
        this.image = image;
    }

    public Integer getFormation() {
        return formation;
    }

    public void setFormation(Integer formation) {
        this.formation = formation;
    }

    /*@Override
    public String toString() {
        return String.valueOf(id);
    }*/

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", origine='" + origine + '\'' +
                ", matiere='" + matiere + '\'' +
                ", image='" + image + '\'' +
                ", categ='" + categ + '\'' +
                '}';
    }
}
