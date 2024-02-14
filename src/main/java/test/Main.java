package test;

import Entities.*;
import Services.UserService;
import Utils.DataSource;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();

      //  User user = new User("achref","abz",29451854,"test@test.com","test",Role.ADMIN);
        User user1 = new User("test","test",29451854,"test","test",Role.CLIENT);
        User user2 = new User("fregf","frzf",848495,"test@test.com","fez",Role.ADMIN);



        //Modification
       // userService.modifierUser(user3, 5);

        //Suppression
        //userService.supprimerUser(5);

        //Affichage
        userService.afficherUser();

    }
}
