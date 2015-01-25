/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.gui.notification;

import it.polimi.se2.meteocal.enums.NotificationType;

/**
 * A view object representing the notification and its details for usage in the
 * notification panel
 *
 * @author edo
 */
public class NotificationView {

    private final String id;
    private String message;
    private final NotificationType nt;
    private final String eventName;
    private String details;
    private Boolean isInvite;

    public NotificationView(String id, NotificationType nt, String eventName, String eventDetails) {
        this.id = id;
        this.nt = nt;
        this.eventName = eventName;
        this.details = eventDetails;
        buildMessage();
    }

    private void buildMessage() {
        if (nt == NotificationType.INVITE) {
            message = "Invite to the event '" + eventName + "'";
            this.details = "You have been invited to the event " + this.details;
        } else if (nt == NotificationType.DELETE) {
            message = "The event '" + eventName + "' has been deleted";
            this.details = "The event was canceled and is not in your schedule anymore";
        } else if (nt == NotificationType.UPDATE) {
            message = "The event '" + eventName + "' has been updated";
            this.details = "Consult the event details in your schedule for more informations";
        } else if (nt == NotificationType.WEATHER) {
            message = "Weather forecast for the event '" + eventName + "'";
            this.details = "Consult the event details in your schedule for more informations";
        }
    }

    public Boolean isInvite() {
        return nt == NotificationType.INVITE;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public String getEventName() {
        return eventName;
    }

    public Boolean getIsInvite() {
        return isInvite = isInvite();
    }

    public String getDetails() {
        return details;
    }

}
