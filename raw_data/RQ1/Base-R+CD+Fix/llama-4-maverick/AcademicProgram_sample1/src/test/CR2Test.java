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
        // Initialize common test objects
        instructor = new Instructor();
        courses = new ArrayList<>();
        students = new ArrayList<>();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Test Case 1: "Single course, single student"
        // Input: An Instructor with one course. That course has one enrolled student.
        
        // Create a course
        Course course = new Course();
        course.setCode("CS101");
        
        // Create a student and enroll in the course
        Student student = new Student();
        student.enrollInCourse(course);
        
        // Assign the course to the instructor
        courses.add(course);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: The total number of students in the instructor's courses should be 1.
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: An Instructor with one course. That course has 25 enrolled students.
        
        // Create a course
        Course course = new Course();
        course.setCode("MATH201");
        
        // Create and enroll 25 students
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.enrollInCourse(course);
            students.add(student);
        }
        
        // Assign the course to the instructor
        courses.add(course);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: The total number of students in the instructor's courses should be 25.
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        
        // Create 3 courses
        Course course1 = new Course();
        course1.setCode("PHY301");
        Course course2 = new Course();
        course2.setCode("CHEM401");
        Course course3 = new Course();
        course3.setCode("BIO501");
        
        // Create and enroll one student in each course
        Student student1 = new Student();
        student1.enrollInCourse(course1);
        Student student2 = new Student();
        student2.enrollInCourse(course2);
        Student student3 = new Student();
        student3.enrollInCourse(course3);
        
        // Assign all courses to the instructor
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: The total number of students in the instructor's courses should be 3.
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students.
        
        // Create 4 courses
        Course course1 = new Course();
        course1.setCode("CS601");
        Course course2 = new Course();
        course2.setCode("MATH701");
        Course course3 = new Course();
        course3.setCode("PHY801");
        Course course4 = new Course();
        course4.setCode("CHEM901");
        
        // Enroll students in each course according to specification
        // Course 1: 5 students
        for (int i = 0; i < 5; i++) {
            Student student = new Student();
            student.enrollInCourse(course1);
        }
        
        // Course 2: 10 students
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.enrollInCourse(course2);
        }
        
        // Course 3: 15 students
        for (int i = 0; i < 15; i++) {
            Student student = new Student();
            student.enrollInCourse(course3);
        }
        
        // Course 4: 20 students
        for (int i = 0; i < 20; i++) {
            Student student = new Student();
            student.enrollInCourse(course4);
        }
        
        // Assign all courses to the instructor
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: The total number of students in the instructor's courses should be 50.
        assertEquals("Four courses with 5+10+15+20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: "No courses"
        // Input: An Instructor with no courses assigned.
        
        // Instructor has no courses (empty list by default from setUp)
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: The total number of students in the instructor's courses should be 0.
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}