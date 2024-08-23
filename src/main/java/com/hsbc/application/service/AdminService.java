package com.hsbc.application.service;

import com.hsbc.application.exceptions.*;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;
import com.hsbc.application.model.User;

import java.util.List;

public interface AdminService {
    Bug getBugInfo(int bugID) throws BugNotFoundException, DatabaseAccessException;
    User getUserInfo(int userID) throws UserNotFoundException, DatabaseAccessException;
    boolean createNewProject(Project project) throws DuplicateProjectException, ProjectLimitReachedException, DatabaseAccessException;
    List<Project> showProjects(int managerID) throws ProjectNotFoundException, DatabaseAccessException;
    List<Bug> showAllBugs() throws DatabaseAccessException;
    List<Bug> showBugsByFilter(int projectID, String filter, String value) throws DatabaseAccessException, BugNotFoundException;
}