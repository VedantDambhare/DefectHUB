package com.hsbc.application.UI;

import com.hsbc.application.config.CurrentSession;
import com.hsbc.application.UI.TesterUI;
import com.hsbc.application.dao.AdminDAO;
import com.hsbc.application.dao.LoginDao;
//import com.hsbc.application.daoimpl.AdminDAOImpl;
import com.hsbc.application.daoimpl.LoginDaoImpl;
import com.hsbc.application.exceptions.UserNotFoundException;

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
                        System.out.println("Redirecting to PROJECT_MANAGER page...");
                        flag = false;
//                        AdminUI adminUi = new AdminUI();
                        break;
                    case "DEVELOPER":
                        System.out.println("Redirecting to DEVELOPER page...");
                        flag = false;
                        break;
                    case "TESTER":
                        System.out.println("Logging in as Tester");
                        TesterUI tester = new TesterUI();
                        System.out.println("Redirecting to TESTER page...");
                        tester.showTesterUI();
                        System.out.println("Exiting from TESTER page...");
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
