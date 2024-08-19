package com.hsbc.application.model;

import java.util.Date;
import java.util.Objects;

public class ProjectManager extends User {
    private int totalProjects;

    public ProjectManager(int id, String username, String passwd, String role, Date lastLoggedIn, int totalProjects) {
        super(id, username, passwd, role, lastLoggedIn);
        this.totalProjects = totalProjects;
    }

    // Getters and Setters

    public int getTotalProjects() {
        return totalProjects;
    }

    public void setTotalProjects(int totalProjects) {
        this.totalProjects = totalProjects;
    }

    @Override
    public String toString() {

        return (super.toString()) + "ProjectManager{" +
                "totalProjects=" + totalProjects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectManager that)) return false;
        if (!super.equals(o)) return false;
        return totalProjects == that.totalProjects;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), totalProjects);
    }
}
