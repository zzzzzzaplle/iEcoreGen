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
        // Input: An Instructor with one course. That course has one enrolled student.
        Course course = new Course();
        List<Enrollment> enrollments = new ArrayList<>();
        Enrollment enrollment = new Enrollment();
        enrollments.add(enrollment);
        course.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Expected Output: The total number of students in the instructor's courses should be 1.
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Input: An Instructor with one course. That course has 25 enrolled students.
        Course course = new Course();
        List<Enrollment> enrollments = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            enrollments.add(new Enrollment());
        }
        course.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Expected Output: The total number of students in the instructor's courses should be 25.
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        List<Course> courses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            List<Enrollment> enrollments = new ArrayList<>();
            enrollments.add(new Enrollment());
            course.setEnrollments(enrollments);
            courses.add(course);
        }
        instructor.setCourses(courses);
        
        // Expected Output: The total number of students in the instructor's courses should be 3.
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Input: An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students.
        List<Course> courses = new ArrayList<>();
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int count : studentCounts) {
            Course course = new Course();
            List<Enrollment> enrollments = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                enrollments.add(new Enrollment());
            }
            course.setEnrollments(enrollments);
            courses.add(course);
        }
        instructor.setCourses(courses);
        
        // Expected Output: The total number of students in the instructor's courses should be 50.
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals("Four courses with 5+10+15+20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Input: An Instructor with no courses assigned.
        instructor.setCourses(new ArrayList<Course>());
        
        // Expected Output: The total number of students in the instructor's courses should be 0.
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}