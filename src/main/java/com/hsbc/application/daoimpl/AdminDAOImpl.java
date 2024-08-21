package com.hsbc.application.daoimpl;

import com.hsbc.application.config.DBConfig;
import com.hsbc.application.dao.AdminDAO;
import com.hsbc.application.exceptions.*;
import com.hsbc.application.model.*;


import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import static com.hsbc.application.model.Developer.getDeveloperById;

public class AdminDAOImpl implements AdminDAO {


    Connection connection;


    public AdminDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Bug getBugInfo(int bugID) throws BugNotFoundException, DatabaseAccessException {
        String sql = "select * from Bugs where bugId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, bugID);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Bug bug = new Bug();
                    bug.setBugID(rs.getInt("bugID"));
                    bug.setTitle(rs.getString("title"));
                    bug.setDesc(rs.getString("description"));
                    bug.setStatus(rs.getString("status"));
                    bug.setPriority(rs.getString("priority"));
                    bug.setSeverity(rs.getString("severity"));
                    bug.setProject(new Project(
                            101,
                            "Website Redesign",
                            "2024-01-15",
                            "In Progress",
                            new ProjectManager(101, "John Doe", "pass","Developer",new java.util.Date()),
                            new java.util.Date(),// createdAt
                            new java.util.Date()  // updatedAt
                    ));

                    bug.setAssignee(new Developer(
                            101,
                            "John Doe",
                            "pass",
                            "Developer",
                            new java.util.Date()
                    ));
                    bug.setCreatedAt(rs.getTimestamp("created_at"));
                    bug.setUpdatedAt(rs.getTimestamp("updated_at"));



                    //FOR PROJECT, DEVELOPER AND TESTER INFO GTO BE PRESENT IN BUG, SIMPLY CALL THE RESPECTIVE DAO METHODS IN THE IMPLEMENTATION
                    //OF GIVEN DAOs E.G. SHOWALLPROJECTS() FROM PROJECTSDAOIMPL, GETTESTERDETAILS() FROM TESTERDAOIMPL, GETDEVELOPERDETAILS() FROM DEVELOPERDAOIMPL
                    //I WILL BE GETTING ONLY IDs HERE SO I WILL BE CALLING THE RESPECTIVE DAO METHODS TO GET THE DETAILS BY PASSING THIS ID.

                    //TEMPORARY SOLUTION FOR TESTING PURPOSES
                    Developer assignee = getDeveloperById(rs.getInt("assigneeId"));
                    bug.setAssignee(assignee);
                    Tester reporter = Tester.getTesterById(rs.getInt("reporterId"));
                    bug.setReporter(reporter);

                    return bug;
                } else {
                    throw new BugNotFoundException("Bug with ID " + bugID + " not found.");
                }
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserId(int userID) throws DatabaseAccessException {
        User user = null;
        String sql = "SELECT * FROM Users WHERE userId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("userId"));
                    user.setUsername(rs.getString("userName"));
                    user.setPasswd(rs.getString("hashedPassword"));
                    user.setRole(rs.getString("role"));
                    user.setLastLoggedIn(rs.getTimestamp("last_logged_in"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Error accessing the database: " + e.getMessage(), e);
        }
        return user;
    }

    @Override
    public boolean createNewProject(Project project) throws DuplicateProjectException, ProjectLimitReachedException, DatabaseAccessException {
            String query = "INSERT INTO Projects (projectId, projectName, startDate, status, projectManagerId, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(query)) {

                System.out.println("In AdminDaoImpl for Create New Project");

                if (isDuplicateProject(project.getProjectName())) {
                    throw new DuplicateProjectException("Project with the same name already exists.");
                }

                if (isProjectLimitReached(project.getProjectManager().getTotalProjects())) {
                    throw new ProjectLimitReachedException("Project limit reached for this manager.");
                }

                LocalDate startDate;
                try {
                    startDate = LocalDate.parse(project.getStartDate());
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("Invalid start date format.", e);
                }
                System.out.println(startDate);

                System.out.println("Project: " + project);
                System.out.println("ID: " +project.getProjectManager().getId());
                // Set parameters
                System.out.println("0");
                ps.setInt(1, project.getProjectId());
                System.out.println("1");
                ps.setString(2, project.getProjectName());
                System.out.println("2");
                ps.setDate(3, Date.valueOf(startDate));
                System.out.println("3");
                ps.setString(4, project.getStatus());
                System.out.println("4");
                ps.setInt(5, project.getProjectManager().getId());
                System.out.println("5");
                ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                System.out.println("6");
                ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

                System.out.println(new Timestamp(System.currentTimeMillis()));
                // Execute update
                System.out.println("Executing update");
                int rowsAffected= ps.executeUpdate();
                System.out.println("Rows affected: " + rowsAffected);
                return rowsAffected > 0;

            } catch (SQLException e) {
                throw new DatabaseAccessException("Error accessing database.", e);
            }
        }

        private boolean isDuplicateProject(String projectName) throws SQLException {
            System.out.println("Checking for duplicate project");
            String query = "SELECT COUNT(*) FROM Projects WHERE projectName = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, projectName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
            return false;
        }

        private boolean isProjectLimitReached(int projectManagerId) throws SQLException {
            System.out.println("Checking project limit");
            String query = "SELECT totalProjects FROM Admins WHERE adminId = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, projectManagerId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int totalProjects = rs.getInt("totalProjects");
                        return totalProjects >= 5;
                    }
                }
            }
            return false;
        }


    @Override
    public List<Project> showProjects(int managerID) throws ProjectNotFoundException, DatabaseAccessException {
        return List.of();
    }

    @Override
    public List<Bug> showBugsByFilter(int projectID, String filter) throws BugNotFoundException, DatabaseAccessException {
        return List.of();
    }

    @Override
    public boolean assignBugToDeveloper(int bugID, int developerID) throws BugNotFoundException, UserNotFoundException, DatabaseAccessException {
        return false;
    }

    @Override
    public boolean closeBug(int bugID) throws BugNotFoundException, DatabaseAccessException {
        return false;
    }
}
