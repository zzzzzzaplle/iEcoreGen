import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private List<Enrollment> enrollments;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course1.setCode("CS101");
        course2 = new Course();
        course2.setCode("MATH201");
        
        enrollments = new ArrayList<>();
        academicProgram.setCourses(new ArrayList<>());
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Setup: Create a course with multiple students having different grades
        course1.setCode("CS101");
        
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(85.0);
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(92.5);
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(78.0);
        
        List<Enrollment> courseEnrollments = new ArrayList<>();
        courseEnrollments.add(enrollment1);
        courseEnrollments.add(enrollment2);
        courseEnrollments.add(enrollment3);
        course1.setEnrollments(courseEnrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade should be calculated
        double expectedAverage = (85.0 + 92.5 + 78.0) / 3;
        assertEquals(expectedAverage, result, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: Create a course with only one student
        course1.setCode("CS101");
        
        Enrollment enrollment = new Enrollment();
        enrollment.setGrade(88.5);
        
        List<Enrollment> courseEnrollments = new ArrayList<>();
        courseEnrollments.add(enrollment);
        course1.setEnrollments(courseEnrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The grade of the single student should be returned as average
        assertEquals(88.5, result, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: Create a course with no enrolled students
        course1.setCode("CS101");
        course1.setEnrollments(new ArrayList<>());
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Should return 0.0 when no students are enrolled
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: Create a course where all students have the same grade
        course1.setCode("CS101");
        
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(90.0);
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(90.0);
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(90.0);
        
        List<Enrollment> courseEnrollments = new ArrayList<>();
        courseEnrollments.add(enrollment1);
        courseEnrollments.add(enrollment2);
        courseEnrollments.add(enrollment3);
        course1.setEnrollments(courseEnrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The common grade value should be returned as average
        assertEquals(90.0, result, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: Create a course with students having valid grades and zero grades
        course1.setCode("CS101");
        
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(85.0);
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(0.0);
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(92.5);
        Enrollment enrollment4 = new Enrollment();
        enrollment4.setGrade(0.0);
        
        List<Enrollment> courseEnrollments = new ArrayList<>();
        courseEnrollments.add(enrollment1);
        courseEnrollments.add(enrollment2);
        courseEnrollments.add(enrollment3);
        courseEnrollments.add(enrollment4);
        course1.setEnrollments(courseEnrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average considering all grades (including zeros)
        double expectedAverage = (85.0 + 0.0 + 92.5 + 0.0) / 4;
        assertEquals(expectedAverage, result, 0.001);
    }
    

}