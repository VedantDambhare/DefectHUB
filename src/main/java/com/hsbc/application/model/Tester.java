package com.hsbc.application.model;

import com.hsbc.application.config.DBConfig;
import com.hsbc.application.exceptions.DatabaseAccessException;
import com.hsbc.application.exceptions.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Tester extends User {

    static Connection conn = DBConfig.getConnection();
    public Tester(int id, String username, String passwd, String role, Date lastLoggedIn) {
        super(id, username, passwd, role, lastLoggedIn);
    }

    public Tester() {
        super();
    }

    public static Tester getTesterById(int testerID) throws UserNotFoundException, DatabaseAccessException {
        String sql = "SELECT * FROM Users WHERE userId = ? AND role = 'TESTER'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, testerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Tester tester = new Tester();
                    tester.setId(rs.getInt("userId"));
                    tester.setUsername(rs.getString("userName"));
                    tester.setPasswd(rs.getString("hashedPassword"));
                    tester.setRole(rs.getString("role"));
                    tester.setLastLoggedIn(rs.getTimestamp("last_logged_in"));
                    return tester;
                } else {
                    throw new UserNotFoundException("Tester with ID " + testerID + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Error accessing the database.", e);
        }
    }


}
