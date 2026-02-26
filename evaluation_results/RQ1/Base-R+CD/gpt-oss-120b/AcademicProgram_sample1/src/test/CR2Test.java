import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Test Case 1: "Single course, single student"
        // Input: "An Instructor with one course. That course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 1."
        
        // Create course and add to instructor
        Course course = new Course();
        course.setQuota(10); // Set sufficient quota
        instructor.addCourse(course);
        
        // Create student and enroll in course
        Student student = new Student();
        student.enrollInCourse(course);
        
        // Verify total enrolled students is 1
        assertEquals("Single course with one student should return 1", 
                     1, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        
        // Create course and add to instructor
        Course course = new Course();
        course.setQuota(30); // Set sufficient quota for 25 students
        instructor.addCourse(course);
        
        // Create 25 students and enroll them in the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.enrollInCourse(course);
        }
        
        // Verify total enrolled students is 25
        assertEquals("Single course with 25 students should return 25", 
                     25, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        
        // Create 3 courses and add to instructor
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setQuota(5); // Set sufficient quota
            instructor.addCourse(course);
            
            // Create one student and enroll in this course
            Student student = new Student();
            student.enrollInCourse(course);
        }
        
        // Verify total enrolled students is 3
        assertEquals("Three courses with one student each should return 3", 
                     3, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        
        int[] studentCounts = {5, 10, 15, 20};
        
        // Create 4 courses with varying student enrollments
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setQuota(studentCounts[i] + 5); // Set sufficient quota
            instructor.addCourse(course);
            
            // Create specified number of students and enroll in this course
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                student.enrollInCourse(course);
            }
        }
        
        // Verify total enrolled students is 50 (5+10+15+20)
        assertEquals("Four courses with 5,10,15,20 students should return 50", 
                     50, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: "No courses"
        // Input: "An Instructor with no courses assigned."
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        
        // Instructor has no courses by default (empty list from constructor)
        // Verify total enrolled students is 0
        assertEquals("Instructor with no courses should return 0", 
                     0, instructor.calculateTotalEnrolledStudents());
    }
}