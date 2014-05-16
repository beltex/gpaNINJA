/*
 * The model "controller" - point of contact
 *
 */

package main.java.model;

import java.util.ArrayList;

import main.java.model.core.Calculator;
import main.java.model.core.Parser;
import main.java.model.core.Student;

import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;

public class NINJA {

    private final Scraper scraper;
    private final Calculator calculator;
    private final Parser parser;

    public NINJA() {
        // All singletons
        scraper = new Scraper();
        parser = new Parser();
        calculator = new Calculator();
    }

    public Student compute(String username, String password) throws HttpStatusException, Exception {
        Student student = null;

        ArrayList<Document> docs = scraper.getDocs(username, password);
        student = parser.processStudentDocs(docs.get(0), docs.get(1));
        student = calculator.statsGeneral(student);

        return student;
    }
}
