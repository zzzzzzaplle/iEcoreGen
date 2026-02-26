import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR3Test {

    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Course course3;

    @Before
    public void setUp() {
        // Initialize academic program and courses before each test
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course1.setCode("CS101");

        course2 = new Course();
        course2.setCode("MATH201");

        course3 = new Course();
        course3.setCode("PHY301");

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        academicProgram.setCourses(courses);
    }

    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled
        // Students with different grades
        // Expected Output: The correct average grade of all the students in that
        // specific Course within the Academic Program

        // Create enrollments with different grades for course1
        List<Enrollment> enrollments = new ArrayList<>();

        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(85.5);
        enrollments.add(enrollment1);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(92.0);
        enrollments.add(enrollment2);

        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(78.5);
        enrollments.add(enrollment3);

        course1.setEnrollments(enrollments);

        // Calculate expected average: (85.5 + 92.0 + 78.5) / 3 = 85.333...
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
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

        // Create enrollment with single student for course2
        List<Enrollment> enrollments = new ArrayList<>();

        Enrollment enrollment = new Enrollment();
        enrollment.setGrade(88.0);
        enrollments.add(enrollment);

        course2.setEnrollments(enrollments);

        double expectedAverage = 88.0;
        double actualAverage = academicProgram.calculateAverageGrade("MATH201");

        assertEquals("Average grade for single student should equal the student's grade",
                expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled
        // in it
        // Expected Output: An appropriate indication (e.g., 0 or a special value) that
        // there are no grades to calculate an average from

        // Course3 has no enrollments (empty list)
        List<Enrollment> enrollments = new ArrayList<>();
        course3.setEnrollments(enrollments);

        double expectedAverage = 0.0;
        double actualAverage = academicProgram.calculateAverageGrade("PHY301");

        assertEquals("Average grade for course with no students should be 0.0",
                expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students
        // have the same grade
        // Expected Output: The common grade value as the average grade

        // Create enrollments with all same grades for course1
        List<Enrollment> enrollments = new ArrayList<>();

        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(90.0);
        enrollments.add(enrollment1);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(90.0);
        enrollments.add(enrollment2);

        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(90.0);
        enrollments.add(enrollment3);

        course1.setEnrollments(enrollments);

        double expectedAverage = 90.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");

        assertEquals("Average grade for all same grades should equal the common grade value",
                expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with
        // valid grades and some with a grade of 0
        // Expected Output: The correct average grade obtained by considering all the
        // grades (valid and 0) of the enrolled students

        // Create enrollments with mix of valid and zero grades for course2
        List<Enrollment> enrollments = new ArrayList<>();

        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(85.0);
        enrollments.add(enrollment1);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(0.0);
        enrollments.add(enrollment2);

        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(95.0);
        enrollments.add(enrollment3);

        Enrollment enrollment4 = new Enrollment();
        enrollment4.setGrade(0.0);
        enrollments.add(enrollment4);

        course2.setEnrollments(enrollments);

        // Calculate expected average: (85.0 + 0.0 + 95.0 + 0.0) / 4 = 45.0
        double expectedAverage = (85.0 + 0.0 + 95.0 + 0.0) / 4;
        double actualAverage = academicProgram.calculateAverageGrade("MATH201");

        assertEquals("Average grade should correctly handle mix of valid and zero grades",
                expectedAverage, actualAverage, 0.001);
    }

}