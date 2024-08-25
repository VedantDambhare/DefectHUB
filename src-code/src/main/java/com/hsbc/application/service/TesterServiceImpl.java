package com.hsbc.application.service;

import com.hsbc.application.dao.TesterDao;
import com.hsbc.application.daoimpl.TesterDaoImpl;
import com.hsbc.application.exceptions.ProjectNotFoundException;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;

import java.util.List;

public class TesterServiceImpl implements TesterService {

    private final TesterDao testerDao;

    public TesterServiceImpl(TesterDao testerDao) {
        this.testerDao = testerDao;
    }
    @Override
    public List<Bug> getAllBugs() {
        return testerDao.getAllBugs();
    }

    @Override
    public List<Project> getAssignedProjects() {
        return testerDao.getAssignedProjects();
    }

    @Override
    public List<Bug> getAllBugsByProjectId(int projectId) throws ProjectNotFoundException {
        return testerDao.getAllBugsByProjectId(projectId);
    }

    @Override
    public boolean addNewBug(Bug bug) {
        return testerDao.addNewBug(bug);
    }
}
