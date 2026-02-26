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
        // Setup: Create one course with one enrolled student
        Course course = new Course();
        Student student = new Student();
        
        // Enroll student in course
        student.enrollInCourse(course);
        
        // Assign course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Verify total number of students is 1
        assertEquals("Single course with one student should return 1", 
                     1, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Setup: Create one course with 25 enrolled students
        Course course = new Course();
        
        // Create and enroll 25 students
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.enrollInCourse(course);
        }
        
        // Assign course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Verify total number of students is 25
        assertEquals("Single course with 25 students should return 25", 
                     25, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Setup: Create 3 courses, each with one enrolled student
        List<Course> courses = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            Student student = new Student();
            student.enrollInCourse(course);
            courses.add(course);
        }
        
        // Assign courses to instructor
        instructor.setCourses(courses);
        
        // Verify total number of students is 3
        assertEquals("Three courses with one student each should return 3", 
                     3, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Setup: Create 4 courses with varying student counts
        List<Course> courses = new ArrayList<>();
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            
            // Add specified number of students to this course
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                student.enrollInCourse(course);
            }
            
            courses.add(course);
        }
        
        // Assign courses to instructor
        instructor.setCourses(courses);
        
        // Verify total number of students is 50 (5+10+15+20)
        assertEquals("Four courses with 5,10,15,20 students should return 50", 
                     50, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Setup: Instructor has no courses (default empty list)
        // No setup needed - instructor starts with empty course list
        
        // Verify total number of students is 0
        assertEquals("Instructor with no courses should return 0", 
                     0, instructor.calculateTotalEnrolledStudents());
    }
}