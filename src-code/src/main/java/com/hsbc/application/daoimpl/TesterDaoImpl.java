package com.hsbc.application.daoimpl;

import com.hsbc.application.config.CurrentSession;
import com.hsbc.application.config.DBConfig;
import com.hsbc.application.dao.TesterDao;
import com.hsbc.application.exceptions.ProjectNotFoundException;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;
import com.hsbc.application.model.ProjectManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TesterDaoImpl implements TesterDao {

    @Override
    public List<Bug> getAllBugs() {

        Connection conn = DBConfig.getConnection();

        String sql = "select * from Bugs where reporterID = ?";
        List<Bug> bugs = new ArrayList<Bug>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, CurrentSession.getUserId());
            System.out.println("Executing query: "+ps.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Bug b = new Bug(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("priority"),
                        rs.getString("severity"),
                        rs.getInt("projectId"),
                        CurrentSession.getUserId(),LocalDateTime.now(),LocalDateTime.now()
                );
                bugs.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Returning list of bugs");
            return bugs;
        }
    }

    @Override
    public List<Project> getAssignedProjects() {
        String sql = "select t3.*, t4.* from (select t1.* , t2.userId from Projects t1, ProjectTeamMembers t2 where t2.userId=? and t2.projectId = t1.projectId) t3, Users t4 where t3.projectManagerId = t4.userId";

        Connection conn = DBConfig.getConnection();
        List<Project> projects = new ArrayList<Project>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, CurrentSession.getUserId());
            System.out.println("Executing query: "+ps.toString());
            ResultSet rs = ps.executeQuery();
            int counter=0;
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

    @Override
    public List<Bug> getAllBugsByProjectId(int projectId) throws ProjectNotFoundException {

        Connection conn = DBConfig.getConnection();

        String sql = "select * from Bugs where reporterID = ? and projectId = ?";
        List<Bug> bugs = new ArrayList<Bug>();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, CurrentSession.getUserId());
            ps.setInt(2, projectId);
            System.out.println("Executing query: "+ps.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Bug b = new Bug(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("priority"),
                        rs.getString("severity"),
                        rs.getInt("projectId"),
                        CurrentSession.getUserId(),LocalDateTime.now(),LocalDateTime.now()
                );
                bugs.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Returning list of bugs");
            return bugs;
        }
    }


    @Override
    public boolean addNewBug(Bug bug) {
        Connection conn = DBConfig.getConnection();

        String sql = "insert into Bugs(title,description,status,priority,severity,projectId,reporterId,created_at,updated_at) values(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,bug.getTitle());
            ps.setString(2, bug.getDesc());
            ps.setString(3, bug.getStatus());
            ps.setString(4,bug.getPriority());
            ps.setString(5,bug.getSeverity());
            ps.setInt(6,bug.getProjectId());
            ps.setInt(7,bug.getReporterId());
            DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String created_at = bug.getCreatedAt().format(sqlFormatter);
            ps.setString(8,bug.getCreatedAt().toString());
            ps.setString(9,bug.getCreatedAt().toString());

            System.out.println("Executing Query... " + ps.toString());
            if(ps.executeUpdate()>0)
                return true;
            return false;


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
