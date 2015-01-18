/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.gui.access;

import it.polimi.se2.meteocal.entity.User;
import it.polimi.se2.meteocal.manager.UserManager;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 * JSF managed bean for the registration page.
 *
 * @author edo
 */
@Named(value = "registrationBean")
@RequestScoped
public class RegistrationBean {

    @EJB
    private UserManager um;
    
    private User user;

    public RegistrationBean() {
    }
    
    public String register() {
        um.saveUser(user);
        return "/personalPages/personalPage";
    }

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
