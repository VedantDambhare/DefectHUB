package com.hsbc.application.dao;

import com.hsbc.application.model.ProjectManager;

import java.sql.SQLException;
import java.util.List;

public interface ProjectDao {
    List<ProjectManager> getAllProjectManagers() throws SQLException;
}
