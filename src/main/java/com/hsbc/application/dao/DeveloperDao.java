package com.hsbc.application.dao;

import com.hsbc.application.exceptions.BugNotFoundException;
import com.hsbc.application.exceptions.ProjectNotFoundException;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;

import java.util.List;
import java.util.Optional;

public interface DeveloperDao {

    Optional<List<Bug>> getAssignedBugs(int developerId) throws BugNotFoundException;
    boolean bugStatus(int bugId, String status);
    Optional<Bug> getBugDetails(int bugID) throws BugNotFoundException;
    List<Project> viewAssignedProjects(int developerID) throws ProjectNotFoundException;

}