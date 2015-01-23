/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.gui.event;

import it.polimi.se2.meteocal.entity.Event;
import it.polimi.se2.meteocal.manager.EventManager;
import it.polimi.se2.meteocal.manager.UserManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 * Backing bean for the primefaces schedule component used in the user personal
 * page
 *
 * @author edo
 */
@ManagedBean(name = "scheduleBean")
@ViewScoped
public class ScheduleBean implements Serializable {

    private ScheduleModel eventModel;
    private ScheduleEvent scheduleEvent;

    private Map<ScheduleEvent, Event> eventsMap;
    private List<ScheduleEvent> scheduleEventsList;

    private Event event;

    @EJB
    private UserManager um;
    
    @EJB 
    private EventManager evm;
    
    /**
     * Get events' map for the current user and build the Schedule model
     */
    @PostConstruct
    public void init() {
        eventsMap = um.getEventsMap();
        scheduleEventsList = new ArrayList<>(eventsMap.keySet());
        eventModel = new DefaultScheduleModel(scheduleEventsList);
    }

    /**
     * Change event entity based on the event selected on the schedule
     * @param selectedEvent the event that was clicked
     */
    public void onEventSelect(SelectEvent selectedEvent) {
        scheduleEvent = (ScheduleEvent) selectedEvent.getObject();
        event = eventsMap.get(scheduleEvent);
    }
    
    /**
     * Check wether the user viewing the event is the owner of the event
     * @return true if the user viewing the event is the owner, false otherwise
     */
    public Boolean isViewerOwner() {
        return um.getLoggedUser() == event.getOwner();
    }
    
    /**
     * Update the event
     */
    public void updateEvent() {
        evm.updateEvent(event);
    }
    
    /**
     * Delete the event
     */
    public void deleteEvent() {
        evm.removeEvent(event);
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public Event getEvent() {
        return event;
    }

}
