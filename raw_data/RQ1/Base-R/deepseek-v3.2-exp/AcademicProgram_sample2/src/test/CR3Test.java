import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course testCourse;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        academicProgram.setProgramName("Computer Science");
        
        testCourse = new Course();
        testCourse.setCourseCode("CS101");
        testCourse.setCourseName("Introduction to Programming");
        
        academicProgram.addCourse(testCourse);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades.
        
        // Create and enroll students with different grades
        Student student1 = new Student();
        student1.setStudentId("S001");
        student1.setName("John");
        student1.recordGrade(testCourse, 85.0);
        testCourse.getEnrolledStudents().add(student1);
        
        Student student2 = new Student();
        student2.setStudentId("S002");
        student2.setName("Jane");
        student2.recordGrade(testCourse, 92.5);
        testCourse.getEnrolledStudents().add(student2);
        
        Student student3 = new Student();
        student3.setStudentId("S003");
        student3.setName("Bob");
        student3.recordGrade(testCourse, 78.0);
        testCourse.getEnrolledStudents().add(student3);
        
        // Calculate expected average: (85.0 + 92.5 + 78.0) / 3 = 255.5 / 3 = 85.166...
        double expectedAverage = (85.0 + 92.5 + 78.0) / 3.0;
        double actualAverage = academicProgram.calculateAverageGradeInCourse("CS101");
        
        // Verify the average grade calculation with delta for floating point precision
        assertEquals("Average grade should be correctly calculated for multiple students with different grades", 
                    expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade.
        
        // Create and enroll a single student with a specific grade
        Student student = new Student();
        student.setStudentId("S001");
        student.setName("Alice");
        student.recordGrade(testCourse, 95.0);
        testCourse.getEnrolledStudents().add(student);
        
        // Expected output: The grade of the single enrolled student (95.0)
        double expectedAverage = 95.0;
        double actualAverage = academicProgram.calculateAverageGradeInCourse("CS101");
        
        // Verify that the single student's grade is returned as the average
        assertEquals("Average grade for single student should equal the student's grade", 
                    expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it.
        
        // Course has no enrolled students (default state)
        
        // Expected output: 0.0 (as specified in the Course.calculateAverageGrade() method)
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.calculateAverageGradeInCourse("CS101");
        
        // Verify that 0.0 is returned when no students are enrolled
        assertEquals("Average grade should be 0.0 when no students are enrolled", 
                    expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade.
        
        // Create and enroll students with the same grade (88.5)
        Student student1 = new Student();
        student1.setStudentId("S001");
        student1.setName("Mike");
        student1.recordGrade(testCourse, 88.5);
        testCourse.getEnrolledStudents().add(student1);
        
        Student student2 = new Student();
        student2.setStudentId("S002");
        student2.setName("Sarah");
        student2.recordGrade(testCourse, 88.5);
        testCourse.getEnrolledStudents().add(student2);
        
        Student student3 = new Student();
        student3.setStudentId("S003");
        student3.setName("Tom");
        student3.recordGrade(testCourse, 88.5);
        testCourse.getEnrolledStudents().add(student3);
        
        // Expected output: The common grade value (88.5)
        double expectedAverage = 88.5;
        double actualAverage = academicProgram.calculateAverageGradeInCourse("CS101");
        
        // Verify that the average equals the common grade value
        assertEquals("Average grade should equal the common grade when all students have same grade", 
                    expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0.
        
        // Create and enroll students with mix of valid and zero grades
        Student student1 = new Student();
        student1.setStudentId("S001");
        student1.setName("Emma");
        student1.recordGrade(testCourse, 75.0);  // Valid grade
        testCourse.getEnrolledStudents().add(student1);
        
        Student student2 = new Student();
        student2.setStudentId("S002");
        student2.setName("David");
        student2.recordGrade(testCourse, 0.0);   // Zero grade
        testCourse.getEnrolledStudents().add(student2);
        
        Student student3 = new Student();
        student3.setStudentId("S003");
        student3.setName("Lisa");
        student3.recordGrade(testCourse, 90.0);  // Valid grade
        testCourse.getEnrolledStudents().add(student3);
        
        Student student4 = new Student();
        student4.setStudentId("S004");
        student4.setName("Kevin");
        student4.recordGrade(testCourse, 0.0);   // Zero grade
        testCourse.getEnrolledStudents().add(student4);
        
        // Calculate expected average: (75.0 + 0.0 + 90.0 + 0.0) / 4 = 165.0 / 4 = 41.25
        double expectedAverage = (75.0 + 0.0 + 90.0 + 0.0) / 4.0;
        double actualAverage = academicProgram.calculateAverageGradeInCourse("CS101");
        
        // Verify the average calculation includes both valid and zero grades
        assertEquals("Average grade should correctly calculate with mix of valid and zero grades", 
                    expectedAverage, actualAverage, 0.001);
    }

}