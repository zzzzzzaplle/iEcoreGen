import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        
        // Create courses
        course1 = new Course();
        course1.setCode("CS101");
        course1.setName("Introduction to Computer Science");
        
        course2 = new Course();
        course2.setCode("MATH201");
        course2.setName("Advanced Mathematics");
        
        // Create students
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        // Create enrollments
        enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        
        enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course1);
        
        enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setCourse(course1);
        
        // Set up course enrollments
        List<Enrollment> enrollmentsCourse1 = new ArrayList<>();
        course1.setEnrollments(enrollmentsCourse1);
        
        List<Enrollment> enrollmentsCourse2 = new ArrayList<>();
        course2.setEnrollments(enrollmentsCourse2);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsWithDifferentGrades() {
        // Test Case 1: Average grade calculation for students in a specific course with different grades
        // Setup: Course with multiple students having different grades
        enrollment1.setGrade(85.0);
        enrollment2.setGrade(92.5);
        enrollment3.setGrade(78.0);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Calculate expected average: (85.0 + 92.5 + 78.0) / 3 = 85.166...
        double expectedAverage = (85.0 + 92.5 + 78.0) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Assert with delta for floating point precision
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Setup: Course with only one student enrolled
        enrollment1.setGrade(88.5);
        course1.getEnrollments().add(enrollment1);
        
        double expectedAverage = 88.5; // Single student's grade
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should equal the single student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Setup: Course with no enrollments (empty enrollment list)
        // course1 enrollments remain empty as set up in @Before
        
        double expectedAverage = 0.0; // Expected when no students are enrolled
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should be 0.0 when no students are enrolled", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Setup: Course with multiple students all having the same grade
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        double expectedAverage = 90.0; // All students have same grade
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should equal the common grade value when all students have same grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Setup: Course with students having both valid grades and zero grades
        enrollment1.setGrade(85.0);  // Valid grade
        enrollment2.setGrade(0.0);   // Zero grade
        enrollment3.setGrade(92.5);  // Valid grade
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Calculate expected average: (85.0 + 0.0 + 92.5) / 3 = 59.166...
        double expectedAverage = (85.0 + 0.0 + 92.5) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should correctly consider both valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    

}