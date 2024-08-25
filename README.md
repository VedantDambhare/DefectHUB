# DefectHUB

# Bug Tracking System

## Overview

The Bug Tracking System is a console-based application designed to manage projects, track bugs, and assign them to developers for resolution. It is built using Java, following J2EE standards, and utilizes a MySQL database for data persistence.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Database Schema](#database-schema)
- [Project Structure](#project-structure)
- [Setup and Installation](#setup-and-installation)
- [Usage](#usage)
- [Admin Functionalities](#admin-functionalities)
- [Developer and Tester Functionalities](#developer-and-tester-functionalities)
- [Error Handling](#error-handling)

## Features

1. **Admin (Project Manager) Functionalities:**
    - View user information.
    - View All Users
    - View Bug Information
    - View Bug Details
    - View Bug By Filter(severity/priority/status)
    - Create new projects.
    - View and manage projects.
    - Assign bugs to developers.
    - Close bugs.

2. **Developer Functionalities:**
    - View assigned bugs.
    - Update bug statuses.
    - View bug details.
   
3. **Tester Functionalities:**
    - Raise new bugs.
    - View the status of reported bugs.
    - View bug details.
    - View Assigned Projects

## Technologies Used

- **Java**: Core application logic.
- **J2EE**: Application standards and patterns.
- **MySQL**: Relational database management system.
- **AWS RDS**: Cloud-based database hosting.
- **JUnit**: Testing framework for unit tests.
- **Maven**: Build and dependency management tool.

## Database Schema

### Tables

1. **Users**: Stores user information.
    - `userId` (Primary Key)
    - `userName` (Unique)
    - `hashedPassword`
    - `role` (ENUM: PROJECT_MANAGER, DEVELOPER, TESTER)
    - `last_logged_in`

2. **Projects**: Stores project details.
    - `projectId` (Primary Key)
    - `projectName`
    - `startDate`
    - `status`
    - `projectManagerId` (Foreign Key from Users table)
    - `created_at`
    - `updated_at`

3. **Bugs**: Stores bug details.
    - `bugID` (Primary Key)
    - `title`
    - `description`
    - `status`
    - `priority`
    - `severity`
    - `projectId` (Foreign Key from Projects table)
    - `reporterId` (Foreign Key from Users table)
    - `assigneeId` (Foreign Key from Users table)
    - `created_at`
    - `updated_at`

To run the project, run the Login UI. java found at src/main/java/com/hsbc/application/UI/LoginUI.java

**Project Manager Credentials:** 
Username: project_manager_1
Password: hashed_password_1

**Developer Credentials:**
Username: developer_1
Password: hashed_password_2

**Tester Credentials:**
Username: tester_1
Password: hashed_password_4

### Contact us at: harshalsonawane127@gmail.com in case of any queries
