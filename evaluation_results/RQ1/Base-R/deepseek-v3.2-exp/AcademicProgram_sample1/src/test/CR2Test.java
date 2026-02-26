import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private AcademicProgram academicProgram;
    private Instructor instructor;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Input: An Instructor with one course. That course has one enrolled student.
        Course course = new Course();
        course.setQuota(10);
        
        Student student = new Student();
        
        instructor.addCourse(course);
        course.addStudent(student);
        
        int result = academicProgram.getNumberOfStudentsInInstructorCourses(instructor);
        
        // Expected Output: The total number of students in the instructor's courses should be 1.
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Input: An Instructor with one course. That course has 25 enrolled students.
        Course course = new Course();
        course.setQuota(30); // Set quota higher than 25
        
        instructor.addCourse(course);
        
        // Add 25 students to the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            course.addStudent(student);
        }
        
        int result = academicProgram.getNumberOfStudentsInInstructorCourses(instructor);
        
        // Expected Output: The total number of students in the instructor's courses should be 25.
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        instructor.addCourse(new Course());
        instructor.addCourse(new Course());
        instructor.addCourse(new Course());
        
        // Add one student to each course
        for (Course course : instructor.getCourses()) {
            course.setQuota(5);
            Student student = new Student();
            course.addStudent(student);
        }
        
        int result = academicProgram.getNumberOfStudentsInInstructorCourses(instructor);
        
        // Expected Output: The total number of students in the instructor's courses should be 3.
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Input: An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students.
        Course course1 = new Course();
        Course course2 = new Course();
        Course course3 = new Course();
        Course course4 = new Course();
        
        course1.setQuota(10);
        course2.setQuota(15);
        course3.setQuota(20);
        course4.setQuota(25);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        instructor.addCourse(course4);
        
        // Add 5 students to first course
        for (int i = 0; i < 5; i++) {
            Student student = new Student();
            course1.addStudent(student);
        }
        
        // Add 10 students to second course
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            course2.addStudent(student);
        }
        
        // Add 15 students to third course
        for (int i = 0; i < 15; i++) {
            Student student = new Student();
            course3.addStudent(student);
        }
        
        // Add 20 students to fourth course
        for (int i = 0; i < 20; i++) {
            Student student = new Student();
            course4.addStudent(student);
        }
        
        int result = academicProgram.getNumberOfStudentsInInstructorCourses(instructor);
        
        // Expected Output: The total number of students in the instructor's courses should be 50.
        assertEquals("Four courses with 5+10+15+20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Input: An Instructor with no courses assigned.
        // Instructor already has no courses from setup
        
        int result = academicProgram.getNumberOfStudentsInInstructorCourses(instructor);
        
        // Expected Output: The total number of students in the instructor's courses should be 0.
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}