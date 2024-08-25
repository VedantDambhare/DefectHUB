ProjectManager.md

# Overview

    The ProjectManager module is part of the Bug Tracking System's backend. It is responsible for managing projects and handling various project-related operations. This module interacts with the database to perform CRUD operations and manage bugs within projects.

##  Functions and Usage

### 1. getBugInfo(int bugID)
**Description:** Fetches detailed information about a specific bug identified by the bugID.

**Usage:**

```java
try {
    Bug bug = adminDAO.getBugInfo(bugID);
    // Use bug object as needed
} catch (BugNotFoundException e) {
    System.out.println("Bug not found: " + e.getMessage());
} catch (DatabaseAccessException e) {
    System.out.println("Database error: " + e.getMessage());
}
```
**Exceptions:**

- BugNotFoundException: Thrown when a bug with the specified ID does not exist.
- DatabaseAccessException: Thrown when there is an issue accessing the database.

### 2. getUserInfo(int userID)

**Description:** Retrieves user information based on the userID.

**Usage:**

```java
try {
    User user = adminDAO.getUserInfo(userID);
    // Use user object as needed
} catch (DatabaseAccessException e) {
    System.out.println("Database error: " + e.getMessage());
}
```
**Exceptions:**

- DatabaseAccessException: Thrown when there is an issue accessing the database.

### 3. createNewProject(Project project)

**Description:** Creates a new project in the system with the details provided in the Project object.

**Usage**:

```java
try {
    boolean success = adminDAO.createNewProject(project);
    if (success) {
        System.out.println("Project created successfully.");
    }
} catch (DuplicateProjectException e) {
    System.out.println("Duplicate project: " + e.getMessage());
} catch (ProjectLimitReachedException e) {
    System.out.println("Project limit reached: " + e.getMessage());
} catch (DatabaseAccessException e) {
    System.out.println("Database error: " + e.getMessage());
}
```

**Exceptions:**

- DuplicateProjectException: Thrown if a project with the same name already exists.
- ProjectLimitReachedException: Thrown if the project manager has reached the maximum number of projects.
- DatabaseAccessException: Thrown when there is an issue accessing the database.

### 4. showProjects(int managerID)

**Description:** Displays all projects managed by a specific project manager identified by managerID.

**Usage:**

```java
try {
    List<Project> projects = adminDAO.showProjects(managerID);
    for (Project project : projects) {
        // Display or use project information
    }
} catch (DatabaseAccessException e) {
    System.out.println("Database error: " + e.getMessage());
}
```

**Exceptions:**

- DatabaseAccessException: Thrown when there is an issue accessing the database.

5. showBugsByFilter(int projectID, String filter)

**Description:** Fetches a list of bugs for a given project based on a specified filter. The filter could be based on bug status, priority, etc.

**Usage:**
```java
try {
    List<Bug> bugs = adminDAO.showBugsByFilter(projectID, filter);
    for (Bug bug : bugs) {
    // Display or use bug information
    }
} catch (DatabaseAccessException e) {
    System.out.println("Database error: " + e.getMessage());
}
```

**Exceptions:**

- DatabaseAccessException: Thrown when there is an issue accessing the database.

### 5. assignBugToDeveloper(int bugID, int developerID)

**Description:** Assigns a specific bug to a developer.

**Usage:**

```java
try {
    boolean success = adminDAO.assignBugToDeveloper(bugID, developerID);
    if (success) {
        System.out.println("Bug assigned to developer successfully.");
    }
} catch (DatabaseAccessException e) {
    System.out.println("Database error: " + e.getMessage());
}
```

**Exceptions:**

- DatabaseAccessException: Thrown when there is an issue accessing the database.

### 6. closeBug(int bugID)

**Description:** Marks a specific bug as closed.

Usage:

```java
try {
    boolean success = adminDAO.closeBug(bugID);
    if (success) {
        System.out.println("Bug closed successfully.");
    }
} catch (DatabaseAccessException e) {
    System.out.println("Database error: " + e.getMessage());
}
```
**Exceptions:**

- DatabaseAccessException: Thrown when there is an issue accessing the database.


## ***Exception Handling***

-- Each function is designed to handle specific exceptions that could arise during the execution of database operations. It is essential to use proper exception handling to ensure the application behaves predictably in the face of errors.

## ***Conclusion***

-- The ProjectManager module provides essential functionalities to manage projects and bugs within the Bug Tracking System. By following the usage guidelines and handling exceptions appropriately, developers can effectively interact with the system's backend to perform necessary project and bug management tasks.
