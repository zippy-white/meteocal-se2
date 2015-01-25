/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.manager;

import it.polimi.se2.meteocal.entity.Event;
import it.polimi.se2.meteocal.entity.Notification;
import it.polimi.se2.meteocal.entity.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Notification manager
 *
 * @author edo
 */
@Stateless
public class NotificationManager {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EventManager evm;

    @EJB
    private UserManager um;

    /**
     * Save a new notification in the DB
     *
     * @param n the notification to save
     */
    public void saveNotification(Notification n) {
        em.persist(n);
        em.flush();
    }

    /**
     * Update a notification in the DB
     *
     * @param n the notification to update
     */
    public void updateNotification(Notification n) {
        em.merge(n);
    }

    /**
     * Remove a notification from the DB
     *
     * @param n the notification to remove
     */
    public void removeNotification(Notification n) {
        em.remove(em.merge(n));
    }

    /**
     * Accept an invite and update the user-event realtionships
     *
     * @param n the invite that was accepted
     */
    public void acceptInvite(Notification n) {
        System.out.println("Notification manager is accepting invite");
        updateNotification(n);
        User attendee = n.getRecipient();
        Event event = n.getSingleGeneratingEvent();
        //User - Event relationships
        attendee.removeInvitedEvent(event);
        attendee.addAttendingEvent(event);
        event.removeInvitedUser(attendee);
        event.addAttendingUser(attendee);
        um.updateUser(attendee);
        evm.updateEvent(event);
    }

    /**
     * Decline an invite and update the user-event relationships
     *
     * @param n the invite to decline
     */
    public void declineInvite(Notification n) {
        System.out.println("Notification manager is declining invite");
        updateNotification(n);
        User invitee = n.getRecipient();
        Event event = n.getSingleGeneratingEvent();
        invitee.removeInvitedEvent(event);
        event.removeInvitedUser(invitee);
        um.updateUser(invitee);
        evm.updateEvent(event);
    }

}
