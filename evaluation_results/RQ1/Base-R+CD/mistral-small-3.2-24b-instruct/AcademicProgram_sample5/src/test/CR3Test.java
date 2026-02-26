import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    private AcademicProgram academicProgram;
    private Course course;
    private Classroom classroom;
    private Student student1;
    private Student student2;
    private Student student3;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;

    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        course = new Course();
        classroom = new Classroom();
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        enrollment1 = new Enrollment();
        enrollment2 = new Enrollment();
        enrollment3 = new Enrollment();

        // Set up classroom
        classroom.setCapacity(50);

        // Set up course
        course.setCode("CS101");
        course.setClassroom(classroom);
        course.setEnrollments(new ArrayList<>());

        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
    }

    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Setup: Create enrollments with different grades
        enrollment1.setGrade(85.5);
        enrollment2.setGrade(92.0);
        enrollment3.setGrade(78.5);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course.setEnrollments(enrollments);

        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");

        // Verify: Check if average is calculated correctly
        double expected = (85.5 + 92.0 + 78.5) / 3;
        assertEquals(expected, result, 0.001);
    }

    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: Create enrollment with single student
        enrollment1.setGrade(88.0);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        course.setEnrollments(enrollments);

        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");

        // Verify: Check if single student's grade is returned as average
        assertEquals(88.0, result, 0.001);
    }

    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: Course has empty enrollment list
        course.setEnrollments(new ArrayList<>());

        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");

        // Verify: Check if 0.0 is returned for no students
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: Create enrollments with same grades
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course.setEnrollments(enrollments);

        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");

        // Verify: Check if common grade value is returned as average
        assertEquals(90.0, result, 0.001);
    }

    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: Create enrollments with mix of valid and zero grades
        enrollment1.setGrade(85.0);
        enrollment2.setGrade(0.0);
        enrollment3.setGrade(92.5);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course.setEnrollments(enrollments);

        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");

        // Verify: Check if average correctly considers zero grades
        double expected = (85.0 + 0.0 + 92.5) / 3;
        assertEquals(expected, result, 0.001);
    }
}