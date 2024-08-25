package com.hsbc.application.test;

import com.hsbc.application.UI.ProjectManagerUI;
import com.hsbc.application.daoimpl.AdminDAOImpl;
import com.hsbc.application.daoimpl.ProjectDAOImpl;
import com.hsbc.application.service.AdminService;
import com.hsbc.application.service.AdminServiceImpl;
import com.hsbc.application.service.ProjectService;
import com.hsbc.application.service.ProjectServiceImpl;

public class Driver {
    public static void main(String[] args) {

        AdminDAOImpl adminDAO = new AdminDAOImpl();
        AdminService adminService = new AdminServiceImpl(adminDAO);

        ProjectDAOImpl projectDAO = new ProjectDAOImpl();
        ProjectService projectService = new ProjectServiceImpl(projectDAO);
        ProjectManagerUI adminUI = new ProjectManagerUI(adminService,projectService);

        adminUI.start();
    }
}
