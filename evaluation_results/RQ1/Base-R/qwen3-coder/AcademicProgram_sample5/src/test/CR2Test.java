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
        // Setup: Create instructor with one course containing one student
        Course course = new Course();
        Student student = new Student();
        
        // Add student to course
        course.getStudents().add(student);
        
        // Add course to instructor
        instructor.getCourses().add(course);
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalStudents();
        
        // Verify: Total should be 1
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Setup: Create instructor with one course containing 25 students
        Course course = new Course();
        
        // Add 25 students to the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            course.getStudents().add(student);
        }
        
        // Add course to instructor
        instructor.getCourses().add(course);
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalStudents();
        
        // Verify: Total should be 25
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Setup: Create instructor with 3 courses, each containing one student
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            Student student = new Student();
            course.getStudents().add(student);
            instructor.getCourses().add(course);
        }
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalStudents();
        
        // Verify: Total should be 3
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Setup: Create instructor with 4 courses with varying student counts
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int count : studentCounts) {
            Course course = new Course();
            
            // Add specified number of students to the course
            for (int i = 0; i < count; i++) {
                Student student = new Student();
                course.getStudents().add(student);
            }
            
            instructor.getCourses().add(course);
        }
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalStudents();
        
        // Verify: Total should be 50 (5+10+15+20)
        assertEquals("Four courses with 5,10,15,20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Setup: Instructor has no courses (empty courses list)
        // No setup needed since instructor starts with empty courses list
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalStudents();
        
        // Verify: Total should be 0
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}