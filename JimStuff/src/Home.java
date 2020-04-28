
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/home"})
public class Home extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        var pw = resp.getWriter();
        var sess = req.getSession();
        var name = sess.getAttribute("name");
        pw.printf("<!DOCTYPE HTML>\n");
        pw.printf("<HTML><head><meta charset=\"utf-8\"></head>");
        pw.printf("<body>");
        if (name == null) {
            pw.printf("Don't know who you are");
            
            pw.printf("<br>" + "I dont know who you are but to sign up use link below");
            //Ethan Big help
            pw.printf("<br>" + "<a href = http://localhost:2020/srv/register>here</a>");
            
            pw.printf("<br>" + "To login in go to below link");
            
            pw.printf("<br>" + "<a href = http://localhost:2020/srv/login>here</a>");
            //Ethan is even more big help
        } else {
            pw.printf("You are " + name + "<br>");
            pw.printf("You can sign out by clicking the link below" + "<br>");
            pw.printf("<a href = http://localhost:2020/srv/logout>here</a>"+ "<br>");
        }
        pw.printf("</body></html>");
    }

}
