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
        // Setup: Create instructor with one course containing one student
        Course course = new Course();
        course.setCode("CS101");
        
        Student student = new Student();
        student.enrollInCourse(course);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Should return 1
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Setup: Create instructor with one course containing 25 students
        Course course = new Course();
        course.setCode("MATH201");
        
        // Add 25 students to the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.enrollInCourse(course);
        }
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Should return 25
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Setup: Create instructor with 3 courses, each containing one student
        List<Course> courses = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCode("COURSE" + (i + 1));
            
            Student student = new Student();
            student.enrollInCourse(course);
            
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Should return 3
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Setup: Create instructor with 4 courses with varying student counts
        List<Course> courses = new ArrayList<>();
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setCode("COURSE" + (i + 1));
            
            // Add specified number of students to this course
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                student.enrollInCourse(course);
            }
            
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Should return 50 (5+10+15+20)
        assertEquals("Four courses with 5,10,15,20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Setup: Instructor has no courses (empty list)
        instructor.setCourses(new ArrayList<Course>());
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Should return 0
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}