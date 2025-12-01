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
        // Input: An Instructor with one course. That course has one enrolled student.
        
        // Create course
        Course course = new Course();
        course.setCode("CS101");
        
        // Create enrollment with one student
        Enrollment enrollment = new Enrollment();
        Student student = new Student();
        enrollment.setStudent(student);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        course.setEnrollments(enrollments);
        
        // Assign course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Verify the total number of students in instructor's courses is 1
        assertEquals("The total number of students in the instructor's courses should be 1", 
                     1, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Input: An Instructor with one course. That course has 25 enrolled students.
        
        // Create course
        Course course = new Course();
        course.setCode("MATH201");
        
        // Create 25 enrollments
        List<Enrollment> enrollments = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Enrollment enrollment = new Enrollment();
            Student student = new Student();
            enrollment.setStudent(student);
            enrollments.add(enrollment);
        }
        course.setEnrollments(enrollments);
        
        // Assign course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Verify the total number of students in instructor's courses is 25
        assertEquals("The total number of students in the instructor's courses should be 25", 
                     25, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        
        List<Course> courses = new ArrayList<>();
        
        // Create 3 courses, each with one student
        for (int i = 1; i <= 3; i++) {
            Course course = new Course();
            course.setCode("COURSE" + i);
            
            Enrollment enrollment = new Enrollment();
            Student student = new Student();
            enrollment.setStudent(student);
            
            List<Enrollment> enrollments = new ArrayList<>();
            enrollments.add(enrollment);
            course.setEnrollments(enrollments);
            
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Verify the total number of students in instructor's courses is 3
        assertEquals("The total number of students in the instructor's courses should be 3", 
                     3, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Input: An Instructor with 4 courses. First course has 5 students, 
        // second has 10, third has 15, and fourth has 20 students.
        
        List<Course> courses = new ArrayList<>();
        int[] studentCounts = {5, 10, 15, 20};
        
        // Create 4 courses with varying numbers of students
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setCode("COURSE" + (i + 1));
            
            List<Enrollment> enrollments = new ArrayList<>();
            for (int j = 0; j < studentCounts[i]; j++) {
                Enrollment enrollment = new Enrollment();
                Student student = new Student();
                enrollment.setStudent(student);
                enrollments.add(enrollment);
            }
            course.setEnrollments(enrollments);
            
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Verify the total number of students in instructor's courses is 50 (5+10+15+20)
        assertEquals("The total number of students in the instructor's courses should be 50", 
                     50, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Input: An Instructor with no courses assigned.
        
        // Instructor already has no courses from setUp()
        // Verify the total number of students in instructor's courses is 0
        assertEquals("The total number of students in the instructor's courses should be 0", 
                     0, instructor.calculateTotalEnrolledStudents());
    }
}