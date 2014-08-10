/*
 * CalculatorTest.java
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
import model.Calculator;
import model.Course;
import model.Student;

import org.junit.BeforeClass;
import org.junit.Test;

public class CalculatorTest {

    private final static Double DELTA = 0.01;
    private static Student student;
    private static Calculator calculator;
    private static final String[] GRADES = {"\u00A0",
                                            "in progress",
                                            "F NGR",
                                            "NGR",
                                            "NCR",
                                            "DEF",
                                            "P",
                                            "F",
                                            "E",
                                            "D",
                                            "D+",
                                            "C",
                                            "C+",
                                            "B",
                                            "B+",
                                            "A",
                                            "A+"};

    @BeforeClass
    public static void init() {
        student = new Student();
        calculator = new Calculator();
        student.setFaculty("Faculty of Science");

        for (int i = 0; i < GRADES.length; i++) {
            Course course = new Course();
            course.setTerm("FW89");
            course.setFaculty("SC");
            course.setDepartment("CHEM");
            course.setCode(1000);
            course.setCredits(3);
            course.setSection("A");
            course.setTitle("Intro Chem");
            course.setGrade(GRADES[i]);
            student.addCourse(course);
        }

        student = calculator.statsGeneral(student);
    }

    @Test
    public void Calculator_testCreditsTaken() {
        assertEquals(33, student.getCreditsTaken(), DELTA);
    }

    @Test
    public void Calculator_testCreditsPassed() {
        assertEquals(27, student.getCreditsPassed(), DELTA);
    }

    @Test
    public void Calculator_testCreditsFailed() {
        assertEquals(6, student.getCreditsFailed(), DELTA);
    }
}