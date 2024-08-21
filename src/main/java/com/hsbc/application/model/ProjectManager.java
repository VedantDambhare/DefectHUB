package com.hsbc.application.model;

import java.util.Date;
import java.util.Objects;

public class ProjectManager extends User {
    private static int totalProjects;

    public ProjectManager(int id, String username, String passwd, String role, Date lastLoggedIn) {
        super(id, username, passwd, role, lastLoggedIn);
        totalProjects += 1;
    }

    // Getters and Setters

    public int getTotalProjects() {
        return totalProjects;
    }

    public void setTotalProjects(int totalProjects) {
        totalProjects += 1;
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
