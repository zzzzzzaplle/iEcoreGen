import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Instructor instructor;
    private Course course1, course2, course3, course4;
    private Student student1, student2, student3, student4, student5;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
        course4 = new Course();
        
        // Initialize some sample students
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        student4 = new Student();
        student5 = new Student();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Test Case 1: Single course, single student
        // Input: An Instructor with one course. That course has one enrolled student.
        
        // Setup: Create a course with one student
        List<Student> students = new ArrayList<>();
        students.add(student1);
        course1.setEnrolledStudents(students);
        
        // Add the course to instructor
        instructor.addCourse(course1);
        
        // Verify the total number of students is 1
        assertEquals("The total number of students in the instructor's courses should be 1", 
                     1, instructor.numberOfStudentsInCourses());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: Single course, multiple students
        // Input: An Instructor with one course. That course has 25 enrolled students.
        
        // Setup: Create a course with 25 students
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            students.add(new Student());
        }
        course1.setEnrolledStudents(students);
        
        // Add the course to instructor
        instructor.addCourse(course1);
        
        // Verify the total number of students is 25
        assertEquals("The total number of students in the instructor's courses should be 25", 
                     25, instructor.numberOfStudentsInCourses());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: Multiple courses, single student per course
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        
        // Setup: Create 3 courses, each with one student
        List<Student> students1 = new ArrayList<>();
        students1.add(student1);
        course1.setEnrolledStudents(students1);
        
        List<Student> students2 = new ArrayList<>();
        students2.add(student2);
        course2.setEnrolledStudents(students2);
        
        List<Student> students3 = new ArrayList<>();
        students3.add(student3);
        course3.setEnrolledStudents(students3);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Verify the total number of students is 3
        assertEquals("The total number of students in the instructor's courses should be 3", 
                     3, instructor.numberOfStudentsInCourses());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: Multiple courses, varying students per course
        // Input: An Instructor with 4 courses. First course has 5 students, 
        // second has 10, third has 15, and fourth has 20 students.
        
        // Setup: Create 4 courses with varying numbers of students
        List<Student> students1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            students1.add(new Student());
        }
        course1.setEnrolledStudents(students1);
        
        List<Student> students2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            students2.add(new Student());
        }
        course2.setEnrolledStudents(students2);
        
        List<Student> students3 = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            students3.add(new Student());
        }
        course3.setEnrolledStudents(students3);
        
        List<Student> students4 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            students4.add(new Student());
        }
        course4.setEnrolledStudents(students4);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        instructor.addCourse(course4);
        
        // Verify the total number of students is 50 (5+10+15+20)
        assertEquals("The total number of students in the instructor's courses should be 50", 
                     50, instructor.numberOfStudentsInCourses());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: No courses
        // Input: An Instructor with no courses assigned.
        
        // No setup needed - instructor starts with no courses
        
        // Verify the total number of students is 0
        assertEquals("The total number of students in the instructor's courses should be 0", 
                     0, instructor.numberOfStudentsInCourses());
    }
}