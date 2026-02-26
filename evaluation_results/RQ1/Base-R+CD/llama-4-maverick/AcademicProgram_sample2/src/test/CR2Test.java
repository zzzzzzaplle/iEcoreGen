import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    private Instructor instructor;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Create a course with one enrolled student
        Course course = new Course();
        Student student = new Student();
        student.enrollInCourse(course);
        
        // Assign the course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Verify total enrolled students is 1
        assertEquals("Single course with one student should return 1", 1, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Create a course with 25 enrolled students
        Course course = new Course();
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.enrollInCourse(course);
        }
        
        // Assign the course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Verify total enrolled students is 25
        assertEquals("Single course with 25 students should return 25", 25, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Create 3 courses, each with one enrolled student
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            Student student = new Student();
            student.enrollInCourse(course);
            courses.add(course);
        }
        
        // Assign courses to instructor
        instructor.setCourses(courses);
        
        // Verify total enrolled students is 3
        assertEquals("Three courses with one student each should return 3", 3, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Create 4 courses with varying student counts: 5, 10, 15, 20
        List<Course> courses = new ArrayList<>();
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                student.enrollInCourse(course);
            }
            courses.add(course);
        }
        
        // Assign courses to instructor
        instructor.setCourses(courses);
        
        // Verify total enrolled students is 50 (5+10+15+20)
        assertEquals("Four courses with 5,10,15,20 students should return 50", 50, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Instructor has no courses assigned (default empty list)
        // Verify total enrolled students is 0
        assertEquals("Instructor with no courses should return 0", 0, instructor.calculateTotalEnrolledStudents());
    }
}