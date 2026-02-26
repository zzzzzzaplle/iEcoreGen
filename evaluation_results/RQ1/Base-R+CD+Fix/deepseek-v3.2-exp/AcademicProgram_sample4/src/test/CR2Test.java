import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Instructor instructor;
    private List<Course> courses;
    private List<Enrollment> enrollments;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
        courses = new ArrayList<>();
        enrollments = new ArrayList<>();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Setup: Create an Instructor with one course that has one enrolled student
        Course course = new Course();
        course.setCode("CS101");
        
        Enrollment enrollment = new Enrollment();
        enrollment.setGrade(85.0);
        List<Enrollment> courseEnrollments = new ArrayList<>();
        courseEnrollments.add(enrollment);
        course.setEnrollments(courseEnrollments);
        
        courses.add(course);
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total number of students should be 1
        assertEquals("Instructor with one course and one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Setup: Create an Instructor with one course that has 25 enrolled students
        Course course = new Course();
        course.setCode("MATH201");
        
        List<Enrollment> courseEnrollments = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Enrollment enrollment = new Enrollment();
            enrollment.setGrade(75.0 + i);
            courseEnrollments.add(enrollment);
        }
        course.setEnrollments(courseEnrollments);
        
        courses.add(course);
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total number of students should be 25
        assertEquals("Instructor with one course and 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Setup: Create an Instructor with 3 courses, each with one enrolled student
        for (int i = 1; i <= 3; i++) {
            Course course = new Course();
            course.setCode("PHY" + (300 + i));
            
            Enrollment enrollment = new Enrollment();
            enrollment.setGrade(80.0 + i);
            List<Enrollment> courseEnrollments = new ArrayList<>();
            courseEnrollments.add(enrollment);
            course.setEnrollments(courseEnrollments);
            
            courses.add(course);
        }
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total number of students should be 3
        assertEquals("Instructor with 3 courses, each with one student, should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Setup: Create an Instructor with 4 courses with varying student counts (5, 10, 15, 20)
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int i = 0; i < studentCounts.length; i++) {
            Course course = new Course();
            course.setCode("CHEM" + (400 + i));
            
            List<Enrollment> courseEnrollments = new ArrayList<>();
            for (int j = 0; j < studentCounts[i]; j++) {
                Enrollment enrollment = new Enrollment();
                enrollment.setGrade(70.0 + j);
                courseEnrollments.add(enrollment);
            }
            course.setEnrollments(courseEnrollments);
            
            courses.add(course);
        }
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total number of students should be 50 (5+10+15+20)
        assertEquals("Instructor with 4 courses (5,10,15,20 students) should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Setup: Instructor with no courses assigned (empty list)
        instructor.setCourses(new ArrayList<Course>());
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total number of students should be 0
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}