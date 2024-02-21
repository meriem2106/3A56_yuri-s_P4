package test;
import Entities.*;
import Services.UserService;
import Utils.DataSource;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserService();

      //  User user = new User("achref","abz",29451854,"test@test.com","test",Role.ADMIN);
        User user1 = new User("test2","test",29451854,"test","test",Role.CLIENT);
        User user2 = new User("fregf","frzf",848495,"test@test.com","fez",Role.ADMIN);

//Ajout
        if (userService.test_Tel(String.valueOf(user1.getNumtel())) == false && userService.test_Email(user1.getEmail()) == false)
        {
            System.out.println("Verifiez votre Email et num de tel");
        }
        else if (userService.test_Tel(String.valueOf(user1.getNumtel())) == true && userService.test_Email(user1.getEmail()) == false) {
            System.out.println("Verifiez votre Email");
        }
        else if (userService.test_Tel(String.valueOf(user1.getNumtel())) == false && userService.test_Email(user1.getEmail()) == true) {
            System.out.println("Verifiez votre num de tel");
        }
        else {
            try {
                userService.ajouterUser(user1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        //Modification
       // userService.modifierUser(user3, 5);

        //Suppression
        //userService.supprimerUser(5);

        //Affichage
        userService.afficherUser();

    }
}
