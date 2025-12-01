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
    private List<Enrollment> enrollments1;
    private List<Enrollment> enrollments2;
    
    @Before
    public void setUp() {
        // Initialize academic program and courses for testing
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course1.setCode("CS101");
        course2 = new Course();
        course2.setCode("MATH201");
        
        // Initialize enrollment lists
        enrollments1 = new ArrayList<>();
        enrollments2 = new ArrayList<>();
        
        course1.setEnrollments(enrollments1);
        course2.setEnrollments(enrollments2);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        // Expected Output: The correct average grade of all the students in that specific Course within the Academic Program
        
        // Create enrollments with different grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(85.0);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(92.0);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(78.0);
        
        enrollments1.add(enrollment1);
        enrollments1.add(enrollment2);
        enrollments1.add(enrollment3);
        
        // Calculate expected average: (85 + 92 + 78) / 3 = 85.0
        double expectedAverage = (85.0 + 92.0 + 78.0) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should be correctly calculated for multiple students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        // Expected Output: The grade of the single enrolled student, as it represents the average grade
        
        // Create a single enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setGrade(88.5);
        enrollments1.add(enrollment);
        
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade for single student should equal the student's grade", 
                     88.5, actualAverage, 0.001);
    }
    
    @Test(expected = ArithmeticException.class)
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        // Expected Output: An ArithmeticException should be thrown
        
        // enrollments1 is empty (no students enrolled)
        academicProgram.calculateAverageGrade("CS101");
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        // Expected Output: The common grade value as the average grade
        
        // Create multiple enrollments with the same grade
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(90.0);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(90.0);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(90.0);
        
        enrollments1.add(enrollment1);
        enrollments1.add(enrollment2);
        enrollments1.add(enrollment3);
        
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should equal the common grade when all students have same grade", 
                     90.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        // Expected Output: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students
        
        // Create enrollments with mix of valid and zero grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(80.0);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(0.0);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(70.0);
        
        Enrollment enrollment4 = new Enrollment();
        enrollment4.setGrade(0.0);
        
        enrollments1.add(enrollment1);
        enrollments1.add(enrollment2);
        enrollments1.add(enrollment3);
        enrollments1.add(enrollment4);
        
        // Calculate expected average: (80 + 0 + 70 + 0) / 4 = 37.5
        double expectedAverage = (80.0 + 0.0 + 70.0 + 0.0) / 4;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    

}