/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.manager;

import it.polimi.se2.meteocal.entity.Event;
import it.polimi.se2.meteocal.entity.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author edo
 */
@Stateless
public class EventManager {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private UserManager um;

    /**
     * Save the event in the DB
     *
     * @param event the event to save
     */
    public void saveEvent(Event event) {
        User owner = um.getLoggedUser();
        //Add the owner to the event
        event.setOwner(owner);
        //Add the owner to the event's participants
        event.getAttendingUsers().add(owner);
        //Add the event to the owner's attending events
        owner.getAttendingEvents().add(event);
        
        //TODO INVITE AND NOTIFY
        //Save event to DB
        em.persist(event);
    }

}
