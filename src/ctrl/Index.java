/*
 * Index page servlet, the app controller
 *
 */

package ctrl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.HttpStatusException;

import model.NINJA;
import model.core.Student;

@WebServlet(urlPatterns={"/gpaNINJA"})
public class Index extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int NUM_PARAMS = 3;
    private static final String VAL_LOGIN = "Login";
    private static final String PATH_GPA = "/GPA.jspx";
    private static final String PATH_INDEX = "/Root.jspx";

    private static final String E1 = "E1";    // Issue with York site(s)
    private static final String E2 = "E2";    // Bad login

    public Index() {
        super();
    }

    public void init() throws ServletException {
        try {
            // Create singleton instance of NINJA class, the model controller
            // Put it in context as we need it for the life-span of the app
            this.getServletContext().setAttribute("gpaNINJA", new NINJA());
        } catch (Exception e) {
            e.printStackTrace();
            // Should exit app if init fails
            System.exit(-1);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,
                                                                                          ServletException,
                                                                                          IllegalStateException {
        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(PATH_INDEX);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,
                                                                                           ServletException,
                                                                                           IllegalStateException {
        String forwardPath = PATH_INDEX;
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (VAL_LOGIN.equals(request.getParameter("loginButton")) && username != null
                                                                  && password != null
                                                                  && request.getParameterMap().size() == NUM_PARAMS) {
            try {
                NINJA gpaNINJA = (NINJA)this.getServletContext().getAttribute("gpaNINJA");
                Student student = gpaNINJA.compute(username, password);

                request.setAttribute("student", student);
                forwardPath = PATH_GPA;
            } catch (HttpStatusException e) {
                e.printStackTrace();
                String ERROR_MSG = e.getMessage();

                if (ERROR_MSG == null || ERROR_MSG.startsWith(E1)) {
                    // The York site that handles transcripts goes down every now and
                    // then after midnight for a few hours
                    request.setAttribute("ERROR_MSG", "Looks like the York site is down");
                    response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
                }
                else if (ERROR_MSG.startsWith(E2)) {
                    request.setAttribute("ERROR_MSG", "Login failed - incorrect username or password");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }

                return;
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(forwardPath);
        dispatcher.forward(request, response);
    }
}
