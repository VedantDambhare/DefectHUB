package com.hsbc.application.service;

import com.hsbc.application.dao.AdminDAO;
import com.hsbc.application.exceptions.*;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;
import com.hsbc.application.model.User;

import java.util.List;

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
    public User getUserInfo(int userID) throws UserNotFoundException, DatabaseAccessException {
            User user = adminDAO.getUserInfo(userID);
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

    @Override
    public List<Project> showProjects(int managerID) throws ProjectNotFoundException, DatabaseAccessException {
        System.out.println("In AdminServiceImpl for Show Projects");
        //System.out.println(adminDAO.showProjects(managerID));
        return adminDAO.showProjects(managerID);
    }

    @Override
    public List<Bug> showAllBugs() throws DatabaseAccessException {
        return adminDAO.showAllBugs();
    }

    @Override
    public List<Bug> showBugsByFilter(int projectID, String filter, String value) throws DatabaseAccessException,BugNotFoundException{
        return adminDAO.showBugsByFilter(projectID, filter,value);
    }


}

