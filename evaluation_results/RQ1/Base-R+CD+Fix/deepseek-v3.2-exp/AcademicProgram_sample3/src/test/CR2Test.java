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
        
        // Create one enrollment for the course
        Enrollment enrollment = new Enrollment();
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        course.setEnrollments(enrollments);
        
        // Add course to instructor
        List<Course> instructorCourses = new ArrayList<>();
        instructorCourses.add(course);
        instructor.setCourses(instructorCourses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        
        // Create a course
        Course course = new Course();
        
        // Create 25 enrollments for the course
        List<Enrollment> enrollments = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Enrollment enrollment = new Enrollment();
            enrollments.add(enrollment);
        }
        course.setEnrollments(enrollments);
        
        // Add course to instructor
        List<Course> instructorCourses = new ArrayList<>();
        instructorCourses.add(course);
        instructor.setCourses(instructorCourses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        
        // Create 3 courses
        List<Course> instructorCourses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            
            // Create one enrollment for each course
            Enrollment enrollment = new Enrollment();
            List<Enrollment> enrollments = new ArrayList<>();
            enrollments.add(enrollment);
            course.setEnrollments(enrollments);
            
            instructorCourses.add(course);
        }
        instructor.setCourses(instructorCourses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        
        // Create 4 courses with varying student counts
        List<Course> instructorCourses = new ArrayList<>();
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int count : studentCounts) {
            Course course = new Course();
            
            // Create enrollments for the course
            List<Enrollment> enrollments = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                Enrollment enrollment = new Enrollment();
                enrollments.add(enrollment);
            }
            course.setEnrollments(enrollments);
            instructorCourses.add(course);
        }
        instructor.setCourses(instructorCourses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result (5 + 10 + 15 + 20 = 50)
        assertEquals("Four courses with 5, 10, 15, and 20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: "No courses"
        // Input: "An Instructor with no courses assigned."
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        
        // Instructor has no courses (default empty list)
        // No setup needed since instructor starts with empty course list
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}