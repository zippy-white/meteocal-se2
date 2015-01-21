/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.entity;

import it.polimi.se2.meteocal.utilities.PasswordEncrypter;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Users entity class used to map to the database.
 *
 * @author edo
 */
@Entity(name = "USERS")
@NamedQueries({
    @NamedQuery(name = User.findByUsername,
            query = "SELECT u FROM USERS u WHERE u.username = :username")
})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String findByUsername = "findByUsername";

    /*
     Attributes
     */
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NotNull(message = "Username must not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$",
            message = "Username may only contain alphanumeric characters and _")
    private String username;

    @NotNull(message = "Password must not be empty")
    private String password;

    @NotNull(message = "Group name must not be empty")
    private String groupName;

    /*
     Relationships
     */
    /**
     * Events created by one user
     */
    @OneToMany(mappedBy = "owner")
    private Set<Event> createdEvents;

    /**
     * Events as invitee
     */
    @ManyToMany
    @JoinTable(
            name = "INVITED_USERS",
            joinColumns = {
                @JoinColumn(name = "UserID", referencedColumnName = "ID")},
            inverseJoinColumns = {
                @JoinColumn(name = "EventID", referencedColumnName = "ID")})
    private Set<Event> invitedToEvents;

    /**
     * Events as participant
     */
    @ManyToMany
    @JoinTable(
            name = "ATTENDING_USERS",
            joinColumns = {
                @JoinColumn(name = "UserID", referencedColumnName = "ID")},
            inverseJoinColumns = {
                @JoinColumn(name = "EventID", referencedColumnName = "ID")})
    private Set<Event> attendingEvents;

    /**
     * User notifications
     */
    @OneToMany(mappedBy = "recipient")
    private Set<Notification> notifications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public Set<Notification> getNotifications() {
        if (notifications == null) {
            notifications = new HashSet<>();
        }
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Set<Event> getCreatedEvents() {
        if (createdEvents == null) {
            createdEvents = new HashSet<>();
        }
        return createdEvents;
    }

    public void setCreatedEvents(Set<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public Set<Event> getInvitedToEvents() {
        if (invitedToEvents == null) {
            invitedToEvents = new HashSet<>();
        }
        return invitedToEvents;
    }

    public void setInvitedToEvents(Set<Event> invitedToEvents) {
        this.invitedToEvents = invitedToEvents;
    }

    public Set<Event> getAttendingEvents() {
        if (attendingEvents == null) {
            attendingEvents = new HashSet<>();
        }
        return attendingEvents;
    }

    public void setAttendingEvents(Set<Event> attendingEvents) {
        this.attendingEvents = attendingEvents;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = PasswordEncrypter.encryptPassword(password);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
