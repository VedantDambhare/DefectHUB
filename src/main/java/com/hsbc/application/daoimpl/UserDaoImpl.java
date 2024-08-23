package com.hsbc.application.daoimpl;

import com.hsbc.application.config.DBConfig;
import com.hsbc.application.dao.UserDao;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.Instant;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    @Override
    public void registerUser(String userName, String password, String role) {
        try (Connection conn = DBConfig.getConnection()) {
            //username exist check
            System.out.println(userName + " " + password + " " + role);
            String hashedPassword = hashPassword(password);
            String sql = "INSERT INTO users (userName, hashedPassword, role) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            stmt.setString(2, hashedPassword);
            stmt.setString(3, role);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User registered successfully.");
            } else {
                System.out.println("User registration failed.");
            }

        } catch (SQLException e) {
            //custom error check
            System.out.println(e.getMessage());
        }
    }

    @Override
    public  Optional<String> loginUser(String userName, String password) {
        try (Connection conn = DBConfig.getConnection()) {
            String sql = "SELECT hashedPassword, role FROM Users WHERE userName = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHashedPassword = rs.getString("hashedPassword");
                if (storedHashedPassword.equals(hashPassword(password))) {
                    // Login successful, get the role
                    String role = rs.getString("role");

                    // Store the login time in the database
                    storeLoginTime(conn, userName);
                    System.out.println("entered");
                    // Return the role for routing purposes
                    return Optional.of(role);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Store the login time in the database
    private void storeLoginTime(Connection conn, String userName) {
        try {
            String sql = "UPDATE users SET lastLogin = ? WHERE userName = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setTimestamp(1, Timestamp.from(Instant.now()));
            stmt.setString(2, userName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
