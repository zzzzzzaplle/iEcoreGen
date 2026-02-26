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
    public void testCase1_SingleCourseSingleStudent() {
        // Setup: Instructor with one course, that course has one enrolled student
        Course course = new Course();
        course.setEnrollments(new ArrayList<>());
        
        Student student = new Student();
        student.enrollInCourse(course);
        
        courses.add(course);
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total number should be 1
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_SingleCourseMultipleStudents() {
        // Setup: Instructor with one course, that course has 25 enrolled students
        Course course = new Course();
        List<Enrollment> enrollments = new ArrayList<>();
        
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollments.add(enrollment);
        }
        
        course.setEnrollments(enrollments);
        courses.add(course);
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total number should be 25
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesSingleStudentPerCourse() {
        // Setup: Instructor with 3 courses, each course has one enrolled student
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            List<Enrollment> enrollments = new ArrayList<>();
            
            Student student = new Student();
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollments.add(enrollment);
            
            course.setEnrollments(enrollments);
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total number should be 3
        assertEquals("3 courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesVaryingStudentsPerCourse() {
        // Setup: Instructor with 4 courses with varying student counts: 5, 10, 15, 20
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int count : studentCounts) {
            Course course = new Course();
            List<Enrollment> enrollments = new ArrayList<>();
            
            for (int i = 0; i < count; i++) {
                Student student = new Student();
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                enrollments.add(enrollment);
            }
            
            course.setEnrollments(enrollments);
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total number should be 50 (5+10+15+20)
        assertEquals("4 courses with 5,10,15,20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_NoCourses() {
        // Setup: Instructor with no courses assigned
        instructor.setCourses(new ArrayList<>());
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: Total number should be 0
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}