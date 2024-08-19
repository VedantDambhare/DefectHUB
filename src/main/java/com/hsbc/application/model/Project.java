package com.hsbc.application.model;


import java.util.Date;
import java.util.Objects;

public class Project {
    private int projectId;
    private String projectName;
    private String startDate;
    private String status;
    private ProjectManager projectManager;
    private Date createdAt;
    private Date updatedAt;

    public Project(int projectId, String projectName, String startDate, String status, ProjectManager projectManager, Date createdAt, Date updatedAt) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.startDate = startDate;
        this.status = status;
        this.projectManager = projectManager;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(ProjectManager projectManager) {
        this.projectManager = projectManager;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project project)) return false;
        return projectId == project.projectId && Objects.equals(projectName, project.projectName) && Objects.equals(startDate, project.startDate) && Objects.equals(status, project.status) && Objects.equals(projectManager, project.projectManager) && Objects.equals(createdAt, project.createdAt) && Objects.equals(updatedAt, project.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName, startDate, status, projectManager, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", status='" + status + '\'' +
                ", projectManager=" + projectManager +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}