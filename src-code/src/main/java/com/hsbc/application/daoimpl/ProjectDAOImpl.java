package com.hsbc.application.daoimpl;

import com.hsbc.application.config.DBConfig;
import com.hsbc.application.dao.ProjectDao;
import com.hsbc.application.model.ProjectManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAOImpl implements ProjectDao {

    Connection connection = DBConfig.getConnection();
    public ProjectDAOImpl() {

    }

    @Override
    public List<ProjectManager> getAllProjectManagers() throws SQLException {
        List<ProjectManager> projectManagers = new ArrayList<>();
        String query = "SELECT * FROM Users WHERE role = 'PROJECT_MANAGER'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("UserId");
                String username = resultSet.getString("username");
                String passwd = resultSet.getString("hashedPassword");
                String role = resultSet.getString("role");
                Date lastLoggedIn = resultSet.getDate("last_logged_in");
                ProjectManager projectManager = new ProjectManager(id, username, passwd, role, lastLoggedIn);
                projectManagers.add(projectManager);
            }
        }
        return projectManagers;
    }
}
