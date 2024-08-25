package com.hsbc.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.*;

@Configuration //makes this class as a xml file
public class DBConfig {


    static Connection conn = null;
    private static final String URL = "jdbc:mysql://bugtrackingsys.cosgvjbgcjho.ap-south-1.rds.amazonaws.com:3306/DefectHub?useSSL=false";
    private static final String USER = "admin";
    private static final String PASSWORD = "BugTrackingSys";

    @Bean
    public static Connection getConnection() {
        System.out.println("Obtaining connection to database...");
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

    public static void main(String[] args) throws SQLException {
        Connection conn = new DBConfig().getConnection();
        if (conn != null) {
            System.out.println("Connection established");
            PreparedStatement st = conn.prepareStatement("select * from Users");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3 )+" "+rs.getString(4)+ " "+rs.getTimestamp(5));
            }


        }
        else {
            System.out.println("Check your connection");
        }
    }

}
