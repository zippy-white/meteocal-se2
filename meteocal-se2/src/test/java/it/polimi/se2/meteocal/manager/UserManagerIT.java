/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.se2.meteocal.manager;

import it.polimi.se2.meteocal.entity.User;
import it.polimi.se2.meteocal.utilities.PasswordEncrypter;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.hamcrest.CoreMatchers.is;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author edo
 */
@RunWith(Arquillian.class)
public class UserManagerIT {
    
    @EJB
    UserManager um;
    
    @PersistenceContext
    EntityManager em;
    
    @Deployment
    public static WebArchive createArchiveAndDeploy() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(UserManager.class)
                .addPackage(User.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
   
    @Test
    public void UserManagerShouldBeInjected() {
        assertNotNull(um);
    }
    
    @Test
    public void EntityManagerShouldBeInjected() {
        assertNotNull(em);
    }
    
    @Test
    public void passwordsShouldBeEncryptedOnDB() {
        User newUser = new User();
        String uname = "test";
        String password = "password";
        newUser.setUsername(uname);
        newUser.setPassword(password);
        um.saveUser(newUser);
        User foundUser = um.findUserByName(uname);
        assertNotNull(foundUser);
        assertThat(foundUser.getPassword(),is(PasswordEncrypter.encryptPassword(password)));
    }
    
}
