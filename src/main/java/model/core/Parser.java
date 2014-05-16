/*
 * Parser for student doc HTML pages
 *
 */

package main.java.model.core;

import java.util.StringTokenizer;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

    private final static int MIN_DEGREE_TOKENS = 3;
    private final static int MIN_COURSE_TOKENS = 5;

    public Parser() {
    }

    private Student processProgram(Document doc, Student student) throws Exception {
        // TODO: On any format issue, could just log and notify the view
        //       but calculator acts on student program

        int numTokens;
        String program;
        StringTokenizer tokenizer;
        Elements elements = doc.getElementsByClass("bodytext");

        // "Input validation" - crude but should catch format changes
        if (elements.size() != 0 && elements.select("b").size() >= 2) {

            program = elements.select("b").get(1).text();

            tokenizer = new StringTokenizer(program, ",");
            numTokens = tokenizer.countTokens();

            if (numTokens != MIN_DEGREE_TOKENS) {
                throw new Exception("PROGRAM - TOKEN - Format Error");
            }

            student.setFaculty(tokenizer.nextToken().trim());
            student.setDegree(tokenizer.nextToken().trim());
            student.setMajor(tokenizer.nextToken().trim());
        }
        else {
            throw new Exception("PROGRAM - HTML - Format Error");
        }

        return student;
    }

    /**
     * Parse transcript and program docs (HTML pages).
     * Based on manual analysis of the HTML structure of
     * pages.
     *
     * @param docTranscript Transcript page (HTML)
     * @param docProgram Program page (HTML)
     * @return Student with name, program, and courses set
     * @throws Exception
     */
    public Student processStudentDocs(Document docTranscript, Document docProgram) throws Exception {
        int numTokens;
        StringTokenizer tokenizer;
        Student student = new Student();

        // Set student program
        student = processProgram(docProgram, student);

        // Now start work on the transcript
        Elements transcript = docTranscript.getElementsByClass("bodytext");

        // "Input validation" - crude but should catch format changes
        if (transcript.size() != 0 && transcript.select("b").size() != 0
                                   && transcript.select("tr").size() != 0
                                   && transcript.select("th").size() != 0) {

            // Grab only name and courses, filtering everything else
            student.setName(transcript.select("b").get(0).text());
            transcript = transcript.select("tr");
            transcript.select("th").remove();
            transcript.remove(0);

            // Read courses line by line (each course is one line)
            for (Element line : transcript) {
                Course course = new Course();
                Elements elements = line.select("td");

                course.setTerm(elements.get(0).text().trim());

                // Strip junk
                tokenizer = new StringTokenizer(elements.get(1).text().replaceAll("[^a-zA-Z\\d+.]", " "), " ");
                numTokens = tokenizer.countTokens();

                if (numTokens != MIN_COURSE_TOKENS) {
                    // TODO: If we find a bad course, log it and notify the view
                    //       but keep going
                    throw new Exception("TRANSCRIPT - COURSE TOKEN - Format Error");
                }

                course.setFaculty(tokenizer.nextToken());
                course.setDepartment(tokenizer.nextToken());
                course.setCourseCode(Integer.parseInt(tokenizer.nextToken()));
                course.setCredits(Double.parseDouble(tokenizer.nextToken()));
                course.setSection(tokenizer.nextToken());
                course.setCourseTitle(elements.get(2).text());

                // DEF and course with no '+' has extra whitespace
                course.setGrade(elements.get(3).text().trim());

                student.addCourse(course);
            }
        }
        else {
            throw new Exception("TRANSCRIPT - HTML - Format Error");
        }

        return student;
    }
}
