/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.manager;

import it.polimi.se2.meteocal.entity.Group;
import it.polimi.se2.meteocal.entity.User;
import javax.persistence.EntityManager;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 *
 * @author edo
 */
public class UserManagerTest {
    
    private UserManager um;
    
    public UserManagerTest() {
    }
    
    @Before
    public void setUp() {
        um = new UserManager();
        um.em = mock(EntityManager.class);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void newUsersShouldBelongToUserGroupAndSavedOnce() {
        User newUser = new User();
        um.saveUser(newUser);
        assertThat(newUser.getGroupName(), is(Group.USERS));
        verify(um.em,times(1)).persist(newUser);
    }
    
    @Test
    public void updateUserOneCallToMerge() {
        User newUser = new User();
        um.saveUser(newUser);
        assertThat(newUser.getGroupName(), is(Group.USERS));
        verify(um.em,times(1)).persist(newUser);
        newUser.setPassword("passowrd");
        um.updateUser(newUser);
        verify(um.em,times(1)).merge(newUser);
    }

    
    
}
