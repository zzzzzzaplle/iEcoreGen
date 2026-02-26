import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        // Reset instructor before each test
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Test Case 1: "Single course, single student"
        // Input: "An Instructor with one course. That course has one enrolled student."
        
        // Create a course
        Course course = new Course();
        course.setCode("CS101");
        course.setName("Introduction to Computer Science");
        
        // Create a student and enroll in the course
        Student student = new Student();
        student.setName("John");
        student.setSurname("Doe");
        student.enrollInCourse(course);
        
        // Add course to instructor
        List<Course> instructorCourses = new ArrayList<>();
        instructorCourses.add(course);
        instructor.setCourses(instructorCourses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 1."
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        
        // Create a course
        Course course = new Course();
        course.setCode("MATH201");
        course.setName("Advanced Mathematics");
        
        // Create and enroll 25 students
        for (int i = 1; i <= 25; i++) {
            Student student = new Student();
            student.setName("Student" + i);
            student.setSurname("LastName" + i);
            student.enrollInCourse(course);
        }
        
        // Add course to instructor
        List<Course> instructorCourses = new ArrayList<>();
        instructorCourses.add(course);
        instructor.setCourses(instructorCourses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        
        List<Course> instructorCourses = new ArrayList<>();
        
        // Create 3 courses, each with one student
        for (int i = 1; i <= 3; i++) {
            Course course = new Course();
            course.setCode("COURSE" + i);
            course.setName("Course " + i);
            
            Student student = new Student();
            student.setName("Student" + i);
            student.setSurname("Surname" + i);
            student.enrollInCourse(course);
            
            instructorCourses.add(course);
        }
        
        instructor.setCourses(instructorCourses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        
        List<Course> instructorCourses = new ArrayList<>();
        int[] studentCounts = {5, 10, 15, 20};
        
        // Create 4 courses with varying numbers of students
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setCode("COURSE" + (i + 1));
            course.setName("Course " + (i + 1));
            
            // Add specified number of students to this course
            for (int j = 1; j <= studentCounts[i]; j++) {
                Student student = new Student();
                student.setName("Student" + j + "_Course" + (i + 1));
                student.setSurname("Surname" + j);
                student.enrollInCourse(course);
            }
            
            instructorCourses.add(course);
        }
        
        instructor.setCourses(instructorCourses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        assertEquals("Four courses with 5+10+15+20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: "No courses"
        // Input: "An Instructor with no courses assigned."
        
        // Instructor already has no courses from setUp()
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}