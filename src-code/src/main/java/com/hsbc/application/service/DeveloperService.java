package com.hsbc.application.service;

import com.hsbc.application.exceptions.BugNotFoundException;
import com.hsbc.application.exceptions.ProjectNotFoundException;
import com.hsbc.application.exceptions.UserNotFoundException;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;

import java.util.List;
import java.util.Optional;

public interface DeveloperService {

    Optional<List<Bug>> getAssignedBugs() throws BugNotFoundException;

    boolean updateBugStatus(int bugId, String status);

    Optional<Bug> getBugDetails(int bugID) throws BugNotFoundException;

    List<Project> viewAssignedProjects() throws ProjectNotFoundException;
}
