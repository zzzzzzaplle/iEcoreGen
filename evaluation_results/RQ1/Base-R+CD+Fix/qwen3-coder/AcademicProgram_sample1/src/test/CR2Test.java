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
    public void testCase1_SingleCourseSingleStudent() {
        // Test Case 1: "Single course, single student"
        // Input: "An Instructor with one course. That course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 1."
        
        // Create course
        Course course = new Course();
        course.setCode("CS101");
        
        // Create student and enroll
        Student student = new Student();
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setGrade(85.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        course.setEnrollments(enrollments);
        
        // Assign course to instructor
        List<Course> instructorCourses = new ArrayList<>();
        instructorCourses.add(course);
        instructor.setCourses(instructorCourses);
        
        // Verify total enrolled students
        assertEquals("Single course with one student should return 1", 
                     1, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase2_SingleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        
        // Create course
        Course course = new Course();
        course.setCode("MATH201");
        
        // Create 25 students and enroll them
        List<Enrollment> enrollments = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setGrade(75.0 + i);
            enrollments.add(enrollment);
        }
        course.setEnrollments(enrollments);
        
        // Assign course to instructor
        List<Course> instructorCourses = new ArrayList<>();
        instructorCourses.add(course);
        instructor.setCourses(instructorCourses);
        
        // Verify total enrolled students
        assertEquals("Single course with 25 students should return 25", 
                     25, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase3_MultipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        
        // Create 3 courses
        List<Course> courses = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Course course = new Course();
            course.setCode("COURSE" + i);
            
            // Create one student and enroll
            Student student = new Student();
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setGrade(80.0);
            
            List<Enrollment> enrollments = new ArrayList<>();
            enrollments.add(enrollment);
            course.setEnrollments(enrollments);
            
            courses.add(course);
        }
        
        // Assign courses to instructor
        instructor.setCourses(courses);
        
        // Verify total enrolled students
        assertEquals("Three courses with one student each should return 3", 
                     3, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase4_MultipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        
        // Create 4 courses with varying student counts
        int[] studentCounts = {5, 10, 15, 20};
        List<Course> courses = new ArrayList<>();
        
        for (int i = 0; i < studentCounts.length; i++) {
            Course course = new Course();
            course.setCode("COURSE" + (i + 1));
            
            // Create specified number of students and enroll them
            List<Enrollment> enrollments = new ArrayList<>();
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                enrollment.setGrade(70.0 + j);
                enrollments.add(enrollment);
            }
            course.setEnrollments(enrollments);
            courses.add(course);
        }
        
        // Assign courses to instructor
        instructor.setCourses(courses);
        
        // Verify total enrolled students (5 + 10 + 15 + 20 = 50)
        assertEquals("Four courses with 5, 10, 15, and 20 students should return 50", 
                     50, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase5_NoCourses() {
        // Test Case 5: "No courses"
        // Input: "An Instructor with no courses assigned."
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        
        // Instructor has no courses (default empty list)
        // Verify total enrolled students
        assertEquals("Instructor with no courses should return 0", 
                     0, instructor.calculateTotalEnrolledStudents());
    }
}