
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/register"})
public class Register extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        var pw = resp.getWriter();
        var email = req.getParameter("email");
        var password = req.getParameter("password");
        if (email == null || password == null || req.getSession().getAttribute("name") != null) {
            pw.print("ERROR");
            return;
        }
        switch( AccountManager.instance.addUser(email, password)){
            case BADEMAIL:
                pw.print("BADEMAIL");
                break;
            case DUPLICATEACCOUNT:
                pw.print("DUPLICATE");
                break;
            case SUCCESS:
                pw.print("CREATED");
                break;
            default:
                throw new RuntimeException("THE WORLD IS COMING TO AN END!");
        }
    }

}
