import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CR2Test {
    
    private Instructor instructor;
    private Course course1, course2, course3, course4;
    private Student student1, student2, student3, student4, student5;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        instructor = new Instructor();
        
        // Create courses
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
        course4 = new Course();
        
        // Create students
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        student4 = new Student();
        student5 = new Student();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Test Case 1: "Single course, single student"
        // Input: "An Instructor with one course. That course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 1."
        
        // Setup: Add one course to instructor and one student to that course
        instructor.addCourse(course1);
        course1.addStudent(student1);
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalNumberOfStudents();
        
        // Verify: The total number of students should be 1
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        
        // Setup: Add one course to instructor and 25 students to that course
        instructor.addCourse(course1);
        
        // Add 25 students to the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            course1.addStudent(student);
        }
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalNumberOfStudents();
        
        // Verify: The total number of students should be 25
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        
        // Setup: Add 3 courses to instructor, each with one student
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        course1.addStudent(student1);
        course2.addStudent(student2);
        course3.addStudent(student3);
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalNumberOfStudents();
        
        // Verify: The total number of students should be 3
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        
        // Setup: Add 4 courses to instructor with varying numbers of students
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        instructor.addCourse(course4);
        
        // Add 5 students to course1
        for (int i = 0; i < 5; i++) {
            Student student = new Student();
            course1.addStudent(student);
        }
        
        // Add 10 students to course2
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            course2.addStudent(student);
        }
        
        // Add 15 students to course3
        for (int i = 0; i < 15; i++) {
            Student student = new Student();
            course3.addStudent(student);
        }
        
        // Add 20 students to course4
        for (int i = 0; i < 20; i++) {
            Student student = new Student();
            course4.addStudent(student);
        }
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalNumberOfStudents();
        
        // Verify: The total number of students should be 50 (5+10+15+20)
        assertEquals("Four courses with 5, 10, 15, 20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: "No courses"
        // Input: "An Instructor with no courses assigned."
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        
        // Setup: Instructor has no courses (default state)
        // No setup needed since instructor starts with no courses
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalNumberOfStudents();
        
        // Verify: The total number of students should be 0
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}