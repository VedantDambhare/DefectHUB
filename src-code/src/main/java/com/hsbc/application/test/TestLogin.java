package com.hsbc.application.test;

import com.hsbc.application.daoimpl.LoginDaoImpl;
import com.hsbc.application.dao.LoginDao;
import com.hsbc.application.exceptions.UserNotFoundException;

public class TestLogin {

    public static void main(String[] args) {

        LoginDao loginDao = new LoginDaoImpl();

        String s = null;
        try {
            s = loginDao.authenticate("project_manager_2","passwrd");
            System.out.println("Welcome "+"You have logged in as a "+s);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
