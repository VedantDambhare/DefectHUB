package com.hsbc.application.UI;


import com.hsbc.application.exceptions.BugTrackingException;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.User;
import com.hsbc.application.service.AdminService;

import java.util.Scanner;

public class AppUI {
    private AdminService adminService;
    private Scanner scanner;

    public AppUI(AdminService adminService) {
        this.adminService = adminService;
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

        System.out.println("Under Construction..");
//        System.out.print("Enter User ID: ");
//        int userID = scanner.nextInt();
//
//        try {
//            User user = adminService.getUserInfo(userID);
//            System.out.println("User Info: " + user);
//        } catch (BugTrackingException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
    }

    private void createNewProject() {
        // Logic for creating a new project
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

    public static void main(String[] args) {
        AppUI appUI = new AppUI();
        appUI.viewBugDetails();
    }
}

