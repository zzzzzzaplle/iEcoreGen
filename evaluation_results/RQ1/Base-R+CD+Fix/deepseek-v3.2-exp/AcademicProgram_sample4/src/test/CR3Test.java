import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR3Test {
    private AcademicProgram academicProgram;
    private Course course;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        course = new Course();
        course.setCode("CS101");
    }
    
    @Test
    public void testCase1_AverageGradeCalculationForMultipleStudents() {
        // Setup: Create academic program with a course having multiple students with different grades
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
        
        course.setEnrollments(enrollments);
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade of all students (85.5 + 92.0 + 78.5) / 3 = 85.333...
        double expected = (85.5 + 92.0 + 78.5) / 3;
        assertEquals(expected, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageGradeCalculationForSingleStudent() {
        // Setup: Create academic program with a course having only one student
        List<Enrollment> enrollments = new ArrayList<>();
        
        Enrollment enrollment = new Enrollment();
        enrollment.setGrade(88.0);
        enrollments.add(enrollment);
        
        course.setEnrollments(enrollments);
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The grade of the single student (88.0) should be returned as the average
        assertEquals(88.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageGradeCalculationForCourseWithNoStudents() {
        // Setup: Create academic program with a course having no enrolled students
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Should return 0.0 when no students are enrolled
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageGradeCalculationWithAllSameGrades() {
        // Setup: Create academic program with a course where all students have the same grade
        List<Enrollment> enrollments = new ArrayList<>();
        
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(75.0);
        enrollments.add(enrollment1);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(75.0);
        enrollments.add(enrollment2);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(75.0);
        enrollments.add(enrollment3);
        
        course.setEnrollments(enrollments);
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The common grade value (75.0) should be returned as the average
        assertEquals(75.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: Create academic program with a course having mix of valid and zero grades
        List<Enrollment> enrollments = new ArrayList<>();
        
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(90.0);
        enrollments.add(enrollment1);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(0.0);
        enrollments.add(enrollment2);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(80.0);
        enrollments.add(enrollment3);
        
        Enrollment enrollment4 = new Enrollment();
        enrollment4.setGrade(0.0);
        enrollments.add(enrollment4);
        
        course.setEnrollments(enrollments);
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Correct average considering all grades including zeros (90.0 + 0.0 + 80.0 + 0.0) / 4 = 42.5
        double expected = (90.0 + 0.0 + 80.0 + 0.0) / 4;
        assertEquals(expected, result, 0.001);
    }
}