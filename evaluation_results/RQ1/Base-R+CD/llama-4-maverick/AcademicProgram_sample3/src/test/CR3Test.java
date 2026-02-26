import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
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
    public void setUp() throws Exception {
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course1.setCode("CS101");
        course2 = new Course();
        course2.setCode("MATH201");
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
        
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        
        enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course1);
        
        enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setCourse(course1);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsWithDifferentGrades() {
        // Setup: Course with students having different grades
        enrollment1.setGrade(85.5);
        enrollment2.setGrade(72.0);
        enrollment3.setGrade(90.5);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade (85.5 + 72.0 + 90.5) / 3 = 82.666...
        assertEquals(82.666, result, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: Course with only one student enrolled
        enrollment1.setGrade(88.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        course1.setEnrollments(enrollments);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The grade of the single student (88.0)
        assertEquals(88.0, result, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: Course with no enrolled students (empty enrollments list)
        course1.setEnrollments(new ArrayList<Enrollment>());
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Returns 0 as specified for no students
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: Course with all students having the same grade
        enrollment1.setGrade(75.0);
        enrollment2.setGrade(75.0);
        enrollment3.setGrade(75.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The common grade value (75.0)
        assertEquals(75.0, result, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: Course with some valid grades and some zero grades
        enrollment1.setGrade(95.0);  // Valid grade
        enrollment2.setGrade(0.0);   // Zero grade
        enrollment3.setGrade(85.0);  // Valid grade
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Correct average including zero grades (95.0 + 0.0 + 85.0) / 3 = 60.0
        assertEquals(60.0, result, 0.001);
    }
}