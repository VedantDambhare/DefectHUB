package com.hsbc.application.config;

public class CurrentSession {
    private static int userId;
    public static void setUserId(int id) {
        System.out.println("Setting Session user id: " + id);
        userId = id;
    }
    public static int getUserId() {
        return userId;
    }
}
