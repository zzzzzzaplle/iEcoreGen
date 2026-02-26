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
        // Setup: Create instructor with one course that has one enrolled student
        Course course = new Course();
        course.setCode("CS101");
        
        Student student = new Student();
        students.add(student);
        
        // Enroll student in course
        student.enrollInCourse(course);
        
        // Add course to instructor
        courses.add(course);
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total should be 1
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Setup: Create instructor with one course that has 25 enrolled students
        Course course = new Course();
        course.setCode("MATH201");
        course.setQuota(30); // Set sufficient quota
        
        // Create and enroll 25 students
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            students.add(student);
            student.enrollInCourse(course);
        }
        
        // Add course to instructor
        courses.add(course);
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total should be 25
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Setup: Create instructor with 3 courses, each with one enrolled student
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCode("COURSE" + (i + 1));
            course.setQuota(5);
            
            Student student = new Student();
            students.add(student);
            student.enrollInCourse(course);
            
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total should be 3
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Setup: Create instructor with 4 courses with varying student counts
        int[] studentCounts = {5, 10, 15, 20};
        int expectedTotal = 0;
        
        for (int i = 0; i < studentCounts.length; i++) {
            Course course = new Course();
            course.setCode("COURSE" + (i + 1));
            course.setQuota(studentCounts[i] + 5); // Set sufficient quota
            
            // Create and enroll specified number of students
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                students.add(student);
                student.enrollInCourse(course);
            }
            
            courses.add(course);
            expectedTotal += studentCounts[i];
        }
        
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total should be 50 (5+10+15+20)
        assertEquals("Four courses with 5,10,15,20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Setup: Instructor with no courses (empty list)
        instructor.setCourses(new ArrayList<Course>());
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total should be 0
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}