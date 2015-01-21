/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.manager;

import it.polimi.se2.meteocal.entity.Group;
import it.polimi.se2.meteocal.entity.User;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
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

    @Resource
    private SessionContext sctx;

    public void saveUser(User user) {
        user.setGroupName(Group.USERS);
        em.persist(user);
    }

    public User getLoggedUser() {
        User u = em.createNamedQuery(User.findByUsername, User.class)
                .setParameter("username", getLoggedUserName())
                .getSingleResult();
        return u;
    }

    public String getLoggedUserName() {
        return sctx.getCallerPrincipal().getName();
    }

}
