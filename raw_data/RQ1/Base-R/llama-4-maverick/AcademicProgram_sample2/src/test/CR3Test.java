import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course;
    
    @Before
    public void setUp() {
        // Setup common test objects before each test
        academicProgram = new AcademicProgram();
        course = new Course();
        course.setCode("CS101");
        academicProgram.addCourse(course);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades.
        
        // Create students with different grades
        Student student1 = new Student();
        student1.setGrade(85.5);
        Student student2 = new Student();
        student2.setGrade(92.0);
        Student student3 = new Student();
        student3.setGrade(78.5);
        
        // Enroll students in the course
        course.enrollStudent(student1);
        course.enrollStudent(student2);
        course.enrollStudent(student3);
        
        // Calculate expected average: (85.5 + 92.0 + 78.5) / 3 = 85.333...
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
        double actualAverage = academicProgram.averageGradeOfCourse("CS101");
        
        // Verify the average grade calculation with delta for floating point precision
        assertEquals("Average grade should be correctly calculated for multiple students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade.
        
        // Create a single student with a specific grade
        Student student = new Student();
        student.setGrade(88.0);
        
        // Enroll the single student in the course
        course.enrollStudent(student);
        
        // Expected output is the grade of the single student (88.0)
        double expectedAverage = 88.0;
        double actualAverage = academicProgram.averageGradeOfCourse("CS101");
        
        // Verify the average grade equals the single student's grade
        assertEquals("Average grade for single student should equal that student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it.
        // Course is already created in setup with no students enrolled
        
        // Expected output: 0 (as specified in the method implementation)
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.averageGradeOfCourse("CS101");
        
        // Verify that average is 0 when no students are enrolled
        assertEquals("Average grade should be 0 when no students are enrolled", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade.
        
        // Create students with the same grade
        Student student1 = new Student();
        student1.setGrade(90.0);
        Student student2 = new Student();
        student2.setGrade(90.0);
        Student student3 = new Student();
        student3.setGrade(90.0);
        
        // Enroll students in the course
        course.enrollStudent(student1);
        course.enrollStudent(student2);
        course.enrollStudent(student3);
        
        // Expected output is the common grade value (90.0)
        double expectedAverage = 90.0;
        double actualAverage = academicProgram.averageGradeOfCourse("CS101");
        
        // Verify the average equals the common grade value
        assertEquals("Average grade should equal the common grade when all students have same grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0.
        
        // Create students with mix of valid and zero grades
        Student student1 = new Student();
        student1.setGrade(80.0);  // valid grade
        Student student2 = new Student();
        student2.setGrade(0.0);   // zero grade
        Student student3 = new Student();
        student3.setGrade(95.0);  // valid grade
        Student student4 = new Student();
        student4.setGrade(0.0);   // zero grade
        
        // Enroll students in the course
        course.enrollStudent(student1);
        course.enrollStudent(student2);
        course.enrollStudent(student3);
        course.enrollStudent(student4);
        
        // Calculate expected average: (80.0 + 0.0 + 95.0 + 0.0) / 4 = 43.75
        double expectedAverage = (80.0 + 0.0 + 95.0 + 0.0) / 4;
        double actualAverage = academicProgram.averageGradeOfCourse("CS101");
        
        // Verify the average grade calculation includes zero grades
        assertEquals("Average grade should correctly calculate with mix of valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
}