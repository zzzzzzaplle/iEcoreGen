import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    private AcademicProgram academicProgram;
    private Course course;
    private Classroom classroom;
    private Student student1;
    private Student student2;
    private Student student3;
    
    @Before
    public void setUp() throws Exception {
        academicProgram = new AcademicProgram();
        course = new Course();
        course.setCode("CS101");
        classroom = new Classroom();
        classroom.setCapacity(50);
        course.setClassroom(classroom);
        
        // Create test students
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        academicProgram.getCourses().add(course);
    }
    
    @Test
    public void testCase1_averageGradeCalculationWithDifferentGrades() {
        // Setup: Create enrollments with different grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(85.0);
        enrollment1.setCourse(course);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(92.5);
        enrollment2.setCourse(course);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(78.5);
        enrollment3.setCourse(course);
        
        course.getEnrollments().add(enrollment1);
        course.getEnrollments().add(enrollment2);
        course.getEnrollments().add(enrollment3);
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: (85.0 + 92.5 + 78.5) / 3 = 85.333...
        assertEquals(85.333, result, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudent() {
        // Setup: Create enrollment with single student
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student1);
        enrollment.setGrade(88.0);
        enrollment.setCourse(course);
        
        course.getEnrollments().add(enrollment);
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Single student grade should be the average
        assertEquals(88.0, result, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: Course has no enrollments (empty list)
        // Enrollment list is already empty from setup
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Should return 0.0 for no students
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: Create enrollments with identical grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(90.0);
        enrollment1.setCourse(course);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(90.0);
        enrollment2.setCourse(course);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(90.0);
        enrollment3.setCourse(course);
        
        course.getEnrollments().add(enrollment1);
        course.getEnrollments().add(enrollment2);
        course.getEnrollments().add(enrollment3);
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: All grades are 90.0, average should be 90.0
        assertEquals(90.0, result, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixedValidAndZeroGrades() {
        // Setup: Create enrollments with mix of valid and zero grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(85.0);
        enrollment1.setCourse(course);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(0.0);  // Zero grade
        enrollment2.setCourse(course);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(95.0);
        enrollment3.setCourse(course);
        
        course.getEnrollments().add(enrollment1);
        course.getEnrollments().add(enrollment2);
        course.getEnrollments().add(enrollment3);
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: (85.0 + 0.0 + 95.0) / 3 = 60.0
        assertEquals(60.0, result, 0.001);
    }
}