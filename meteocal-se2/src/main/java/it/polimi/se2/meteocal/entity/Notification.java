/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.entity;

import it.polimi.se2.meteocal.enums.NotificationStatus;
import it.polimi.se2.meteocal.enums.NotificationType;
import it.polimi.se2.meteocal.utilities.DateHelper;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * Entity class for Notification
 *
 * @author edo
 */
@Entity
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     Attributes
     */
    @Id
    @GeneratedValue
    private Long id;

    private NotificationType type;

    private NotificationStatus status;

    /*
     Relationships
     */
    /**
     * User to which the notification is sent
     */
    @ManyToOne
    @JoinColumn(name = "RECIPIENT")
    @NotNull(message = "The notification must have a recipient")
    private User recipient;

    /**
     * The event that generated the notification
     */
    @ManyToMany(mappedBy = "generatedNotifications")
    private Set<Event> generatingEvent;

    /*
     Methods
     */
    /**
     * Add the event that generated the notification
     *
     * @param e the event
     */
    public void addGeneratingEvent(Event e) {
        this.getGeneratingEvent().add(e);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Set<Event> getGeneratingEvent() {
        if (generatingEvent == null) {
            generatingEvent = new HashSet<>();
        }
        return generatingEvent;
    }

    public void setGeneratingEvent(Set<Event> generatingEvent) {
        this.generatingEvent = generatingEvent;
    }

    /**
     * Get the name of the event that generated the notification
     *
     * @return the event name if an event generated the notification, null
     * otherwise
     */
    public String getGeneratingEventName() {
        String eventName = null;
        for (Event e : getGeneratingEvent()) {
            eventName = e.getName();
        }
        return eventName;
    }

    /**
     * Get a brief summary of the event associated to the notification
     *
     * @return a string that is a brief description of the event associated to
     * the notification
     */
    public String getGeneratingEventDetails() {
        String eventDetails = null;
        for (Event e : getGeneratingEvent()) {
            String eventName = e.getName();
            String owner = e.getOwner().getUsername();
            String from = DateHelper.buildDate(e.getEventDate(), e.getStartingTime()).toString();
            String to = DateHelper.buildDate(e.getEventDate(), e.getEndingTime()).toString();
            eventDetails = eventName + " by " + owner + " from " + from + " to " + to;
        }
        return eventDetails;
    }

    /**
     * Get the event that generated the notification
     *
     * @return the event that generated the notification if it exists, null
     * otherwise
     */
    public Event getSingleGeneratingEvent() {
        Event sge = null;
        for (Event e : getGeneratingEvent()) {
            sge = e;
        }
        return sge;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.se2.meteocal.entity.Notification[ id=" + id + " ]";
    }

}
