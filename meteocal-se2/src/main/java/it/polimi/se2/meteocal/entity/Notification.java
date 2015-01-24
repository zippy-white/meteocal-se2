/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.entity;

import it.polimi.se2.meteocal.enums.NotificationStatus;
import it.polimi.se2.meteocal.enums.NotificationType;
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
