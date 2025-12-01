import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Course course3;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    
    @Before
    public void setUp() {
        // Initialize academic program and courses
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course1.setCode("CS101");
        course2 = new Course();
        course2.setCode("MATH201");
        course3 = new Course();
        course3.setCode("PHY301");
        
        // Initialize students
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        student4 = new Student();
        
        // Set up enrollments list for each course
        course1.setEnrollments(new ArrayList<>());
        course2.setEnrollments(new ArrayList<>());
        course3.setEnrollments(new ArrayList<>());
        
        // Add courses to academic program
        academicProgram.setCourses(Arrays.asList(course1, course2, course3));
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: "Average grade calculation for students in a specific course"
        // Input: "An AcademicProgram with a specific Course having a list of enrolled Students with different grades."
        // Expected Output: "The correct average grade of all the students in that specific Course within the Academic Program."
        
        // Create enrollments with different grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(85.0);
        enrollment1.setCourse(course1);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(92.5);
        enrollment2.setCourse(course1);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(78.0);
        enrollment3.setCourse(course1);
        
        // Add enrollments to course
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Calculate expected average: (85.0 + 92.5 + 78.0) / 3 = 85.1666...
        double expectedAverage = (85.0 + 92.5 + 78.0) / 3.0;
        
        // Test the calculation
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result with delta for floating point precision
        assertEquals("Average grade should match calculated value", expectedAverage, actualAverage, 0.0001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: "Average grade calculation for a single - student course"
        // Input: "An AcademicProgram with a specific Course where only one student is enrolled with a specific grade."
        // Expected Output: "The grade of the single enrolled student, as it represents the average grade."
        
        // Create enrollment with a specific grade
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student1);
        enrollment.setGrade(95.5);
        enrollment.setCourse(course2);
        
        // Add enrollment to course
        course2.getEnrollments().add(enrollment);
        
        // Test the calculation
        double actualAverage = academicProgram.calculateAverageGrade("MATH201");
        
        // Verify the result equals the single student's grade
        assertEquals("Average grade should equal single student's grade", 95.5, actualAverage, 0.0001);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: "Average grade calculation for a course with no students"
        // Input: "An AcademicProgram with a specific Course and no students are enrolled in it."
        // Expected Output: "An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from."
        
        // Course3 has no enrollments (empty list as set up)
        
        // This should throw IllegalArgumentException
        academicProgram.calculateAverageGrade("PHY301");
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: "Average grade calculation with all same grades"
        // Input: "An AcademicProgram with a specific Course where all enrolled students have the same grade."
        // Expected Output: "The common grade value as the average grade."
        
        // Create enrollments with same grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(88.0);
        enrollment1.setCourse(course1);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(88.0);
        enrollment2.setCourse(course1);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(88.0);
        enrollment3.setCourse(course1);
        
        // Add enrollments to course
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Test the calculation
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result equals the common grade value
        assertEquals("Average grade should equal common grade value", 88.0, actualAverage, 0.0001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: "Average grade calculation with a mix of valid and zero grades"
        // Input: "An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0."
        // Expected Output: "The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students."
        
        // Create enrollments with mix of valid and zero grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(90.0);
        enrollment1.setCourse(course2);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(0.0);
        enrollment2.setCourse(course2);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(85.0);
        enrollment3.setCourse(course2);
        
        Enrollment enrollment4 = new Enrollment();
        enrollment4.setStudent(student4);
        enrollment4.setGrade(0.0);
        enrollment4.setCourse(course2);
        
        // Add enrollments to course
        course2.getEnrollments().add(enrollment1);
        course2.getEnrollments().add(enrollment2);
        course2.getEnrollments().add(enrollment3);
        course2.getEnrollments().add(enrollment4);
        
        // Calculate expected average: (90.0 + 0.0 + 85.0 + 0.0) / 4 = 43.75
        double expectedAverage = (90.0 + 0.0 + 85.0 + 0.0) / 4.0;
        
        // Test the calculation
        double actualAverage = academicProgram.calculateAverageGrade("MATH201");
        
        // Verify the result with delta for floating point precision
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                    expectedAverage, actualAverage, 0.0001);
    }
}