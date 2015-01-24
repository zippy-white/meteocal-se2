/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.manager;

import it.polimi.se2.meteocal.entity.Notification;
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

}
