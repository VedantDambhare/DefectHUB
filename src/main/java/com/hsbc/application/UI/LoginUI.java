package com.hsbc.application.UI;

import com.hsbc.application.config.CurrentSession;
import com.hsbc.application.UI.TesterUI;
import com.hsbc.application.dao.AdminDAO;
import com.hsbc.application.dao.LoginDao;
//import com.hsbc.application.daoimpl.AdminDAOImpl;
import com.hsbc.application.daoimpl.AdminDAOImpl;
import com.hsbc.application.daoimpl.DeveloperDaoImpl;
import com.hsbc.application.daoimpl.LoginDaoImpl;
import com.hsbc.application.daoimpl.ProjectDAOImpl;
import com.hsbc.application.exceptions.UserNotFoundException;
import com.hsbc.application.model.ProjectManager;
import com.hsbc.application.service.*;

import java.util.Scanner;

public class LoginUI {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean flag = true;
        while(flag) {
            System.out.println("Enter your username: ");
            String username = sc.nextLine();
            System.out.println("Enter your password: ");
            String password = sc.nextLine();
            LoginDao login = new LoginDaoImpl();

            try {
                String s = login.authenticate(username, password);
                System.out.println("Welcome " + username+ " logging in as "+s);
                //redirect to UI

                switch (s){
                    case "PROJECT_MANAGER":
                        AdminDAOImpl adminDAO = new AdminDAOImpl();
                        AdminService adminService = new AdminServiceImpl(adminDAO);
                        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
                        ProjectService projectService = new ProjectServiceImpl(projectDAO);
                        ProjectManagerUI adminUI = new ProjectManagerUI(adminService,projectService);
                        adminUI.start();
                        flag = false;
                        break;
                    case "DEVELOPER":
                        DeveloperDaoImpl developerDao = new DeveloperDaoImpl();
                        DeveloperService developerService = new DeveloperServiceImpl(developerDao);
                        DeveloperUI developerUI = new DeveloperUI(developerService);
                        developerUI.displayDeveloperPanel("developer_1");
                        flag = false;
                        break;
                    case "TESTER":
                        TesterUI tester = new TesterUI();
                        tester.showTesterUI();
                        flag = false;
                        break;
                    default:
                        System.out.println("Invalid username or password");
                        break;
                }
            } catch (UserNotFoundException e) {
                System.out.println("No such user found!");
                System.out.println("Please re-enter credentials");
            } finally {
                continue;
            }
        }


        sc.close();
    }
}
