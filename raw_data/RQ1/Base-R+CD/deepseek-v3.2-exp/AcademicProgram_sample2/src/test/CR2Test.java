import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Instructor instructor;
    private List<Course> courses;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
        courses = new ArrayList<>();
        instructor.setCourses(courses);
    }
    
    @Test
    public void testCase1_SingleCourseSingleStudent() {
        // Test Case 1: "Single course, single student"
        // Input: "An Instructor with one course. That course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 1."
        
        // Create a course
        Course course = new Course();
        course.setEnrollments(createEnrollments(1));
        
        // Add course to instructor
        courses.add(course);
        
        // Verify the total number of enrolled students
        assertEquals("Total enrolled students should be 1", 1, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase2_SingleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        
        // Create a course with 25 enrollments
        Course course = new Course();
        course.setEnrollments(createEnrollments(25));
        
        // Add course to instructor
        courses.add(course);
        
        // Verify the total number of enrolled students
        assertEquals("Total enrolled students should be 25", 25, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase3_MultipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        
        // Create 3 courses, each with 1 enrollment
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setEnrollments(createEnrollments(1));
            courses.add(course);
        }
        
        // Verify the total number of enrolled students
        assertEquals("Total enrolled students should be 3", 3, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase4_MultipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        
        // Create courses with specified enrollment counts
        int[] enrollmentCounts = {5, 10, 15, 20};
        
        for (int count : enrollmentCounts) {
            Course course = new Course();
            course.setEnrollments(createEnrollments(count));
            courses.add(course);
        }
        
        // Verify the total number of enrolled students
        assertEquals("Total enrolled students should be 50", 50, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase5_NoCourses() {
        // Test Case 5: "No courses"
        // Input: "An Instructor with no courses assigned."
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        
        // Instructor already has no courses from setUp()
        
        // Verify the total number of enrolled students
        assertEquals("Total enrolled students should be 0", 0, instructor.calculateTotalEnrolledStudents());
    }
    
    /**
     * Helper method to create a list of enrollments with the specified count
     * @param count Number of enrollments to create
     * @return List of enrollments
     */
    private List<Enrollment> createEnrollments(int count) {
        List<Enrollment> enrollments = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Enrollment enrollment = new Enrollment();
            enrollments.add(enrollment);
        }
        return enrollments;
    }
}