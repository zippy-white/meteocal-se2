/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.gui.access;

import it.polimi.se2.meteocal.entity.User;
import it.polimi.se2.meteocal.manager.UserManager;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

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
    
    @Inject
    private LoginBean lb;
    
    //Needed for the automatic login after registration
    private String username;
    private String password;
    
    private User user;

    public RegistrationBean() {
    }
    
    /**
     * Registers the new user and logs it in
     * @return the user's personal page if the registration is successful
     */
    public String register() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (um.findUserByName(username.toLowerCase()) != null) {
            context.addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "This username is already taken. Usernames are case insensitive.", "Registration failed."));
            return null;
        }
        getUser();
        user.setUsername(username);
        user.setPassword(password);
        um.saveUser(user);
        return lb.login(this.username, this.password);
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
