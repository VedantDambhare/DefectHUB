package com.hsbc.application.service;

import com.hsbc.application.exceptions.UserNotFoundException;

public interface LoginService {
    public String authenticate(String username, String password) throws UserNotFoundException;
}
