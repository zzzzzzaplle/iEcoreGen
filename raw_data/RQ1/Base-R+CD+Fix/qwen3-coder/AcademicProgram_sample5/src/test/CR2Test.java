import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        // Initialize a fresh instructor before each test
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Input: An Instructor with one course. That course has one enrolled student.
        
        // Create a course and add one enrollment
        Course course = new Course();
        course.getEnrollments().add(new Enrollment()); // One enrollment = one student
        
        // Assign the course to the instructor
        instructor.getCourses().add(course);
        
        // Expected Output: The total number of students in the instructor's courses should be 1.
        assertEquals("Single course with one student should return 1", 
                     1, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Input: An Instructor with one course. That course has 25 enrolled students.
        
        // Create a course and add 25 enrollments
        Course course = new Course();
        for (int i = 0; i < 25; i++) {
            course.getEnrollments().add(new Enrollment());
        }
        
        // Assign the course to the instructor
        instructor.getCourses().add(course);
        
        // Expected Output: The total number of students in the instructor's courses should be 25.
        assertEquals("Single course with 25 students should return 25", 
                     25, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        
        // Create 3 courses, each with one enrollment
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.getEnrollments().add(new Enrollment());
            instructor.getCourses().add(course);
        }
        
        // Expected Output: The total number of students in the instructor's courses should be 3.
        assertEquals("Three courses with one student each should return 3", 
                     3, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Input: An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students.
        
        int[] studentCounts = {5, 10, 15, 20};
        int expectedTotal = 0;
        
        // Create 4 courses with specified number of enrollments
        for (int count : studentCounts) {
            Course course = new Course();
            for (int i = 0; i < count; i++) {
                course.getEnrollments().add(new Enrollment());
            }
            instructor.getCourses().add(course);
            expectedTotal += count;
        }
        
        // Expected Output: The total number of students in the instructor's courses should be 50.
        assertEquals("Four courses with 5, 10, 15, 20 students should return 50", 
                     expectedTotal, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Input: An Instructor with no courses assigned.
        // Instructor is already initialized with no courses in setUp()
        
        // Expected Output: The total number of students in the instructor's courses should be 0.
        assertEquals("Instructor with no courses should return 0", 
                     0, instructor.calculateTotalEnrolledStudents());
    }
}