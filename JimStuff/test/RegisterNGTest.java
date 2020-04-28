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
public class RegisterNGTest {
    
    public RegisterNGTest() {
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
    public void testRegister1() throws Exception{
        String txt = fetch("/srv/register");
        assertTrue(txt.contains("ERROR"));
    }
    
    @Test
    public void testRegister2() throws Exception{
        String txt = fetch("/srv/register?email=bob@yahoo.com");
        assertTrue(txt.contains("ERROR"));
    }
    
    @Test
    public void testRegister3() throws Exception{
        String txt = fetch("/srv/register?password=bob@yahoo.com");
        assertTrue(txt.contains("ERROR"));
    }
    
    @Test
    public void testRegister4() throws Exception{
        String txt = fetch("/srv/register?email=bob@yahoo.com&password=tom");
        assertTrue(txt.contains("CREATED"));
    }
    
    @Test
    public void testRegister5() throws Exception{
        String txt = fetch("/srv/register?email=&password=tom");
        
        assertTrue(txt.contains("BADEMAIL"));
    }
    @Test
    public void testRegister6() throws Exception{
        String txt = fetch("/srv/register?email=@bob&password=tom");
        
        assertTrue(txt.contains("BADEMAIL"));
    }
    @Test
    public void testRegister7() throws Exception{
        String txt = fetch("/srv/register?email=bob&password=tom");
        
        assertTrue(txt.contains("BADEMAIL"));
    }
    @Test
    public void testRegister8() throws Exception{
        String txt = fetch("/srv/register?email=bob@&password=tom");
        
        assertTrue(txt.contains("BADEMAIL"));
    }
    
    @Test
    public void testRegister9() throws Exception{
        String txt = fetch("/srv/register?email=bob@yahoo.com&password=tom");
        String txt1 = fetch("/srv/register?email=bob@yahoo.com&password=tom");
        assertTrue(txt1.contains("DUPLICATE"));
    }
    
    
}
