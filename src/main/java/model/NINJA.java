package main.java.model;

import java.util.ArrayList;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;


/**
 * Model "controller" - single point of contact for Index servlet
 *
 */
public class NINJA {


    ///////////////////////////////////////////////////////////////////////////
    // PRIVATE ATTRIBUTES
    ///////////////////////////////////////////////////////////////////////////


    private Scraper scraper;
    private Parser parser;
    private Calculator calculator;


    ///////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////////////////////////////////


    public NINJA() {
        scraper = new Scraper();
        parser = new Parser();
        calculator = new Calculator();
    }


    ///////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ///////////////////////////////////////////////////////////////////////////


    /**
     * Compute stats for given user (student).
     *
     * @param username Passport York ID
     * @param password Password for Passport York ID
     * @return Student with stats attributes set
     * @throws HttpStatusException HTTP related issues on login and scraping
     * @throws Exception HTML format related issues
     */
    public Student compute(String username, String password) throws HttpStatusException, Exception {
        Student student = null;

        ArrayList<Document> docs = scraper.getDocs(username, password);
        student = parser.processStudentDocs(docs.get(0), docs.get(1));
        student = calculator.statsGeneral(student);

        return student;
    }
}
