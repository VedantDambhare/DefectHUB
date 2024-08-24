package com.hsbc.application.daoimpl;

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
    public Optional<List<Bug>> getAssignedBugs(int developerId) throws BugNotFoundException {
        List<Bug> bugs = new ArrayList<>();

        try (Connection conn = DBConfig.getConnection()) {

            String sql = "SELECT * FROM Bugs WHERE assigneeId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, developerId);
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
                bug.setAssigneeId(rs.getInt("assigneeId"));
                bug.setCreatedAt(rs.getTimestamp("created_at"));
                bug.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
        } catch (SQLException e) {
            // Custom error handling
            System.out.println(e.getMessage());
            throw new BugNotFoundException("Unable to retrieve bugs for developer ID: " + developerId, e);
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
                bug.setAssigneeId(rs.getInt("assigneeId"));
                bug.setCreatedAt(rs.getTimestamp("created_at"));
                bug.setUpdatedAt(rs.getTimestamp("updated_at"));

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
    public Optional<Project> viewAssignedProjects(int developerID) throws ProjectNotFoundException {
        try (Connection conn = DBConfig.getConnection()) {
            String sql = "SELECT * FROM project WHERE developer0id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, developerID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Project project = new Project(
                        rs.getInt("projectID"),
                        rs.getString("projectName"),
                        rs.getString("startDate"),
                        rs.getString("status"),
                        (ProjectManager) rs.getObject("projectManager"),
                        rs.getDate("createdAt"),
                        rs.getDate("updateAt")
                );
                return Optional.of(project);
            } else {
                throw new ProjectNotFoundException("The project does not exist");
            }
        } catch (SQLException e) {
            //custom error check
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}
