import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
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
        course1.setName("Introduction to Computer Science");
        
        course2 = new Course();
        course2.setCode("MATH201");
        course2.setName("Advanced Mathematics");
        
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        enrollment1 = new Enrollment();
        enrollment2 = new Enrollment();
        enrollment3 = new Enrollment();
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Setup: Create AcademicProgram with specific Course having enrolled Students with different grades
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Setup enrollments with different grades
        enrollment1.setGrade(85.0);
        enrollment2.setGrade(92.5);
        enrollment3.setGrade(78.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        // Calculate expected average: (85.0 + 92.5 + 78.0) / 3 = 85.16666666666667
        double expectedAverage = (85.0 + 92.5 + 78.0) / 3;
        
        // Execute: Calculate average grade for course CS101
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade of all students in that specific course
        assertEquals(expectedAverage, actualAverage, 0.0001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: AcademicProgram with specific Course where only one student is enrolled
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Setup single enrollment with specific grade
        enrollment1.setGrade(88.5);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        course1.setEnrollments(enrollments);
        
        // Execute: Calculate average grade for course CS101
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The grade of the single enrolled student (88.5)
        assertEquals(88.5, actualAverage, 0.0001);
    }
    
    @Test(expected = IllegalStateException.class)
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: AcademicProgram with specific Course and no enrolled students
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Course1 has empty enrollments list (default)
        
        // Execute and Verify: Should throw IllegalStateException when no students enrolled
        academicProgram.calculateAverageGrade("CS101");
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: AcademicProgram with specific Course where all enrolled students have same grade
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Setup enrollments with same grades
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        // Execute: Calculate average grade for course CS101
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The common grade value (90.0) as the average grade
        assertEquals(90.0, actualAverage, 0.0001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: AcademicProgram with specific Course having students with valid grades and zero grades
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Setup enrollments with mix of valid and zero grades
        enrollment1.setGrade(85.0);  // valid grade
        enrollment2.setGrade(0.0);   // zero grade
        enrollment3.setGrade(92.5);  // valid grade
        Enrollment enrollment4 = new Enrollment();
        enrollment4.setGrade(0.0);  // zero grade
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        enrollments.add(enrollment4);
        course1.setEnrollments(enrollments);
        
        // Calculate expected average: (85.0 + 0.0 + 92.5 + 0.0) / 4 = 44.375
        double expectedAverage = (85.0 + 0.0 + 92.5 + 0.0) / 4;
        
        // Execute: Calculate average grade for course CS101
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade considering all grades (valid and 0)
        assertEquals(expectedAverage, actualAverage, 0.0001);
    }

}