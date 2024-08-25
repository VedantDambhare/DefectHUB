package com.hsbc.application.model;

import java.util.Date;
import java.util.Objects;

public class User {
    private int id;
    private String username;
    private String passwd;
    private String role;
    private Date lastLoggedIn;

    public User() {
    }

    public User(int id, String username, String passwd, String role, Date lastLoggedIn) {
        this.id = id;
        this.username = username;
        this.passwd = passwd;
        this.role = role;
        this.lastLoggedIn = lastLoggedIn;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id == user.id && Objects.equals(username, user.username) && Objects.equals(passwd, user.passwd) && Objects.equals(role, user.role) && Objects.equals(lastLoggedIn, user.lastLoggedIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, passwd, role, lastLoggedIn);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwd='" + passwd + '\'' +
                ", role='" + role + '\'' +
                ", lastLoggedIn=" + lastLoggedIn +
                '}';
    }


}