/*
 * Scraper.java
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

package model;

import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * York site scraper for login to get student transcript and program
 *
 */
public class Scraper {


    ///////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////////////////////////////////////////////////


    /**
     * HTTP connection timeout default. York site is slow.
     */
    private static final int TIMEOUT_DEFAULT = 3000;


    /**
     * HTTP connection timeout for login. Higher than default, as bad login
     * causes a slow response, so to prevent a socket timeout we do this
     */
    private static final int TIMEOUT_LOGIN = 5000;


    /**
     * Data for HTTP requests
     */
    private final HashMap<String, String> REQUEST_DATA;


    /*
     * URLs need to login, logout, pull transcript, and pull program.
     */
    private static final String URL_LOGIN = "https://passportyork.yorku.ca/ppylogin/ppylogin";
    private static final String URL_LOGOUT = "https://passportyork.yorku.ca/ppylogin/ppylogout";
    private static final String URL_TRANS = "https://wrem.sis.yorku.ca/Apps/WebObjects/ydml.woa/wa/DirectAction/document?name=CourseListv1";
    private static final String URL_PROGRAM = "https://wrem.sis.yorku.ca/Apps/WebObjects/ydml.woa/wa/DirectAction/document?name=GradeReportv1";


    ///////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////////////////////////////////


    public Scraper() {
        REQUEST_DATA = new HashMap<String, String>();

        // Header
        REQUEST_DATA.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        REQUEST_DATA.put("Content-Type", "application/x-www-form-urlencoded");
        REQUEST_DATA.put("Dnt", "1");
        REQUEST_DATA.put("Origin", "https://passportyork.yorku.ca");
        REQUEST_DATA.put("Host", "wrem.sis.yorku.ca");
        REQUEST_DATA.put("Referrer", "https://passportyork.yorku.ca/ppylogin/ppylogin");
        REQUEST_DATA.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/534.59.10 (KHTML, like Gecko) Version/5.1.9 Safari/534.59.10");


        /*
         * Post request body data. Hard-coded values in the York login page
         * HTML that are passed as part of the post request.
         */
        REQUEST_DATA.put("dologin", "Login");
        REQUEST_DATA.put("__albform__", "eJx7pNB78tFWyx0aTdqVKx+wsjYw1RYyaoRyFCQWF5fnF6UUMnkzhDLn5mQWMgMZ7Cn5OfnpmXmF LECOAEhNckZiXnpqeWJeSWpKIas3Q6keAPkYHLM=");
        REQUEST_DATA.put("__albstate__", "eJxrcur3uD5lsm67zd+m9iVBVxqYagsZNUI54uMLEtNT4+MLmUJZc/LTM/MKmUM5IYLFQFGW2ELWUj0A/p8WmA==");
        REQUEST_DATA.put("__pybpp__", "eKINO6vnGvmoarBRL6EEGTA=");
    }


    ///////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ///////////////////////////////////////////////////////////////////////////


    /**
     * Scrape transcript and program (faculty/degree/major) which are
     * on separate pages, hence list of Document's returned.
     *
     * @param username Passport York ID
     * @param password Max 8 characters
     * @return ArrayList of Documents, the transcript and major HTML pages
     * @throws HttpStatusException On any HTTP related exception, we catch and
     *                             throw this.
     */
    public ArrayList<Document> getDocs(String username,
                                       String password) throws
                                                        HttpStatusException {
        Response res = null;
        ArrayList<Document> docs = new ArrayList<Document>();


        /*
         * 1st Request
         *
         * First hit the "application" (URL) to get the right cookies
         * Can't login and then access transcript page, site doesn't allow it
         */
        Connection conn = Jsoup.connect(URL_TRANS);
        conn.referrer(REQUEST_DATA.get("Referrer"));
        conn.userAgent(REQUEST_DATA.get("User-Agent"));

        conn.header("Accept", REQUEST_DATA.get("Accept"));
        conn.header("Content-Type", REQUEST_DATA.get("Content-Type"));
        conn.header("Dnt", REQUEST_DATA.get("Dnt"));
        conn.header("Origin", REQUEST_DATA.get("Origin"));
        conn.header("Host", REQUEST_DATA.get("Host"));

        try {
            res = conn.method(Method.GET).execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpStatusException("E1 - URL_TRANS - INITIAL - HTTP " +
                                          "Request Failure",
                                          res.statusCode(),
                                          URL_TRANS);
        }


        /*
         * 2nd Request
         *
         * Now login
         */
        conn.url(URL_LOGIN);
        conn.cookies(res.cookies());

        /*
         * On failed login (bad creds), socket timeout occurs, thus to prevent
         * this, we increase timeout
         */
        conn.timeout(TIMEOUT_LOGIN);

        // Post request body data for login
        conn.data("dologin", REQUEST_DATA.get("dologin"));
        conn.data("mli", username);
        conn.data("password", password);
        conn.data("__albform__", REQUEST_DATA.get("__albform__"));
        conn.data("__albstate__", REQUEST_DATA.get("__albstate__"));
        conn.data("__pybpp__", REQUEST_DATA.get("__pybpp__"));

        try {
            res = conn.method(Method.POST).execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpStatusException("E1 - URL_LOGIN - LOGIN - HTTP " +
                                          "Request Failure",
                                          res.statusCode(),
                                          URL_LOGIN);
        }


        /*
         * On failed login (bad creds), York site still returns 200 response,
         * thus we have to check for cookies
         */
        if (!res.cookies().containsKey("mayaauth") &&
            !res.cookies().containsKey("pyauth")) {
            // Login failed
            throw new HttpStatusException("E2 - URL_LOGIN - LOGIN - Bad Creds",
                                          res.statusCode(),
                                          URL_LOGIN);
        }
        conn.timeout(TIMEOUT_DEFAULT);


        /*
         * 3rd Request
         *
         * Hit the original URL to actually get the transcript
         */
        conn.url(URL_TRANS);
        conn.cookies(res.cookies());
        try {
            res = conn.method(Method.GET).execute();
            docs.add(res.parse());
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpStatusException("E1 - URL_TRANS - SECOND - HTTP " +
                                          "Request Failure",
                                          res.statusCode(),
                                          URL_TRANS);
        }


        /*
         * 4th Request
         *
         * Get program
         */
        conn.url(URL_PROGRAM);
        try {
            res = conn.method(Method.GET).execute();
            docs.add(res.parse());
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpStatusException("E1 - URL_PROGRAM - HTTP Request " +
                                          "Failure",
                                          res.statusCode(),
                                          URL_PROGRAM);
        }


        /*
         * 5th Request
         *
         * Now logout to invalidate the session cookies for security
         */
        conn.url(URL_LOGOUT);
        try {
            res = conn.method(Method.POST).execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpStatusException("E1 - URL_LOGIN - LOGOUT - HTTP " +
                                          "Request Failure",
                                          res.statusCode(),
                                          URL_LOGIN);
        }

        return docs;
    }
}
