import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CR3Test {
    private AcademicProgram academicProgram;
    private Course course;
    private Student student1;
    private Student student2;
    private Student student3;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;
    private Classroom classroom;

    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        course = new Course();
        course.setCode("CS101");
        classroom = new Classroom();
        classroom.setCapacity(50);
        course.setClassroom(classroom);
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        enrollment1 = new Enrollment();
        enrollment2 = new Enrollment();
        enrollment3 = new Enrollment();
    }

    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Set up: Create AcademicProgram with specific Course having multiple enrolled Students with different grades
        enrollment1.setGrade(85.0);
        enrollment2.setGrade(92.5);
        enrollment3.setGrade(78.0);
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course.setEnrollments(enrollments);
        academicProgram.getCourses().add(course);

        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");

        // Verify: Correct average grade is calculated
        double expectedAverage = (85.0 + 92.5 + 78.0) / 3;
        assertEquals(expectedAverage, result, 0.001);
    }

    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Set up: Create AcademicProgram with specific Course having one enrolled student with a grade
        enrollment1.setGrade(88.0);
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        course.setEnrollments(enrollments);
        academicProgram.getCourses().add(course);

        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");

        // Verify: The single student's grade is returned as average
        assertEquals(88.0, result, 0.001);
    }

    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Set up: Create AcademicProgram with specific Course and no enrolled students
        course.setEnrollments(new ArrayList<>());
        academicProgram.getCourses().add(course);

        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");

        // Verify: Returns 0.0 as there are no grades to average
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Set up: Create AcademicProgram with specific Course where all students have same grade
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course.setEnrollments(enrollments);
        academicProgram.getCourses().add(course);

        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");

        // Verify: The common grade value is returned as average
        assertEquals(90.0, result, 0.001);
    }

    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Set up: Create AcademicProgram with specific Course having mix of valid and zero grades
        enrollment1.setGrade(80.0);
        enrollment2.setGrade(0.0);
        enrollment3.setGrade(70.0);
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course.setEnrollments(enrollments);
        academicProgram.getCourses().add(course);

        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");

        // Verify: Correct average considering all grades (including zeros)
        double expectedAverage = (80.0 + 0.0 + 70.0) / 3;
        assertEquals(expectedAverage, result, 0.001);
    }
}