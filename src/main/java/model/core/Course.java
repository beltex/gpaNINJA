package main.java.model.core;

public class Course {

    private String term,            // FW77
                   faculty,         // AP
                   department,      // PHIL
                   section,         // B
                   courseTitle,     // Philosophy of Law
                   grade;           // A

    private int courseCode;         // 2050
    private double credits;         // 6.00

    public Course() {
    }

    public Course(String term,
                  String faculty,
                  String department,
                  int courseCode,
                  double credits,
                  String section,
                  String courseTitle,
                  String grade) {
        this.term = term;
        this.faculty = faculty;
        this.department = department;
        this.courseCode = courseCode;
        this.credits = credits;
        this.section = section;
        this.courseTitle = courseTitle;
        this.grade = grade;
    }

    public String toString() {
        return term + " " +
               faculty + " " +
               department + " " +
               courseCode + " " +
               credits + " " +
               section + " " +
               courseTitle + " \t" +
               grade;
    }

    // Getters

    public String getTerm() {
        return term;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getDepartment() {
        return department;
    }

    public String getSection() {
        return section;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getGrade() {
        return grade;
    }

    public int getCourseCode() {
        return courseCode;
    }

    public double getCredits() {
        return credits;
    }

    // Setters

    public void setTerm(String term) {
        this.term = term;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public void setCredits(double credits) {
        this.credits = credits;
    }
}
