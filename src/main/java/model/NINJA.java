/*
 * NINJA.java
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
    public Student compute(String username,
                           String password) throws Exception,
                                                   HttpStatusException {
        Student student = null;

        ArrayList<Document> docs = scraper.getDocs(username, password);
        student = parser.processStudentDocs(docs.get(0), docs.get(1));
        student = calculator.statsGeneral(student);

        return student;
    }
}
