package com.hsbc.application.service;

import com.hsbc.application.dao.AdminDAO;
import com.hsbc.application.exceptions.*;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;
import com.hsbc.application.model.User;

public class AdminServiceImpl implements AdminService {
    private final AdminDAO adminDAO;

    public AdminServiceImpl(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    @Override
    public Bug getBugInfo(int bugID) throws BugNotFoundException, DatabaseAccessException {
        return adminDAO.getBugInfo(bugID);
    }

    @Override
    public User getUserId(int userID) throws UserNotFoundException, DatabaseAccessException {
            User user = adminDAO.getUserId(userID);
            if (user == null) {
                throw new UserNotFoundException("User with ID " + userID + " not found.");
            }
            return user;
    }

    @Override
    public boolean createNewProject(Project project) throws DuplicateProjectException, ProjectLimitReachedException, DatabaseAccessException {
        System.out.println("In AdminServiceImpl for Create New Project");
        return adminDAO.createNewProject(project);
    }
}

