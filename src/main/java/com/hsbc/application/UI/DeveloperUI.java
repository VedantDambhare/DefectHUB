package com.hsbc.application.UI;

import com.hsbc.application.daoimpl.DeveloperDaoImpl;
import com.hsbc.application.exceptions.BugNotFoundException;
import com.hsbc.application.exceptions.ProjectNotFoundException;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;
import com.hsbc.application.service.DeveloperService;
import com.hsbc.application.service.DeveloperServiceImpl;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class DeveloperUI {
    private final Scanner scanner = new Scanner(System.in);
    private final DeveloperService developerService;
    Logger logger;

    public DeveloperUI(DeveloperService developerService) {
        this.developerService = developerService;
        this.logger = org.slf4j.LoggerFactory.getLogger(DeveloperUI.class);
    }

    public void displayDeveloperPanel(String developerUsername) {
        System.out.println("Welcome, Developer " + developerUsername);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\nDeveloper Panel:");
            System.out.println("1. View Assigned Bugs");
            System.out.println("2. Update Bug Status");
            System.out.println("3. Get Bug Details");
            System.out.println("4. View Assigned Projects");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAssignedBugs();
                    break;
                case 2:
                    updateBugStatus();
                    break;
                case 3:
                    viewBugDetails();
                    break;
                case 4:
                    viewAssignedProject();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void viewAssignedBugs() {
        System.out.print("Enter Developer ID: ");
        int developerId = scanner.nextInt(); // Assuming scanner is initialized somewhere

        System.out.println("\nAssigned Bugs:");

        try {
            // Get the assigned bugs for the developer ID
            Optional<List<Bug>> optionalBugs = developerService.getAssignedBugs(developerId);
            System.out.println("BUGS INFO: "+optionalBugs);

            // Print the bugs if they are present
            optionalBugs.ifPresentOrElse(bugs -> {
                // Iterate over the list of bugs and print their details
                bugs.forEach(bug -> {
                    System.out.println("Bug ID: " + bug.getBugID());
                    System.out.println("Title: " + bug.getTitle());
                    System.out.println("Description: " + bug.getDesc());
                    System.out.println("Status: " + bug.getStatus());
                    System.out.println("Priority: " + bug.getPriority());
                    System.out.println("Severity: " + bug.getSeverity());
                    System.out.println("Project ID: " + bug.getProjectId());
                    System.out.println("Reporter ID: " + bug.getReporterId());
                    System.out.println("Assignee ID: " + bug.getAssigneeId());
                    System.out.println("Created At: " + bug.getCreatedAt());
                    System.out.println("Updated At: " + bug.getUpdatedAt());
                    System.out.println("----------------------");
                });
            }, () -> System.out.println("No bugs assigned to this developer."));

        } catch (BugNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateBugStatus() {
        System.out.print("Enter Bug ID to update: ");
        int bugId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Select new status: ");
        System.out.println("1. Press 1 for changing status to IN_PROGRESS");
        System.out.println("2. Press 2 for changing status to RESOLVED");
        int statusChoice = scanner.nextInt();
        scanner.nextLine();

        String newStatus = (statusChoice == 1) ? "IN_PROGRESS" : "RESOLVED";
        developerService.updateBugStatus(bugId, newStatus);
        System.out.println("Bug status updated successfully.");
    }

    private void viewBugDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Bug ID: ");
        int bugID = scanner.nextInt();

        try {
            Optional<Bug> bug = developerService.getBugDetails(bugID);
            System.out.println("BUG INFO: ");
            System.out.println(bug.toString());
        } catch (BugNotFoundException e) {
            //System.out.println("Error: " + e.getMessage());
            logger.error("Error: Bug with ID " + bugID + " does not exist!!!" + e.getMessage());
        }

    }

    private void viewAssignedProject() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Developer ID: ");
        int developerID = scanner.nextInt();
        scanner.nextLine();

        Optional<Project> project = null;
        try {
            project = developerService.viewAssignedProjects(developerID);
        } catch (ProjectNotFoundException e) {
            throw new RuntimeException(e);
        }

        project.ifPresentOrElse((proj) -> System.out.println(proj.toString()),
                () -> System.out.println("No projects assigned to this developer."));

    }

    public static void main(String[] args) {

        DeveloperDaoImpl developerDao = new DeveloperDaoImpl();
        DeveloperService developerService = new DeveloperServiceImpl(developerDao);
        DeveloperUI developerUI = new DeveloperUI(developerService);
        developerUI.displayDeveloperPanel("developer_1");
    }
}
