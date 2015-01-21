/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.gui.event;

import it.polimi.se2.meteocal.manager.UserManager;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

/**
 *Backing bean for the primefaces schedule component
 * used in the user personal page
 * @author edo
 */
@Named(value = "scheduleBean")
@RequestScoped
public class ScheduleBean {

    private ScheduleModel eventModel;
    
    @EJB
    private UserManager um;
    
    @PostConstruct
    public void populateSchedule() {
        eventModel = new DefaultScheduleModel(um.getScheduledEvents());
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }
            
            
    public ScheduleBean() {
    }
    
}
