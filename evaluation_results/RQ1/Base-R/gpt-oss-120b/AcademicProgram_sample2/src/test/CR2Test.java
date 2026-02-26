import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private Instructor instructor;
    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;
    private Student student;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
        course4 = new Course();
        student = new Student();
    }
    
    @Test
    public void testCase1_SingleCourseSingleStudent() {
        // Input: "An Instructor with one course. That course has one enrolled student."
        instructor.addCourse(course1);
        
        Student student1 = new Student();
        course1.getEnrolledStudents().add(student1);
        
        // Expected Output: "The total number of students in the instructor's courses should be 1."
        assertEquals("Single course with one student should return 1", 1, instructor.getTotalEnrolledStudents());
    }
    
    @Test
    public void testCase2_SingleCourseMultipleStudents() {
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        instructor.addCourse(course1);
        
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            course1.getEnrolledStudents().add(student);
        }
        
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        assertEquals("Single course with 25 students should return 25", 25, instructor.getTotalEnrolledStudents());
    }
    
    @Test
    public void testCase3_MultipleCoursesSingleStudentPerCourse() {
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        
        course1.getEnrolledStudents().add(student1);
        course2.getEnrolledStudents().add(student2);
        course3.getEnrolledStudents().add(student3);
        
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        assertEquals("Three courses with one student each should return 3", 3, instructor.getTotalEnrolledStudents());
    }
    
    @Test
    public void testCase4_MultipleCoursesVaryingStudentsPerCourse() {
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        instructor.addCourse(course4);
        
        // Add 5 students to course1
        for (int i = 0; i < 5; i++) {
            course1.getEnrolledStudents().add(new Student());
        }
        
        // Add 10 students to course2
        for (int i = 0; i < 10; i++) {
            course2.getEnrolledStudents().add(new Student());
        }
        
        // Add 15 students to course3
        for (int i = 0; i < 15; i++) {
            course3.getEnrolledStudents().add(new Student());
        }
        
        // Add 20 students to course4
        for (int i = 0; i < 20; i++) {
            course4.getEnrolledStudents().add(new Student());
        }
        
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        assertEquals("Four courses with 5+10+15+20 students should return 50", 50, instructor.getTotalEnrolledStudents());
    }
    
    @Test
    public void testCase5_NoCourses() {
        // Input: "An Instructor with no courses assigned."
        // No courses added to instructor
        
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        assertEquals("Instructor with no courses should return 0", 0, instructor.getTotalEnrolledStudents());
    }
}