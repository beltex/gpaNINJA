package main.java.model;


/**
 * Representation of a single course taken by a student. In other words, a
 * grade in a transcript. Here is sample of what a course looks like in the
 * transcript:
 *
 * 		FW07	AP PHIL 2060  6.00 C	Jurisprudence	A
 *
 * Where the format is
 *
 * 		<TERM> <FACULTY> <DEPARTMENT> <CODE> <CREDITS> <SECTION> <TITLE> <GRADE>
 *
 * Thus
 *
 * 		FW07 		  - Term
 * 		AP 			  - Faculty
 * 		PHIL 		  - Department
 * 		2060 		  - Course code
 * 		6.00 		  - Credits
 * 		C			  - Section
 * 		Jurisprudence - Title
 * 		A			  - Grade
 */
public class Course {


    ///////////////////////////////////////////////////////////////////////////
    // PRIVATE ATTRIBUTES
    ///////////////////////////////////////////////////////////////////////////


    /**
     * Which term was this course was taken in?
     *
     * Made up of two parts, the session and the year. For example, "FW05", is
     * Fall/Winter 2005. For more, see link below.
     *
     * http://www.registrar.yorku.ca/enrol/dates/
     */
    private String term;


    /**
     * Which faculty is this course from?
     *
     * Two letter code, for example, "SC" is the Faculty of Science. For more,
     * see the link below.
     *
     * http://www.yorku.ca/web/about_yorku/faculties.html
     */
    private String faculty;


    /**
     * What is the course code?
     *
     * Four digit number. Most significant digit denotes year level. 1-4 is
     * undergrad, 5-6 is graduate. For example, 2050 is second year course.
     */
    private int code;


    /**
     * How many credits is this course worth?
     *
     * Whole numbers. However, since the credits are listed as a double on the
     * transcript, and we need to do floating point calculations with them, we
     * us double type.
     *
     * NOTE: A course could be worth zero credits. For example, CSE internship
     * (TIP) course.
     */
    private double credits;


    /**
     * Which department is this course from?
     *
     * Departments are within faculties. For example, "PHIL" is the Department
     * of Philosophy, within Faculty of Liberal Arts & Professional Studies.
     * For more, see the link below.
     *
     * http://www.yorku.ca/web/about_yorku/faculties.html
     */
    private String department;


    /**
     * Which section was the student enrolled in?
     *
     * In any term, there may be multiple time slots that the same class is
     * offered. Hence, section denotes which specific one was taken. Single
     * letter code, for example, "B" is simply section B of the course. For
     * some departments, which section the student is enrolled in matters. For
     * example, for ENG students, they must be in the ENG section of all CSE
     * courses. This is usually section "Z".
     */
    private String section;


    /**
     * What is the title of the course?
     *
     * For example, "Philosophy of Law".
     */
    private String title;


    /**
     * What grade did the student get in the course?
     *
     * This is a letter grade.
     *
     * http://www.yorku.ca/laps/students/grading.html
     */
    private String grade;


    ///////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ///////////////////////////////////////////////////////////////////////////


    public Course() {
    }


    public Course(String term,
                  String faculty,
                  String department,
                  int code,
                  double credits,
                  String section,
                  String title,
                  String grade) {
        this.term = term;
        this.faculty = faculty;
        this.department = department;
        this.code = code;
        this.credits = credits;
        this.section = section;
        this.title = title;
        this.grade = grade;
    }


    ///////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS
    ///////////////////////////////////////////////////////////////////////////


    public String toString() {
        return term + " " +
               faculty + " " +
               department + " " +
               code + " " +
               credits + " " +
               section + " " +
               title + " \t" +
               grade;
    }


    ///////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS - GETTERS
    ///////////////////////////////////////////////////////////////////////////


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


    public String getTitle() {
        return title;
    }


    public String getGrade() {
        return grade;
    }


    public int getCode() {
        return code;
    }


    public double getCredits() {
        return credits;
    }


    ///////////////////////////////////////////////////////////////////////////
    // PUBLIC METHODS - SETTERS
    ///////////////////////////////////////////////////////////////////////////


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


    public void setTitle(String title) {
        this.title = title;
    }


    public void setGrade(String grade) {
        this.grade = grade;
    }


    public void setCode(int code) {
        this.code = code;
    }


    public void setCredits(double credits) {
        this.credits = credits;
    }
}
