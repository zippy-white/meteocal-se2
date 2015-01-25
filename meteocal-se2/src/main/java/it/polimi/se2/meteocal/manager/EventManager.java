/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.manager;

import it.polimi.se2.meteocal.entity.Event;
import it.polimi.se2.meteocal.entity.Notification;
import it.polimi.se2.meteocal.entity.User;
import it.polimi.se2.meteocal.enums.NotificationStatus;
import it.polimi.se2.meteocal.enums.NotificationType;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Manages the event lifecycle and the necessary interactions with other
 * entities
 *
 * @author edo
 */
@Stateless
public class EventManager {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private UserManager um;

    @EJB
    private NotificationManager nm;

    /**
     * Save the event in the DB Create relationships with owner, invite invited
     * users and send notifications
     *
     * @param event the event to save
     */
    public void saveEvent(Event event) {
        User owner = um.getLoggedUser();
        //Add the owner to the event
        event.setOwner(owner);
        //Add the owner to the event's participants
        event.addAttendingUser(owner);
        //Add the event to the owner's created events
        owner.addCreatedEvent(event);
        //Add the event to the owner's attending events
        owner.addAttendingEvent(event);
        //Save event to DB
        em.persist(event);
        em.flush();
        //Update owner. This has to be after event persist for transactions to work!
        um.updateUser(owner);
        //Invite Users. This has to be after event persist for transactions to work!
        inviteUsers(event);
    }

    /**
     * Update an event
     *
     * @param e the event to update
     */
    public void updateEvent(Event e) {
        System.out.println("Event manager is updating event: " + e);
        em.merge(e);
        em.flush();
        notifyUsers(e);
        System.out.println("Event manager has updated event: " + e);
    }

    /**
     * Remove an event, remove relationships with users and delete notifications
     *
     * @param e the event to remove
     */
    public void removeEvent(Event e) {
        System.out.println("Event manager is removing event: " + e);
        //Remove the event from the events created by the owner
        e.getOwner().removeCreatedEvent(e);
        //Remove the event (merge is necessary for the obj to become managed)
        em.remove(em.merge(e));
        em.flush();
        //Remove event from invited and attending users side of the relationship
        removeInvitedUsers(e);
        removeAttendingUsers(e);
        removeGeneratedNotifications(e);
        System.out.println("Event manager has deleted the event");
    }

    private void inviteUsers(Event event) {
        for (User u : event.getInvitedUsers()) {
            System.out.println("INVITING USER " + u.getUsername() + " TO EVENT " + event.getName());
            u.addInvitedToEvent(event);
            sendNotification(u, NotificationType.INVITE, event);
            um.updateUser(u);
        }
    }

    private void removeInvitedUsers(Event e) {
        for (User u : e.getInvitedUsers()) {
            System.out.println("REMOVING INVITED EVENT FOR " + u.getUsername());
            u.removeInvitedEvent(e);
            um.updateUser(u);
        }
    }

    private void removeAttendingUsers(Event e) {
        for (User u : e.getAttendingUsers()) {
            System.out.println("REMOVING ATTENDING EVENT FOR " + u.getUsername());
            u.removeAttendingEvent(e);
            um.updateUser(u);
        }
    }

    private void sendNotification(User user, NotificationType notificationType, Event e) {
        Notification n = new Notification();
        //All sent notifications are PENDING until read/accepted
        n.setStatus(NotificationStatus.PENDING);
        n.setType(notificationType);
        n.setRecipient(user);
        n.addGeneratingEvent(e);
        //Save the notification
        nm.saveNotification(n);
        //Build relationships
        e.addGeneratedNotification(n);
        user.addNotification(n);
    }

    private void removeGeneratedNotifications(Event e) {
        for (Notification n : e.getGeneratedNotifications()) {
            n.getRecipient().removeNotification(n);
            nm.removeNotification(n);
        }
    }
    
    private void notifyUsers(Event e) {
        for (User u : e.getAttendingUsers()) {
            sendNotification(u, NotificationType.UPDATE, e);
            um.updateUser(u);
        }
    }

}
