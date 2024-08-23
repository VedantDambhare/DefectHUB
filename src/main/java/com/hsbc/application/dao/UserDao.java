package com.hsbc.application.dao;

import java.util.Optional;

public interface UserDao {

    public void registerUser(String userName, String password, String role);
    public Optional<String> loginUser(String userName, String password);

}
