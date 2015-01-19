/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.enums;

/**
 *Enum for the possible types of a Notification.
 * Simple: basic notification
 * Invite: a user has been invited to an event
 * Update: an event has been updated
 * Delete: an event has been deleted
 * Weather: forecast of bad weather for an event
 * @author edo
 */
public enum NotificationType {
    SIMPLE,
    INVITE,
    UPDATE,
    DELETE,
    WEATHER
}
