/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.gui.notification;

import it.polimi.se2.meteocal.entity.Notification;
import it.polimi.se2.meteocal.enums.NotificationStatus;
import it.polimi.se2.meteocal.manager.NotificationManager;
import it.polimi.se2.meteocal.manager.UserManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * The JSF managed bean that operates the notification panel
 *
 * @author edo
 */
@ManagedBean
@ViewScoped
public class NotificationBean implements Serializable {

    @EJB
    private UserManager um;

    @EJB
    NotificationManager nm;

    private List<Notification> userNotifications;
    private List<NotificationView> pendingNotifications;
    private Map<NotificationView, Notification> notificationsMap;
    private NotificationView selectedNotification;

    @PostConstruct
    public void init() {
        userNotifications = new ArrayList<>(um.getLoggedUser().getNotifications());
        buildViewNotifications();
        pendingNotifications = new ArrayList<>(notificationsMap.keySet());
    }

    public void acceptInvite() {
        FacesContext context = FacesContext.getCurrentInstance();
        System.out.println("Accepting invite");
        Notification n = notificationsMap.get(selectedNotification);
        if (um.userIsAlreadyBusy(n.getSingleGeneratingEvent())) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "You already have a scheduled event overlapping with this one.", "Invite not accepted."));
        } else {
            n.setStatus(NotificationStatus.ACCEPTED);
            nm.acceptInvite(n);
            init();
        }
    }

    public void declineInvite() {
        System.out.println("Declining invite");
        Notification n = notificationsMap.get(selectedNotification);
        n.setStatus(NotificationStatus.DECLINED);
        nm.declineInvite(n);
        init();
    }

    public void markAsRead() {
        System.out.println("Marking as read");
        Notification n = notificationsMap.get(selectedNotification);
        n.setStatus(NotificationStatus.READ);
        nm.updateNotification(n);
        init();
    }

    public List<NotificationView> getPendingNotifications() {
        return pendingNotifications;
    }

    private void buildViewNotifications() {
        notificationsMap = new HashMap<>();
        for (Notification n : userNotifications) {
            if (n.getStatus() == NotificationStatus.PENDING) {
                NotificationView nv = new NotificationView(n.getId().toString(), n.getType(), n.getGeneratingEventName(), n.getGeneratingEventDetails());
                notificationsMap.put(nv, n);
            }
        }
    }

    public NotificationView getSelectedNotification() {
        return selectedNotification;
    }

    public void setSelectedNotification(NotificationView selectedNotification) {
        this.selectedNotification = selectedNotification;
    }

}
