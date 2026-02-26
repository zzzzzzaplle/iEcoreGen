import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course;
    private Student student1, student2, student3;
    private Enrollment enrollment1, enrollment2, enrollment3;
    
    @Before
    public void setUp() throws Exception {
        // Initialize objects for test setup
        academicProgram = new AcademicProgram();
        course = new Course();
        course.setCode("CS101");
        
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        enrollment1 = new Enrollment();
        enrollment2 = new Enrollment();
        enrollment3 = new Enrollment();
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        
        // Setup course with students having different grades
        enrollment1.setGrade(85.5);
        enrollment2.setGrade(92.0);
        enrollment3.setGrade(78.5);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        
        course.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Calculate expected average: (85.5 + 92.0 + 78.5) / 3 = 256.0 / 3 = 85.333...
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the average grade calculation with delta for floating point precision
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        
        // Setup course with one student
        enrollment1.setGrade(88.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        
        course.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Expected output: The grade of the single enrolled student (88.0)
        double expectedAverage = 88.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should equal the single student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        
        // Setup course with no enrollments
        course.setEnrollments(new ArrayList<>());
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Expected output: 0.0 (as specified in the method implementation)
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should be 0.0 for a course with no students", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        
        // Setup course with students having same grades
        enrollment1.setGrade(75.0);
        enrollment2.setGrade(75.0);
        enrollment3.setGrade(75.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        
        course.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Expected output: The common grade value (75.0)
        double expectedAverage = 75.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should equal the common grade value when all students have same grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        
        // Setup course with mix of valid and zero grades
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(0.0);
        enrollment3.setGrade(80.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        
        course.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Calculate expected average: (90.0 + 0.0 + 80.0) / 3 = 170.0 / 3 = 56.666...
        double expectedAverage = (90.0 + 0.0 + 80.0) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should correctly consider both valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
}