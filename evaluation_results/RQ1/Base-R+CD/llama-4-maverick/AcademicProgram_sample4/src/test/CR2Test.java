import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

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
    public void testCase1_singleCourseSingleStudent() throws Exception {
        // Setup: Create instructor with one course that has one enrolled student
        Course course = new Course();
        course.setName("Math");
        course.setCode("M101");
        
        // Create a student and enroll in the course
        Student student = new Student();
        student.enrollInCourse(course);
        
        // Add course to instructor's course list
        instructor.getCourses().add(course);
        
        // Execute: Calculate total enrolled students for instructor
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total should be 1
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() throws Exception {
        // Setup: Create instructor with one course that has 25 enrolled students
        Course course = new Course();
        course.setName("Physics");
        course.setCode("P201");
        
        // Create 25 students and enroll them in the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.enrollInCourse(course);
        }
        
        // Add course to instructor's course list
        instructor.getCourses().add(course);
        
        // Execute: Calculate total enrolled students for instructor
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total should be 25
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() throws Exception {
        // Setup: Create instructor with 3 courses, each with one student
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setName("Course " + (i + 1));
            course.setCode("C" + (i + 1));
            
            Student student = new Student();
            student.enrollInCourse(course);
            
            instructor.getCourses().add(course);
        }
        
        // Execute: Calculate total enrolled students for instructor
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total should be 3
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() throws Exception {
        // Setup: Create instructor with 4 courses with varying student counts
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setName("Course " + (i + 1));
            course.setCode("C" + (i + 1));
            
            // Add specified number of students to this course
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                student.enrollInCourse(course);
            }
            
            instructor.getCourses().add(course);
        }
        
        // Execute: Calculate total enrolled students for instructor
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total should be 50 (5+10+15+20)
        assertEquals("Four courses with 5, 10, 15, 20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() throws Exception {
        // Setup: Instructor with no courses (empty courses list)
        // No setup needed since courses list is empty by default
        
        // Execute: Calculate total enrolled students for instructor
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total should be 0
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}