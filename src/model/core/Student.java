package model.core;

import java.util.ArrayList;

public class Student {

    private ArrayList<Course> courses;
    private String name,
                   faculty,				// Faculty of Science
                   degree,				// B.Sc.
                   major;				// Physics

    // TODO: Some of these attributes are program
    //       specific, move them to a student sub-class
    private double creditsPassed,
                   creditsFailed,
                   creditsSci,          // Science credits
                   creditsUpper,        // 3xxx/4xxx level credits
                   credits_NonCSEMath,  // Non CSE/Math credits
                   gpaPointsOLSAS,      // OLSAS GPA points
                   gpa_OLSAS,           // OLSAS GPA
                   gpaPoints,           // GPA points overall
                   gpa;                 // GPA overall

    public Student() {
         courses = new ArrayList<Course>();
    }

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

    // Setters

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        courses.add(course);
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
