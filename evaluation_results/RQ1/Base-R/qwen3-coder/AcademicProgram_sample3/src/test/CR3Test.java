import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course;
    private Student student1;
    private Student student2;
    private Student student3;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        academicProgram = new AcademicProgram();
        course = new Course();
        course.setCode("CS101");
        course.setName("Introduction to Computer Science");
        
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades.
        
        // Set up students with different grades
        student1.setGrade(85.5);
        student2.setGrade(92.0);
        student3.setGrade(78.5);
        
        // Enroll students in the course
        course.getEnrolledStudents().add(student1);
        course.getEnrolledStudents().add(student2);
        course.getEnrolledStudents().add(student3);
        
        // Add course to academic program
        academicProgram.getCourses().add(course);
        
        // Calculate expected average: (85.5 + 92.0 + 78.5) / 3 = 85.333...
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
        double actualAverage = academicProgram.getAverageGradeInCourse("CS101");
        
        // Verify the average grade calculation with delta for floating point precision
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                    expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade.
        
        // Set up single student with specific grade
        student1.setGrade(88.0);
        
        // Enroll only one student in the course
        course.getEnrolledStudents().add(student1);
        
        // Add course to academic program
        academicProgram.getCourses().add(course);
        
        // Calculate average - should be the student's grade itself
        double expectedAverage = 88.0;
        double actualAverage = academicProgram.getAverageGradeInCourse("CS101");
        
        // Verify the average equals the single student's grade
        assertEquals("Average grade for single student should equal the student's grade", 
                    expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it.
        
        // Course has no enrolled students (empty list by default)
        // Add course to academic program
        academicProgram.getCourses().add(course);
        
        // Calculate average for course with no students
        double actualAverage = academicProgram.getAverageGradeInCourse("CS101");
        
        // Verify that 0.0 is returned when no students are enrolled
        assertEquals("Average grade should be 0.0 when no students are enrolled", 
                    0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade.
        
        // Set up students with identical grades
        student1.setGrade(90.0);
        student2.setGrade(90.0);
        student3.setGrade(90.0);
        
        // Enroll students in the course
        course.getEnrolledStudents().add(student1);
        course.getEnrolledStudents().add(student2);
        course.getEnrolledStudents().add(student3);
        
        // Add course to academic program
        academicProgram.getCourses().add(course);
        
        // Calculate average - should be the common grade value
        double expectedAverage = 90.0;
        double actualAverage = academicProgram.getAverageGradeInCourse("CS101");
        
        // Verify the average equals the common grade value
        assertEquals("Average grade should equal the common grade when all students have same grade", 
                    expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0.
        
        // Set up students with mix of valid and zero grades
        student1.setGrade(80.0);  // Valid grade
        student2.setGrade(0.0);   // Zero grade
        student3.setGrade(70.0);  // Valid grade
        
        // Enroll students in the course
        course.getEnrolledStudents().add(student1);
        course.getEnrolledStudents().add(student2);
        course.getEnrolledStudents().add(student3);
        
        // Add course to academic program
        academicProgram.getCourses().add(course);
        
        // Calculate expected average: (80.0 + 0.0 + 70.0) / 3 = 50.0
        double expectedAverage = (80.0 + 0.0 + 70.0) / 3;
        double actualAverage = academicProgram.getAverageGradeInCourse("CS101");
        
        // Verify the average considers both valid and zero grades
        assertEquals("Average grade should correctly calculate with mix of valid and zero grades", 
                    expectedAverage, actualAverage, 0.001);
    }
}