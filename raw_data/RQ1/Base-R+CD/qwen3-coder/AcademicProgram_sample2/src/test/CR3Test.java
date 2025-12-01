import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {

    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;
    private Student student3;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;

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

        enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);

        enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course1);

        enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setCourse(course1);
    }

    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled
        // Students with different grades.

        // Setup course with students having different grades
        course1.addEnrollment(enrollment1);
        course1.addEnrollment(enrollment2);
        course1.addEnrollment(enrollment3);

        enrollment1.setGrade(85.0);
        enrollment2.setGrade(92.5);
        enrollment3.setGrade(78.0);

        academicProgram.addCourse(course1);

        // Calculate average grade
        double averageGrade = academicProgram.calculateAverageGrade("CS101");

        // Expected Output: The correct average grade of all the students in that
        // specific Course within the Academic Program.
        double expectedAverage = (85.0 + 92.5 + 78.0) / 3;
        assertEquals("Average grade should be correctly calculated for multiple students with different grades",
                expectedAverage, averageGrade, 0.001);
    }

    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is
        // enrolled with a specific grade.

        // Setup course with single student
        course1.addEnrollment(enrollment1);
        enrollment1.setGrade(88.5);

        academicProgram.addCourse(course1);

        // Calculate average grade
        double averageGrade = academicProgram.calculateAverageGrade("CS101");

        // Expected Output: The grade of the single enrolled student, as it represents
        // the average grade.
        assertEquals("Average grade for single student should equal the student's grade",
                88.5, averageGrade, 0.001);
    }

    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled
        // in it.

        // Setup course with no enrollments
        academicProgram.addCourse(course1);

        // Calculate average grade
        double averageGrade = academicProgram.calculateAverageGrade("CS101");

        // Expected Output: An appropriate indication (e.g., 0 or a special value) that
        // there are no grades to calculate an average from.
        assertEquals("Average grade for course with no students should be 0.0",
                0.0, averageGrade, 0.001);
    }

    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students
        // have the same grade.

        // Setup course with students having same grades
        course1.addEnrollment(enrollment1);
        course1.addEnrollment(enrollment2);
        course1.addEnrollment(enrollment3);

        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);

        academicProgram.addCourse(course1);

        // Calculate average grade
        double averageGrade = academicProgram.calculateAverageGrade("CS101");

        // Expected Output: The common grade value as the average grade.
        assertEquals("Average grade for all same grades should equal the common grade value",
                90.0, averageGrade, 0.001);
    }

    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with
        // valid grades and some with a grade of 0.

        // Setup course with mix of valid and zero grades
        course1.addEnrollment(enrollment1);
        course1.addEnrollment(enrollment2);
        course1.addEnrollment(enrollment3);

        enrollment1.setGrade(85.0);
        enrollment2.setGrade(0.0); // Zero grade
        enrollment3.setGrade(95.0);

        academicProgram.addCourse(course1);

        // Calculate average grade
        double averageGrade = academicProgram.calculateAverageGrade("CS101");

        // Expected Output: The correct average grade obtained by considering all the
        // grades (valid and 0) of the enrolled students.
        double expectedAverage = (85.0 + 0.0 + 95.0) / 3;
        assertEquals("Average grade should correctly handle mix of valid and zero grades",
                expectedAverage, averageGrade, 0.001);
    }
}