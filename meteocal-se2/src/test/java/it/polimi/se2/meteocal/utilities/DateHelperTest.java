/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.utilities;

import java.util.Calendar;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author edo
 */
public class DateHelperTest {

    public DateHelperTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of overlaps method, of class DateHelper.
     */
    @Test
    public void testOverlaps() {
        System.out.println("overlaps");
        //Today 15:00-16:45
        Date date1 = new Date();
        Date start1 = new Date();
        start1.setHours(15);
        start1.setMinutes(00);
        Date end1 = new Date();
        end1.setHours(16);
        end1.setMinutes(45);

        //Today 12:24-18:39
        Date date2 = new Date();
        Date start2 = new Date();
        start2.setHours(12);
        start2.setMinutes(24);
        Date end2 = new Date();
        end2.setHours(18);
        end2.setMinutes(39);

        //Today 10:15-12:21
        Date date3 = new Date();
        Date start3 = new Date();
        start3.setHours(10);
        start3.setMinutes(15);
        Date end3 = new Date();
        end3.setHours(12);
        end3.setMinutes(21);

        TimeWindow one = new TimeWindow(DateHelper.buildDate(date1, start1),
                DateHelper.buildDate(date1, end1));
        TimeWindow two = new TimeWindow(DateHelper.buildDate(date2, start2),
                DateHelper.buildDate(date2, end2));
        TimeWindow three = new TimeWindow(DateHelper.buildDate(date3, start3),
                DateHelper.buildDate(date3, end3));
        
        
        Boolean result1 = DateHelper.overlaps(one, two);
        Boolean result2 = DateHelper.overlaps(two, three);
        Boolean result3 = DateHelper.overlaps(one, three);
        assertEquals(true, result1);
        assertEquals(false, result2);
        assertEquals(false, result3);
    }

    /**
     * Test of scheduledInThePast method, of class DateHelper.
     */
    @Test
    public void testScheduledInThePast() {
        System.out.println("scheduledInThePast");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -5);
        Date hour = new Date();
        hour.setHours(15);
        hour.setMinutes(00);
        Boolean result = DateHelper.scheduledInThePast(cal.getTime(), hour);
        assertEquals(true, result);
        cal.add(Calendar.YEAR, 2);
        Boolean result2 = DateHelper.scheduledInThePast(cal.getTime(), hour);
        assertEquals(false, result2);
    }

}
