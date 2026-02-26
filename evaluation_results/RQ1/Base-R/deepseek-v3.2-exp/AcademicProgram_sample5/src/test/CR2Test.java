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
        // Input: An Instructor with one course. That course has one enrolled student.
        
        // Create course
        Course course = new Course();
        course.setCourseCode("CS101");
        course.setCourseName("Introduction to Programming");
        
        // Create student
        Student student = new Student();
        student.setStudentId("S001");
        student.setName("John");
        student.setSurname("Doe");
        
        // Enroll student in course
        course.addStudent(student);
        
        // Assign course to instructor
        instructor.addCourse(course);
        
        // Calculate total students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Expected Output: The total number of students in the instructor's courses should be 1.
        assertEquals("Single course with one student should return 1", 1, totalStudents);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Input: An Instructor with one course. That course has 25 enrolled students.
        
        // Create course
        Course course = new Course();
        course.setCourseCode("CS201");
        course.setCourseName("Data Structures");
        
        // Create and enroll 25 students
        for (int i = 1; i <= 25; i++) {
            Student student = new Student();
            student.setStudentId("S" + String.format("%03d", i));
            student.setName("Student" + i);
            student.setSurname("Test");
            course.addStudent(student);
        }
        
        // Assign course to instructor
        instructor.addCourse(course);
        
        // Calculate total students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Expected Output: The total number of students in the instructor's courses should be 25.
        assertEquals("Single course with 25 students should return 25", 25, totalStudents);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        
        // Create 3 courses
        for (int i = 1; i <= 3; i++) {
            Course course = new Course();
            course.setCourseCode("CS30" + i);
            course.setCourseName("Course " + i);
            
            // Create and enroll one student per course
            Student student = new Student();
            student.setStudentId("S10" + i);
            student.setName("Student" + i);
            student.setSurname("Test");
            course.addStudent(student);
            
            // Assign course to instructor
            instructor.addCourse(course);
        }
        
        // Calculate total students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Expected Output: The total number of students in the instructor's courses should be 3.
        assertEquals("3 courses with one student each should return 3", 3, totalStudents);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Input: An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students.
        
        int[] studentCounts = {5, 10, 15, 20};
        
        // Create 4 courses with varying student counts
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setCourseCode("CS40" + (i + 1));
            course.setCourseName("Course " + (i + 1));
            
            // Create and enroll students for this course
            for (int j = 1; j <= studentCounts[i]; j++) {
                Student student = new Student();
                student.setStudentId("S20" + i + j);
                student.setName("Student" + i + j);
                student.setSurname("Test");
                course.addStudent(student);
            }
            
            // Assign course to instructor
            instructor.addCourse(course);
        }
        
        // Calculate total students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Expected Output: The total number of students in the instructor's courses should be 50.
        assertEquals("4 courses with 5+10+15+20 students should return 50", 50, totalStudents);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Input: An Instructor with no courses assigned.
        // No courses added to instructor - using default empty state
        
        // Calculate total students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Expected Output: The total number of students in the instructor's courses should be 0.
        assertEquals("Instructor with no courses should return 0", 0, totalStudents);
    }
}