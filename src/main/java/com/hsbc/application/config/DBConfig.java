package com.hsbc.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration //makes this class as a xml file
public class DBConfig {


    static Connection conn = null;
    private static final String URL = "jdbc:mysql://localhost:3306/BugTrackingSys";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @Bean
    public static Connection getConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            //Class.forName("con.mysql.cj.jdbc.Driver()");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (SQLException  e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

//    public static void main(String[] args) {
//        Connection conn = new DBConfig().connection();
//        if (conn != null) {
//            System.out.println("Connection established");
//        }
//        else {
//            System.out.println("Check your connection");
//        }
//    }

}
