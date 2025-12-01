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
        course.setCourseCode("CS101");
        
        Student student = new Student();
        student.setStudentId("S001");
        
        // Enroll student in course
        course.getEnrolledStudents().add(student);
        student.getEnrolledCourses().add(course);
        
        // Assign course to instructor
        instructor.getCourses().add(course);
        course.setInstructor(instructor);
        
        // Execute: Get total number of students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Verify: The total number of students in the instructor's courses should be 1.
        assertEquals("Single course with one student should return 1", 1, totalStudents);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Setup: An Instructor with one course. That course has 25 enrolled students.
        Course course = new Course();
        course.setCourseCode("CS102");
        
        // Create and enroll 25 students
        for (int i = 1; i <= 25; i++) {
            Student student = new Student();
            student.setStudentId("S" + String.format("%03d", i));
            course.getEnrolledStudents().add(student);
            student.getEnrolledCourses().add(course);
            students.add(student);
        }
        
        // Assign course to instructor
        instructor.getCourses().add(course);
        course.setInstructor(instructor);
        
        // Execute: Get total number of students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Verify: The total number of students in the instructor's courses should be 25.
        assertEquals("Single course with 25 students should return 25", 25, totalStudents);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Setup: An Instructor with 3 courses. Each course has one enrolled student.
        for (int i = 1; i <= 3; i++) {
            Course course = new Course();
            course.setCourseCode("CS" + (200 + i));
            
            Student student = new Student();
            student.setStudentId("S" + String.format("%03d", i));
            
            // Enroll student in course
            course.getEnrolledStudents().add(student);
            student.getEnrolledCourses().add(course);
            
            // Assign course to instructor
            instructor.getCourses().add(course);
            course.setInstructor(instructor);
            
            courses.add(course);
            students.add(student);
        }
        
        // Execute: Get total number of students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Verify: The total number of students in the instructor's courses should be 3.
        assertEquals("Three courses with one student each should return 3", 3, totalStudents);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Setup: An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students.
        int[] studentCounts = {5, 10, 15, 20};
        int studentIdCounter = 1;
        
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setCourseCode("CS" + (300 + i));
            
            // Enroll specified number of students in this course
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                student.setStudentId("S" + String.format("%03d", studentIdCounter++));
                course.getEnrolledStudents().add(student);
                student.getEnrolledCourses().add(course);
                students.add(student);
            }
            
            // Assign course to instructor
            instructor.getCourses().add(course);
            course.setInstructor(instructor);
            
            courses.add(course);
        }
        
        // Execute: Get total number of students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Verify: The total number of students in the instructor's courses should be 50.
        assertEquals("Four courses with 5, 10, 15, 20 students should return 50", 50, totalStudents);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Setup: An Instructor with no courses assigned.
        // No courses are added to the instructor
        
        // Execute: Get total number of students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Verify: The total number of students in the instructor's courses should be 0.
        assertEquals("Instructor with no courses should return 0", 0, totalStudents);
    }
}