/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.gui.access;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * JSF managed bean for the login page. It allows the user to login to the
 * application given a valid combination of username and a password.
 *
 * @author edo
 */
@Named(value = "loginBean")
@RequestScoped
public class LoginBean {

    private static final Logger logger = Logger.getLogger("loginBean");

    private String username;
    private String password;

    public LoginBean() {
    }

    /**
     * Performs the login operation.
     *
     * @return the personal page if the user logged in successfully, null
     * otherwise
     */
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.login(this.username, this.password);
            return "/personalPages/personalPage";
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed.\nAnother user may still be logged in.", "Login failed."));
            logger.log(Level.SEVERE, "Login Failed");
            return null;
        }
    }

    /**
     * Authenticates the user given a username and a password
     *
     * @param username: the user's username
     * @param password : the user's password
     * @return JSF outcome
     *
     */
    public String login(String username, String password) {
        setUsername(username);
        setPassword(password);
        return login();
    }

    /**
     * Deauthenticates the currently logged in user.
     *
     * @return JSF outcome
     */
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        logger.log(Level.INFO, "User Logged out");
        return "/index?faces-redirect=true";
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
