import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    
    private Instructor instructor;
    private List<Course> courses;
    
    @Before
    public void setUp() {
        // Initialize instructor and courses list before each test
        instructor = new Instructor();
        courses = new ArrayList<>();
        instructor.setCourses(courses);
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Test Case 1: Single course, single student
        Course course = new Course();
        List<Enrollment> enrollments = new ArrayList<>();
        Enrollment enrollment = new Enrollment(new Student(), course);
        enrollments.add(enrollment);
        course.setEnrollments(enrollments);
        
        courses.add(course);
        
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals("The total number of students in the instructor's courses should be 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: Single course, multiple students
        Course course = new Course();
        List<Enrollment> enrollments = new ArrayList<>();
        
        // Add 25 students to the course
        for (int i = 0; i < 25; i++) {
            Enrollment enrollment = new Enrollment(new Student(), course);
            enrollments.add(enrollment);
        }
        course.setEnrollments(enrollments);
        
        courses.add(course);
        
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals("The total number of students in the instructor's courses should be 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: Multiple courses, single student per course
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            List<Enrollment> enrollments = new ArrayList<>();
            Enrollment enrollment = new Enrollment(new Student(), course);
            enrollments.add(enrollment);
            course.setEnrollments(enrollments);
            courses.add(course);
        }
        
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals("The total number of students in the instructor's courses should be 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: Multiple courses, varying students per course
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int count : studentCounts) {
            Course course = new Course();
            List<Enrollment> enrollments = new ArrayList<>();
            
            for (int i = 0; i < count; i++) {
                Enrollment enrollment = new Enrollment(new Student(), course);
                enrollments.add(enrollment);
            }
            course.setEnrollments(enrollments);
            courses.add(course);
        }
        
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals("The total number of students in the instructor's courses should be 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: No courses
        // Instructor already has empty courses list from setUp()
        
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals("The total number of students in the instructor's courses should be 0", 0, result);
    }
}