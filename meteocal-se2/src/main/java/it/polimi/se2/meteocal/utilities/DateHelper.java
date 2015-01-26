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

    /**
     * Check if to events, represented by time windows overlap
     *
     * @param one the first time window
     * @param two the second time window
     * @return true if the time windows overlap false otherwise
     */
    public static Boolean overlaps(TimeWindow one, TimeWindow two) {
        return !((one.getStart().before(two.getStart())
                && one.getEnd().before(two.getStart()))
                || (one.getStart().after(two.getEnd())
                && one.getEnd().after(two.getEnd())));
    }

    /**
     * 
     * @param day the day we want to check
     * @param hour the hour of the day
     * @return true if the day plus hour date is before now
     */
    public static Boolean scheduledInThePast(Date day, Date hour) {
        Date date = buildDate(day, hour);
        return date.before(Calendar.getInstance().getTime());
    }

    /**
     * 
     * @return only today's date without hours, minutes, seconds and milliseconds.
     */
    public static Date getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
