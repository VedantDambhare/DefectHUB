package com.hsbc.application.UI;


import com.hsbc.application.dao.AdminDAO;
import com.hsbc.application.daoimpl.AdminDAOImpl;
import com.hsbc.application.exceptions.BugTrackingException;
import com.hsbc.application.exceptions.DatabaseAccessException;
import com.hsbc.application.exceptions.DuplicateProjectException;
import com.hsbc.application.exceptions.ProjectLimitReachedException;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;
import com.hsbc.application.model.ProjectManager;
import com.hsbc.application.model.User;
import com.hsbc.application.service.AdminService;
import com.hsbc.application.service.ProjectService;
import com.hsbc.application.service.ProjectServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AppUI {
    private AdminService adminService;
    private ProjectService projectService;
    private Scanner scanner;

    public AppUI(AdminService adminService, ProjectService projectService) {
        this.adminService = adminService;
        this.projectService = projectService;
        this.scanner = new Scanner(System.in);
    }

    public AppUI() {

    }

    public void start() {
        while (true) {
            System.out.println("Welcome to the Bug Tracking System");
            System.out.println("1. View User Information");
            System.out.println("2. Create New Project");
            System.out.println("3. View Projects");
            System.out.println("4. View Project Details");
            System.out.println("5. View Bug Details");
            System.out.println("6. Assign Bug to Developer");
            System.out.println("7. Close Bug");
            System.out.println("8. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewUserInfo();
                    break;
                case 2:
                    createNewProject();
                    break;
                case 3:
                    viewProjects();
                    break;
                case 4:
                    viewProjectDetails();
                    break;
                case 5:
                    viewBugDetails();
                    break;
                case 6:
                    assignBugToDeveloper();
                    break;
                case 7:
                    closeBug();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewBugDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Bug ID: ");
        int bugID = scanner.nextInt();

        try {
            Bug bug = adminService.getBugInfo(bugID);
            System.out.println("BUG INFO: ");
            System.out.println(bug.toString());
        } catch (BugTrackingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewUserInfo() {
        //System.out.println("Under Construction..");
        System.out.print("Enter User ID: ");
        int userID = scanner.nextInt();

        try {
            User user = adminService.getUserId(userID);
            System.out.println("User Info: " + user);
        } catch (BugTrackingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void createNewProject() {
        try {
            // Display available project managers
            List<ProjectManager> projectManagers = projectService.getAllProjectManagers();
            if (projectManagers.isEmpty()) {
                System.out.println("No project managers available.");
                return;
            }

            System.out.println("Available Project Managers:");
            for (int i = 0; i < projectManagers.size(); i++) {
                ProjectManager pm = projectManagers.get(i);
                System.out.println((i + 1) + ". " + pm.getUsername() + " (ID: " + pm.getId() + ")");
            }

            System.out.print("Select Project Manager by ID: ");
            int pmId = scanner.nextInt();

            // Find selected project manager
            ProjectManager selectedPM = null;
            for (ProjectManager pm : projectManagers) {
                if (pm.getId() == pmId) {
                    selectedPM = pm;
                    break;
                }
            }

            if (selectedPM == null) {
                System.out.println("Invalid project manager ID.");
                return;
            }

            // Get other project details from user
            System.out.println("ADD NEW PROJECT HERE");
            System.out.println("-----------------------------");
            System.out.println("Enter Project ID");
            int projectId = scanner.nextInt();
            System.out.print("Enter Project Name: ");
            String projectName = scanner.next();
            System.out.print("Enter Project Start Date (yyyy-MM-dd): ");
            scanner.nextLine();
            String startDate = scanner.next();
            System.out.println("START DATE"+startDate);
            System.out.print("Enter Project Status (IN_PROGRESS, COMPLETED, ON_HOLD): ");
            scanner.nextLine();
            String status = scanner.next();
            System.out.println("STATUS"+status);

            // Create Project object
            Project project = new Project();
            project.setProjectId(projectId);
            project.setProjectName(projectName);
            project.setStartDate(startDate);
            project.setStatus(status.valueOf(status)); // Assuming ProjectStatus is an enum
            project.setProjectManager(selectedPM);

            System.out.println("NEW PROJECT CREATION UNDER PROCESS");
            // Call service to create new project
            boolean success = adminService.createNewProject(project); //<-- to be Implemented
            if (success) {
                System.out.println("Project created successfully.");
            } else {
                System.out.println("Failed to create project.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error accessing database.");
        } catch (ProjectLimitReachedException | DuplicateProjectException | DatabaseAccessException e) {
//            throw new RuntimeException(e);
        }
    }

    private void viewProjects() {
        // Logic for viewing projects
    }

    private void viewProjectDetails() {
        // Logic for viewing project details
    }

    private void assignBugToDeveloper() {
        // Logic for assigning a bug to a developer
    }

    private void closeBug() {
        // Logic for closing a bug
    }
}

