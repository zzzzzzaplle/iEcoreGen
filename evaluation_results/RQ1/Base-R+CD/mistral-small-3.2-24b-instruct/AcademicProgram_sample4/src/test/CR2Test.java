import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private Instructor instructor;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Setup: Create a single course with one enrolled student
        Course course = new Course();
        Enrollment enrollment = new Enrollment();
        enrollment.setGrade(85.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        course.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Execute and verify
        assertEquals("Single course with one student should return 1", 
                     1, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Setup: Create a single course with 25 enrolled students
        Course course = new Course();
        List<Enrollment> enrollments = new ArrayList<>();
        
        for (int i = 0; i < 25; i++) {
            Enrollment enrollment = new Enrollment();
            enrollment.setGrade(75.0 + i);
            enrollments.add(enrollment);
        }
        
        course.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Execute and verify
        assertEquals("Single course with 25 students should return 25", 
                     25, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Setup: Create 3 courses, each with one enrolled student
        List<Course> courses = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            Enrollment enrollment = new Enrollment();
            enrollment.setGrade(80.0 + i);
            
            List<Enrollment> enrollments = new ArrayList<>();
            enrollments.add(enrollment);
            course.setEnrollments(enrollments);
            
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Execute and verify
        assertEquals("Three courses with one student each should return 3", 
                     3, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Setup: Create 4 courses with varying student counts: 5, 10, 15, 20
        List<Course> courses = new ArrayList<>();
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int count : studentCounts) {
            Course course = new Course();
            List<Enrollment> enrollments = new ArrayList<>();
            
            for (int i = 0; i < count; i++) {
                Enrollment enrollment = new Enrollment();
                enrollment.setGrade(70.0 + i);
                enrollments.add(enrollment);
            }
            
            course.setEnrollments(enrollments);
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Execute and verify
        assertEquals("Four courses with 5+10+15+20 students should return 50", 
                     50, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Setup: Instructor has no courses assigned (empty list)
        instructor.setCourses(new ArrayList<Course>());
        
        // Execute and verify
        assertEquals("Instructor with no courses should return 0", 
                     0, instructor.calculateTotalEnrolledStudents());
    }
}