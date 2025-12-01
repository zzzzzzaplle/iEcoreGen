import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        // Initialize instructor before each test
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Test Case 1: "Single course, single student"
        // Input: "An Instructor with one course. That course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 1."
        
        // Create a course
        Course course = new Course();
        
        // Create a student and enroll in the course
        Student student = new Student();
        student.enrollInCourse(course);
        
        // Add course to instructor's course list
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify result
        assertEquals("Total students should be 1 for single course with single student", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        
        // Create a course
        Course course = new Course();
        
        // Create 25 students and enroll them in the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.enrollInCourse(course);
        }
        
        // Add course to instructor's course list
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify result
        assertEquals("Total students should be 25 for single course with 25 students", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        
        // Create 3 courses
        Course course1 = new Course();
        Course course2 = new Course();
        Course course3 = new Course();
        
        // Create students and enroll one in each course
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        
        student1.enrollInCourse(course1);
        student2.enrollInCourse(course2);
        student3.enrollInCourse(course3);
        
        // Add all courses to instructor's course list
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify result
        assertEquals("Total students should be 3 for 3 courses with one student each", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        
        // Create 4 courses
        Course course1 = new Course();
        Course course2 = new Course();
        Course course3 = new Course();
        Course course4 = new Course();
        
        // Add 5 students to first course
        for (int i = 0; i < 5; i++) {
            Student student = new Student();
            student.enrollInCourse(course1);
        }
        
        // Add 10 students to second course
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.enrollInCourse(course2);
        }
        
        // Add 15 students to third course
        for (int i = 0; i < 15; i++) {
            Student student = new Student();
            student.enrollInCourse(course3);
        }
        
        // Add 20 students to fourth course
        for (int i = 0; i < 20; i++) {
            Student student = new Student();
            student.enrollInCourse(course4);
        }
        
        // Add all courses to instructor's course list
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify result (5 + 10 + 15 + 20 = 50)
        assertEquals("Total students should be 50 for 4 courses with 5, 10, 15, and 20 students respectively", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: "No courses"
        // Input: "An Instructor with no courses assigned."
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        
        // Instructor already has no courses (empty list from constructor)
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify result
        assertEquals("Total students should be 0 for instructor with no courses", 0, result);
    }
}