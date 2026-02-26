import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CR3Test {
    
    private AcademicProgram program;
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    
    @Before
    public void setUp() {
        // Initialize academic program
        program = new AcademicProgram();
        program.setName("Computer Science");
        
        // Initialize courses
        course1 = new Course();
        course1.setName("Data Structures");
        course1.setCode("CS201");
        course1.setQuotaLimit(10);
        
        course2 = new Course();
        course2.setName("Algorithms");
        course2.setCode("CS301");
        course2.setQuotaLimit(5);
        
        // Initialize students
        student1 = new Student();
        student1.setName("Alice");
        student1.setSurname("Johnson");
        student1.setStudentId("S001");
        
        student2 = new Student();
        student2.setName("Bob");
        student2.setSurname("Smith");
        student2.setStudentId("S002");
        
        student3 = new Student();
        student3.setName("Charlie");
        student3.setSurname("Brown");
        student3.setStudentId("S003");
        
        student4 = new Student();
        student4.setName("Diana");
        student4.setSurname("Lee");
        student4.setStudentId("S004");
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsWithDifferentGrades() {
        // Test Case 1: Average grade calculation for students in a specific course with different grades
        // Setup: Add course to program and enroll students with different grades
        program.addCourse(course1);
        
        // Enroll students
        student1.enrollInCourse(course1);
        student2.enrollInCourse(course1);
        student3.enrollInCourse(course1);
        
        // Set different grades
        student1.setGrade(course1, 85.0);
        student2.setGrade(course1, 92.0);
        student3.setGrade(course1, 78.0);
        
        // Calculate expected average: (85 + 92 + 78) / 3 = 85.0
        double expectedAverage = (85.0 + 92.0 + 78.0) / 3;
        
        // Verify the average grade calculation
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                     expectedAverage, course1.getAverageGrade(), 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Setup: Add course to program and enroll one student with a specific grade
        program.addCourse(course1);
        
        // Enroll single student
        student1.enrollInCourse(course1);
        
        // Set grade for the single student
        double singleGrade = 95.0;
        student1.setGrade(course1, singleGrade);
        
        // Expected output: the grade of the single enrolled student
        assertEquals("Average grade for single student course should equal the student's grade", 
                     singleGrade, course1.getAverageGrade(), 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Setup: Add course to program but no students are enrolled
        program.addCourse(course1);
        
        // No students enrolled, no grades recorded
        // Expected output: 0.0 as specified in the Course.getAverageGrade() method
        assertEquals("Average grade for course with no students should be 0.0", 
                     0.0, course1.getAverageGrade(), 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Setup: Add course to program and enroll students with identical grades
        program.addCourse(course1);
        
        // Enroll students
        student1.enrollInCourse(course1);
        student2.enrollInCourse(course1);
        student3.enrollInCourse(course1);
        student4.enrollInCourse(course1);
        
        // Set all students to have the same grade
        double commonGrade = 88.0;
        student1.setGrade(course1, commonGrade);
        student2.setGrade(course1, commonGrade);
        student3.setGrade(course1, commonGrade);
        student4.setGrade(course1, commonGrade);
        
        // Expected output: the common grade value
        assertEquals("Average grade with all same grades should equal the common grade", 
                     commonGrade, course1.getAverageGrade(), 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Setup: Add course to program and enroll students with mix of valid and zero grades
        program.addCourse(course1);
        
        // Enroll students
        student1.enrollInCourse(course1);
        student2.enrollInCourse(course1);
        student3.enrollInCourse(course1);
        student4.enrollInCourse(course1);
        
        // Set mix of valid and zero grades
        student1.setGrade(course1, 90.0);
        student2.setGrade(course1, 0.0);  // Zero grade
        student3.setGrade(course1, 85.0);
        student4.setGrade(course1, 0.0);  // Zero grade
        
        // Calculate expected average: (90 + 0 + 85 + 0) / 4 = 43.75
        double expectedAverage = (90.0 + 0.0 + 85.0 + 0.0) / 4;
        
        // Verify the average grade calculation considers all grades including zeros
        assertEquals("Average grade should correctly calculate with mix of valid and zero grades", 
                     expectedAverage, course1.getAverageGrade(), 0.001);
    }
}