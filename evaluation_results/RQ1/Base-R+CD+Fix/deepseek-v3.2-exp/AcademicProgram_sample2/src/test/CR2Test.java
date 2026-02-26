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
        // Setup: An Instructor with one course. That course has one enrolled student.
        Course course = new Course();
        course.setCode("CS101");
        
        Student student = new Student();
        course.getEnrollments().add(new Enrollment());
        course.getEnrollments().get(0).setStudent(student);
        
        instructor.getCourses().add(course);
        
        // Execute: Calculate total number of students in instructor's courses
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 1.
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Setup: An Instructor with one course. That course has 25 enrolled students.
        Course course = new Course();
        course.setCode("MATH201");
        
        // Add 25 students to the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            course.getEnrollments().add(enrollment);
        }
        
        instructor.getCourses().add(course);
        
        // Execute: Calculate total number of students in instructor's courses
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 25.
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Setup: An Instructor with 3 courses. Each course has one enrolled student.
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCode("COURSE" + (i + 1));
            
            Student student = new Student();
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            course.getEnrollments().add(enrollment);
            
            instructor.getCourses().add(course);
        }
        
        // Execute: Calculate total number of students in instructor's courses
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 3.
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Setup: An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students.
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setCode("COURSE" + (i + 1));
            
            // Add specified number of students to the course
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                course.getEnrollments().add(enrollment);
            }
            
            instructor.getCourses().add(course);
        }
        
        // Execute: Calculate total number of students in instructor's courses
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 50.
        assertEquals("Four courses with 5+10+15+20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Setup: An Instructor with no courses assigned.
        // Instructor is already created in setUp() with empty courses list
        
        // Execute: Calculate total number of students in instructor's courses
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 0.
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}