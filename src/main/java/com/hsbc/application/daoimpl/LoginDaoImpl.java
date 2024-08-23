package com.hsbc.application.daoimpl;



import com.hsbc.application.config.DBConfig;
import com.hsbc.application.dao.LoginDao;
import com.hsbc.application.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDaoImpl implements LoginDao {

//    Logger logger;

    @Override
    public String authenticate(String username, String hashedPassword) throws UserNotFoundException {
        Connection conn = DBConfig.getConnection();
//        logger.info("Connection to database successful..");
//        logger.info("Authenticating...");
        System.out.println("Connection to database successful..");
        System.out.println("Authenticating...");
        String sql = "select * from Users where userName=? and hashedPassword =?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            ResultSet rs = stmt.executeQuery();
            if(!rs.isBeforeFirst()){
                System.out.println("LoginDaoImpl.authenticate : ResultSet is empty");
                throw new UserNotFoundException("No User Found");   //User not found
            }
            rs.next();
            if(rs.getString("userName").equals(username) && rs.getString("hashedPassword").equals(hashedPassword)){
                return rs.getString("role");
            }
            return "";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
