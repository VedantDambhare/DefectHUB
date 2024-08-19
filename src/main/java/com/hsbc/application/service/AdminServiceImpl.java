package com.hsbc.application.service;

import com.hsbc.application.dao.AdminDAO;
import com.hsbc.application.exceptions.BugNotFoundException;
import com.hsbc.application.exceptions.DatabaseAccessException;
import com.hsbc.application.model.Bug;

public class AdminServiceImpl implements AdminService {
    private final AdminDAO adminDAO;

    public AdminServiceImpl(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    @Override
    public Bug getBugInfo(int bugID) throws BugNotFoundException, DatabaseAccessException {
        return adminDAO.getBugInfo(bugID);
    }
}