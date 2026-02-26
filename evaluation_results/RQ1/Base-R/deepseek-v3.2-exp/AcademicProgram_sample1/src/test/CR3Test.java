import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        academicProgram = new AcademicProgram();
        course = new Course();
        course.setCode("CS101");
        course.setName("Introduction to Computer Science");
        academicProgram.addCourse(course);
        
        student1 = new Student();
        student1.setStudentId("S001");
        student1.setName("John");
        student1.setSurname("Doe");
        
        student2 = new Student();
        student2.setStudentId("S002");
        student2.setName("Jane");
        student2.setSurname("Smith");
        
        student3 = new Student();
        student3.setStudentId("S003");
        student3.setName("Bob");
        student3.setSurname("Johnson");
        
        student4 = new Student();
        student4.setStudentId("S004");
        student4.setName("Alice");
        student4.setSurname("Brown");
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades.
        
        // Set up course with sufficient quota
        course.setQuota(5);
        
        // Enroll students in the course
        student1.enrollCourse(course);
        student2.enrollCourse(course);
        student3.enrollCourse(course);
        
        // Set different grades for each student
        student1.setCourseGrade(course, 85.0);
        student2.setCourseGrade(course, 92.5);
        student3.setCourseGrade(course, 78.0);
        
        // Calculate expected average: (85.0 + 92.5 + 78.0) / 3 = 255.5 / 3 = 85.166...
        double expectedAverage = (85.0 + 92.5 + 78.0) / 3;
        double actualAverage = academicProgram.calculateAverageStudentGradeInCourse(course);
        
        // Verify the average grade calculation with delta for floating point precision
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade.
        
        // Set up course with sufficient quota
        course.setQuota(5);
        
        // Enroll only one student in the course
        student1.enrollCourse(course);
        
        // Set a specific grade for the single student
        double singleGrade = 88.5;
        student1.setCourseGrade(course, singleGrade);
        
        // Calculate average - should be the single student's grade
        double actualAverage = academicProgram.calculateAverageStudentGradeInCourse(course);
        
        // Verify the average equals the single student's grade
        assertEquals("Average grade for single student course should equal the student's grade", 
                     singleGrade, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it.
        
        // Set up course but don't enroll any students
        course.setQuota(5);
        // No students enrolled
        
        // Calculate average for course with no students
        double actualAverage = academicProgram.calculateAverageStudentGradeInCourse(course);
        
        // Verify the result is 0.0 as specified in the expected output
        assertEquals("Average grade for course with no students should be 0.0", 
                     0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade.
        
        // Set up course with sufficient quota
        course.setQuota(5);
        
        // Enroll multiple students in the course
        student1.enrollCourse(course);
        student2.enrollCourse(course);
        student3.enrollCourse(course);
        student4.enrollCourse(course);
        
        // Set the same grade for all students
        double commonGrade = 90.0;
        student1.setCourseGrade(course, commonGrade);
        student2.setCourseGrade(course, commonGrade);
        student3.setCourseGrade(course, commonGrade);
        student4.setCourseGrade(course, commonGrade);
        
        // Calculate average - should be the common grade
        double actualAverage = academicProgram.calculateAverageStudentGradeInCourse(course);
        
        // Verify the average equals the common grade
        assertEquals("Average grade for students with all same grades should equal the common grade", 
                     commonGrade, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0.
        
        // Set up course with sufficient quota
        course.setQuota(5);
        
        // Enroll students in the course
        student1.enrollCourse(course);
        student2.enrollCourse(course);
        student3.enrollCourse(course);
        student4.enrollCourse(course);
        
        // Set grades: some valid, some zero
        student1.setCourseGrade(course, 75.0);  // valid grade
        student2.setCourseGrade(course, 0.0);   // zero grade
        student3.setCourseGrade(course, 82.5);  // valid grade
        student4.setCourseGrade(course, 0.0);   // zero grade
        
        // Calculate expected average: (75.0 + 0.0 + 82.5 + 0.0) / 4 = 157.5 / 4 = 39.375
        double expectedAverage = (75.0 + 0.0 + 82.5 + 0.0) / 4;
        double actualAverage = academicProgram.calculateAverageStudentGradeInCourse(course);
        
        // Verify the average calculation includes zero grades
        assertEquals("Average grade should correctly include zero grades in calculation", 
                     expectedAverage, actualAverage, 0.001);
    }
}