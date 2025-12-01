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
        Student student = new Student();
        course.enrollStudent(student);
        instructor.addCourse(course);
        
        // Execute: Call the method to calculate total students
        int result = instructor.numberOfStudentsInCourses();
        
        // Verify: The total number of students in the instructor's courses should be 1.
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Setup: An Instructor with one course. That course has 25 enrolled students.
        Course course = new Course();
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            course.enrollStudent(student);
        }
        instructor.addCourse(course);
        
        // Execute: Call the method to calculate total students
        int result = instructor.numberOfStudentsInCourses();
        
        // Verify: The total number of students in the instructor's courses should be 25.
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Setup: An Instructor with 3 courses. Each course has one enrolled student.
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            Student student = new Student();
            course.enrollStudent(student);
            instructor.addCourse(course);
        }
        
        // Execute: Call the method to calculate total students
        int result = instructor.numberOfStudentsInCourses();
        
        // Verify: The total number of students in the instructor's courses should be 3.
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Setup: An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students.
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int count : studentCounts) {
            Course course = new Course();
            for (int i = 0; i < count; i++) {
                Student student = new Student();
                course.enrollStudent(student);
            }
            instructor.addCourse(course);
        }
        
        // Execute: Call the method to calculate total students
        int result = instructor.numberOfStudentsInCourses();
        
        // Verify: The total number of students in the instructor's courses should be 50.
        assertEquals("Four courses with 5, 10, 15, 20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Setup: An Instructor with no courses assigned.
        // Instructor is already created in setUp() with no courses
        
        // Execute: Call the method to calculate total students
        int result = instructor.numberOfStudentsInCourses();
        
        // Verify: The total number of students in the instructor's courses should be 0.
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}