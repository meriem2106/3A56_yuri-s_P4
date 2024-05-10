package services;

import entities.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface UserInterface {
    public List<User> afficherUser();
    public void ajouterUser(User user)throws SQLException;
    public void modifierUser(User user, int id);
    public void supprimerUser(int id);
    public boolean test_Email(String mail);
    public boolean test_Tel(String numtel);
    public boolean test_num_telephonique(String numtel);
    public boolean test_dateNaissance(LocalDate date);
    public boolean verfier_mail(String mail);
    public User rechercheUserByEmail(String email);
    public void modifier_password(int id, String password);
    public List<User> rechercheUser(int id);
}
