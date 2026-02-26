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
        course.setCode("CS101");
        
        // Create student
        Student student = new Student();
        students.add(student);
        
        // Enroll student in course
        student.enrollInCourse(course);
        
        // Assign course to instructor
        courses.add(course);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: The total number of students in the instructor's courses should be 1.
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Input: An Instructor with one course. That course has 25 enrolled students.
        
        // Create course
        Course course = new Course();
        course.setCode("MATH201");
        
        // Create and enroll 25 students
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            students.add(student);
            student.enrollInCourse(course);
        }
        
        // Assign course to instructor
        courses.add(course);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: The total number of students in the instructor's courses should be 25.
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        
        // Create 3 courses
        Course course1 = new Course();
        course1.setCode("PHY301");
        
        Course course2 = new Course();
        course2.setCode("CHEM401");
        
        Course course3 = new Course();
        course3.setCode("BIO501");
        
        // Create 3 students and enroll each in a different course
        Student student1 = new Student();
        students.add(student1);
        student1.enrollInCourse(course1);
        
        Student student2 = new Student();
        students.add(student2);
        student2.enrollInCourse(course2);
        
        Student student3 = new Student();
        students.add(student3);
        student3.enrollInCourse(course3);
        
        // Assign all courses to instructor
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
        
        // Enroll 5 students in course1
        for (int i = 0; i < 5; i++) {
            Student student = new Student();
            students.add(student);
            student.enrollInCourse(course1);
        }
        
        // Enroll 10 students in course2
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            students.add(student);
            student.enrollInCourse(course2);
        }
        
        // Enroll 15 students in course3
        for (int i = 0; i < 15; i++) {
            Student student = new Student();
            students.add(student);
            student.enrollInCourse(course3);
        }
        
        // Enroll 20 students in course4
        for (int i = 0; i < 20; i++) {
            Student student = new Student();
            students.add(student);
            student.enrollInCourse(course4);
        }
        
        // Assign all courses to instructor
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: The total number of students in the instructor's courses should be 50.
        assertEquals("Four courses with 5, 10, 15, and 20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Input: An Instructor with no courses assigned.
        
        // Instructor has no courses (default empty list)
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: The total number of students in the instructor's courses should be 0.
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}