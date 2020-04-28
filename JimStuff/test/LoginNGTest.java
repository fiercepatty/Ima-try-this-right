/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import org.eclipse.jetty.util.log.AbstractLogger;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 *
 * @author patri
 */





public class LoginNGTest {
    
    public LoginNGTest() {
    }
    String fetch(String... allurls) throws Exception{
    String str=null;
    byte[] returnedData=new byte[]{0};  //dummy
    for(String oneurl: allurls ){
        var url = new URL("http://localhost:2020"+oneurl);
        var conn = url.openConnection();
        conn.connect();
        var istr = conn.getInputStream();
        returnedData = istr.readAllBytes();
    }
    return new String(returnedData,0,returnedData.length);
    }
    
    @BeforeClass
public static void startJetty() throws Exception {
    String[] args = new String[]{
        "jetty.home=../jetty",
        "STOP.PORT=2021", "STOP.KEY=AutomaticTofu"
    };
    var LG = new StdErrLog();
    LG.setLevel(AbstractLogger.LEVEL_OFF);
    Log.setLog(LG);
    org.eclipse.jetty.start.Main.main(args);
}
@AfterClass
public static void stopJetty() throws Exception {
    String[] args = new String[]{ "jetty.home=../jetty",
        "STOP.PORT=2021", "STOP.KEY=AutomaticTofu",
        "--stop"
    };
    org.eclipse.jetty.start.Main.main(args);
}

    /**
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testLogin1() throws Exception{
        String txt = fetch("/srv/register?email=bob@yahoo.com&password=tom");
        String txt1 = fetch("/srv/login?username=bob@yahoo.com&password=tom");
        assertTrue(txt1.contains("You are now logged in as:bob@yahoo.com"));
    }
    
    @Test
    public void testLogin2() throws Exception{
        String txt = fetch("/srv/login");
        assertTrue(txt.contains("Failed need password or email"));
    }
    
     @Test
    public void testLogin3() throws Exception{
        String txt = fetch("/srv/login?username=bob@yahoo.com");
        assertTrue(txt.contains("Failed need password or email"));
    }
    
    @Test
    public void testLogin4() throws Exception{
        String txt = fetch("/srv/login?password=bob@yahoo.com");
        assertTrue(txt.contains("Failed need password or email"));
    }
    
    public void testLogin5() throws Exception{
        String txt = fetch("/srv/register?email=bob@yahoo.com&password=tom","/srv/login?username=bob@yahoo.com&password=paul");
        
        assertTrue(txt.contains("Already logged in or wrong password or wrong email"));
    }
    
    @Test
    public void testLogin6() throws Exception{
        String txt1 = fetch("/srv/login?username=bob@yahoo.com&password=tom");
        assertTrue(txt1.contains("Already logged in or wrong password or wrong email"));
    }
    
    @Test
    public void testLogin7() throws Exception{
        String txt = fetch("/srv/register?email=bob@yahoo.com&password=tom");
        String txt1 = fetch("/srv/login?username=bob@yahoo.com&password=tom");
        String txt2 = fetch("/srv/login?username=bob@yahoo.com&password=tom");
        assertTrue(txt2.contains("Already logged in or wrong password or wrong email"));
    }
    
    
    

}
