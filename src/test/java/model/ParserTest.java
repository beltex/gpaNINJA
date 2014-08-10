/*
 * ParserTest.java
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

import static org.junit.Assert.*;
import model.Parser;
import model.Student;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class ParserTest {

    private final static String RESOURCE_PATH = System.getProperty("user.dir") +
                                                File.separator + "src" +
                                                File.separator + "test" +
                                                File.separator + "resources" +
                                                File.separator;

    private static Parser parser;
    private static Student student;
    private static ArrayList<Document> docs;

    @BeforeClass
    public static void init() {
        student = null;
        parser = new Parser();
        docs = new ArrayList<Document>();

        try {
            docs.add(Jsoup.parse(new File(RESOURCE_PATH + "transcript.html"),
                                 "UTF-8", "https://gpaNINJA.com/"));
            docs.add(Jsoup.parse(new File(RESOURCE_PATH + "program.html"),
                                 "UTF-8", "https://gpaNINJA.com/"));

            student = parser.processStudentDocs(docs.get(0), docs.get(1));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Test
    public void TranscriptParser_testName() {
        assertEquals(student.getName(), "Sherlock Holmes");
    }

    @Test
    public void TranscriptParser_testFaculty() {
        assertEquals(student.getFaculty(), "Faculty of Science");
    }

    @Test
    public void TranscriptParser_testDegree() {
        assertEquals(student.getDegree(), "B.Sc.");
    }

    @Test
    public void TranscriptParser_testMajor() {
        assertEquals(student.getMajor(), "Chemistry");
    }
}