package com.hsbc.application.UI;

import com.hsbc.application.config.CurrentSession;
import com.hsbc.application.dao.TesterDao;
import com.hsbc.application.daoimpl.TesterDaoImpl;
import com.hsbc.application.exceptions.ProjectNotFoundException;
import com.hsbc.application.model.Bug;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TesterUI {
    public void showTesterUI() {
        Scanner sc = new Scanner(System.in);
        TesterDao tester = new TesterDaoImpl();

        while(true){
            System.out.println("Enter choice");
            System.out.println("1. Add new Bug");
            System.out.println("2. Show All Bugs Raised");
            System.out.println("3. Show Bugs by Project Id");
            System.out.println("4. View All assigned projects");
            System.out.println("5. Logout");
            System.out.println("6. Exit");


            int ch = sc.nextInt();
            switch(ch){
                case 1:
                    addNewBug(sc,tester);
                    break;

                case 2:
                    tester.getAllBugs().forEach(System.out::println);
                    break;

                case 3:
                    System.out.println("Enter project Id");
                    int searchProjectId = sc.nextInt();
                    try {
                        tester.getAllBugsByProjectId(searchProjectId).forEach(System.out::println);
                    } catch (ProjectNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    System.out.println("Here are your assigned projects...");
                    tester.getAssignedProjects().forEach(System.out::println);
                    break;

                case 5:
                    System.out.println("Logging out...");
                    return;

                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addNewBug(Scanner sc, TesterDao tester){
        System.out.println("Enter bug Title");
        String title = sc.next();
        System.out.println("Enter bug Description");
        String desc = sc.next();
        String status = "NEW";
        System.out.println("Enter bug Priority ('LOW','MEDIUM','HIGH','CRITICAL') ");
        sc.nextLine();
        String priority = sc.nextLine();
        System.out.println("Enter Bug Severity ('MINOR','MAJOR','BLOCKER')");
        String severity = sc.next();
        System.out.println("Enter Project Id");
        int projectId = sc.nextInt();
        int reporterId = CurrentSession.getUserId();

        LocalDateTime created_at = LocalDateTime.now();
        DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                    String created_at = now.format(sqlFormatter);
        System.out.println("Adding bug...");
        if(tester.addNewBug(new Bug(title,desc,status,priority,severity,projectId,reporterId,created_at,created_at)))
            System.out.println("Added Bug successfully..");
        else System.out.println("Failed to add Bug");
    }

}
