/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.manager;

import it.polimi.se2.meteocal.entity.Group;
import it.polimi.se2.meteocal.entity.User;
import java.security.Principal;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EJB managing the users.
 *
 * @author edo
 */
@Stateless
public class UserManager {

    @PersistenceContext
    EntityManager em;

    @Inject
    Principal principal;

    public void saveUser(User user) {
        user.setGroupName(Group.USERS);
        em.persist(user);
    }

    public User getLoggerUser() {
        return em.find(User.class, principal.getName());
    }

}
