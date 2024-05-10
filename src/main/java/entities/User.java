package entities;

import java.util.Date;
import java.util.List;

public class User {

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private Date dateNaissance;
    private String num;
    private List<String> roles;
    private String image;
    private String file;
    private String status;

    public User() {
    }

    public User(int id, String nom, String prenom, String email, String password, Date dateNaissance, String num, List<String> roles, String image, String file, String status) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.dateNaissance = dateNaissance;
        this.num = num;
        this.roles = roles;
        this.image = image;
        this.file = file;
        this.status = status;
    }

    public User(String nom, String prenom, String email, String password, Date dateNaissance, String num, List<String> roles, String image, String file, String status) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.dateNaissance = dateNaissance;
        this.num = num;
        this.roles = roles;
        this.image = image;
        this.file = file;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return nom + " | " + prenom + " | " + dateNaissance + " | " + email + " | " + password + " | " + num + " | " + roles;
    }
}
