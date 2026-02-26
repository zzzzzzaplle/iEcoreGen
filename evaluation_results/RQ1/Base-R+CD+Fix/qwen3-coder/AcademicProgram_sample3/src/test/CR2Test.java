import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        // Initialize instructor before each test
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Test Case 1: "Single course, single student"
        // Input: "An Instructor with one course. That course has one enrolled student."
        
        // Create a course
        Course course = new Course();
        course.setCode("CS101");
        
        // Create a student and enroll in the course
        Student student = new Student();
        student.setName("John");
        student.setSurname("Doe");
        
        // Create enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setGrade(85.0);
        
        // Add enrollment to course
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        course.setEnrollments(enrollments);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 1."
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        
        // Create a course
        Course course = new Course();
        course.setCode("MATH201");
        
        // Create 25 students and enroll them in the course
        List<Enrollment> enrollments = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            Student student = new Student();
            student.setName("Student" + i);
            student.setSurname("Lastname" + i);
            
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setGrade(75.0 + i);
            enrollments.add(enrollment);
        }
        course.setEnrollments(enrollments);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        
        // Create 3 courses
        List<Course> courses = new ArrayList<>();
        
        for (int i = 1; i <= 3; i++) {
            Course course = new Course();
            course.setCode("COURSE" + i);
            
            // Create one student for each course
            Student student = new Student();
            student.setName("Student" + i);
            student.setSurname("Surname" + i);
            
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setGrade(80.0);
            
            List<Enrollment> enrollments = new ArrayList<>();
            enrollments.add(enrollment);
            course.setEnrollments(enrollments);
            
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        
        // Create 4 courses with varying student counts
        List<Course> courses = new ArrayList<>();
        int[] studentCounts = {5, 10, 15, 20};
        int totalExpected = 0;
        
        for (int i = 0; i < studentCounts.length; i++) {
            Course course = new Course();
            course.setCode("COURSE" + (i + 1));
            
            List<Enrollment> enrollments = new ArrayList<>();
            int studentCount = studentCounts[i];
            totalExpected += studentCount;
            
            for (int j = 1; j <= studentCount; j++) {
                Student student = new Student();
                student.setName("Student" + j);
                student.setSurname("Course" + (i + 1));
                
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                enrollment.setGrade(70.0 + j);
                enrollments.add(enrollment);
            }
            
            course.setEnrollments(enrollments);
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        assertEquals("Four courses with 5+10+15+20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: "No courses"
        // Input: "An Instructor with no courses assigned."
        
        // Instructor already has no courses from setUp()
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}