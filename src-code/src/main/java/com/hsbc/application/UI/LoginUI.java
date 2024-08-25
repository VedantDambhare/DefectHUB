package com.hsbc.application.UI;

import com.hsbc.application.config.CurrentSession;
import com.hsbc.application.UI.TesterUI;
import com.hsbc.application.dao.AdminDAO;
import com.hsbc.application.dao.LoginDao;
//import com.hsbc.application.daoimpl.AdminDAOImpl;
import com.hsbc.application.dao.TesterDao;
import com.hsbc.application.daoimpl.*;
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

            LoginDao loginDaoImpl = new LoginDaoImpl();
            LoginService login = new LoginServiceImpl(loginDaoImpl);


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
                        break;
                    case "DEVELOPER":
                        DeveloperDaoImpl developerDao = new DeveloperDaoImpl();
                        DeveloperService developerService = new DeveloperServiceImpl(developerDao);
                        DeveloperUI developerUI = new DeveloperUI(developerService);
                        developerUI.displayDeveloperPanel("developer_1");
                        break;
                    case "TESTER":
                        TesterDao testerDao = new TesterDaoImpl();
                        TesterService tester = new TesterServiceImpl(testerDao);
                        TesterUI testerUI = new TesterUI(tester);
                        testerUI.showTesterUI();
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
