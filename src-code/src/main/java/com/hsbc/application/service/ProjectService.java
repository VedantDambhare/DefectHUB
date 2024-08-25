package com.hsbc.application.service;

import com.hsbc.application.model.ProjectManager;

import java.sql.SQLException;
import java.util.List;

public interface ProjectService {

    List<ProjectManager> getAllProjectManagers() throws SQLException;
}
