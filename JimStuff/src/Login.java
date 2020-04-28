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
        var sess = req.getSession();
        var username = req.getParameter("username");
        var password = req.getParameter("password");
        if( password == null || username == null ){
            pw.printf("Failed need password or email");
        } else {
            if (AccountManager.login(username,password)){
                sess.setAttribute("username", username );
                pw.println("You are now logged in as:"+username);
            }
            else{
                pw.println("Already logged in or wrong password or wrong email");
            }
        }
    }

}
