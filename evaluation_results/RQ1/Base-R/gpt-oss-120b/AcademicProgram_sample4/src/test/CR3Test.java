import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram program;
    private Course course;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        program = new AcademicProgram();
        course = new Course();
        course.setCode("CS101");
        program.addCourse(course);
        
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        student4 = new Student();
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        // Expected Output: The correct average grade of all the students in that specific Course within the Academic Program
        
        // Setup: Enroll students with different grades
        student1.setGradeForCourse(course, 85.0);
        student2.setGradeForCourse(course, 92.0);
        student3.setGradeForCourse(course, 78.0);
        
        // Enroll students in the course
        course.setQuotaLimit(10); // Set sufficient quota
        student1.enrollInCourse(course);
        student2.enrollInCourse(course);
        student3.enrollInCourse(course);
        
        // Calculate expected average: (85 + 92 + 78) / 3 = 85.0
        double expectedAverage = (85.0 + 92.0 + 78.0) / 3;
        double actualAverage = course.getAverageGrade();
        
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        // Expected Output: The grade of the single enrolled student, as it represents the average grade
        
        // Setup: Enroll one student with a specific grade
        student1.setGradeForCourse(course, 95.0);
        
        // Enroll the student in the course
        course.setQuotaLimit(10);
        student1.enrollInCourse(course);
        
        // Expected average should be the single student's grade
        double expectedAverage = 95.0;
        double actualAverage = course.getAverageGrade();
        
        assertEquals("Average grade should equal the single student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        // Expected Output: An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from
        
        // No students enrolled in the course
        double actualAverage = course.getAverageGrade();
        
        // Expected output is 0.0 as per the implementation
        assertEquals("Average grade should be 0.0 when no students are enrolled", 
                     0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        // Expected Output: The common grade value as the average grade
        
        // Setup: Enroll students with identical grades
        student1.setGradeForCourse(course, 88.0);
        student2.setGradeForCourse(course, 88.0);
        student3.setGradeForCourse(course, 88.0);
        student4.setGradeForCourse(course, 88.0);
        
        // Enroll students in the course
        course.setQuotaLimit(10);
        student1.enrollInCourse(course);
        student2.enrollInCourse(course);
        student3.enrollInCourse(course);
        student4.enrollInCourse(course);
        
        // Expected average should be the common grade value
        double expectedAverage = 88.0;
        double actualAverage = course.getAverageGrade();
        
        assertEquals("Average grade should equal the common grade value when all students have same grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        // Expected Output: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students
        
        // Setup: Enroll students with mix of valid grades and zero grades
        student1.setGradeForCourse(course, 75.0);  // Valid grade
        student2.setGradeForCourse(course, 0.0);   // Zero grade
        student3.setGradeForCourse(course, 90.0);  // Valid grade
        student4.setGradeForCourse(course, 0.0);   // Zero grade
        
        // Enroll students in the course
        course.setQuotaLimit(10);
        student1.enrollInCourse(course);
        student2.enrollInCourse(course);
        student3.enrollInCourse(course);
        student4.enrollInCourse(course);
        
        // Calculate expected average: (75 + 0 + 90 + 0) / 4 = 41.25
        double expectedAverage = (75.0 + 0.0 + 90.0 + 0.0) / 4;
        double actualAverage = course.getAverageGrade();
        
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
}