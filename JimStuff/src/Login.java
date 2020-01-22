import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet(urlPatterns={"/login"})
public class Login extends HttpServlet
{
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var name = req.getParameter("user");
        if( name == null ){
            pw.printf("No username provided");
        } else {
            var sess = req.getSession();
            sess.setAttribute("name", name );
            pw.printf("Logged in as "+name);
        }
    }

}
