/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.gui.event;

import it.polimi.se2.meteocal.entity.Event;
import java.util.logging.Logger;
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

    private Event event;

    public CreateEventBean() {
    }
    
    
    public String createEvent() {
        return "";
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

}
