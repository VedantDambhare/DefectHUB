package com.hsbc.application.dao;

import com.hsbc.application.exceptions.UserNotFoundException;

public interface LoginDao {
    public String authenticate(String username, String password) throws UserNotFoundException;
}
