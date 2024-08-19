package com.hsbc.application.model;

import java.util.Date;
import java.util.Objects;

public class Bug {
    private int bugID;
    private String title;
    private String desc;
    private String status;
    private String priority;
    private String severity;
    private Project project;
    private Tester reporter;
    private Developer assignee;
    private Date createdAt;
    private Date updatedAt;

    public Bug(int bugID, String title, String desc, String status, String priority, String severity, Project project, Tester reporter, Developer assignee, Date createdAt, Date updatedAt) {
        this.bugID = bugID;
        this.title = title;
        this.desc = desc;
        this.status = status;
        this.priority = priority;
        this.severity = severity;
        this.project = project;
        this.reporter = reporter;
        this.assignee = assignee;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Bug() {

    }

    // Getters and Setters

    public int getBugID() {
        return bugID;
    }

    public void setBugID(int bugID) {
        this.bugID = bugID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Tester getReporter() {
        return reporter;
    }

    public void setReporter(Tester reporter) {
        this.reporter = reporter;
    }

    public Developer getAssignee() {
        return assignee;
    }

    public void setAssignee(Developer assignee) {
        this.assignee = assignee;
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
        if (!(o instanceof Bug bug)) return false;
        return bugID == bug.bugID && Objects.equals(title, bug.title) && Objects.equals(desc, bug.desc) && Objects.equals(status, bug.status) && Objects.equals(priority, bug.priority) && Objects.equals(severity, bug.severity) && Objects.equals(project, bug.project) && Objects.equals(reporter, bug.reporter) && Objects.equals(assignee, bug.assignee) && Objects.equals(createdAt, bug.createdAt) && Objects.equals(updatedAt, bug.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bugID, title, desc, status, priority, severity, project, reporter, assignee, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Bug{" +
                "bugID=" + bugID +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", severity='" + severity + '\'' +
                ", project=" + project +
                ", reporter=" + reporter +
                ", assignee=" + assignee +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}