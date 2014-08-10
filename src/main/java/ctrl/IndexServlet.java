/*
 * IndexServlet.java
 * gpaNINJA
 *
 * Copyright (C) 2014  beltex <https://github.com/beltex>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
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
import model.Student;


/**
 * Application controller. Single point of contact for client.
 *
 */
@WebServlet(urlPatterns={"/gpaNINJA"})
public class IndexServlet extends HttpServlet {


    ///////////////////////////////////////////////////////////////////////////
    // PRIVATE ATTRIBUTES
    ///////////////////////////////////////////////////////////////////////////


    private static final long serialVersionUID = 1L;


    /**
     * Index (root) page path
     */
    private static final String PATH_INDEX = "/Index.jspx";


    /**
     * Computed stats page path
     */
    private static final String PATH_GPA = "/GPA.jspx";


    /**
     * Expected number of parameters in POST request
     */
    private static final int PARAMS = 3;


    /**
     * Error code: Issue with York site(s)
     */
    private static final String E1 = "E1";


    /**
     * Error code: Bad login
     */
    private static final String E2 = "E2";


    private static final String ATTRIBUTE_MODEL = "NINJA";


    private static final String COMPUTE = "compute";


    ///////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////////////////////////////////


    public IndexServlet() {
        super();
    }


    ///////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ///////////////////////////////////////////////////////////////////////////


    public void init() throws ServletException {
        try {
            // Need this for life-span of app, hence in servlet context
            this.getServletContext().setAttribute(ATTRIBUTE_MODEL, new NINJA());
        } catch (Exception e) {
            e.printStackTrace();

            // Should exit app if init fails
            System.exit(-1);
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // PROTECTED METHODS
    ///////////////////////////////////////////////////////////////////////////


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws
                                                       IOException,
                                                       ServletException,
                                                       IllegalStateException {
        RequestDispatcher dispatcher = this.getServletContext()
                                           .getRequestDispatcher(PATH_INDEX);
        dispatcher.forward(request, response);
    }


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws
                                                        IOException,
                                                        ServletException,
                                                        IllegalStateException {
        String forwardPath = PATH_INDEX;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int paramMapSize = request.getParameterMap().size();

        if (COMPUTE.equals(request.getParameter(COMPUTE)) && username != null
                                                          && password != null
                                                          && paramMapSize ==
                                                             PARAMS) {
            try {
                NINJA gpaNINJA = (NINJA) this.getServletContext()
                                             .getAttribute(ATTRIBUTE_MODEL);
                Student student = gpaNINJA.compute(username, password);

                request.setAttribute("student", student);
                forwardPath = PATH_GPA;
            } catch (HttpStatusException e) {
                e.printStackTrace();
                String ERROR_MSG = e.getMessage();

                if (ERROR_MSG == null || ERROR_MSG.startsWith(E1)) {
                    /*
                     * York site that handles transcripts goes down every now
                     * and then after midnight for a few hours
                     */
                    request.setAttribute("ERROR_MSG", "Looks like the York " +
                                         "site is down");
                    response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
                }
                else if (ERROR_MSG.startsWith(E2)) {
                    request.setAttribute("ERROR_MSG", "Login failed - " +
                                         "incorrect username or password");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }

                return;
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }

        RequestDispatcher dispatcher = this.getServletContext()
                                           .getRequestDispatcher(forwardPath);
        dispatcher.forward(request, response);
    }
}
