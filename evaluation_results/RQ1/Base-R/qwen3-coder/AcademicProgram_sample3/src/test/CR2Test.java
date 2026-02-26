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
        // Setup: Create instructor with one course that has one enrolled student
        Course course = new Course();
        course.setCode("CS101");
        course.setName("Introduction to Computer Science");
        
        Student student = new Student();
        student.setName("John");
        student.setSurname("Doe");
        student.setGrade(85.5);
        
        // Enroll student in course
        course.enrollStudent(student);
        student.getEnrolledCourses().add(course);
        
        // Assign course to instructor
        instructor.addCourse(course);
        
        // Verify: Total number of students should be 1
        assertEquals("Single course with one student should return 1", 
                     1, instructor.getNumberOfStudentsInCourses());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Setup: Create instructor with one course that has 25 enrolled students
        Course course = new Course();
        course.setCode("MATH201");
        course.setName("Advanced Mathematics");
        course.setQuota(30); // Set sufficient quota
        
        // Create and enroll 25 students
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.setName("Student" + i);
            student.setSurname("Surname" + i);
            student.setGrade(75.0 + i);
            
            course.enrollStudent(student);
            student.getEnrolledCourses().add(course);
        }
        
        // Assign course to instructor
        instructor.addCourse(course);
        
        // Verify: Total number of students should be 25
        assertEquals("Single course with 25 students should return 25", 
                     25, instructor.getNumberOfStudentsInCourses());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Setup: Create instructor with 3 courses, each with one enrolled student
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCode("COURSE" + (i + 1));
            course.setName("Course " + (i + 1));
            
            Student student = new Student();
            student.setName("Student" + i);
            student.setSurname("Surname" + i);
            student.setGrade(80.0 + i);
            
            course.enrollStudent(student);
            student.getEnrolledCourses().add(course);
            
            instructor.addCourse(course);
        }
        
        // Verify: Total number of students should be 3
        assertEquals("Three courses with one student each should return 3", 
                     3, instructor.getNumberOfStudentsInCourses());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Setup: Create instructor with 4 courses with varying student counts
        int[] studentCounts = {5, 10, 15, 20};
        int expectedTotal = 0;
        
        for (int i = 0; i < studentCounts.length; i++) {
            Course course = new Course();
            course.setCode("COURSE" + (i + 1));
            course.setName("Course " + (i + 1));
            course.setQuota(studentCounts[i] + 5); // Set sufficient quota
            
            // Create and enroll specified number of students
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                student.setName("Student" + i + "_" + j);
                student.setSurname("Surname" + i + "_" + j);
                student.setGrade(70.0 + j);
                
                course.enrollStudent(student);
                student.getEnrolledCourses().add(course);
            }
            
            expectedTotal += studentCounts[i];
            instructor.addCourse(course);
        }
        
        // Verify: Total number of students should be 50 (5+10+15+20)
        assertEquals("Four courses with 5,10,15,20 students should return 50", 
                     expectedTotal, instructor.getNumberOfStudentsInCourses());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Setup: Instructor with no courses assigned (default state)
        // No setup needed - instructor starts with no courses
        
        // Verify: Total number of students should be 0
        assertEquals("Instructor with no courses should return 0", 
                     0, instructor.getNumberOfStudentsInCourses());
    }
}