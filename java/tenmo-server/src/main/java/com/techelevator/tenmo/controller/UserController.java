package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

//@PreAuthorize("isAuthenticated()")
@RestController
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(path = "/users", method = RequestMethod.GET) // get all the users
    public List<User> listAllUsers(){
        return userDAO.findAll();
    }

}
