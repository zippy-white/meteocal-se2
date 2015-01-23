/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.gui.event;

import it.polimi.se2.meteocal.entity.Event;
import it.polimi.se2.meteocal.enums.EventType;
import it.polimi.se2.meteocal.manager.EventManager;
import it.polimi.se2.meteocal.manager.UserManager;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * JSF managed bean for the event creation
 *
 * @author edo
 */
@Named(value = "createEventBean")
@RequestScoped
public class CreateEventBean {

    private static final Logger logger = Logger.getLogger("createEventBean");

    @EJB
    EventManager evm;

    @EJB
    UserManager um;

    private Event event;

    private EventType[] eventTypes;

    private String guests;

    public CreateEventBean() {
    }

    /**
     * Create the event
     *
     * @return the personal page of the user
     */
    public String createEvent() {
        addGuests();
        evm.saveEvent(event);
        return "/personalPages/personalPage?faces-redirect=true";
    }

    public Event getEvent() {
        if (event == null) {
            event = new Event();
        }
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventType[] getEventTypes() {
        return EventType.values();
    }

    public String getGuests() {
        return guests;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    /**
     * Parse the guest string and build a list of invited users to add to the
     * event
     */
    private void addGuests() {
        if (guests != null) {
            List<String> invitedUsernames = Arrays.asList(this.guests.split(", "));
            for (String username : invitedUsernames) {
                //Inexistent users and the event creator cannot be invited to the event
                if (um.findUserByName(username) != null
                        && !username.equalsIgnoreCase(um.getLoggedUserName())) {
                    event.addInvitedUser(um.findUserByName(username));
                }
            }
        }
    }

}
