/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.util.log.AbstractLogger;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author patri
 */
public class LogoutNGTest {
    
    public LogoutNGTest() {
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

    @Test
    public void testLogout1() throws Exception{
        String txt = fetch("/srv/register?email=bob@yahoo.com&password=tom");
        String txt1 = fetch("/srv/login?username=bob@yahoo.com&password=tom");
        String txt2 = fetch("/srv/logout?username=bob@yahoo.com");
        assertTrue(txt2.contains("Logged out"));
    }
    
    @Test
    public void testLogout2() throws Exception{
        String txt = fetch("/srv/logout");
        assertTrue(txt.contains("Failed"));
    }
    
    @Test
    public void testLogout3() throws Exception{
        String txt = fetch("/srv/register?email=bob@yahoo.com&password=tom");
        String txt1 = fetch("/srv/login?username=bob@yahoo.com&password=tom");
        String txt2 = fetch("/srv/logout?username=bob@com");
        assertTrue(txt2.contains("Failed"));
    }
    
    @Test
    public void testLogout4() throws Exception{
        String txt = fetch("/srv/register?email=bob@yahoo.com&password=tom");
        String txt1 = fetch("/srv/login?username=bob@yahoo.com&password=tom");
        String txt2 = fetch("/srv/logout?username=taco");
        assertTrue(txt2.contains("Failed"));
    }
}
