package com.hsbc.application.model;

import java.util.Date;
import java.util.Objects;

public class Bug {
    private String title;
    private String desc;
    private String status;
    private String priority;
    private String severity;
    private int projectId;
    private int reporterId;
    private int assigneeId;
    private Date createdAt;
    private Date updatedAt;

    public Bug() {
    }

    public Bug(String title, String desc, String status, String priority, String severity, int projectId, int reporterId, int assigneeId, Date createdAt, Date updatedAt) {
        this.title = title;
        this.desc = desc;
        this.status = status;
        this.priority = priority;
        this.severity = severity;
        this.projectId = projectId;
        this.reporterId = reporterId;
        this.assigneeId = assigneeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getReporterId() {
        return reporterId;
    }

    public void setReporterId(int reporterId) {
        this.reporterId = reporterId;
    }

    public int getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(int assigneeId) {
        this.assigneeId = assigneeId;
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
    public String toString() {
        return "Bug{" +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", severity='" + severity + '\'' +
                ", projectId=" + projectId +
                ", reporterId=" + reporterId +
                ", assigneeId=" + assigneeId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bug bug)) return false;
        return projectId == bug.projectId && reporterId == bug.reporterId && assigneeId == bug.assigneeId && Objects.equals(title, bug.title) && Objects.equals(desc, bug.desc) && Objects.equals(status, bug.status) && Objects.equals(priority, bug.priority) && Objects.equals(severity, bug.severity) && Objects.equals(createdAt, bug.createdAt) && Objects.equals(updatedAt, bug.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, desc, status, priority, severity, projectId, reporterId, assigneeId, createdAt, updatedAt);
    }
}