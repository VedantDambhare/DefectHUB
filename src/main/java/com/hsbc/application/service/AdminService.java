package com.hsbc.application.service;

import com.hsbc.application.exceptions.BugNotFoundException;
import com.hsbc.application.exceptions.DatabaseAccessException;
import com.hsbc.application.model.Bug;

public interface AdminService {
    Bug getBugInfo(int bugID) throws BugNotFoundException, DatabaseAccessException;
}