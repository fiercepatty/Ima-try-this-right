
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MainNGTest {

    public MainNGTest() {
    }

    static CookieManager cookieManager = new CookieManager();

    @BeforeClass
    public static void setupcookie() {
        CookieHandler.setDefault(cookieManager);

    }

    @BeforeMethod
    public void clearCookie() {
        cookieManager.getCookieStore().removeAll();
    }

    @BeforeClass
    public static void setup() {
        Main.startOrStopJetty(true);
    }

    @AfterClass
    public static void teardown() {
        Main.startOrStopJetty(false);
    }

    @BeforeMethod
    public void clearDB() {
        fetch("/srv/clear");
    }

    static String fetch(String... allurls) {
        try {
            String str = null;
            byte[] returnedData = new byte[]{0};  //dummy
            for (String oneurl : allurls) {
                var url = new URL("http://localhost:2020" + oneurl);
                var conn = url.openConnection();
                conn.connect();
                var istr = conn.getInputStream();
                returnedData = istr.readAllBytes();
            }
            return new String(returnedData, 0, returnedData.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSignup() throws UnsupportedEncodingException {
        String resp = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assert (resp.contains("CREATED"));
    }

    @Test
    public void testSignup2() throws UnsupportedEncodingException {
        String resp = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assert (resp.contains("CREATED"));

        resp = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("xyz", "UTF-8")
        );
        assert (resp.contains("ERROR"));

    }

    @Test
    public void testSignup3() throws UnsupportedEncodingException {
        String resp = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assert (resp.contains("CREATED"));

        resp = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("alice@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("xyz", "UTF-8")
        );
        assert (resp.contains("ERROR"));

    }

    @Test
    public void testSignupDuplicateNoLogin() throws UnsupportedEncodingException {
        String resp = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assert (resp.contains("CREATED"));
        cookieManager.getCookieStore().removeAll();
        resp = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("xyz", "UTF-8")
        );
        assert (resp.contains("DUPLICATE"));
        resp = fetch("/srv/who");
        assertTrue(resp.contains("Don't know who you are"));
    }

    @Test
    public void testSignupLogin() throws UnsupportedEncodingException {
        String resp = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertEquals(resp, "CREATED");
        resp = fetch("/srv/who");
        assertTrue(resp.contains("You are bob@example.com"));
    }

    @Test
    public void testMissingEmail() throws UnsupportedEncodingException {
        String resp = fetch("/srv/register"
                + "?password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assert (resp.contains("ERROR"));
    }

    @Test
    public void testMissingPassword() throws UnsupportedEncodingException {
        String resp = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@exameple.com", "UTF-8")
        );
        assert (resp.contains("ERROR"));
    }

    @Test
    public void testMissingEverything() throws UnsupportedEncodingException {
        String resp = fetch("/srv/register");
        assert (resp.contains("ERROR"));
    }

    @Test
    public void testSignupBadEmail() throws UnsupportedEncodingException {
        String resp = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bobexample.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertEquals(resp, "BADEMAIL");
    }

    @Test
    public void testSignupBadEmail2() throws UnsupportedEncodingException {
        String resp = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("@example.com", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertEquals(resp, "BADEMAIL");
    }

    @Test
    public void testSignupBadEmail3() throws UnsupportedEncodingException {
        String resp = fetch("/srv/register"
                + "?email=" + URLEncoder.encode("bob@", "UTF-8")
                + "&password=" + URLEncoder.encode("s3cr3t", "UTF-8")
        );
        assertEquals(resp, "BADEMAIL");
    }
}
