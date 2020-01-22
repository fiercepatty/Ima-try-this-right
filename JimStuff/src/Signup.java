import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet(urlPatterns={"/login"})
public class Signup extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/plain");
        var us = resp.getWriter();
        var user = req.getParameter("username");
        if( user == null ){
            us.printf("No username provided");
        } else {
            String sess[];
            var sess = req.getSession();
            sess.setAttribute("name", user );
            us.printf("Logged in as "+user);
        }
    }

}
