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
        // Initialize common objects before each test
        academicProgram = new AcademicProgram();
        course = new Course();
        academicProgram.setCourses(Arrays.asList(course));
    }
    
    @Test
    public void testCase1_averageGradeCalculationForMultipleStudents() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: AcademicProgram with a Course having multiple students with different grades
        // Expected Output: Correct average grade of all students
        
        // Create students with different grades
        Student student1 = new Student();
        student1.setGrade(85.0);
        
        Student student2 = new Student();
        student2.setGrade(92.0);
        
        Student student3 = new Student();
        student3.setGrade(78.0);
        
        // Add students to course
        course.setStudents(Arrays.asList(student1, student2, student3));
        
        // Calculate expected average: (85 + 92 + 78) / 3 = 85.0
        double expectedAverage = 85.0;
        double actualAverage = course.getAverageGrade();
        
        assertEquals("Average grade should be correctly calculated for multiple students", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudent() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: AcademicProgram with a Course where only one student is enrolled
        // Expected Output: The grade of the single enrolled student
        
        // Create a single student with specific grade
        Student student = new Student();
        student.setGrade(88.5);
        
        // Add student to course
        course.setStudents(Arrays.asList(student));
        
        // Expected average should be the student's grade
        double expectedAverage = 88.5;
        double actualAverage = course.getAverageGrade();
        
        assertEquals("Average grade for single student should equal student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: AcademicProgram with a Course and no enrolled students
        // Expected Output: 0 (as per implementation)
        
        // Ensure course has no students (empty list)
        course.setStudents(new ArrayList<>());
        
        double expectedAverage = 0.0;
        double actualAverage = course.getAverageGrade();
        
        assertEquals("Average grade for course with no students should be 0", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: AcademicProgram with a Course where all students have same grade
        // Expected Output: The common grade value
        
        // Create students with identical grades
        Student student1 = new Student();
        student1.setGrade(75.0);
        
        Student student2 = new Student();
        student2.setGrade(75.0);
        
        Student student3 = new Student();
        student3.setGrade(75.0);
        
        // Add students to course
        course.setStudents(Arrays.asList(student1, student2, student3));
        
        // Expected average should be the common grade value
        double expectedAverage = 75.0;
        double actualAverage = course.getAverageGrade();
        
        assertEquals("Average grade should equal common grade value when all students have same grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with mix of valid and zero grades
        // Input: AcademicProgram with a Course having students with valid and zero grades
        // Expected Output: Correct average considering all grades including zeros
        
        // Create students with mix of valid and zero grades
        Student student1 = new Student();
        student1.setGrade(90.0);  // Valid grade
        
        Student student2 = new Student();
        student2.setGrade(0.0);   // Zero grade
        
        Student student3 = new Student();
        student3.setGrade(80.0);  // Valid grade
        
        Student student4 = new Student();
        student4.setGrade(0.0);   // Zero grade
        
        // Add students to course
        course.setStudents(Arrays.asList(student1, student2, student3, student4));
        
        // Calculate expected average: (90 + 0 + 80 + 0) / 4 = 42.5
        double expectedAverage = 42.5;
        double actualAverage = course.getAverageGrade();
        
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
}