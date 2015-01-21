/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.utilities;

import java.util.Calendar;
import java.util.Date;

/**
 * Helps with dates
 *
 * @author edo
 */
public class DateHelper {

    /**
     * Build a complete date given two partial dates representing day and time
     *
     * @param rawDate the day part of the date
     * @param rawTime the time part of the date
     * @return the Date set at rawDate day and rawTime hours
     */
    public static Date buildDate(Date rawDate, Date rawTime) {
        Calendar t = Calendar.getInstance();
        rawDate.setHours(rawTime.getHours());
        rawDate.setMinutes(rawTime.getMinutes());
        t.setTime(rawDate);
        return t.getTime();
    }
}
