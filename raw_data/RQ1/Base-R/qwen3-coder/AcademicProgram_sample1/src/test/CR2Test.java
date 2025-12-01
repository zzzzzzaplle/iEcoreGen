import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_SingleCourseSingleStudent() {
        // Input: An Instructor with one course. That course has one enrolled student.
        
        // Create course
        Course course = new Course();
        course.setCode("CS101");
        
        // Create student
        Student student = new Student();
        student.setName("John");
        student.setSurname("Doe");
        student.setStudentId("12345");
        
        // Enroll student in course
        course.enrollStudent(student);
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Expected Output: The total number of students in the instructor's courses should be 1.
        assertEquals("Single course with one student should return 1", 1, instructor.getNumberOfStudentsInCourses());
    }
    
    @Test
    public void testCase2_SingleCourseMultipleStudents() {
        // Input: An Instructor with one course. That course has 25 enrolled students.
        
        // Create course
        Course course = new Course();
        course.setCode("CS102");
        course.setQuotaLimit(30); // Set high quota to allow 25 students
        
        // Create and enroll 25 students
        for (int i = 1; i <= 25; i++) {
            Student student = new Student();
            student.setName("Student" + i);
            student.setSurname("Surname" + i);
            student.setStudentId("ID" + i);
            course.enrollStudent(student);
        }
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Expected Output: The total number of students in the instructor's courses should be 25.
        assertEquals("Single course with 25 students should return 25", 25, instructor.getNumberOfStudentsInCourses());
    }
    
    @Test
    public void testCase3_MultipleCoursesSingleStudentPerCourse() {
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        
        // Create 3 courses
        Course course1 = new Course();
        course1.setCode("CS201");
        Course course2 = new Course();
        course2.setCode("CS202");
        Course course3 = new Course();
        course3.setCode("CS203");
        
        // Create and enroll one student in each course
        Student student1 = new Student();
        student1.setName("Alice");
        student1.setSurname("Smith");
        student1.setStudentId("S001");
        course1.enrollStudent(student1);
        
        Student student2 = new Student();
        student2.setName("Bob");
        student2.setSurname("Johnson");
        student2.setStudentId("S002");
        course2.enrollStudent(student2);
        
        Student student3 = new Student();
        student3.setName("Charlie");
        student3.setSurname("Brown");
        student3.setStudentId("S003");
        course3.enrollStudent(student3);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Expected Output: The total number of students in the instructor's courses should be 3.
        assertEquals("Three courses with one student each should return 3", 3, instructor.getNumberOfStudentsInCourses());
    }
    
    @Test
    public void testCase4_MultipleCoursesVaryingStudentsPerCourse() {
        // Input: An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students.
        
        // Create 4 courses with high quotas
        Course course1 = new Course();
        course1.setCode("CS301");
        course1.setQuotaLimit(10);
        
        Course course2 = new Course();
        course2.setCode("CS302");
        course2.setQuotaLimit(15);
        
        Course course3 = new Course();
        course3.setCode("CS303");
        course3.setQuotaLimit(20);
        
        Course course4 = new Course();
        course4.setCode("CS304");
        course4.setQuotaLimit(25);
        
        // Enroll students in course1 (5 students)
        for (int i = 1; i <= 5; i++) {
            Student student = new Student();
            student.setName("Student1_" + i);
            student.setSurname("Course1");
            student.setStudentId("C1S" + i);
            course1.enrollStudent(student);
        }
        
        // Enroll students in course2 (10 students)
        for (int i = 1; i <= 10; i++) {
            Student student = new Student();
            student.setName("Student2_" + i);
            student.setSurname("Course2");
            student.setStudentId("C2S" + i);
            course2.enrollStudent(student);
        }
        
        // Enroll students in course3 (15 students)
        for (int i = 1; i <= 15; i++) {
            Student student = new Student();
            student.setName("Student3_" + i);
            student.setSurname("Course3");
            student.setStudentId("C3S" + i);
            course3.enrollStudent(student);
        }
        
        // Enroll students in course4 (20 students)
        for (int i = 1; i <= 20; i++) {
            Student student = new Student();
            student.setName("Student4_" + i);
            student.setSurname("Course4");
            student.setStudentId("C4S" + i);
            course4.enrollStudent(student);
        }
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        instructor.addCourse(course4);
        
        // Expected Output: The total number of students in the instructor's courses should be 50.
        assertEquals("Four courses with 5, 10, 15, and 20 students should return 50", 50, instructor.getNumberOfStudentsInCourses());
    }
    
    @Test
    public void testCase5_NoCourses() {
        // Input: An Instructor with no courses assigned.
        
        // Instructor already has no courses from setup
        
        // Expected Output: The total number of students in the instructor's courses should be 0.
        assertEquals("Instructor with no courses should return 0", 0, instructor.getNumberOfStudentsInCourses());
    }
}