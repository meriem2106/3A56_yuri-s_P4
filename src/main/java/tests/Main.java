package tests;
//
import entities.*;
import services.ServiceHotel;
import services.ServiceMaison;
import services.ServiceReservationH;
import services.ServiceReservationM;
//
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
//
////TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
//// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        ServiceHotel serviceHotel= new ServiceHotel();
//        ServiceMaison serviceMaison= new ServiceMaison();
////        Utilisateur u = new Utilisateur(2,"youssef","abid");
////        Utilisateur u1 = new Utilisateur(3,"sarra","abid");
//        ServiceReservationH serviceReservationH= new ServiceReservationH();
//        ServiceReservationM serviceReservationM= new ServiceReservationM();
//        Hotel h1= new Hotel(1,4,"140","khayemmm","khayemm@gmail.com","98318909","nabeul","nabeul","ouii","jdjdhd","gggggg");
//        Hotel h2= new Hotel(2,4,"12345","royalazurr","elfell1@gmail.com","90318909","nabeul","nabeul","ouii","jdjdhd","rggrggrr");
//        Hotel h3= new Hotel(3,4,"","","khayemm@gmail.com","98318909","nabeul","nabeul","ouii","jdjdhd","gggggg");
//        Maison m1= new Maison(13,4,"dar 3","dar3@gmail.com","elfell@gmail.com","98318909","nabeul","nabeul","ouii","jdjdhd","gggggg");
//        Maison m2= new Maison(2,4,"dar 4","dar4@gmail.com","elfell1@gmail.com","90318909","nabeul","nabeul","ouii","jdjdhd","rggrggrr");
////        ReservationH rh1 = new ReservationH(1, 4, 5, "demi pension", "double", LocalDate.of(2024, 6, 11), LocalDate.of(2024, 6, 24),h1  ,u);
////        ReservationH rh2 = new ReservationH(2, 4, 6, "pension complete", "double", LocalDate.of(2024, 6, 11), LocalDate.of(2024, 6, 24),h2,u1);
//////        ReservationM rm3 = new ReservationM(1, 6, 6, "demi pension", "double", new Date(124, 5, 11), new Date(124, 5, 24),m1,u);
//////        ReservationM rm2 = new ReservationM(2, 4, 5, "demi pension", "double", new Date(124, 5, 11), new Date(124, 5, 24),m2,u1);
//        try {
//
//            System.out.println(serviceHotel.afficher());
//            serviceHotel.supprimer(15);
//            serviceMaison.ajouter(m1);
//            serviceMaison.ajouter(m2);
//            System.out.println(serviceMaison.afficher());
////            serviceReservationH.ajouter(rh1);
////            serviceReservationH.ajouter(rh2);
//            System.out.println(serviceReservationH.afficher());
////            serviceReservationM.ajouter(rm2);
////            serviceReservationM.ajouter(rm3);
//            System.out.println(serviceReservationM.afficher());
//
//            List<ReservationM> filteredList= serviceReservationM.afficher().stream().filter(e->e.getUtilisateur().getNom().toLowerCase().equals(u1.getPrenom().toLowerCase())).collect(Collectors.toList());
//            System.out.println(filteredList);
//
//
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//

}}