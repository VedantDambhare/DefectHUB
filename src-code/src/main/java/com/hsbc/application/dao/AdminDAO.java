package com.hsbc.application.dao;

import java.sql.SQLException;
import java.util.List;

import com.hsbc.application.exceptions.*;
import com.hsbc.application.model.*;

public interface AdminDAO {
    Bug getBugInfo(int bugID) throws BugNotFoundException, DatabaseAccessException;
    User getUserInfo(int userID) throws UserNotFoundException, DatabaseAccessException;
    boolean createNewProject(Project project) throws DuplicateProjectException, ProjectLimitReachedException, DatabaseAccessException;
    List<Project> showProjects(int managerID) throws ProjectNotFoundException, DatabaseAccessException;
    List<Bug> showAllBugs() throws DatabaseAccessException;
    List<Bug> showBugsByFilter(int projectID, String filter, String value) throws BugNotFoundException, DatabaseAccessException, UserNotFoundException, SQLException;


    boolean assignBugToDeveloper(int bugID, int developerID) throws BugNotFoundException, UserNotFoundException, DatabaseAccessException;
    boolean closeBug(int bugID, String uname, String upass) throws BugNotFoundException, DatabaseAccessException;
}
