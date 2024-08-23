package com.hsbc.application.daoimpl;

import com.hsbc.application.dao.DeveloperDao;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;
import com.hsbc.application.model.ProjectManager;
import com.hsbc.application.config.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DeveloperDaoImpl implements DeveloperDao {
    @Override
    public Optional<Bug> getAssignedBugs(int developerId, int projectId) {
        try (Connection conn = DBConfig.getConnection()) {

            String sql = "SELECT * FROM bugs WHERE developer_id = ? AND project_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, developerId);
            stmt.setInt(2, projectId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Bug bug = new Bug();
            }
        } catch (SQLException e) {
            //custom error check
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean bugStatus(int bugId, String status) {
        return false;
    }

    @Override
    public Optional<Bug> getBugDetails(int bugID) {
        try (Connection conn = DBConfig.getConnection()) {
            String sql = "SELECT * FROM bugs WHERE bug_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bugID);

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
            }
        } catch (SQLException e) {
            //custom error check
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Project> viewAssignedProjects(int developerID) {
        try (Connection conn = DBConfig.getConnection()) {
            String sql = "SELECT * FROM project WHERE developer_id = ?";
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
            }
        } catch (SQLException e) {
            //custom error check
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}
