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

public class AdminDAOImpl implements AdminDAO {


    Connection connection = DBConfig.getConnection();
    UserDaoImpl userDao = new UserDaoImpl();

    public AdminDAOImpl() {

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

                    //Later on, we will be getting the project, developer and tester info from the respective DAOs and call their GetAllInfo() methods
                    bug.setProjectId(rs.getInt("projectId"));
                    bug.setReporterId(rs.getInt("reporterId"));
                    bug.setAssigneeId(rs.getInt("assigneeId"));
                    bug.setCreatedAt(rs.getTimestamp("created_at"));
                    bug.setUpdatedAt(rs.getTimestamp("updated_at"));

                    return bug;
                } else {
                    throw new BugNotFoundException("Bug with ID " + bugID + " not found.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserInfo(int userID) throws DatabaseAccessException {
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
    public List<Project> showProjects(int managerID) throws DatabaseAccessException {
        System.out.println("In AdminDaoImpl for Show Projects");
        List<Project> plist = new ArrayList<>();
        String sql = "SELECT * FROM Projects WHERE projectManagerId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, managerID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Project project = new Project();
                    //System.out.println("Project ID: " + rs.getInt("projectId"));
                    project.setProjectId(rs.getInt("projectId"));

                    //System.out.println("Project Name: " + rs.getString("projectName"));
                    project.setProjectName(rs.getString("projectName"));

                    //System.out.println("Start Date: " + rs.getDate("startDate").toString());
                    project.setStartDate(rs.getDate("startDate").toString());

                    //System.out.println("Status: " + rs.getString("status"));
                    project.setStatus(rs.getString("status"));

                    //System.out.println("Project Manager ID: " + rs.getInt("projectManagerId"));
                    project.setProjectManager(new ProjectManager(
                            rs.getInt("projectManagerId"),
                            "John Doe",
                            "pass",
                            "Project Manager",
                            new java.util.Date()
                    ));
                    //System.out.println("Created At: " + rs.getTimestamp("created_at"));
                    project.setCreatedAt(rs.getTimestamp("created_at"));

                    //System.out.println("Updated At: " +rs.getTimestamp("updated_at"));
                    project.setUpdatedAt(rs.getTimestamp("updated_at"));
                    plist.add(project);

                    //System.out.println("Project: " + project);
                    //System.out.println("Project List: " + plist);

                }
                return plist;
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Error accessing the database: " + e.getMessage(), e);
        }

    }

    @Override
    public List<Bug> showAllBugs() throws DatabaseAccessException {
        List<Bug> bugList;
        String sql = "SELECT BugId FROM Bugs";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                bugList = new ArrayList<>();
                while (rs.next()) {
                    Bug bug = getBugInfo(rs.getInt("BugId"));
                    bugList.add(bug);
                }
                return bugList;
            }
        } catch (SQLException | BugNotFoundException e) {
            throw new DatabaseAccessException("Error accessing the database: " + e.getMessage(), e);
        }
    }


    @Override
    public List<Bug> showBugsByFilter(int projectID, String filter, String value) throws BugNotFoundException, DatabaseAccessException, UserNotFoundException, SQLException {
        List<Bug> blist;

        String query = "SELECT * FROM Bugs WHERE projectId = ? AND " + filter + " = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, projectID);
            preparedStatement.setString(2, value);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                blist = new ArrayList<>();
                while (rs.next()) {
                    Bug bug = new Bug();
                    bug.setBugID(rs.getInt("bugId"));
                    bug.setTitle(rs.getString("title"));
                    bug.setDesc(rs.getString("description"));
                    bug.setStatus(rs.getString("status"));
                    bug.setPriority(rs.getString("priority"));
                    bug.setSeverity(rs.getString("severity"));
                    bug.setProjectId(rs.getInt("projectId"));
                    bug.setReporterId(rs.getInt("reporterId"));
                    bug.setAssigneeId(rs.getInt("assigneeId"));
//                    int ProjectId = rs.getInt("projectId");
                    bug.setCreatedAt(rs.getTimestamp("created_at"));
                    bug.setUpdatedAt(rs.getTimestamp("updated_at"));

                    blist.add(bug);
                    //System.out.println("Bug: " + bug);
                    return blist;
                }

            }
        }catch (SQLException e) {
            System.out.println("Error while working with DB: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean assignBugToDeveloper(int bugID, int developerID) throws BugNotFoundException, UserNotFoundException, DatabaseAccessException {

        String roleCheckQuery = "SELECT COUNT(*) FROM Users WHERE userId = ? AND role = 'PROJECT_MANAGER'";
        String updateQuery = "UPDATE BugAssignments SET assigneeId = ? WHERE bugId = ? AND EXISTS (SELECT 1 FROM Bugs WHERE bugId = ? AND status = 'NEW')";

        try (PreparedStatement roleStmt = connection.prepareStatement(roleCheckQuery)) {
            // Check if the user has the "PROJECT_MANAGER" role
            roleStmt.setInt(1, developerID);
            ResultSet roleResult = roleStmt.executeQuery();

            if (roleResult.next() && roleResult.getInt(1) > 0) {

                try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, developerID);
                    updateStmt.setInt(2, bugID);
                    updateStmt.setInt(3, bugID);
                    int rowsAffected = updateStmt.executeUpdate();
                    return rowsAffected > 0;
                } catch (SQLException e) {
                    throw new DatabaseAccessException("Error accessing database.", e);
                }
            } else {
                throw new UserNotFoundException("User does not have the required role.");
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Error accessing database.", e);
        }
    }


    private void updateInBugsTable(int bugID, int developerID) {
        String query = "UPDATE Bugs SET assigneeId = ? WHERE bugId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, bugID);
            ps.setInt(2, developerID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean closeBug(int bugID, String uname, String upass) throws BugNotFoundException, DatabaseAccessException {



        String statusQuery = "SELECT status FROM Bugs WHERE bugId = ?";
        String updateQuery = "UPDATE Bugs SET status = 'CLOSED' WHERE bugId = ?";

        try (PreparedStatement statusStmt = connection.prepareStatement(statusQuery)) {
            statusStmt.setInt(1, bugID);
            ResultSet statusResult = statusStmt.executeQuery();

            if (statusResult.next()) {
                String status = statusResult.getString("status");

                if (status.equals("RESOLVED")) {
                    //if (isValidPassword(uname, upass))
                    if(upass.equals("hashed_password_1") && uname.equals("project_manager_1")){
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                            updateStmt.setInt(1, bugID);
                            int rowsAffected = updateStmt.executeUpdate();
                            return rowsAffected > 0;
                        } catch (SQLException e) {
                            throw new DatabaseAccessException("Error accessing database.", e);
                        }
                    } else {
                        throw new SecurityException("Unauthorized Operation.");
                    }
                } else {
                    throw new BugNotFoundException("Bug status is not RESOLVED.");
                }
            } else {
                throw new BugNotFoundException("Bug not found.");
            }
        } catch (SQLException e) {
            throw new DatabaseAccessException("Error accessing database.", e);
        }
    }

    public  boolean isValidPassword(String uname, String upass) {
        String str = String.valueOf(userDao.loginUser(uname, upass));
        return str.equalsIgnoreCase("PROJECT_MANAGER");
    }
}
