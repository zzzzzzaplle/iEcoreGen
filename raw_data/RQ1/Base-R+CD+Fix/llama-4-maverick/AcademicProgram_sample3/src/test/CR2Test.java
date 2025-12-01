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
        
        // Create student and enroll in course
        Student student = new Student();
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setGrade(85.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        course.setEnrollments(enrollments);
        
        // Assign course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Verify expected output: The total number of students in the instructor's courses should be 1.
        assertEquals("Single course with one student should return 1", 1, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Input: An Instructor with one course. That course has 25 enrolled students.
        
        // Create course
        Course course = new Course();
        course.setCode("MATH201");
        
        // Create 25 students and enroll them in course
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
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Verify expected output: The total number of students in the instructor's courses should be 25.
        assertEquals("Single course with 25 students should return 25", 25, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        
        List<Course> courses = new ArrayList<>();
        
        // Create 3 courses, each with one student
        for (int i = 1; i <= 3; i++) {
            Course course = new Course();
            course.setCode("COURSE" + i);
            
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
        
        instructor.setCourses(courses);
        
        // Verify expected output: The total number of students in the instructor's courses should be 3.
        assertEquals("Three courses with one student each should return 3", 3, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Input: An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students.
        
        List<Course> courses = new ArrayList<>();
        int[] studentCounts = {5, 10, 15, 20};
        int expectedTotal = 0;
        
        // Create 4 courses with varying student counts
        for (int i = 0; i < studentCounts.length; i++) {
            Course course = new Course();
            course.setCode("COURSE" + (i + 1));
            
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
            expectedTotal += studentCounts[i];
        }
        
        instructor.setCourses(courses);
        
        // Verify expected output: The total number of students in the instructor's courses should be 50.
        assertEquals("Four courses with 5, 10, 15, and 20 students should return 50", 50, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Input: An Instructor with no courses assigned.
        
        // Instructor already has no courses from setUp()
        
        // Verify expected output: The total number of students in the instructor's courses should be 0.
        assertEquals("Instructor with no courses should return 0", 0, instructor.calculateTotalEnrolledStudents());
    }
}