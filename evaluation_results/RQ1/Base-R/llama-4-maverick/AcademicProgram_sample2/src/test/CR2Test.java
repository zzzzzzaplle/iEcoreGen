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
        
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        student4 = new Student();
        student5 = new Student();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Test Case 1: "Single course, single student"
        // Setup: An Instructor with one course. That course has one enrolled student.
        
        // Create and configure the course with one student
        List<Student> studentsCourse1 = new ArrayList<>();
        studentsCourse1.add(student1);
        course1.setEnrolledStudents(studentsCourse1);
        
        // Add course to instructor
        instructor.addCourse(course1);
        
        // Verify the total number of students in instructor's courses is 1
        assertEquals("Single course with one student should return 1", 
                     1, instructor.numberOfStudentsInCourses());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Setup: An Instructor with one course. That course has 25 enrolled students.
        
        // Create and configure the course with 25 students
        List<Student> studentsCourse1 = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            studentsCourse1.add(new Student());
        }
        course1.setEnrolledStudents(studentsCourse1);
        
        // Add course to instructor
        instructor.addCourse(course1);
        
        // Verify the total number of students in instructor's courses is 25
        assertEquals("Single course with 25 students should return 25", 
                     25, instructor.numberOfStudentsInCourses());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Setup: An Instructor with 3 courses. Each course has one enrolled student.
        
        // Create and configure three courses, each with one student
        List<Student> studentsCourse1 = new ArrayList<>();
        studentsCourse1.add(student1);
        course1.setEnrolledStudents(studentsCourse1);
        
        List<Student> studentsCourse2 = new ArrayList<>();
        studentsCourse2.add(student2);
        course2.setEnrolledStudents(studentsCourse2);
        
        List<Student> studentsCourse3 = new ArrayList<>();
        studentsCourse3.add(student3);
        course3.setEnrolledStudents(studentsCourse3);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Verify the total number of students in instructor's courses is 3
        assertEquals("Three courses with one student each should return 3", 
                     3, instructor.numberOfStudentsInCourses());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Setup: An Instructor with 4 courses. First course has 5 students, 
        // second has 10, third has 15, and fourth has 20 students.
        
        // Create and configure four courses with varying numbers of students
        List<Student> studentsCourse1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            studentsCourse1.add(new Student());
        }
        course1.setEnrolledStudents(studentsCourse1);
        
        List<Student> studentsCourse2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            studentsCourse2.add(new Student());
        }
        course2.setEnrolledStudents(studentsCourse2);
        
        List<Student> studentsCourse3 = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            studentsCourse3.add(new Student());
        }
        course3.setEnrolledStudents(studentsCourse3);
        
        List<Student> studentsCourse4 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            studentsCourse4.add(new Student());
        }
        course4.setEnrolledStudents(studentsCourse4);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        instructor.addCourse(course4);
        
        // Verify the total number of students in instructor's courses is 50 (5+10+15+20)
        assertEquals("Four courses with 5, 10, 15, and 20 students should return 50", 
                     50, instructor.numberOfStudentsInCourses());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: "No courses"
        // Setup: An Instructor with no courses assigned.
        
        // Instructor has no courses by default (empty list)
        // Verify the total number of students in instructor's courses is 0
        assertEquals("Instructor with no courses should return 0", 
                     0, instructor.numberOfStudentsInCourses());
    }
}