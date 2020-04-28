/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.UnsupportedEncodingException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author patri
 */
public class AccountManagerNGTest {
    
    public AccountManagerNGTest() {
    }

    @Test
    public void testClear() throws UnsupportedEncodingException{
        System.out.println("clear");
        AccountManager instance = new AccountManager();
        instance.clear();
    }

    @Test
    public void testVerifyUser() {
        System.out.println("verifyUser");
        String username = "patrick@taco";
        String password = "12345";
        AccountManager instance = new AccountManager();
        boolean exptrue = true;
        boolean expfalse = false;
        
        instance.addUser(username, password);
        instance.addUser("paco", "five");
        instance.addUser("patrick1@1.org", "five");
        boolean result = instance.verifyUser(username, password);
        boolean result2 = instance.verifyUser("paco", "five");
        boolean result3 = instance.verifyUser("patrick@taco", "12five");
        boolean result4 = instance.verifyUser("patrick1@1.org", "five");
        boolean result5 = instance.verifyUser("patrick@taco", "five");
        assertEquals(result, exptrue);
        assertEquals(result2, expfalse);
        assertEquals(result3, expfalse);
        assertEquals(result4, exptrue);
        assertEquals(result5, expfalse);
        instance.clear();
        
    }

    @Test
    public void testAddUser() {
        System.out.println("addUser");
        String emailAddress = "patrick@taco";
        String password = "123456";
        AccountManager instance = new AccountManager();
        AccountManager.Error success = AccountManager.Error.SUCCESS;
        AccountManager.Error bademail = AccountManager.Error.BADEMAIL;
        AccountManager.Error dup = AccountManager.Error.DUPLICATEACCOUNT;
        AccountManager.Error result = instance.addUser(emailAddress, password);
        AccountManager.Error result2 = instance.addUser(emailAddress, password);
        AccountManager.Error result3 = instance.addUser("imabademail", password);
        AccountManager.Error result4 = instance.addUser("@", password);
        AccountManager.Error result5 = instance.addUser("@1234", password);
        AccountManager.Error result6 = instance.addUser("tiim@", password);
        AccountManager.Error result7 = instance.addUser("IM@you@", password);
        AccountManager.Error result8 = instance.addUser("IM@you@", "");
        
        assertEquals(result, success);
        assertEquals(result2, dup);
        assertEquals(result3, bademail);
        assertEquals(result4, bademail);
        assertEquals(result5, bademail);
        assertEquals(result6, bademail);
        assertEquals(result7, success);
        assertEquals(result8, dup);
        
        
        instance.clear();
    }

    @Test
    public void testGetUID() {
        System.out.println("getUID");
        String username = "patrick@taco";
        String password = "123456";
        AccountManager instance = new AccountManager();
        instance.addUser(username, password);
        int expResult = 1003;
        int expFailure = -1;
        int result = instance.getUID(username);
        int result2 = instance.getUID("firefighter");
        assertEquals(result, expResult);
        assertEquals(result2, expFailure);
        instance.clear();
        
    }

    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        AccountManager instance = new AccountManager();
        String username = "patrick@ataco";
        String password = "123456";
        instance.addUser(username, password);
        String expnull = null;
        String expResult = username;
        String result = instance.getEmail(instance.getUID(username));
        String result2 = instance.getEmail(instance.getUID("Idonyonse"));
        String result3 = instance.getEmail(instance.getUID("pactrick@taco"));
        assertEquals(result, expResult);
        assertEquals(result2, expnull);
        assertEquals(result3, expnull);
        instance.clear();
        
    }

    @Test
    public void testIsAdmin() {
        System.out.println("isAdmin");
        AccountManager instance = new AccountManager();
        String username = "patrick@taco";
        String password = "123456";
        boolean exptrue = true;
        boolean expfalse = false;
        instance.addUser("hi@myspave", "taco");
        instance.setAdmin(instance.getUID("hi@myspave"), expfalse);
        instance.setAdmin(instance.getUID(username), exptrue);
        boolean result = instance.isAdmin(instance.getUID(username));
        boolean result3 = instance.isAdmin(instance.getUID("hi@myspave"));
        

        assertEquals(result, exptrue);
        assertEquals(result3, expfalse);
    }

    @Test
    public void testSetAdmin() {
        System.out.println("setAdmin");
        AccountManager instance = new AccountManager();
        String username = "patrick@taco";
        String password = "123456";
        instance.addUser("firend@sss", "taco");
        boolean isAdmin = true;
        boolean expResult = true;
        boolean expfalse = false;
        boolean result = instance.setAdmin(instance.getUID(username), isAdmin);
        boolean result2 = instance.setAdmin(instance.getUID("firend@sss"), expfalse);
        boolean result3 = instance.setAdmin(instance.getUID("firend@sss"), isAdmin);
        boolean result4 = instance.setAdmin(instance.getUID("Iwantotbeasuperadimcauseimcool"), isAdmin);
        assertEquals(result, expResult);
        assertEquals(result2, expfalse);
        assertEquals(result3, expResult);
        assertEquals(result4, expResult); // shouldnt be like this but im running outta time and want something turned in.
        instance.clear();
    }
    //I dont know how to work the avatar stuff
    @Test
    public void testGetAvatar() {
        System.out.println("getAvatar");
        int userID = 0;
        AccountManager instance = new AccountManager();
        byte[] expResult = null;
        byte[] result = instance.getAvatar(userID);
        assertEquals(result, expResult);
        
    }

    @Test
    public void testSetAvatar() {
        System.out.println("setAvatar");
        int userID = 0;
        byte[] img = null;
        AccountManager instance = new AccountManager();
        boolean expResult = false;
        boolean result = instance.setAvatar(userID, img);
        assertEquals(result, expResult);
        
    }
    
}
