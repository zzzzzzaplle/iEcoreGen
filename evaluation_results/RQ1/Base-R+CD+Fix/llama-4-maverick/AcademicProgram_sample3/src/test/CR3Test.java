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
    private Student student1;
    private Student student2;
    private Student student3;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;
    
    @Before
    public void setUp() {
        // Initialize academic program and courses before each test
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course1.setCode("CS101");
        
        course2 = new Course();
        course2.setCode("MATH201");
        
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        enrollment1 = new Enrollment();
        enrollment2 = new Enrollment();
        enrollment3 = new Enrollment();
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course with different grades
        // Setup course with multiple enrollments and different grades
        course1.getEnrollments().clear();
        
        enrollment1.setGrade(85.5);
        enrollment2.setGrade(92.0);
        enrollment3.setGrade(78.5);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        academicProgram.getCourses().add(course1);
        
        // Calculate expected average: (85.5 + 92.0 + 78.5) / 3 = 256.0 / 3 = 85.333...
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
        
        // Call the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result with delta for floating point precision
        assertEquals("Average grade should be correctly calculated for multiple students with different grades", 
                      expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Setup course with only one enrollment
        course1.getEnrollments().clear();
        
        enrollment1.setGrade(88.0);
        course1.getEnrollments().add(enrollment1);
        
        academicProgram.getCourses().add(course1);
        
        // Expected output is the single student's grade
        double expectedAverage = 88.0;
        
        // Call the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result
        assertEquals("Average grade for single student should equal that student's grade", 
                      expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Setup course with no enrollments
        course1.getEnrollments().clear();
        
        academicProgram.getCourses().add(course1);
        
        // Expected output is 0.0 as specified in the method implementation
        double expectedAverage = 0.0;
        
        // Call the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result
        assertEquals("Average grade for course with no students should be 0.0", 
                      expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Setup course with multiple enrollments all having the same grade
        course1.getEnrollments().clear();
        
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        academicProgram.getCourses().add(course1);
        
        // Expected output is the common grade value
        double expectedAverage = 90.0;
        
        // Call the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result
        assertEquals("Average grade for all same grades should equal the common grade value", 
                      expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Setup course with enrollments having both valid grades and zero grades
        course1.getEnrollments().clear();
        
        enrollment1.setGrade(85.0);
        enrollment2.setGrade(0.0);  // Zero grade
        enrollment3.setGrade(95.0);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        academicProgram.getCourses().add(course1);
        
        // Calculate expected average: (85.0 + 0.0 + 95.0) / 3 = 180.0 / 3 = 60.0
        double expectedAverage = (85.0 + 0.0 + 95.0) / 3;
        
        // Call the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                      expectedAverage, actualAverage, 0.001);
    }
}