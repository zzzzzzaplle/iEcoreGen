import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Instructor instructor;
    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;
    private List<Student> students;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
        course4 = new Course();
        students = new ArrayList<>();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Input: "An Instructor with one course. That course has one enrolled student."
        
        // Create instructor and add one course
        instructor.addCourse(course1);
        
        // Create one student and enroll in course
        Student student = new Student();
        students.add(student);
        course1.getEnrolledStudents().add(student);
        
        // Expected Output: "The total number of students in the instructor's courses should be 1."
        assertEquals("Single course with one student should return 1", 
                     1, instructor.getTotalEnrolledStudents());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        
        // Create instructor and add one course
        instructor.addCourse(course1);
        
        // Create 25 students and enroll them in course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            students.add(student);
            course1.getEnrolledStudents().add(student);
        }
        
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        assertEquals("Single course with 25 students should return 25", 
                     25, instructor.getTotalEnrolledStudents());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        
        // Create instructor and add 3 courses
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Create 3 students and enroll one in each course
        for (int i = 0; i < 3; i++) {
            Student student = new Student();
            students.add(student);
        }
        
        course1.getEnrolledStudents().add(students.get(0));
        course2.getEnrolledStudents().add(students.get(1));
        course3.getEnrolledStudents().add(students.get(2));
        
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        assertEquals("Three courses with one student each should return 3", 
                     3, instructor.getTotalEnrolledStudents());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        
        // Create instructor and add 4 courses
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        instructor.addCourse(course4);
        
        // Create students for first course (5 students)
        for (int i = 0; i < 5; i++) {
            Student student = new Student();
            students.add(student);
            course1.getEnrolledStudents().add(student);
        }
        
        // Create students for second course (10 students)
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            students.add(student);
            course2.getEnrolledStudents().add(student);
        }
        
        // Create students for third course (15 students)
        for (int i = 0; i < 15; i++) {
            Student student = new Student();
            students.add(student);
            course3.getEnrolledStudents().add(student);
        }
        
        // Create students for fourth course (20 students)
        for (int i = 0; i < 20; i++) {
            Student student = new Student();
            students.add(student);
            course4.getEnrolledStudents().add(student);
        }
        
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        assertEquals("Four courses with 5, 10, 15, and 20 students should return 50", 
                     50, instructor.getTotalEnrolledStudents());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Input: "An Instructor with no courses assigned."
        // No setup needed - instructor has no courses by default
        
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        assertEquals("Instructor with no courses should return 0", 
                     0, instructor.getTotalEnrolledStudents());
    }
}