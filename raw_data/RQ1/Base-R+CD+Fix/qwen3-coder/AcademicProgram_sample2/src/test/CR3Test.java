import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        course = new Course();
        course.setCode("CS101");
        academicProgram.setCourses(new ArrayList<>(Arrays.asList(course)));
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Setup: Create enrollments with different grades
        List<Enrollment> enrollments = new ArrayList<>();
        
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(85.0);
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(92.5);
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(78.0);
        
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        
        course.setEnrollments(enrollments);
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Check if average is calculated correctly
        double expected = (85.0 + 92.5 + 78.0) / 3;
        assertEquals(expected, result, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: Create enrollment with single student
        List<Enrollment> enrollments = new ArrayList<>();
        
        Enrollment enrollment = new Enrollment();
        enrollment.setGrade(88.5);
        enrollments.add(enrollment);
        
        course.setEnrollments(enrollments);
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Check if single student's grade is returned as average
        assertEquals(88.5, result, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: Course with empty enrollments list
        course.setEnrollments(new ArrayList<>());
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Check if 0.0 is returned for no students
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: Create enrollments with all same grades
        List<Enrollment> enrollments = new ArrayList<>();
        
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(90.0);
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(90.0);
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(90.0);
        
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        
        course.setEnrollments(enrollments);
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Check if common grade value is returned as average
        assertEquals(90.0, result, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: Create enrollments with mix of valid and zero grades
        List<Enrollment> enrollments = new ArrayList<>();
        
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(85.0);
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(0.0);
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(92.5);
        Enrollment enrollment4 = new Enrollment();
        enrollment4.setGrade(0.0);
        
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        enrollments.add(enrollment4);
        
        course.setEnrollments(enrollments);
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Check if average includes both valid and zero grades
        double expected = (85.0 + 0.0 + 92.5 + 0.0) / 4;
        assertEquals(expected, result, 0.001);
    }
}