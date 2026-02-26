import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        course = new Course();
        course.setCode("CS101");
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Setup: Create enrollments with different grades
        List<Enrollment> enrollments = new ArrayList<>();
        
        Student student1 = new Student();
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(85.5);
        enrollments.add(enrollment1);
        
        Student student2 = new Student();
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(92.0);
        enrollments.add(enrollment2);
        
        Student student3 = new Student();
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(78.5);
        enrollments.add(enrollment3);
        
        course.setEnrollments(enrollments);
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Correct average grade calculation
        double expected = (85.5 + 92.0 + 78.5) / 3;
        assertEquals(expected, result, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: Create enrollment with single student
        List<Enrollment> enrollments = new ArrayList<>();
        
        Student student = new Student();
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setGrade(88.0);
        enrollments.add(enrollment);
        
        course.setEnrollments(enrollments);
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Single student's grade equals average
        assertEquals(88.0, result, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: Course with no enrollments (empty list)
        course.setEnrollments(new ArrayList<Enrollment>());
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Returns 0.0 for no enrollments
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: Create enrollments with all same grades
        List<Enrollment> enrollments = new ArrayList<>();
        
        Student student1 = new Student();
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(90.0);
        enrollments.add(enrollment1);
        
        Student student2 = new Student();
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(90.0);
        enrollments.add(enrollment2);
        
        Student student3 = new Student();
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(90.0);
        enrollments.add(enrollment3);
        
        course.setEnrollments(enrollments);
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Common grade value equals average
        assertEquals(90.0, result, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: Create enrollments with mix of valid and zero grades
        List<Enrollment> enrollments = new ArrayList<>();
        
        Student student1 = new Student();
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setGrade(75.0);
        enrollments.add(enrollment1);
        
        Student student2 = new Student();
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setGrade(0.0);
        enrollments.add(enrollment2);
        
        Student student3 = new Student();
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setGrade(85.0);
        enrollments.add(enrollment3);
        
        Student student4 = new Student();
        Enrollment enrollment4 = new Enrollment();
        enrollment4.setStudent(student4);
        enrollment4.setGrade(0.0);
        enrollments.add(enrollment4);
        
        course.setEnrollments(enrollments);
        
        // Execute: Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Correct average considering all grades including zeros
        double expected = (75.0 + 0.0 + 85.0 + 0.0) / 4;
        assertEquals(expected, result, 0.001);
    }
}