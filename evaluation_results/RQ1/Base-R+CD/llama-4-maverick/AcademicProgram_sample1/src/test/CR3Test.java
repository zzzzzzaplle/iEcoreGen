import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CR3Test {
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;
    private Student student3;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;
    private Enrollment enrollment4;
    private Enrollment enrollment5;

    @Before
    public void setUp() {
        // Initialize AcademicProgram
        academicProgram = new AcademicProgram();
        
        // Create courses
        Classroom classroom = new Classroom("A101", 30, "1", "A");
        List<String> courseDays = Arrays.asList("Monday", "Wednesday");
        Date startTime = new Date();
        Date endTime = new Date();
        
        course1 = new Course("Mathematics", "MATH101", startTime, endTime, courseDays, 30, 4, classroom);
        course2 = new Course("Physics", "PHYS101", startTime, endTime, courseDays, 30, 4, classroom);
        
        // Create students
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        // Create enrollments
        enrollment1 = new Enrollment(student1, course1);
        enrollment2 = new Enrollment(student2, course1);
        enrollment3 = new Enrollment(student3, course1);
        enrollment4 = new Enrollment(student1, course2);
        enrollment5 = new Enrollment(student2, course2);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
    }

    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: "Average grade calculation for students in a specific course"
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        // Expected Output: The correct average grade of all the students in that specific Course within the Academic Program
        
        // Set different grades for enrollments in course1
        enrollment1.setGrade(85.5);
        enrollment2.setGrade(92.0);
        enrollment3.setGrade(78.5);
        
        // Add enrollments to course1
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Calculate expected average: (85.5 + 92.0 + 78.5) / 3 = 256.0 / 3 = 85.333...
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify the average grade is calculated correctly with a delta for floating point precision
        assertEquals("Average grade should be calculated correctly for multiple students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: "Average grade calculation for a single-student course"
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        // Expected Output: The grade of the single enrolled student, as it represents the average grade
        
        // Set grade for single enrollment in course1
        enrollment1.setGrade(88.0);
        
        // Add only one enrollment to course1
        course1.getEnrollments().add(enrollment1);
        
        // Calculate average grade
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify the average grade equals the single student's grade
        assertEquals("Average grade should equal the single student's grade", 
                     88.0, actualAverage, 0.001);
    }

    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: "Average grade calculation for a course with no students"
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        // Expected Output: An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from
        
        // Course1 has no enrollments (empty list)
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify the method returns 0 when there are no students enrolled
        assertEquals("Average grade should be 0 when no students are enrolled", 
                     0.0, actualAverage, 0.001);
    }

    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: "Average grade calculation with all same grades"
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        // Expected Output: The common grade value as the average grade
        
        // Set same grade for all enrollments in course1
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);
        
        // Add enrollments to course1
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Calculate average grade
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify the average grade equals the common grade value
        assertEquals("Average grade should equal the common grade value when all students have the same grade", 
                     90.0, actualAverage, 0.001);
    }

    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: "Average grade calculation with a mix of valid and zero grades"
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        // Expected Output: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students
        
        // Set mixed grades for enrollments in course1 (some valid, some zero)
        enrollment1.setGrade(80.0);
        enrollment2.setGrade(0.0);
        enrollment3.setGrade(70.0);
        
        // Add enrollments to course1
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Calculate expected average: (80.0 + 0.0 + 70.0) / 3 = 150.0 / 3 = 50.0
        double expectedAverage = (80.0 + 0.0 + 70.0) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify the average grade is calculated correctly including zero grades
        assertEquals("Average grade should be calculated correctly including zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
}