package com.hsbc.application.model;

import java.util.Date;

public class Developer extends User {

    public Developer() {

    }

    public Developer(int id, String username, String passwd, String role, Date lastLoggedIn) {
        super(id, username, passwd, role, lastLoggedIn);
    }

    @Override
    public String toString() {
        return (super.toString());
    }

    //    public static Developer getDeveloperById(int developerID) throws UserNotFoundException, DatabaseAccessException {
//        String sql = "SELECT * FROM Users WHERE userId = ? AND role = 'DEVELOPER'";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, developerID);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    Developer developer = new Developer();
//                    developer.setId(rs.getInt("userId"));
//                    developer.setUsername(rs.getString("userName"));
//                    developer.setPasswd(rs.getString("hashedPassword"));
//                    developer.setRole(rs.getString("role"));
//                    developer.setLastLoggedIn(rs.getTimestamp("last_logged_in"));
//                    return developer;
//                } else {
//                    throw new UserNotFoundException("Developer with ID " + developerID + " not found.");
//                }
//            }
//        } catch (SQLException e) {
//            throw new DatabaseAccessException("Error accessing the database.", e);
//        }
//    }
}
