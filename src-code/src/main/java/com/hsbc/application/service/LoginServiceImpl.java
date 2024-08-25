package com.hsbc.application.service;

import com.hsbc.application.dao.LoginDao;
import com.hsbc.application.exceptions.UserNotFoundException;

public class LoginServiceImpl implements  LoginService{

    private final LoginDao loginDao;

    public LoginServiceImpl(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    @Override
    public String authenticate(String username, String password) throws UserNotFoundException {
        return loginDao.authenticate(username, password);
    }
}
