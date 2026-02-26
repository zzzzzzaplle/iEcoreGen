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
        // Test Case 1: "Single course, single student"
        // Input: "An Instructor with one course. That course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 1."
        
        // Create a course
        Course course = new Course();
        course.setCode("CS101");
        
        // Create enrollment with one student
        Enrollment enrollment = new Enrollment();
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        course.setEnrollments(enrollments);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Verify the total number of enrolled students
        assertEquals(1, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        
        // Create a course
        Course course = new Course();
        course.setCode("MATH201");
        
        // Create 25 enrollments
        List<Enrollment> enrollments = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            enrollments.add(new Enrollment());
        }
        course.setEnrollments(enrollments);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Verify the total number of enrolled students
        assertEquals(25, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        
        List<Course> courses = new ArrayList<>();
        
        // Create 3 courses, each with one enrollment
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCode("PHY" + (300 + i));
            
            List<Enrollment> enrollments = new ArrayList<>();
            enrollments.add(new Enrollment());
            course.setEnrollments(enrollments);
            
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Verify the total number of enrolled students
        assertEquals(3, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        
        List<Course> courses = new ArrayList<>();
        int[] studentCounts = {5, 10, 15, 20};
        
        // Create 4 courses with varying number of enrollments
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setCode("CHEM" + (400 + i));
            
            List<Enrollment> enrollments = new ArrayList<>();
            for (int j = 0; j < studentCounts[i]; j++) {
                enrollments.add(new Enrollment());
            }
            course.setEnrollments(enrollments);
            
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Verify the total number of enrolled students
        assertEquals(50, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: "No courses"
        // Input: "An Instructor with no courses assigned."
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        
        // Instructor has no courses (default empty list)
        // Verify the total number of enrolled students is 0
        assertEquals(0, instructor.calculateTotalEnrolledStudents());
    }
}