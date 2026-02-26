import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;

public class CR2Test {
    private Instructor instructor;
    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
        course4 = new Course();
        
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        student4 = new Student();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Test Case 1: Single course, single student
        // Input: An Instructor with one course. That course has one enrolled student.
        
        // Setup: Create one course with one student
        course1.setCourseCode("CS101");
        course1.setQuotaLimit(30);
        course1.addStudent(student1);
        
        // Add course to instructor
        instructor.addCourse(course1);
        
        // Verify: The total number of students in the instructor's courses should be 1
        assertEquals("Instructor with one course and one student should have total 1 student", 
                     1, instructor.getTotalNumberOfStudents());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: Single course, multiple students
        // Input: An Instructor with one course. That course has 25 enrolled students.
        
        // Setup: Create one course with 25 students
        course1.setCourseCode("MATH201");
        course1.setQuotaLimit(30);
        
        // Add 25 students to the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            course1.addStudent(student);
        }
        
        // Add course to instructor
        instructor.addCourse(course1);
        
        // Verify: The total number of students in the instructor's courses should be 25
        assertEquals("Instructor with one course and 25 students should have total 25 students", 
                     25, instructor.getTotalNumberOfStudents());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: Multiple courses, single student per course
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        
        // Setup: Create 3 courses, each with one student
        course1.setCourseCode("PHYS101");
        course2.setCourseCode("CHEM101");
        course3.setCourseCode("BIO101");
        
        course1.setQuotaLimit(30);
        course2.setQuotaLimit(30);
        course3.setQuotaLimit(30);
        
        course1.addStudent(student1);
        course2.addStudent(student2);
        course3.addStudent(student3);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Verify: The total number of students in the instructor's courses should be 3
        assertEquals("Instructor with 3 courses, each with one student, should have total 3 students", 
                     3, instructor.getTotalNumberOfStudents());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: Multiple courses, varying students per course
        // Input: An Instructor with 4 courses. First course has 5 students, second has 10, 
        // third has 15, and fourth has 20 students.
        
        // Setup: Create 4 courses with varying number of students
        course1.setCourseCode("CS301");
        course2.setCourseCode("MATH301");
        course3.setCourseCode("PHYS301");
        course4.setCourseCode("CHEM301");
        
        course1.setQuotaLimit(30);
        course2.setQuotaLimit(30);
        course3.setQuotaLimit(30);
        course4.setQuotaLimit(30);
        
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
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        instructor.addCourse(course4);
        
        // Verify: The total number of students in the instructor's courses should be 50
        assertEquals("Instructor with 4 courses (5+10+15+20 students) should have total 50 students", 
                     50, instructor.getTotalNumberOfStudents());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: No courses
        // Input: An Instructor with no courses assigned.
        
        // Setup: Instructor has no courses (default state)
        
        // Verify: The total number of students in the instructor's courses should be 0
        assertEquals("Instructor with no courses should have total 0 students", 
                     0, instructor.getTotalNumberOfStudents());
    }
}