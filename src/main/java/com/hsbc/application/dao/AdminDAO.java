package com.hsbc.application.dao;

import java.util.List;

import com.hsbc.application.exceptions.*;
import com.hsbc.application.model.*;

public interface AdminDAO {
    Bug getBugInfo(int bugID) throws BugNotFoundException, DatabaseAccessException;
    User getUserId(int userID) throws UserNotFoundException, DatabaseAccessException;
    boolean createNewProject(Project project) throws DuplicateProjectException, ProjectLimitReachedException, DatabaseAccessException;

    //TO BE IMPLEMENTED
    List<Project> showProjects(int managerID) throws ProjectNotFoundException, DatabaseAccessException;
    List<Bug> showBugsByFilter(int projectID, String filter) throws BugNotFoundException, DatabaseAccessException;
    boolean assignBugToDeveloper(int bugID, int developerID) throws BugNotFoundException, UserNotFoundException, DatabaseAccessException;
    boolean closeBug(int bugID) throws BugNotFoundException, DatabaseAccessException;
}
