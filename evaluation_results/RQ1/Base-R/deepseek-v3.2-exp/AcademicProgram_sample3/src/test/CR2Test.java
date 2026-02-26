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
        // Initialize common test objects before each test
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
        instructor.addCourse(course);
        
        // Create student and enroll in course
        Student student = new Student();
        student.enrollCourse(course);
        
        // Verify total students in instructor's courses
        assertEquals("Instructor with one course and one student should return 1", 
                     1, instructor.getTotalStudentsInCourses());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        
        // Create course and add to instructor
        Course course = new Course();
        course.setQuota(30); // Set sufficient quota
        instructor.addCourse(course);
        
        // Create and enroll 25 students
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.enrollCourse(course);
            students.add(student);
        }
        
        // Verify total students in instructor's courses
        assertEquals("Instructor with one course and 25 students should return 25", 
                     25, instructor.getTotalStudentsInCourses());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        
        // Create 3 courses and add to instructor
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            instructor.addCourse(course);
            courses.add(course);
        }
        
        // Create and enroll one student in each course
        for (Course course : courses) {
            Student student = new Student();
            student.enrollCourse(course);
        }
        
        // Verify total students in instructor's courses
        assertEquals("Instructor with 3 courses and one student each should return 3", 
                     3, instructor.getTotalStudentsInCourses());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        
        // Create 4 courses with different student counts
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int i = 0; i < studentCounts.length; i++) {
            Course course = new Course();
            course.setQuota(studentCounts[i] + 5); // Set sufficient quota
            instructor.addCourse(course);
            courses.add(course);
            
            // Create and enroll specified number of students
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                student.enrollCourse(course);
                students.add(student);
            }
        }
        
        // Verify total students in instructor's courses (5+10+15+20=50)
        assertEquals("Instructor with 4 courses and varying student counts should return 50", 
                     50, instructor.getTotalStudentsInCourses());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: "No courses"
        // Input: "An Instructor with no courses assigned."
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        
        // Instructor has no courses by default after setup
        // Verify total students in instructor's courses is 0
        assertEquals("Instructor with no courses should return 0", 
                     0, instructor.getTotalStudentsInCourses());
    }
}