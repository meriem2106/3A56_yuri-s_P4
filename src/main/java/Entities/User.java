package Entities;

public class User {

    private int id;
    private String nom;
    private String prenom;
    private int numtel;
    private String email;
    private String password;
    private Role role;

    public User() {
    }
    public User(String nom, String prenom, int numtel, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.numtel = numtel;
        this.email = email;
        this.password = password;
    }
    public User(int id, String nom, String prenom, int numtel, String email, String password, Role role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numtel = numtel;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String nom, String prenom, int numtel, String email, String password, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.numtel = numtel;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numtel=" + numtel +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
