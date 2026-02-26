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
        // Setup: An Instructor with one course. That course has one enrolled student.
        Course course = new Course();
        Student student = new Student();
        
        // Create enrollment and add to course
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        course.getEnrollments().add(enrollment);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 1.
        assertEquals(1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Setup: An Instructor with one course. That course has 25 enrolled students.
        Course course = new Course();
        
        // Add 25 students to the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            course.getEnrollments().add(enrollment);
        }
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 25.
        assertEquals(25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Setup: An Instructor with 3 courses. Each course has one enrolled student.
        List<Course> courses = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            Student student = new Student();
            
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            course.getEnrollments().add(enrollment);
            
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 3.
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Setup: An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students.
        List<Course> courses = new ArrayList<>();
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            
            // Add specified number of students to this course
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                course.getEnrollments().add(enrollment);
            }
            
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 50.
        assertEquals(50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Setup: An Instructor with no courses assigned.
        // Instructor already has empty courses list from setUp()
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 0.
        assertEquals(0, result);
    }
}