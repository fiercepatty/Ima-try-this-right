import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns={"/signup"})
public class Signup extends HttpServlet
{
    static public User_Class users = User_Class.getInstance();
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var name = req.getParameter("username");
        var pass = req.getParameter("pass");
        var real = req.getParameter("name");
        if( name == null || pass == null || real == null)
        {
            pw.printf("Something is missing. Type this in format of the follwing" + "<br>");
            pw.printf(".../signup?username=Your username&pass=Your password&name=Your Real Name");
        }
        else {
            users.setusername(name);
            pw.printf("Created username is "+name);
            
            users.setpasswords(pass);
            pw.printf("\n" + "Password is " +pass);
           
            users.setname(real);
            pw.printf("\n" + "Your name is " +real);
        }
    }

}
