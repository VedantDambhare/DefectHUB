Overview
The TesterDaoImpl module is part of the backend for a Bug Tracking System. It is responsible for managing bugs and retrieving 
project data related to testers. This module interacts with the database to perform CRUD operations and manage data specific 
to bugs and projects assigned to testers.

Functions and Usage
1. getAllBugs()
Description: Retrieves all bugs reported by the currently logged-in user.

Usage:
```
List<Bug> bugs = testerDao.getAllBugs();
for (Bug bug : bugs) {
    // Process or display bug information
}
```
Exceptions:

Handles SQLException internally, printing stack trace if any exception occurs.

2. getAssignedProjects()
Description: Retrieves all projects assigned to the currently logged-in user.

Usage:

```
List<Project> projects = testerDao.getAssignedProjects();
for (Project project : projects) {
    // Process or display project information
}
```
Exceptions:

Handles SQLException internally, printing stack trace if any exception occurs.
3. getAllBugsByProjectId(int projectId)
Description: Retrieves all bugs reported by the currently logged-in user for a specific project.

Usage:

```
try {
    List<Bug> bugs = testerDao.getAllBugsByProjectId(projectId);
    for (Bug bug : bugs) {
        // Process or display bug information
    }
} catch (ProjectNotFoundException e) {
    System.out.println("Project not found: " + e.getMessage());
}
```
Exceptions:

ProjectNotFoundException: Thrown when a project with the specified ID does not exist.
Handles SQLException internally, printing stack trace if any exception occurs.
4. addNewBug(Bug bug)
Description: Adds a new bug to the database.

Usage:

```
boolean success = testerService.addNewBug(bug);
if (success) {
    System.out.println("Bug added successfully.");
} else {
    System.out.println("Failed to add bug.");
}
```
Exceptions:

Handles SQLException internally, printing stack trace if any exception occurs.
Exception Handling
Each function is designed to handle specific exceptions that could arise during the execution of database operations. Proper exception handling ensures that the application behaves predictably and provides useful error messages when issues occur.

Conclusion
The TesterDaoImpl module provides essential functionalities to manage bugs and retrieve project data specific to testers within the Bug Tracking System. By following the usage guidelines and handling exceptions appropriately, developers can effectively interact with the system's backend to perform necessary bug and project management tasks.