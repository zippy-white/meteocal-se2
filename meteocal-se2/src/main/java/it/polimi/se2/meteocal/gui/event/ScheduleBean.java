/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.gui.event;

import it.polimi.se2.meteocal.entity.Event;
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

    @PostConstruct
    public void init() {
        eventsMap = um.getEventsMap();
        scheduleEventsList = new ArrayList<>(eventsMap.keySet());
        eventModel = new DefaultScheduleModel(scheduleEventsList);
    }

    public void onEventSelect(SelectEvent selectedEvent) {
        scheduleEvent = (ScheduleEvent) selectedEvent.getObject();
        event = eventsMap.get(scheduleEvent);
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public Event getEvent() {
        return event;
    }

}
