package com.hsbc.application;


import com.hsbc.application.daoimpl.AdminDAOImpl;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;
import com.hsbc.application.model.ProjectManager;
import com.hsbc.application.model.User;
import com.hsbc.application.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminDAOImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private AdminDAOImpl adminDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        // Mock SQL behavior
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    // Test cases for getBugInfo
    @Test
    public void testGetBugInfo_Success() throws SQLException, BugNotFoundException, DatabaseAccessException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("bugID")).thenReturn(1);
        when(resultSet.getString("title")).thenReturn("Test Bug");
        when(resultSet.getString("description")).thenReturn("Bug Description");
        when(resultSet.getString("status")).thenReturn("Open");
        when(resultSet.getString("priority")).thenReturn("High");
        when(resultSet.getString("severity")).thenReturn("Critical");
        when(resultSet.getInt("projectId")).thenReturn(1);
        when(resultSet.getInt("reporterId")).thenReturn(1);
        when(resultSet.getInt("assigneeId")).thenReturn(2);
        when(resultSet.getTimestamp("created_at")).thenReturn(Timestamp.valueOf("2023-08-25 10:00:00"));
        when(resultSet.getTimestamp("updated_at")).thenReturn(Timestamp.valueOf("2023-08-25 12:00:00"));

        Bug bug = adminDAO.getBugInfo(1);

        assertNotNull(bug);
        assertEquals(1, bug.getBugID());
        assertEquals("Test Bug", bug.getTitle());
        assertEquals("Bug Description", bug.getDesc());
    }

    @Test
    public void testGetBugInfo_BugNotFound() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        assertThrows(BugNotFoundException.class, () -> adminDAO.getBugInfo(1));
    }

    @Test
    public void testGetBugInfo_DatabaseAccessException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        assertThrows(DatabaseAccessException.class, () -> adminDAO.getBugInfo(1));
    }

    // Test cases for getUserInfo
    @Test
    public void testGetUserInfo_Success() throws SQLException, DatabaseAccessException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("userId")).thenReturn(1);
        when(resultSet.getString("userName")).thenReturn("john_doe");
        when(resultSet.getString("hashedPassword")).thenReturn("hashed_password");
        when(resultSet.getString("role")).thenReturn("PROJECT_MANAGER");
        when(resultSet.getTimestamp("last_logged_in")).thenReturn(Timestamp.valueOf("2023-08-25 10:00:00"));

        User user = adminDAO.getUserInfo(1);

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("john_doe", user.getUsername());
        assertEquals("hashed_password", user.getPasswd());
        assertEquals("PROJECT_MANAGER", user.getRole());
    }

    @Test
    public void testGetUserInfo_DatabaseAccessException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        assertThrows(DatabaseAccessException.class, () -> adminDAO.getUserInfo(1));
    }

    // Test cases for createNewProject
    @Test
    public void testCreateNewProject_Success() throws SQLException, DuplicateProjectException, ProjectLimitReachedException, DatabaseAccessException {
        ProjectManager projectManager = new ProjectManager(1, "John Doe", "john_doe", "hashed_password", Timestamp.valueOf("2023-08-25 10:00:00"));
        Project project = new Project(1, "New Project", LocalDate.now().plusDays(3).toString(), "In Progress", projectManager, null, null);

        when(resultSet.next()).thenReturn(false);  // No duplicate project
        when(resultSet.getInt("totalProjects")).thenReturn(3);  // Under limit
        when(preparedStatement.executeUpdate()).thenReturn(1);  // Insertion success

        boolean isCreated = adminDAO.createNewProject(project);

        assertTrue(isCreated);
    }

    @Test
    public void testCreateNewProject_DuplicateProjectException() throws SQLException {
        when(resultSet.next()).thenReturn(true);  // Duplicate project

        Project project = new Project(1, "Duplicate Project", LocalDate.now().plusDays(3).toString(), "In Progress", new ProjectManager(), null, null);

        assertThrows(DuplicateProjectException.class, () -> adminDAO.createNewProject(project));
    }

    @Test
    public void testCreateNewProject_ProjectLimitReachedException() throws SQLException {
        when(resultSet.next()).thenReturn(false);  // No duplicate project
        when(resultSet.getInt("totalProjects")).thenReturn(5);  // Limit reached

        Project project = new Project(1, "New Project", LocalDate.now().plusDays(3).toString(), "In Progress", new ProjectManager(), null, null);

        assertThrows(ProjectLimitReachedException.class, () -> adminDAO.createNewProject(project));
    }

    @Test
    public void testCreateNewProject_DatabaseAccessException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        Project project = new Project(1, "New Project", LocalDate.now().plusDays(3).toString(), "In Progress", new ProjectManager(), null, null);

        assertThrows(DatabaseAccessException.class, () -> adminDAO.createNewProject(project));
    }

    // Test cases for showProjects
    @Test
    public void testShowProjects_Success() throws SQLException, DatabaseAccessException {
        when(resultSet.next()).thenReturn(true, true, false); // Simulate two projects
        when(resultSet.getInt("projectId")).thenReturn(1, 2);
        when(resultSet.getString("projectName")).thenReturn("Project One", "Project Two");
        when(resultSet.getDate("startDate")).thenReturn(Date.valueOf(LocalDate.now()));
        when(resultSet.getString("status")).thenReturn("In Progress");
        when(resultSet.getInt("projectManagerId")).thenReturn(1);
        when(resultSet.getTimestamp("created_at")).thenReturn(Timestamp.valueOf("2023-08-25 10:00:00"));
        when(resultSet.getTimestamp("updated_at")).thenReturn(Timestamp.valueOf("2023-08-25 12:00:00"));

        List<Project> projects = adminDAO.showProjects(1);

        assertNotNull(projects);
        assertEquals(2, projects.size());
        assertEquals("Project One", projects.get(0).getProjectName());
        assertEquals("Project Two", projects.get(1).getProjectName());
    }

    @Test
    public void testShowProjects_NoProjectsFound() throws SQLException, DatabaseAccessException {
        when(resultSet.next()).thenReturn(false);

        List<Project> projects = adminDAO.showProjects(1);

        assertNotNull(projects);
        assertEquals(0, projects.size());
    }

    @Test
    public void testShowProjects_DatabaseAccessException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Database error"));

        DatabaseAccessException exception = assertThrows(DatabaseAccessException.class, () -> adminDAO.showProjects(1));
        assertTrue(exception.getMessage().contains("Error accessing the database"));
    }
}
