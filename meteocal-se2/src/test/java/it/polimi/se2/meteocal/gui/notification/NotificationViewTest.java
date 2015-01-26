/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.gui.notification;

import it.polimi.se2.meteocal.enums.NotificationType;
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
public class NotificationViewTest {
    
    public NotificationViewTest() {
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
     * Test of isInvite method, of class NotificationView.
     */
    @Test
    public void testIsInvite() {
        System.out.println("isInvite");
        NotificationView instance = new NotificationView("ID", NotificationType.INVITE, "EventName", "EventDetails");
        Boolean result = instance.isInvite();
        assertEquals(true, result);
        instance = new NotificationView("ID", NotificationType.UPDATE, "EventName", "EventDetails");
        result = instance.isInvite();
        assertEquals(false, result);
        
    }

}
