/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.gui.event;

import it.polimi.se2.meteocal.entity.Event;
import it.polimi.se2.meteocal.enums.EventType;
import it.polimi.se2.meteocal.manager.EventManager;
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

    private Event event;

    private EventType[] eventTypes;

    public CreateEventBean() {
    }

    /**
     * Create the event
     * @return the personal page of the user
     */
    public String createEvent() {
        evm.saveEvent(event);
        return "/personalPages/personalPage";
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

}
