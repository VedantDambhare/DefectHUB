package com.hsbc.application.service;

import com.hsbc.application.exceptions.*;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;
import com.hsbc.application.model.User;

public interface AdminService {
    Bug getBugInfo(int bugID) throws BugNotFoundException, DatabaseAccessException;
    User getUserId(int userID) throws UserNotFoundException, DatabaseAccessException;
    boolean createNewProject(Project project) throws DuplicateProjectException, ProjectLimitReachedException, DatabaseAccessException;

}