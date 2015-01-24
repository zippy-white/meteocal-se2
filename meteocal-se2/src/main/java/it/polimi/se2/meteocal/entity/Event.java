/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.entity;

import it.polimi.se2.meteocal.enums.EventType;
import it.polimi.se2.meteocal.enums.WeatherCondition;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Entity class for the event.
 *
 * @author edo
 */
@Entity
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     Attributes
     */
    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = "Name must not be empty")
    private String name;

    private String description;

    @NotNull(message = "Event's type must not be empty")
    private EventType type;

    @NotNull(message = "Event's location must not be empty")
    private String location;

    @NotNull(message = "Event's date must not be empty")
    @Temporal(TemporalType.DATE)
    private Date eventDate;

    @NotNull(message = "Event's starting time must not be empty")
    @Temporal(TemporalType.TIME)
    private Date startingTime;

    @NotNull(message = "Event's ending time must not be empty")
    @Temporal(TemporalType.TIME)
    private Date endingTime;

    private WeatherCondition weather;

    /*
     Relationships
     */
    /**
     * Event's owning user
     */
    @ManyToOne
    @JoinColumn(name = "OWNER")
    @NotNull(message = "The event must have a owner")
    private User owner;

    /**
     * Users invited to an event
     */
    @ManyToMany(mappedBy = "invitedToEvents", fetch = FetchType.EAGER)
    private Set<User> invitedUsers;

    /**
     * Users attending an event
     */
    @ManyToMany(mappedBy = "attendingEvents", fetch = FetchType.EAGER)
    private Set<User> attendingUsers;

    /**
     * Notifications generated by an event
     */
    @ManyToMany
    @JoinTable(
            name = "EVENT_NOTIFICATIONS",
            joinColumns = {
                @JoinColumn(name = "EventID", referencedColumnName = "ID")},
            inverseJoinColumns = {
                @JoinColumn(name = "NotificationID", referencedColumnName = "ID")})
    private Set<Notification> generatedNotifications;
    
    /*
    Methods
    */
    
    /**
     * Add a user to the invited users of the event
     * @param u the user to invite
     */
    public void addInvitedUser(User u) {
        this.getInvitedUsers().add(u);
    }
    
    /**
     * Add a user to the event's participants
     * @param u the user to add
     */
    public void addAttendingUser(User u) {
        this.getAttendingUsers().add(u);
    }
    
    /**
     * Add a notification to the notifications generated by the event
     * @param n the notification to add
     */
    public void addGeneratedNotification (Notification n) {
        this.getGeneratedNotifications().add(n);
    }
    
    /**
     * Remove a notification that was generated by the event
     * @param n the notification to remove
     */
    public void removeGeneratedNotification(Notification n) {
        this.getGeneratedNotifications().remove(n);
    }
    
    /*
    Getters and Setters
    */

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Date getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Date startingTime) {
        this.startingTime = startingTime;
    }

    public Date getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(Date endingTime) {
        this.endingTime = endingTime;
    }

    public WeatherCondition getWeather() {
        return weather;
    }

    public void setWeather(WeatherCondition weather) {
        this.weather = weather;
    }

    public Set<User> getInvitedUsers() {
        if (invitedUsers == null) {
            invitedUsers = new HashSet<>();
        }
        return invitedUsers;
    }

    public void setInvitedUsers(Set<User> invitedUsers) {
        this.invitedUsers = invitedUsers;
    }

    public Set<User> getAttendingUsers() {
        if (attendingUsers == null) {
            attendingUsers = new HashSet<>();
        }
        return attendingUsers;
    }

    public void setAttendingUsers(Set<User> attendingUsers) {
        this.attendingUsers = attendingUsers;
    }

    public Set<Notification> getGeneratedNotifications() {
        if (generatedNotifications == null) {
            generatedNotifications = new HashSet<>();
        }
        return generatedNotifications;
    }

    public void setGeneratedNotifications(Set<Notification> generatedNotifications) {
        this.generatedNotifications = generatedNotifications;
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
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Id: " + id + "Name: " + name;
    }

}
