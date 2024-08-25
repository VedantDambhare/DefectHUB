package com.hsbc.application.service;

import com.hsbc.application.dao.DeveloperDao;
import com.hsbc.application.exceptions.BugNotFoundException;
import com.hsbc.application.exceptions.ProjectNotFoundException;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;

import java.util.List;
import java.util.Optional;

public class DeveloperServiceImpl implements DeveloperService {

    private final DeveloperDao developerDao;

    public DeveloperServiceImpl(DeveloperDao developerDao) {
        this.developerDao = developerDao;
    }

    @Override
    public Optional<List<Bug>> getAssignedBugs(int developerId) throws BugNotFoundException {
        return developerDao.getAssignedBugs(developerId);
    }

    @Override
    public boolean updateBugStatus(int bugId, String status) {
        return developerDao.bugStatus(bugId, status);
    }

    @Override
    public Optional<Bug> getBugDetails(int bugID) throws BugNotFoundException {
        return developerDao.getBugDetails(bugID);
    }

    @Override
    public List<Project> viewAssignedProjects() throws ProjectNotFoundException {
        return developerDao.viewAssignedProjects();
    }
}
