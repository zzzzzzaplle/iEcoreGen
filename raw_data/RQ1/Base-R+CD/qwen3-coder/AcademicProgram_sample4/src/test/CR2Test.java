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
        // Input: An Instructor with one course. That course has one enrolled student.
        
        // Create one course
        Course course = new Course();
        course.setCode("CS101");
        courses.add(course);
        
        // Create one student and enroll in the course
        Student student = new Student();
        student.enrollInCourse(course);
        students.add(student);
        
        // Assign courses to instructor
        instructor.setCourses(courses);
        
        // Expected Output: The total number of students in the instructor's courses should be 1.
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Input: An Instructor with one course. That course has 25 enrolled students.
        
        // Create one course with sufficient quota
        Course course = new Course();
        course.setCode("CS101");
        course.setQuota(30); // Set quota higher than 25
        courses.add(course);
        
        // Create 25 students and enroll them in the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.enrollInCourse(course);
            students.add(student);
        }
        
        // Assign courses to instructor
        instructor.setCourses(courses);
        
        // Expected Output: The total number of students in the instructor's courses should be 25.
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals(25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        
        // Create 3 courses
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCode("CS" + (100 + i));
            course.setQuota(5); // Set sufficient quota
            courses.add(course);
            
            // Create one student per course and enroll
            Student student = new Student();
            student.enrollInCourse(course);
            students.add(student);
        }
        
        // Assign courses to instructor
        instructor.setCourses(courses);
        
        // Expected Output: The total number of students in the instructor's courses should be 3.
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Input: An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students.
        
        // Create 4 courses with varying student enrollments
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setCode("CS" + (100 + i));
            course.setQuota(studentCounts[i] + 5); // Set sufficient quota
            courses.add(course);
            
            // Create specified number of students for this course
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                student.enrollInCourse(course);
                students.add(student);
            }
        }
        
        // Assign courses to instructor
        instructor.setCourses(courses);
        
        // Expected Output: The total number of students in the instructor's courses should be 50.
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals(50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Input: An Instructor with no courses assigned.
        
        // Instructor has no courses (default empty list)
        
        // Expected Output: The total number of students in the instructor's courses should be 0.
        int result = instructor.calculateTotalEnrolledStudents();
        assertEquals(0, result);
    }
}