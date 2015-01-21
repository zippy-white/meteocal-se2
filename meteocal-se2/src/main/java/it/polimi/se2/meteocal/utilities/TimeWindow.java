/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.utilities;

import java.util.Date;

/**
 *Class to represent a time window delimited by two dates
 * @author edo
 */
public class TimeWindow {
    
    private final Date start;
    private final Date end;
    
    public TimeWindow(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
    
}
