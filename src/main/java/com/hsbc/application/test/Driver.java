package com.hsbc.application.test;

import com.hsbc.application.UI.AppUI;
import com.hsbc.application.config.DBConfig;
import com.hsbc.application.daoimpl.AdminDAOImpl;
import com.hsbc.application.daoimpl.ProjectDAOImpl;
import com.hsbc.application.service.AdminService;
import com.hsbc.application.service.AdminServiceImpl;
import com.hsbc.application.service.ProjectService;
import com.hsbc.application.service.ProjectServiceImpl;

import java.sql.Connection;

public class Driver {
    public static void main(String[] args) {
        Connection connection = DBConfig.getConnection();
        AdminDAOImpl adminDAO = new AdminDAOImpl();
        AdminService adminService = new AdminServiceImpl(adminDAO);

        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        ProjectService projectService = new ProjectServiceImpl(projectDAO);
        AppUI adminUI = new AppUI(adminService,projectService);

        adminUI.start();
    }
}
