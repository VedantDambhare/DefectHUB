package com.hsbc.application.UI;


import com.hsbc.application.exceptions.*;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;
import com.hsbc.application.model.ProjectManager;
import com.hsbc.application.model.User;
import com.hsbc.application.service.AdminService;
import com.hsbc.application.service.ProjectService;
import org.slf4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ProjectManagerUI {
    private AdminService adminService;
    private ProjectService projectService;
    private Scanner scanner;
    Logger logger;

    public ProjectManagerUI(AdminService adminService, ProjectService projectService) {
        this.adminService = adminService;
        this.projectService = projectService;
        this.logger = org.slf4j.LoggerFactory.getLogger(ProjectManagerUI.class);
    }

    public ProjectManagerUI() {

    }

    public void start() {

        scanner = new Scanner(System.in);

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("\n Welcome to the Bug Tracking System");
            System.out.println("1. View User Information");
            System.out.println("2. Create New Project");
            System.out.println("3. View Project");
            System.out.println("4. View All Project Details");
            System.out.println("5. View All Bug Details");
            System.out.println("6. View Bug Details By ID");
            System.out.println("7. View Bug Details By Filter");
            System.out.println("8. Assign Bug to Developer");
            System.out.println("9. Close Bug");
            System.out.println("10. Logout");
            System.out.println("11. Exit");

            System.out.println("Enter your choice: ");
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
                    viewAllBugDetails();
                    break;
                case 6:
                    viewBugDetails();
                    break;
                case 7:
                    viewBugDetailsByFilter();
                    break;
                case 8:
                    assignBugToDeveloper();
                    break;
                case 9:
                    closeBug();
                    break;
                case 10:
                    System.out.println("Logging out...");
                    return;
                case 11:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewBugDetailsByFilter() {
        List<Bug> bugs;
        String value = "",filter;
        System.out.println("Enter Project ID: ");
        int projectID = scanner.nextInt();
        System.out.println("Enter Filter (status, priority, severity): ");
        filter = scanner.next();
        if(filter.equalsIgnoreCase("status")){
            System.out.println("STATUS VALUES: NEW, IN_PROGRESS, RESOLVED, CLOSED");
            System.out.println("ENTER STATUS VALUE");
            value = scanner.next();
        }else if(filter.equalsIgnoreCase("priority")){
            System.out.println("PRIORITY VALUES: LOW, MEDIUM, HIGH, CRITICAL");
            System.out.println("ENTER PRIORITY VALUE");
            value = scanner.next();
            }
        else if(filter.equalsIgnoreCase("severity")) {
            System.out.println("SEVERITY VALUES: MINOR, MAJOR, BLOCKER");
            System.out.println("ENTER SEVERITY VALUE");
            value = scanner.next();
        }

        try {
            bugs = adminService.showBugsByFilter(projectID, filter, value);
            if(bugs == null)
                throw new BugNotFoundException("Bug for Project with ID " +projectID+" does not exist");

            System.out.println("Bugs filtered by " + filter + " for value " +value+ " for Project ID: " + projectID);
            System.out.println("-------------------------------------------------");
            System.out.println("Bug ID\tTitle\tDescription\tStatus\tPriority\tSeverity\tProject ID\tReporter ID\tCreated Date\tUpdated Date");
            for(Bug b : bugs){
                System.out.println(b.getBugID() + "\t" + b.getTitle() + "\t" + b.getDesc() + "\t" + b.getStatus() + "\t" + b.getPriority() + "\t" + b.getSeverity() + "\t" + b.getProjectId() + "\t" + b.getReporterId() + "\t"  + b.getCreatedAt() + "\t" + b.getUpdatedAt());
            }
        } catch (BugNotFoundException | UserNotFoundException | SQLException e) {
            System.out.println("Bug not found");
            logger.error("Bug not found");
        } catch (DatabaseAccessException e) {
            System.out.println("Database access error");
            logger.error("Database access error while displaying bugs by filter");
        }

    }

    private void viewAllBugDetails() {
        List<Bug> bugs;
        try {
            bugs = adminService.showAllBugs();
            System.out.println("All Bugs: ");
            System.out.println("-------------------------------------------------");
            System.out.println("Bug ID\tTitle\tDescription\tStatus\tPriority\tSeverity\tProject ID\tReporter ID\tCreated Date\tUpdated Date");
            for(Bug b : bugs){
                System.out.println(b.getBugID() + "\t" + b.getTitle() + "\t" + b.getDesc() + "\t" + b.getStatus() + "\t" + b.getPriority() + "\t" + b.getSeverity() + "\t" + b.getProjectId() + "\t" + b.getReporterId() + "\t"  + b.getCreatedAt() + "\t" + b.getUpdatedAt());
            }
        } catch (DatabaseAccessException e) {
            System.out.println("Database access error");
            logger.error("Database access error while displaying all bugs");
        }
    }

    private void viewBugDetails() {
        System.out.print("Enter Bug ID: ");
        int bugID = scanner.nextInt();

        try {
            Bug bug = adminService.getBugInfo(bugID);
            System.out.println("BUG INFO: ");
            System.out.println(bug.toString());
        } catch (BugTrackingException e) {
            //System.out.println("Error: " + e.getMessage());
            logger.error("Error: Bug with ID " +bugID+ " does not exist!!!" + e.getMessage());
        }
    }

    private void viewUserInfo() {
        //System.out.println("Under Construction..");
        System.out.print("Enter User ID: ");
        int userID = scanner.nextInt();

        try {
            User user = adminService.getUserInfo(userID);
            System.out.println("User Info: " + user);
        } catch (BugTrackingException e) {
            //System.out.println("Error: " + e.getMessage());
            logger.error("Error: User with ID " +userID+ " does not exist!!!" + e.getMessage());
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
            project.setStatus(String.valueOf(status));
            project.setProjectManager(selectedPM);

            System.out.println("NEW PROJECT CREATION UNDER PROCESS");
            // Call service to create new project
            boolean success = adminService.createNewProject(project);
            if (success) {
                System.out.println("Project created successfully.");
            } else {
                System.out.println("Failed to create project.");
            }

        } catch (SQLException e) {
            logger.error("Error accessing database.");
            System.out.println("Error accessing database.");
        } catch (ProjectLimitReachedException | DuplicateProjectException | DatabaseAccessException e) {
                logger.error("Error creating new project", e);
        }
    }

    private void viewProjects() {
        // Logic for viewing projects
        List<Project> plist;
        System.out.println("Enter Project Manager ID to view projects: ");
        int managerID = scanner.nextInt();
        try {
            plist = adminService.showProjects(managerID);
            System.out.println("Projects associated with Manager ID: " + managerID);
            System.out.println("-------------------------------------------------");
            System.out.println("Project ID\tProject Name\tStart Date\tStatus \tManager ID \tCreated Date \tUpdated Date");
            for(Project p : plist){
                System.out.println(p.getProjectId() + "\t" + p.getProjectName() + "\t" + p.getStartDate() + "\t" + p.getStatus() + "\t" + p.getProjectManager().getId() + "\t" + p.getCreatedAt() + "\t" + p.getUpdatedAt());
            }
        } catch (ProjectNotFoundException e) {
            System.out.println("Project not found");
            logger.error("Project not found "+ e.getMessage());
        } catch (DatabaseAccessException e) {
            System.out.println("Database access error");
            logger.error("Database access error" + e.getMessage());
        }
    }

    private void viewProjectDetails() {
        List<Project> plist;
        System.out.println("Enter Manager ID to view associated projects: ");
        int managerID = scanner.nextInt();
        try {
            plist = adminService.showProjects(managerID);
            for(Project p : plist)
                System.out.println(p.toString());
        } catch (ProjectNotFoundException e) {
            logger.error("Project not found", e);
        } catch (DatabaseAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void assignBugToDeveloper() {

        this.viewAllBugDetails();
        System.out.println("LIST OF ALL DEVELOPERS: (UNDER CONSTRUCTION)");

        boolean flag = false;
        System.out.println("Enter Bug ID to assign: ");
        int bugID = scanner.nextInt();
        System.out.println("Here's a List of available developers: (UNDER PROGRESS)");
        System.out.println("Enter Developer ID to assign: ");
        int developerID = scanner.nextInt();
        try {
            flag = adminService.assignBugToDeveloper(bugID, developerID);
            if(flag)
                System.out.println("Bug with ID " +bugID+ " assigned to developer with ID " +developerID+ " successfully.");
            else
                System.out.println("Failed to assign bug to developer. NOTE: Only 'NEW' bugs can be assigned to developers.");
        } catch (BugNotFoundException e) {
            logger.error("Bug not found", e);
        } catch (UserNotFoundException e) {
            logger.error("User not found", e);
        } catch (DatabaseAccessException e) {
            logger.error("Database access error", e);
        }
    }

    private void closeBug() {

        boolean flag = false;
        System.out.println("INITIATING BUG CLOSURE...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("VERIFY YOURSELF...");
        System.out.println("Enter your username: ");
        String uname = scanner.next();
        System.out.println("Enter your password: ");
        String upass = scanner.next();
        System.out.println("Enter Bug ID to perform operation: ");
        int bugID = scanner.nextInt();
        System.out.println("VERIFYING...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            flag = adminService.closeBug(bugID, uname, upass);
            if (flag)
                System.out.println("Bug with ID " +bugID+ " closed successfully.");
            else
                System.out.println("Failed to close bug. NOTE: Only 'RESOLVED' bugs can be closed.");
        } catch (BugNotFoundException e) {
            logger.error("Bug not found", e);
        } catch (DatabaseAccessException e) {
            logger.error("Database access error", e);
        }
    }
}

