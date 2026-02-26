import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;
    private Course course5;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    private Student student5;
    private Student student6;
    private Student student7;
    
    @Before
    public void setUp() {
        // Initialize AcademicProgram and Courses for testing
        academicProgram = new AcademicProgram();
        
        course1 = new Course();
        course1.setCode("CS101");
        
        course2 = new Course();
        course2.setCode("MATH201");
        
        course3 = new Course();
        course3.setCode("PHY301");
        
        course4 = new Course();
        course4.setCode("CHEM401");
        
        course5 = new Course();
        course5.setCode("BIO501");
        
        // Add courses to academic program
        academicProgram.addCourse(course1);
        academicProgram.addCourse(course2);
        academicProgram.addCourse(course3);
        academicProgram.addCourse(course4);
        academicProgram.addCourse(course5);
        
        // Initialize students
        student1 = new Student();
        student1.setGrade(85.5);
        
        student2 = new Student();
        student2.setGrade(92.0);
        
        student3 = new Student();
        student3.setGrade(78.5);
        
        student4 = new Student();
        student4.setGrade(88.0);
        
        student5 = new Student();
        student5.setGrade(95.5);
        
        student6 = new Student();
        student6.setGrade(0.0);
        
        student7 = new Student();
        student7.setGrade(82.0);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        
        // Setup course with multiple students having different grades
        course1.enrollStudent(student1); // Grade: 85.5
        course1.enrollStudent(student2); // Grade: 92.0
        course1.enrollStudent(student3); // Grade: 78.5
        
        // Calculate expected average: (85.5 + 92.0 + 78.5) / 3 = 256.0 / 3 = 85.333...
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3.0;
        
        // Call the method under test
        double actualAverage = academicProgram.averageGradeInCourse("CS101");
        
        // Verify the result with delta for floating point precision
        assertEquals("Average grade should be correctly calculated for multiple students", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        
        // Setup course with only one student
        course2.enrollStudent(student4); // Grade: 88.0
        
        // Expected output: The grade of the single enrolled student (88.0)
        double expectedAverage = 88.0;
        
        // Call the method under test
        double actualAverage = academicProgram.averageGradeInCourse("MATH201");
        
        // Verify the result
        assertEquals("Average grade should equal the single student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        
        // Course3 has no students enrolled
        
        // Expected output: 0 (as specified in the method implementation)
        double expectedAverage = 0.0;
        
        // Call the method under test
        double actualAverage = academicProgram.averageGradeInCourse("PHY301");
        
        // Verify the result
        assertEquals("Average grade should be 0 for course with no students", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        
        // Setup students with same grade
        Student sameGradeStudent1 = new Student();
        sameGradeStudent1.setGrade(90.0);
        
        Student sameGradeStudent2 = new Student();
        sameGradeStudent2.setGrade(90.0);
        
        Student sameGradeStudent3 = new Student();
        sameGradeStudent3.setGrade(90.0);
        
        // Enroll students in course
        course4.enrollStudent(sameGradeStudent1);
        course4.enrollStudent(sameGradeStudent2);
        course4.enrollStudent(sameGradeStudent3);
        
        // Expected output: The common grade value (90.0)
        double expectedAverage = 90.0;
        
        // Call the method under test
        double actualAverage = academicProgram.averageGradeInCourse("CHEM401");
        
        // Verify the result
        assertEquals("Average grade should equal the common grade value", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        
        // Setup course with mix of valid and zero grades
        course5.enrollStudent(student5); // Grade: 95.5 (valid)
        course5.enrollStudent(student6); // Grade: 0.0 (zero)
        course5.enrollStudent(student7); // Grade: 82.0 (valid)
        
        // Calculate expected average: (95.5 + 0.0 + 82.0) / 3 = 177.5 / 3 = 59.166...
        double expectedAverage = (95.5 + 0.0 + 82.0) / 3.0;
        
        // Call the method under test
        double actualAverage = academicProgram.averageGradeInCourse("BIO501");
        
        // Verify the result with delta for floating point precision
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
}