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
        academicProgram.setCourses(new ArrayList<>());
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsWithDifferentGrades() {
        // Setup: Create course with multiple students having different grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(85.5);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(92.0);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(78.5);
        
        List<Enrollment> enrollments = Arrays.asList(enrollment1, enrollment2, enrollment3);
        course.setEnrollments(enrollments);
        
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade is calculated
        double expected = (85.5 + 92.0 + 78.5) / 3;
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                     expected, result, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: Create course with only one student
        Enrollment enrollment = new Enrollment();
        enrollment.setGrade(88.0);
        
        List<Enrollment> enrollments = Arrays.asList(enrollment);
        course.setEnrollments(enrollments);
        
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The single student's grade is returned as the average
        assertEquals("Average grade should equal the single student's grade", 
                     88.0, result, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: Create course with no enrollments
        course.setEnrollments(new ArrayList<>());
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Returns 0.0 when no students are enrolled
        assertEquals("Should return 0.0 for course with no students", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: Create course with multiple students having identical grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(90.0);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(90.0);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(90.0);
        
        List<Enrollment> enrollments = Arrays.asList(enrollment1, enrollment2, enrollment3);
        course.setEnrollments(enrollments);
        
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The common grade value is returned as the average
        assertEquals("Average grade should equal the common grade value", 
                     90.0, result, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: Create course with students having valid grades and zero grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(85.0);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(0.0);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(92.5);
        
        Enrollment enrollment4 = new Enrollment();
        enrollment4.setGrade(0.0);
        
        List<Enrollment> enrollments = Arrays.asList(enrollment1, enrollment2, enrollment3, enrollment4);
        course.setEnrollments(enrollments);
        
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Correct average considering all grades including zeros
        double expected = (85.0 + 0.0 + 92.5 + 0.0) / 4;
        assertEquals("Average grade should correctly consider both valid and zero grades", 
                     expected, result, 0.001);
    }
}