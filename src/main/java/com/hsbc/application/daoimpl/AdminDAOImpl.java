package com.hsbc.application.daoimpl;

import com.hsbc.application.config.DBConfig;
import com.hsbc.application.dao.AdminDAO;
import com.hsbc.application.exceptions.*;
import com.hsbc.application.model.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hsbc.application.model.Developer.getDeveloperById;

public class AdminDAOImpl implements AdminDAO {

    //@Autowired
    private final Connection conn = DBConfig.getConnection();
    Tester reporter;

    @Override
    public Bug getBugInfo(int bugID) throws BugNotFoundException, DatabaseAccessException {
        String sql = "select * from bugs where bug_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, bugID);
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Bug bug = new Bug();
                    bug.setBugID(rs.getInt("bugID"));
                    bug.setTitle(rs.getString("title"));
                    bug.setDesc(rs.getString("desc"));
                    bug.setStatus(rs.getString("status"));
                    bug.setPriority(rs.getString("priority"));
                    bug.setSeverity(rs.getString("severity"));
                    bug.setProject(new Project(
                            101,
                            "Website Redesign",
                            "2024-01-15",
                            "In Progress",
                            new ProjectManager(101, "John Doe", "pass","Developer",new Date(), 3 ),
                            new Date(), // createdAt
                            new Date()  // updatedAt
                    ));

                    bug.setAssignee(new Developer(
                            101,
                            "John Doe",
                            "pass",
                            "Developer",
                            new Date()
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
    public User getUserInfo(int userID) throws UserNotFoundException, DatabaseAccessException {
        return null;
    }

    @Override
    public boolean createNewProject(Project project) throws DuplicateProjectException, ProjectLimitReachedException, DatabaseAccessException {
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
