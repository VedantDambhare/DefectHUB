package com.hsbc.application.dao;

import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;

import java.util.Optional;

public interface DeveloperDao {
    Optional<Bug> getAssignedBugs(int developerId, int projectId);

    boolean bugStatus(int bugId, String status);

    Optional<Bug> getBugDetails(int bugID);

    Optional<Project> viewAssignedProjects(int developerID);

}