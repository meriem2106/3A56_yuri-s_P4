package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    static final String URL="jdbc:mysql://localhost:3306/pidev";


    static final String USERNAME="root";
    static final String PASSWORD="";

    public static Connection connection;

    static MyDatabase instance;

    public MyDatabase(){

        if(connection != null){
            try{
                connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Connexion etablie");

    }

    public static Connection getConnection() throws SQLException {
        if(connection != null){
            return connection;
        }else{
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
            return connection;
        }
    }


    public static   MyDatabase getInstance(){
        if (instance==null){
            instance= new MyDatabase();
        }
        return instance;
    }
}
