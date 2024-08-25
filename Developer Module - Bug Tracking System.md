# Developer Module - Bug Tracking System
## Overview
The Developer Module is a part of the Bug Tracking System that allows developers to manage their assigned bugs, update bug statuses, and view assigned projects. This module is designed to be an interactive console-based user interface (UI) for developers, allowing them to seamlessly navigate through various features essential for bug tracking and project management.

## Features
1. View Assigned Bugs
- Displays a list of bugs assigned to the logged-in developer.
- Provides details such as Bug ID, Title, Description, Status, Priority, Severity, Project ID, Reporter ID, Assignee ID, and timestamps for creation and updates.
2. Update Bug Status
- Allows the developer to update the status of a specific bug.
- Status options include "In Progress" and "Resolved".
3. Get Bug Details
- Fetches and displays detailed information about a specific bug based on the Bug ID.
4. View Assigned Projects
- Displays information about projects assigned to the developer.
- Provides project details including Project ID, Project Name, Start Date, Status, Project Manager, and timestamps for creation and updates.

## DeveloperUI Class
- displayDeveloperPanel(String developerUsername): Main method that displays the developer panel and handles user inputs for different functionalities.
- viewAssignedBugs(): Retrieves and displays bugs assigned to the developer.
- updateBugStatus(): Allows updating the status of a bug.
- viewBugDetails(): Fetches and displays details of a bug.
- viewAssignedProject(): Retrieves and displays details of assigned projects.

# How to Use
## Prerequisites
- Ensure you have the necessary database configuration in DBConfig.java.
- Database tables such as Users, Bugs, Projects, and ProjectTeamMembers should be properly set up.
## Running the Application

- Instantiate the DeveloperService and DeveloperUI classes in your main method or entry point of the application.
- Call the displayDeveloperPanel(String developerUsername) method with the developer's username to start the developer console UI.

## Navigating the Developer Panel
- Use the numeric options displayed in the developer panel to navigate through the different functionalities.
- View, update, and manage bugs and projects assigned to the developer.

# Logging and Error Handling
- The application uses SLF4J for logging errors and other information.
- Custom exceptions BugNotFoundException and ProjectNotFoundException are used to handle cases where bugs or projects are not found in the database.

