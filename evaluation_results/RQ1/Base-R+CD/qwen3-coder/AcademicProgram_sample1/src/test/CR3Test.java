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

        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
    }

    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Input: An AcademicProgram with a specific Course having a list of enrolled
        // Students with different grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(85.0);
        enrollment1.setCourse(course1);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(92.5);
        enrollment2.setCourse(course1);

        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(78.0);
        enrollment3.setCourse(course1);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);

        // Expected Output: The correct average grade of all the students in that
        // specific Course within the Academic Program
        double expectedAverage = (85.0 + 92.5 + 78.0) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");

        assertEquals("Average grade should be correctly calculated for multiple students with different grades",
                expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Input: An AcademicProgram with a specific Course where only one student is
        // enrolled with a specific grade
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student1);
        enrollment.setGrade(88.5);
        enrollment.setCourse(course1);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        course1.setEnrollments(enrollments);

        // Expected Output: The grade of the single enrolled student, as it represents
        // the average grade
        double expectedAverage = 88.5;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");

        assertEquals("Average grade should equal the single student's grade",
                expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Input: An AcademicProgram with a specific Course and no students are enrolled
        // in it
        List<Enrollment> enrollments = new ArrayList<>();
        course1.setEnrollments(enrollments);

        // Expected Output: An appropriate indication (e.g., 0 or a special value) that
        // there are no grades to calculate an average from
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");

        assertEquals("Average grade should be 0.0 when no students are enrolled",
                expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Input: An AcademicProgram with a specific Course where all enrolled students
        // have the same grade
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(75.0);
        enrollment1.setCourse(course1);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(75.0);
        enrollment2.setCourse(course1);

        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(75.0);
        enrollment3.setCourse(course1);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);

        // Expected Output: The common grade value as the average grade
        double expectedAverage = 75.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");

        assertEquals("Average grade should equal the common grade value when all students have same grade",
                expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Input: An AcademicProgram with a specific Course having some students with
        // valid grades and some with a grade of 0
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(90.0);
        enrollment1.setCourse(course1);

        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(0.0);
        enrollment2.setCourse(course1);

        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(85.5);
        enrollment3.setCourse(course1);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);

        // Expected Output: The correct average grade obtained by considering all the
        // grades (valid and 0) of the enrolled students
        double expectedAverage = (90.0 + 0.0 + 85.5) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");

        assertEquals("Average grade should be correctly calculated with mix of valid and zero grades",
                expectedAverage, actualAverage, 0.001);
    }
}