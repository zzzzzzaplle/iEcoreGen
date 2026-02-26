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
    
    /**
     * Test Case 1: "Single course, single student"
     * Input: An Instructor with one course. That course has one enrolled student.
     * Expected Output: The total number of students in the instructor's courses should be 1.
     */
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Create one course
        Course course = new Course();
        course.setCode("CS101");
        
        // Create one student
        Student student = new Student();
        
        // Enroll student in course
        student.enrollInCourse(course);
        
        // Add course to instructor
        courses.add(course);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify result matches expected output
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    /**
     * Test Case 2: "Single course, multiple students"
     * Input: An Instructor with one course. That course has 25 enrolled students.
     * Expected Output: The total number of students in the instructor's courses should be 25.
     */
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Create one course
        Course course = new Course();
        course.setCode("CS102");
        
        // Create and enroll 25 students
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.enrollInCourse(course);
            students.add(student);
        }
        
        // Add course to instructor
        courses.add(course);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify result matches expected output
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    /**
     * Test Case 3: "Multiple courses, single student per course"
     * Input: An Instructor with 3 courses. Each course has one enrolled student.
     * Expected Output: The total number of students in the instructor's courses should be 3.
     */
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Create 3 courses
        for (int i = 1; i <= 3; i++) {
            Course course = new Course();
            course.setCode("CS" + (200 + i));
            
            // Create and enroll one student per course
            Student student = new Student();
            student.enrollInCourse(course);
            
            courses.add(course);
        }
        
        // Add courses to instructor
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify result matches expected output
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    /**
     * Test Case 4: "Multiple courses, varying students per course"
     * Input: An Instructor with 4 courses. First course has 5 students, second has 10, 
     * third has 15, and fourth has 20 students.
     * Expected Output: The total number of students in the instructor's courses should be 50.
     */
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        int[] studentCounts = {5, 10, 15, 20};
        
        // Create 4 courses with varying student counts
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setCode("CS" + (300 + i));
            
            // Create and enroll specified number of students
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                student.enrollInCourse(course);
            }
            
            courses.add(course);
        }
        
        // Add courses to instructor
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify result matches expected output (5 + 10 + 15 + 20 = 50)
        assertEquals("Four courses with 5, 10, 15, and 20 students should return 50", 50, result);
    }
    
    /**
     * Test Case 5: "No courses"
     * Input: An Instructor with no courses assigned.
     * Expected Output: The total number of students in the instructor's courses should be 0.
     */
    @Test
    public void testCase5_noCourses() {
        // Instructor has no courses (default state after setup)
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify result matches expected output
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}