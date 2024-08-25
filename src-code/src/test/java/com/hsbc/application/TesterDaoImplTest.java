package com.hsbc.application;

import com.hsbc.application.config.CurrentSession;
import com.hsbc.application.dao.DeveloperDao;
import com.hsbc.application.dao.TesterDao;
import com.hsbc.application.daoimpl.DeveloperDaoImpl;
import com.hsbc.application.daoimpl.TesterDaoImpl;
import com.hsbc.application.exceptions.BugNotFoundException;
import com.hsbc.application.exceptions.ProjectNotFoundException;
import com.hsbc.application.model.Bug;
import com.hsbc.application.service.DeveloperServiceImpl;
import com.hsbc.application.service.TesterService;
import com.hsbc.application.service.TesterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TesterDaoImplTest {
    private TesterService testerService;

    @BeforeEach
    public void setUp() {
        TesterDao testerDao = new TesterDaoImpl(); // Replace with the actual implementation
        testerService = new TesterServiceImpl(testerDao);
    }

    @Test
    void testGetAssignedBugs_Success() throws BugNotFoundException {

        CurrentSession.setUserId(7);

        assertNotNull(testerService.getAllBugs());
//        assertFalse(bugList.isEmpty());
//        assertEquals(1, bugList.get().get(0).getBugID());
    }

    @Test
    void testGetAssignedBugs_ThrowsException() {
        CurrentSession.setUserId(-1);

        assertThrows(BugNotFoundException.class, () -> testerService.getAllBugs());
    }


    @Test
    void testGetAllBugs_Success() throws BugNotFoundException {

        List<Bug> bug = testerService.getAllBugs();

        assertNotNull(bug);
    }

    @Test
    void testGetBugDetails_ThrowsException() throws ProjectNotFoundException {
        int bugID = 102;

        assertThrows(ProjectNotFoundException.class, () -> testerService.getAllBugsByProjectId(-1));
    }

    @Test
    void getAssignedProjects_Success()  {
        CurrentSession.setUserId(7);

        assertNotNull(testerService.getAssignedProjects());
    }

    @Test
    void getAssignedProjects_Exception()  {
        CurrentSession.setUserId(-1);

        assertNull(testerService.getAssignedProjects());
    }

}
