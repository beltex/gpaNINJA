/*
 * Calculator that computes stats from given student courses
 *
 */
package model.core;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

public class Calculator {

    private final HashMap<String, Double> YORK_GRADES;
    private final HashSet<String> YORK_GRADES_SPECIAL;

    // For 4.0 scale - same as OMSAS
    // http://www.ouac.on.ca/docs/olsas/c_olsas_b.pdf
    // http://www.ouac.on.ca/docs/omsas/c_omsas_b.pdf
    private final HashMap<String, Double> OLSAS_2014;

    public Calculator() {
        YORK_GRADES = new HashMap<String, Double>();
        YORK_GRADES.put("A+", 9.0);
        YORK_GRADES.put("A", 8.0);
        YORK_GRADES.put("B+", 7.0);
        YORK_GRADES.put("B", 6.0);
        YORK_GRADES.put("C+", 5.0);
        YORK_GRADES.put("C", 4.0);
        YORK_GRADES.put("D+", 3.0);
        YORK_GRADES.put("D", 2.0);
        YORK_GRADES.put("E", 1.0);
        YORK_GRADES.put("F", 0.0);
        YORK_GRADES.put("P", -1.0);

        YORK_GRADES_SPECIAL = new HashSet<String>();
        YORK_GRADES_SPECIAL.add("NCR");
        YORK_GRADES_SPECIAL.add("A+ NCR");
        YORK_GRADES_SPECIAL.add("A NCR");
        YORK_GRADES_SPECIAL.add("B+ NCR");
        YORK_GRADES_SPECIAL.add("B NCR");
        YORK_GRADES_SPECIAL.add("C+ NCR");
        YORK_GRADES_SPECIAL.add("C NCR");
        YORK_GRADES_SPECIAL.add("D+ NCR");
        YORK_GRADES_SPECIAL.add("D NCR");
        YORK_GRADES_SPECIAL.add("E NCR");
        YORK_GRADES_SPECIAL.add("F NCR");
        YORK_GRADES_SPECIAL.add("NGR");
        YORK_GRADES_SPECIAL.add("F NGR");
        YORK_GRADES_SPECIAL.add("DEF");
        YORK_GRADES_SPECIAL.add("\u00A0");	// Non-break space - waiting on grade
        YORK_GRADES_SPECIAL.add("in progress");

        OLSAS_2014 = new HashMap<String, Double>();
        OLSAS_2014.put("A+", 4.0);
        OLSAS_2014.put("A", 3.8);
        OLSAS_2014.put("B+", 3.3);
        OLSAS_2014.put("B", 3.0);
        OLSAS_2014.put("C+", 2.3);
        OLSAS_2014.put("C", 2.0);
        OLSAS_2014.put("D+", 1.3);
        OLSAS_2014.put("D", 1.0);
        OLSAS_2014.put("E", 0.0);
        OLSAS_2014.put("F", 0.0);
    }

    // Stats only relevant to ENG/CSE students - one of many possible special cases
    // For the time being, will only support this department
    // Lots of manual work required to get everyone of them
    // It's ugly, but good enough for now
    private Student statsLE(Student student) {
        double creditsSci = 0.0;
        double creditsUpper = 0.0;
        double credits_NonCSEMath = 0.0;
        ArrayList<Course> courses = student.getCourses();

        for (Course course : courses) {
            String grade = course.getGrade();

            if (YORK_GRADES.containsKey(grade)) {
                double gradeValue = YORK_GRADES.get(grade);

                if (gradeValue >= YORK_GRADES.get("D") || gradeValue == YORK_GRADES.get("P")) {
                    String faculty = course.getFaculty();
                    String department = course.getDepartment();
                    int courseCode = course.getCourseCode();
                    double courseCredits = course.getCredits();

                    // Get 3xxx/4xxx credits
                    if (courseCode >= 3000) {
                        creditsUpper += courseCredits;
                    }

                    // Non-CSE/MATH credits
                    if (!department.equals("MATH") && !department.equals("CSE")) {
                        credits_NonCSEMath += courseCredits;
                    }

                    // Get science credits
                    // AS courses could cause more damage then good
                    if (faculty.equals("SC") || faculty.equals("LE")) {
                        creditsSci += courseCredits;
                    }
                }
            }
        }

        student.setCreditsSci(creditsSci);
        student.setCreditsUpper(creditsUpper);
        student.setCredits_NonCSEMath(credits_NonCSEMath);

        return student;
    }

    /**
     * Computes stats that are relevant to any student and can be
     * certain about it's correctness. Credits, GPA, etc.
     *
     * @param student
     * @return Student with stats set
     */
    public Student statsGeneral(Student student) {
        ArrayList<Course> courseList = student.getCourses();
        double creditsPassed = 0.0;
        double creditsFailed = 0.0;
        double creditsPassFailCourse = 0.0;     // Not part of GPA

        double gpaPoints = 0.0;                 // Used in conjunction with creditsPassed
        double gpaPointsOLSAS = 0.0;

        for (Course course : courseList) {
            String grade = course.getGrade();
            double creditsCourse = course.getCredits();

            if (YORK_GRADES.containsKey(grade)) {
                double gradeValue = YORK_GRADES.get(grade);

                if (gradeValue >= YORK_GRADES.get("D")) {
                    // Passed the course
                    creditsPassed += creditsCourse;
                    gpaPoints += gradeValue * creditsCourse;
                    gpaPointsOLSAS += OLSAS_2014.get(grade) * creditsCourse;
                }
                else if (gradeValue == YORK_GRADES.get("P")) {
                    // Pass/Fail course
                    // Could be a 0 credit course
                    // NOTE: This grade doesn't count towards GPA,
                    //		 thus, we added these credits to creditsPassed
                    //		 at the end, after GPA has been computed
                    creditsPassFailCourse += creditsCourse;
                }
                else {
                    // Failed the course
                    // NOTE: No way of telling if this is an actual F or an F
                    //       from a Pass/Fail course (which doesn't count towards GPA).
                    //       We default to the more common case, an actual F.

                    // TODO: Note this to the view

                    // NOTE 2: To fix this, need extra meta-data about the grade. There are
                    //         defined Pass/Fail courses, getting this list will help. But, a
                    //         student can always apply to have a course converted to Pass/Fail.
                    creditsFailed += creditsCourse;
                }
            }
            else {
                // Special cases which we ignore
                // Want to log unknown special case grades to learn if
                // any corner cases are being missed
                if (!YORK_GRADES_SPECIAL.contains(grade)) {
                    System.out.println("UNKNOWN BAD GRADE: [" + grade + "]");
                }
            }
        }

        student.setCreditsPassed(creditsPassed + creditsPassFailCourse);
        student.setCreditsFailed(creditsFailed);
        student.setGpaPoints(gpaPoints);
        student.setGpaPointsOLSAS(gpaPointsOLSAS);
        student.setGpa_OLSAS(gpaPointsOLSAS/creditsPassed);
        student.setGpa(gpaPoints/creditsPassed);

        // HACK ALERT: This is bad, a temp solution for program specific stats
        if (student.getFaculty().equals("Lassonde School of Engineering")) {
            student = statsLE(student);
        }

        return student;
    }
}
