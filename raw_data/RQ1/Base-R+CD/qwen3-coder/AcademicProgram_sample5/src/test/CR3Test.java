import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;
    private Student student3;

    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course1.setCode("CS101");
        course2 = new Course();
        course2.setCode("MATH201");

        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
    }

    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled
        // Students with different grades
        // Expected Output: The correct average grade of all the students in that
        // specific Course within the Academic Program

        // Setup course with multiple enrollments having different grades
        Enrollment enrollment1 = new Enrollment(student1, course1, 85.0);
        Enrollment enrollment2 = new Enrollment(student2, course1, 92.5);
        Enrollment enrollment3 = new Enrollment(student3, course1, 78.0);

        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);

        academicProgram.addCourse(course1);

        // Calculate expected average: (85.0 + 92.5 + 78.0) / 3 = 255.5 / 3 = 85.166...
        double expectedAverage = (85.0 + 92.5 + 78.0) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");

        assertEquals("Average grade should be correctly calculated for multiple students with different grades",
                expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is
        // enrolled with a specific grade
        // Expected Output: The grade of the single enrolled student, as it represents
        // the average grade

        // Setup course with single enrollment
        Enrollment enrollment = new Enrollment(student1, course1, 88.5);
        course1.getEnrollments().add(enrollment);
        academicProgram.addCourse(course1);

        double expectedAverage = 88.5;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");

        assertEquals("Average grade for single student should equal that student's grade",
                expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled
        // in it
        // Expected Output: An appropriate indication (e.g., 0 or a special value) that
        // there are no grades to calculate an average from

        // Setup course without any enrollments
        academicProgram.addCourse(course1);

        double expectedAverage = 0.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");

        assertEquals("Average grade for course with no students should be 0.0",
                expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students
        // have the same grade
        // Expected Output: The common grade value as the average grade

        // Setup course with multiple enrollments all having the same grade
        Enrollment enrollment1 = new Enrollment(student1, course1, 90.0);
        Enrollment enrollment2 = new Enrollment(student2, course1, 90.0);
        Enrollment enrollment3 = new Enrollment(student3, course1, 90.0);

        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);

        academicProgram.addCourse(course1);

        double expectedAverage = 90.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");

        assertEquals("Average grade should equal the common grade when all students have same grade",
                expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with
        // valid grades and some with a grade of 0
        // Expected Output: The correct average grade obtained by considering all the
        // grades (valid and 0) of the enrolled students

        // Setup course with mix of valid grades and zero grades
        Enrollment enrollment1 = new Enrollment(student1, course1, 80.0); // Valid grade
        Enrollment enrollment2 = new Enrollment(student2, course1, 0.0); // Zero grade
        Enrollment enrollment3 = new Enrollment(student3, course1, 70.0); // Valid grade
        Enrollment enrollment4 = new Enrollment(new Student(), course1, 0.0); // Another zero grade

        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        course1.getEnrollments().add(enrollment4);

        academicProgram.addCourse(course1);

        // Calculate expected average: (80.0 + 0.0 + 70.0 + 0.0) / 4 = 150.0 / 4 = 37.5
        double expectedAverage = (80.0 + 0.0 + 70.0 + 0.0) / 4;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");

        assertEquals("Average grade should correctly calculate with mix of valid and zero grades",
                expectedAverage, actualAverage, 0.001);
    }
}