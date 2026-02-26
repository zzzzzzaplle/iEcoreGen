import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private List<Enrollment> enrollments;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        academicProgram = new AcademicProgram();
        enrollments = new ArrayList<>();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Create classroom for courses
        Classroom classroom = new Classroom("C101", 50, "1", "A");
        
        try {
            // Create course1 for testing
            course1 = new Course("Mathematics", "MATH101", 
                               dateFormat.parse("2024-01-15 09:00:00"),
                               dateFormat.parse("2024-01-15 10:30:00"),
                               10, 3, classroom);
            
            // Create course2 for testing
            course2 = new Course("Physics", "PHYS101",
                               dateFormat.parse("2024-01-15 11:00:00"),
                               dateFormat.parse("2024-01-15 12:30:00"),
                               5, 3, classroom);
        } catch (Exception e) {
            fail("Date parsing failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        
        // Setup enrollments with different grades
        enrollments.clear();
        enrollments.add(new Enrollment(85.5, new Student(), course1));
        enrollments.add(new Enrollment(92.0, new Student(), course1));
        enrollments.add(new Enrollment(78.5, new Student(), course1));
        enrollments.add(new Enrollment(88.0, new Student(), course1));
        
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Calculate expected average: (85.5 + 92.0 + 78.5 + 88.0) / 4 = 86.0
        double expectedAverage = 86.0;
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify the calculated average matches expected value
        assertEquals("Average grade should be correctly calculated for multiple students with different grades", 
                    expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        
        // Setup enrollment with single student
        enrollments.clear();
        enrollments.add(new Enrollment(95.5, new Student(), course1));
        
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Expected average should be the single student's grade
        double expectedAverage = 95.5;
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify the average equals the single student's grade
        assertEquals("Average grade for single student should equal that student's grade", 
                    expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        
        // Setup course with no enrollments
        enrollments.clear();
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Expected output: 0 (as specified in the method implementation)
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify that average is 0 when no students are enrolled
        assertEquals("Average grade should be 0 when no students are enrolled", 
                    expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        
        // Setup enrollments with identical grades
        enrollments.clear();
        enrollments.add(new Enrollment(80.0, new Student(), course1));
        enrollments.add(new Enrollment(80.0, new Student(), course1));
        enrollments.add(new Enrollment(80.0, new Student(), course1));
        
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Expected average should be the common grade value (80.0)
        double expectedAverage = 80.0;
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify the average equals the common grade value
        assertEquals("Average grade should equal the common grade when all students have same grade", 
                    expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        
        // Setup enrollments with mix of valid and zero grades
        enrollments.clear();
        enrollments.add(new Enrollment(90.0, new Student(), course1));
        enrollments.add(new Enrollment(0.0, new Student(), course1));  // Zero grade
        enrollments.add(new Enrollment(85.0, new Student(), course1));
        enrollments.add(new Enrollment(0.0, new Student(), course1));  // Zero grade
        enrollments.add(new Enrollment(95.0, new Student(), course1));
        
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Calculate expected average: (90.0 + 0.0 + 85.0 + 0.0 + 95.0) / 5 = 54.0
        double expectedAverage = 54.0;
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify the average correctly includes zero grades in calculation
        assertEquals("Average grade should correctly include zero grades in calculation", 
                    expectedAverage, actualAverage, 0.01);
    }
}