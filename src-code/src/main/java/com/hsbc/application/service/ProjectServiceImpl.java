package com.hsbc.application.service;

import com.hsbc.application.dao.ProjectDao;
import com.hsbc.application.model.ProjectManager;

import java.sql.SQLException;
import java.util.List;

public class ProjectServiceImpl implements ProjectService {
    ProjectDao projectDao;

    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public List<ProjectManager> getAllProjectManagers() throws SQLException {
        return projectDao.getAllProjectManagers();
    }
}
