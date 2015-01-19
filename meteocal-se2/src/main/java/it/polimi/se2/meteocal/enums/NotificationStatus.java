/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.enums;

/**
 *Enum with all the possible states for a Notification.
 * Pending: the user has not read the notification yet
 * Read: the user has read a non-invite notification
 * Accepted: the user has accepted an invite
 * Declined: the user has declined an invite
 * @author edo
 */
public enum NotificationStatus {
    PENDING,
    READ,
    ACCEPTED,
    DECLINED
}
