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
        // Test Case 1: "Single course, single student"
        // Input: "An Instructor with one course. That course has one enrolled student."
        
        // Create a course
        Course course = new Course();
        
        // Create a student and enroll in the course
        Student student = new Student();
        student.enrollInCourse(course);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Verify the total number of students in instructor's courses is 1
        assertEquals("The total number of students in the instructor's courses should be 1", 
                     1, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        
        // Create a course
        Course course = new Course();
        
        // Create 25 students and enroll them in the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.enrollInCourse(course);
        }
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Verify the total number of students in instructor's courses is 25
        assertEquals("The total number of students in the instructor's courses should be 25", 
                     25, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        
        // Create 3 courses
        Course course1 = new Course();
        Course course2 = new Course();
        Course course3 = new Course();
        
        // Create students and enroll one in each course
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        
        student1.enrollInCourse(course1);
        student2.enrollInCourse(course2);
        student3.enrollInCourse(course3);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Verify the total number of students in instructor's courses is 3
        assertEquals("The total number of students in the instructor's courses should be 3", 
                     3, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        
        // Create 4 courses
        Course course1 = new Course();
        Course course2 = new Course();
        Course course3 = new Course();
        Course course4 = new Course();
        
        // Add students to first course (5 students)
        for (int i = 0; i < 5; i++) {
            Student student = new Student();
            student.enrollInCourse(course1);
        }
        
        // Add students to second course (10 students)
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.enrollInCourse(course2);
        }
        
        // Add students to third course (15 students)
        for (int i = 0; i < 15; i++) {
            Student student = new Student();
            student.enrollInCourse(course3);
        }
        
        // Add students to fourth course (20 students)
        for (int i = 0; i < 20; i++) {
            Student student = new Student();
            student.enrollInCourse(course4);
        }
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        instructor.addCourse(course4);
        
        // Verify the total number of students in instructor's courses is 50 (5+10+15+20)
        assertEquals("The total number of students in the instructor's courses should be 50", 
                     50, instructor.calculateTotalEnrolledStudents());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: "No courses"
        // Input: "An Instructor with no courses assigned."
        
        // No courses added to instructor
        
        // Verify the total number of students in instructor's courses is 0
        assertEquals("The total number of students in the instructor's courses should be 0", 
                     0, instructor.calculateTotalEnrolledStudents());
    }
}