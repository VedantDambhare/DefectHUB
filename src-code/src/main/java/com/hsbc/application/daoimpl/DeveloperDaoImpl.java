package com.hsbc.application.daoimpl;

import com.hsbc.application.config.CurrentSession;
import com.hsbc.application.dao.DeveloperDao;
import com.hsbc.application.exceptions.BugNotFoundException;
import com.hsbc.application.exceptions.ProjectNotFoundException;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;
import com.hsbc.application.model.ProjectManager;
import com.hsbc.application.config.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeveloperDaoImpl implements DeveloperDao {


    public DeveloperDaoImpl() {

    }

    @Override
    public Optional<List<Bug>> getAssignedBugs() throws BugNotFoundException {
        List<Bug> bugs = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection()) {

            String sql = "SELECT * FROM Bugs WHERE assigneeId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, CurrentSession.getUserId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bug bug = new Bug();
                bug.setBugID(rs.getInt("bugId"));
                bug.setTitle(rs.getString("title"));
                bug.setDesc(rs.getString("description"));
                bug.setStatus(rs.getString("status"));
                bug.setPriority(rs.getString("priority"));
                bug.setSeverity(rs.getString("severity"));
                //Later on, we will be getting the project, developer and tester info from the respective DAOs and call their GetAllInfo() methods
                bug.setProjectId(rs.getInt("projectId"));
                bug.setReporterId(rs.getInt("reporterId"));
                bug.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                bug.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                bugs.add(bug);
            }
        } catch (SQLException e) {
            // Custom error handling
            System.out.println(e.getMessage());
            throw new BugNotFoundException("Unable to retrieve bugs for developer ID: " + CurrentSession.getUserId() + e);
        }

        return bugs.isEmpty() ? Optional.empty() : Optional.of(bugs);
    }
    @Override
    public boolean bugStatus(int bugId, String status) {
        try(Connection connection = DBConfig.getConnection()) {
            String sql = "UPDATE Bugs SET status = ? WHERE bugId = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, bugId);

            int rows = stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Optional<Bug> getBugDetails(int bugID) throws BugNotFoundException {
        try (Connection conn = DBConfig.getConnection()) {
           String sql = "SELECT * FROM Bugs WHERE bugId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bugID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Bug bug = new Bug();
                bug.setBugID(rs.getInt("bugId"));
                bug.setTitle(rs.getString("title"));
                bug.setDesc(rs.getString("description"));
                bug.setStatus(rs.getString("status"));
                bug.setPriority(rs.getString("priority"));
                bug.setSeverity(rs.getString("severity"));

                //Later on, we will be getting the project, developer and tester info from the respective DAOs and call their GetAllInfo() methods
                bug.setProjectId(rs.getInt("projectId"));
                bug.setReporterId(rs.getInt("reporterId"));
                bug.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                bug.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

                return Optional.of(bug);
            }
            else{
                throw new BugNotFoundException("The bug does not exist");
            }
        } catch (SQLException e) {
            //custom error check
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Project> viewAssignedProjects() throws ProjectNotFoundException {
        String sql = "select t3.*, t4.* from (select t1.* , t2.userId from Projects t1, ProjectTeamMembers t2 where t2.userId=? and t2.projectId = t1.projectId) t3, Users t4 where t3.projectManagerId = t4.userId";

        Connection conn = DBConfig.getConnection();
        List<Project> projects = new ArrayList<Project>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, CurrentSession.getUserId());
            System.out.println("Executing query: "+ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Project p = new Project();
                p.setProjectId(rs.getInt("projectId"));
                p.setProjectName(rs.getString("projectName"));
                p.setStartDate(rs.getString("startDate"));
                p.setStatus(rs.getString("status"));
                p.setProjectManager(
                        new ProjectManager(rs.getInt("projectManagerId"),
                                rs.getString("userName"),
                                rs.getString("hashedPassword"),
                                "PROJECT_MANAGER",
                                rs.getDate("last_logged_in"))
                );
                p.setCreatedAt(rs.getDate("created_at"));
                p.setUpdatedAt(rs.getDate("updated_at"));

                projects.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {return projects;}
    }
}
