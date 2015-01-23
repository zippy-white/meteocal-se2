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
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
     *
     * @param user the new user to add to the DB
     */
    public void saveUser(User user) {
        user.setGroupName(Group.USERS);
        em.persist(user);
    }

    /**
     * Update the user record on the DB
     *
     * @param u the user to update
     */
    public void updateUser(User u) {
        em.merge(u);
    }

    /**
     *
     * @return the logged User object
     */
    public User getLoggedUser() {
        return findUserByName(getLoggedUserName());
    }

    /**
     *
     * @return the logged user's username
     */
    public String getLoggedUserName() {
        return sctx.getCallerPrincipal().getName();
    }

    /**
     * Get the mapping between ScheduleEvent events (for schedule) and Event
     * events (for data)
     *
     * @return the map with ScheduleEvent as keys and Event entities as values
     */
    public Map<ScheduleEvent, Event> getEventsMap() {
        Map<ScheduleEvent, Event> eventsMap = new HashMap<>();
        User u = getLoggedUser();
        ScheduleEvent scheduleEvent;
        //For each event that the user will be attending we build the corrisponding schedule event for display
        for (Event e : u.getAttendingEvents()) {
            scheduleEvent = new DefaultScheduleEvent(e.getName(),
                    DateHelper.buildDate(e.getEventDate(), e.getStartingTime()),
                    DateHelper.buildDate(e.getEventDate(), e.getEndingTime()));
            //MUST SET THE ID, NULLPOINTEREXCEPTION IF NOT SET
            scheduleEvent.setId(e.getId().toString());
            eventsMap.put(scheduleEvent, e);
        }
        return eventsMap;
    }

    /**
     * Finds the User given the username
     *
     * @param username
     * @return the User with the given username if it exists, null otherwise
     */
    public User findUserByName(String username) {
        try {
            User u = em.createNamedQuery(User.findByUsername, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return u;
        } catch (NoResultException e) {
            return null;
        }
    }

}
