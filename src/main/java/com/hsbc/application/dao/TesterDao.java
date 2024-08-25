package com.hsbc.application.dao;

import com.hsbc.application.exceptions.ProjectNotFoundException;
import com.hsbc.application.exceptions.UserNotFoundException;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;

import java.util.List;

public interface TesterDao {
    public List<Bug> getAllBugs();
    public List<Project> getAssignedProjects();
    public List<Bug> getAllBugsByProjectId(int projectId) throws ProjectNotFoundException;
    public boolean addNewBug(Bug bug);

}
