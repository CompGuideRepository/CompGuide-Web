/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compguide.web.Service;

import com.compguide.web.Persistence.SessionBeans.UserFacade;
import com.compguide.web.Persistence.Entities.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author António
 */
@ManagedBean(name = "userService")
@ApplicationScoped
public class UserService {

    private List<User> users;
    @EJB
    private com.compguide.web.Persistence.SessionBeans.UserFacade ejbFacade;

    @PostConstruct
    public void init() {
        users = new ArrayList<User>();
        users = getFacade().findAll();
    }

    public List<User> getUsers() {
        return users;
    }

    private UserFacade getFacade() {
        if (ejbFacade == null) {
            ejbFacade = new UserFacade();
        }
        return ejbFacade;
    }

}
