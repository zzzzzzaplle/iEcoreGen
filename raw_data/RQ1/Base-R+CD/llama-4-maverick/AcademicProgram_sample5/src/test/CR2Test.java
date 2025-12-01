import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    private Instructor instructor;
    private List<Course> courses;
    private List<Student> students;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
        courses = new ArrayList<>();
        students = new ArrayList<>();
        instructor.setCourses(courses);
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Create a single course
        Course course = new Course();
        course.setEnrollments(new ArrayList<>());
        
        // Create a single student and enroll in the course
        Student student = new Student();
        student.enrollInCourse(course);
        
        // Add the course to instructor's course list
        courses.add(course);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Single course with single student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Create a single course
        Course course = new Course();
        course.setEnrollments(new ArrayList<>());
        
        // Create 25 students and enroll them in the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.enrollInCourse(course);
        }
        
        // Add the course to instructor's course list
        courses.add(course);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Create 3 courses
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setEnrollments(new ArrayList<>());
            
            // Create one student and enroll in the course
            Student student = new Student();
            student.enrollInCourse(course);
            
            // Add the course to instructor's course list
            courses.add(course);
        }
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("3 courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Create 4 courses with different number of students
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setEnrollments(new ArrayList<>());
            
            // Create students and enroll them in the course
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                student.enrollInCourse(course);
            }
            
            // Add the course to instructor's course list
            courses.add(course);
        }
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("4 courses with 5, 10, 15, 20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Instructor has no courses (empty list from setup)
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}