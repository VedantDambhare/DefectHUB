package com.hsbc.application;

import com.hsbc.application.config.CurrentSession;
import com.hsbc.application.dao.DeveloperDao;
import com.hsbc.application.daoimpl.DeveloperDaoImpl;
import com.hsbc.application.exceptions.BugNotFoundException;
import com.hsbc.application.exceptions.ProjectNotFoundException;
import com.hsbc.application.model.Bug;
import com.hsbc.application.model.Project;
import com.hsbc.application.service.DeveloperServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestDeveloper {

    private DeveloperServiceImpl developerService;

    @BeforeEach
    public void setUp() {
        DeveloperDao developerDao = new DeveloperDaoImpl(); // Replace with the actual implementation
        developerService = new DeveloperServiceImpl(developerDao);
    }

    @Test
    void testGetAssignedBugs_Success() throws BugNotFoundException {

        CurrentSession.setUserId(1);

        Optional<List<Bug>> bugList = developerService.getAssignedBugs();

        assertNotNull(bugList);
//        assertFalse(bugList.isEmpty());
//        assertEquals(1, bugList.get().get(0).getBugID());
    }

    @Test
    void testGetAssignedBugs_ThrowsException() {
        CurrentSession.setUserId(-1);

        assertThrows(BugNotFoundException.class, () -> developerService.getAssignedBugs()
                .orElseThrow(() -> new BugNotFoundException("Bug not found")));
    }

    @Test
    void testUpdateBugStatus_Success() {
        int bugId = 1;
        String status = "RESOLVED";

        boolean result = developerService.updateBugStatus(bugId, status);

        assertTrue(result);
    }

    @Test
    void testGetBugDetails_Success() throws BugNotFoundException {
        int bugID = 1;

        Bug bug = developerService.getBugDetails(bugID)
                .orElseThrow(() -> new BugNotFoundException("Bug not found"));

        assertNotNull(bug);
        assertEquals(bugID, bug.getBugID());
        assertEquals("Login Issue", bug.getTitle());
    }

    @Test
    void testGetBugDetails_ThrowsException() {
        int bugID = 102;

        assertThrows(BugNotFoundException.class, () -> developerService.getBugDetails(bugID)
                .orElseThrow(() -> new BugNotFoundException("Bug not found")));
    }

}
