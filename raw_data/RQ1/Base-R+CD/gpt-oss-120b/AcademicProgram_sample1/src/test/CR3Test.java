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
    private Student student1;
    private Student student2;
    private Student student3;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course1.setCode("CS101");
        
        course2 = new Course();
        course2.setCode("MATH201");
        
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        enrollment1 = new Enrollment();
        enrollment2 = new Enrollment();
        enrollment3 = new Enrollment();
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsWithDifferentGrades() {
        // Test Case 1: Average grade calculation for students in a specific course with different grades
        // Setup: Course with multiple students having different grades
        course1.setCode("CS101");
        
        enrollment1.setGrade(85.0);
        enrollment2.setGrade(92.5);
        enrollment3.setGrade(78.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        academicProgram.addCourse(course1);
        
        // Calculate expected average: (85.0 + 92.5 + 78.0) / 3 = 255.5 / 3 = 85.166...
        double expectedAverage = (85.0 + 92.5 + 78.0) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Setup: Course with only one student enrolled
        course1.setCode("CS101");
        
        enrollment1.setGrade(88.5);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        course1.setEnrollments(enrollments);
        
        academicProgram.addCourse(course1);
        
        // Expected output: The grade of the single enrolled student (88.5)
        double expectedAverage = 88.5;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade for single student should equal that student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Setup: Course with no enrollments
        course1.setCode("CS101");
        
        // No enrollments added to course1 (empty enrollments list)
        List<Enrollment> enrollments = new ArrayList<>();
        course1.setEnrollments(enrollments);
        
        academicProgram.addCourse(course1);
        
        // Expected output: 0.0 (as specified in the method implementation)
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade for course with no students should be 0.0", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Setup: Course where all students have the same grade
        course1.setCode("CS101");
        
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        academicProgram.addCourse(course1);
        
        // Expected output: The common grade value (90.0)
        double expectedAverage = 90.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade when all students have same grade should be that common grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Setup: Course with some students having valid grades and some with grade 0
        course1.setCode("CS101");
        
        enrollment1.setGrade(80.0);
        enrollment2.setGrade(0.0);  // Zero grade
        enrollment3.setGrade(95.0);
        enrollment1.setGrade(0.0);  // Another zero grade
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        academicProgram.addCourse(course1);
        
        // Calculate expected average: (0.0 + 0.0 + 95.0) / 3 = 95.0 / 3 = 31.666...
        double expectedAverage = (0.0 + 0.0 + 95.0) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
}