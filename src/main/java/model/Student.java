package main.java.model;

import java.util.ArrayList;


/**
 * Representation of a student. Includes name, program, transcript, and stats
 * (computed by Calculator class).
 *
 */
public class Student {


    ///////////////////////////////////////////////////////////////////////////
    // PRIVATE ATTRIBUTES
    ///////////////////////////////////////////////////////////////////////////


    /**
     * What courses has the student taken?
     *
     * This is basically the students transcript
     */
    private ArrayList<Course> courses;


    /**
     * What is the student's name?
     */
    private String name;


    /**
     * What faculty does this student belong to?
     *
     * This is the full name of the faculty, not the code like in Course class.
     * For example, "Faculty of Environmental Studies". For more, see the link
     * below.
     *
     * http://www.yorku.ca/web/about_yorku/faculties.html
     */
    private String faculty;


    /**
     * What degree is this student pursing?
     *
     * For example, "B.Sc.", Bachelor of Science.
     */
    private String degree;


    /**
     * What major is the student in?
     *
     * For example, "Physics".
     */
    private String major;


    ///////////////////////////////////////////////////////////////////////////
    // PRIVATE ATTRIBUTES - STATS
    ///////////////////////////////////////////////////////////////////////////


    /**
     * How many credits has this student passed?
     *
     */
    private double creditsPassed;


    /**
     * How many credits has this student failed?
     */
    private double creditsFailed;


    /**
     * How many science credits does this student have?
     *
     * These are any courses from SC or LE faculties.
     */
    private double creditsSci;


    /**
     * How many 3xxx/4xxx level credits does this student have?
     *
     * Based on course code.
     */
    private double creditsUpper;


    /**
     * How many non CSE/MATH credits does this student have?
     *
     * Based on department of the course.
     */
    private double credits_NonCSEMath;


    /**
     *
     */
    private double gpaPointsOLSAS;      // OLSAS GPA points


    /**
     *
     */
    private double gpaPoints;           // GPA points overall


    /**
     *
     */
    private double gpa_OLSAS;


    /**
     * What is the overall GPA (grade point average) of this student on the
     * York grading scale (out of 9.0)?
     */
    private double gpa;


    ///////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////////////////////////////////


    public Student() {
         courses = new ArrayList<Course>();
    }


    ///////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ///////////////////////////////////////////////////////////////////////////


    public String toString() {
        return name + "\n" +
               faculty + ", " + degree + ", " + major + "\n";
    }


    /**
     * Add a course to the students list of courses (transcript).
     *
     * @param course Course to be added
     * @return true (as specified by Collection.add)
     */
    public boolean addCourse(Course course) {
        return courses.add(course);
    }


    ///////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS - GETTERS
    ///////////////////////////////////////////////////////////////////////////


    public ArrayList<Course> getCourses() {
        return courses;
    }


    public String getName() {
        return name;
    }


    public String getFaculty() {
        return faculty;
    }


    public String getDegree() {
        return degree;
    }


    public String getMajor() {
        return major;
    }


    public double getCreditsTaken() {
        return creditsPassed + creditsFailed;
    }


    public double getCreditsPassed() {
        return creditsPassed;
    }


    public double getCreditsFailed() {
        return creditsFailed;
    }


    public double getCreditsSci() {
        return creditsSci;
    }


    public double getCreditsUpper() {
        return creditsUpper;
    }


    public double getCredits_NonCSEMath() {
        return credits_NonCSEMath;
    }


    public double getGpaPointsOLSAS() {
        return gpaPointsOLSAS;
    }


    public double getGpa_OLSAS() {
        return gpa_OLSAS;
    }


    public double getGpaPoints() {
        return gpaPoints;
    }


    public double getGpa() {
        return gpa;
    }


    ///////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS - SETTERS
    ///////////////////////////////////////////////////////////////////////////


    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }


    public void setDegree(String degree) {
        this.degree = degree;
    }


    public void setMajor(String major) {
        this.major = major;
    }


    public void setCreditsPassed(double creditsPassed) {
        this.creditsPassed = creditsPassed;
    }


    public void setCreditsFailed(double creditsFailed) {
        this.creditsFailed = creditsFailed;
    }


    public void setCreditsSci(double creditsSci) {
        this.creditsSci = creditsSci;
    }


    public void setCreditsUpper(double creditsUpper) {
        this.creditsUpper = creditsUpper;
    }


    public void setCredits_NonCSEMath(double credits_NonCSEMath) {
        this.credits_NonCSEMath = credits_NonCSEMath;
    }


    public void setGpaPointsOLSAS(double gpaPointsOLSAS) {
        this.gpaPointsOLSAS = gpaPointsOLSAS;
    }


    public void setGpa_OLSAS(double gpa_OLSAS) {
        this.gpa_OLSAS = gpa_OLSAS;
    }


    public void setGpaPoints(double gpaPoints) {
        this.gpaPoints = gpaPoints;
    }


    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
}
