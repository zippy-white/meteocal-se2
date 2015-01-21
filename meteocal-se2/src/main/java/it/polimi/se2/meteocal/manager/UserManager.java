/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.manager;

import it.polimi.se2.meteocal.entity.Event;
import it.polimi.se2.meteocal.entity.Group;
import it.polimi.se2.meteocal.entity.User;
import it.polimi.se2.meteocal.utilities.DateHelper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.ScheduleEvent;

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

    /**
     * Save a user in the DB
     * @param user the new user to add to the DB
     */
    public void saveUser(User user) {
        user.setGroupName(Group.USERS);
        em.persist(user);
    }

    /**
     *
     * @return the logged User object
     */
    public User getLoggedUser() {
        User u = em.createNamedQuery(User.findByUsername, User.class)
                .setParameter("username", getLoggedUserName())
                .getSingleResult();
        return u;
    }

    /**
     *
     * @return the logged user's username
     */
    public String getLoggedUserName() {
        return sctx.getCallerPrincipal().getName();
    }

    /**
     *
     * @return the scheduled events that the user will be attending ready to be used by primefaces schedule
     */
    public List<ScheduleEvent> getScheduledEvents() {
        List<ScheduleEvent> eventList = new ArrayList<>();
        User u = getLoggedUser();
        ScheduleEvent scheduleEvent;
        //For each event that the user will be attending we build the corrisponding schedule event for display
        for (Event e : u.getAttendingEvents()) {
            scheduleEvent = new DefaultScheduleEvent(e.getName(),
                    DateHelper.buildDate(e.getEventDate(), e.getStartingTime()),
                    DateHelper.buildDate(e.getEventDate(), e.getEndingTime()));
            eventList.add(scheduleEvent);
        }
        return eventList;
    }

}
