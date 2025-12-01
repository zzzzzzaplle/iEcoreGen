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
        // Create a single course and add it to instructor
        Course course = new Course();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Create a single student and enroll in the course
        Student student = new Student();
        course.enrollStudent(student);
        
        // Verify the total number of students in instructor's courses is 1
        assertEquals("Single course with one student should return 1", 
                     1, instructor.getTotalStudentsInCourses());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Create a single course and add it to instructor
        Course course = new Course();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Create 25 students and enroll them in the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            course.enrollStudent(student);
        }
        
        // Verify the total number of students in instructor's courses is 25
        assertEquals("Single course with 25 students should return 25", 
                     25, instructor.getTotalStudentsInCourses());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Create 3 courses and add them to instructor
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            courses.add(course);
            
            // Create one student per course and enroll
            Student student = new Student();
            course.enrollStudent(student);
        }
        instructor.setCourses(courses);
        
        // Verify the total number of students in instructor's courses is 3
        assertEquals("Three courses with one student each should return 3", 
                     3, instructor.getTotalStudentsInCourses());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Create 4 courses with varying student counts
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            courses.add(course);
            
            // Create specified number of students for this course
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                course.enrollStudent(student);
            }
        }
        instructor.setCourses(courses);
        
        // Verify the total number of students in instructor's courses is 50 (5+10+15+20)
        assertEquals("Four courses with 5,10,15,20 students should return 50", 
                     50, instructor.getTotalStudentsInCourses());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Instructor has no courses (empty list)
        instructor.setCourses(new ArrayList<Course>());
        
        // Verify the total number of students in instructor's courses is 0
        assertEquals("Instructor with no courses should return 0", 
                     0, instructor.getTotalStudentsInCourses());
    }
}