package Services;
import Entities.*;

import java.sql.SQLException;
import java.util.List;

public interface IService {
    public void ajouterUser(User user)throws SQLException;
    public void modifierUser(User user, int id);
     public void supprimerUser(int id);
     public List<User> afficherUser();
    public boolean test_num_telephonique(String numtel);
    public boolean test_Tel(String numtel);
    public boolean test_Email(String mail);
}
