/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.gui.access;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Backing bean for the login page. It allows the user to login to the
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
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed", "Login failed"));
            logger.log(Level.SEVERE,"Login Failed");
            return null;
        }
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
