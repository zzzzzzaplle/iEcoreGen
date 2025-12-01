import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

public class CR2Test {
    
    private Instructor instructor;
    private Course course1, course2, course3, course4;
    private Student student1, student2, student3, student4, student5;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        instructor = new Instructor();
        instructor.setName("John");
        instructor.setSurname("Doe");
        
        course1 = new Course();
        course1.setCourseCode("CS101");
        course1.setCourseName("Introduction to Computer Science");
        course1.setQuotaLimit(30);
        
        course2 = new Course();
        course2.setCourseCode("CS102");
        course2.setCourseName("Data Structures");
        course2.setQuotaLimit(30);
        
        course3 = new Course();
        course3.setCourseCode("CS103");
        course3.setCourseName("Algorithms");
        course3.setQuotaLimit(30);
        
        course4 = new Course();
        course4.setCourseCode("CS104");
        course4.setCourseName("Database Systems");
        course4.setQuotaLimit(30);
        
        student1 = new Student();
        student1.setStudentId("S001");
        student1.setName("Alice");
        student1.setSurname("Smith");
        
        student2 = new Student();
        student2.setStudentId("S002");
        student2.setName("Bob");
        student2.setSurname("Johnson");
        
        student3 = new Student();
        student3.setStudentId("S003");
        student3.setName("Charlie");
        student3.setSurname("Brown");
        
        student4 = new Student();
        student4.setStudentId("S004");
        student4.setName("Diana");
        student4.setSurname("Miller");
        
        student5 = new Student();
        student5.setStudentId("S005");
        student5.setName("Eve");
        student5.setSurname("Davis");
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Test Case 1: "Single course, single student"
        // Input: An Instructor with one course. That course has one enrolled student.
        
        // Setup: Add one course to instructor and enroll one student
        instructor.addCourse(course1);
        student1.enrolInCourse(course1);
        
        // Verify: The total number of students in the instructor's courses should be 1
        assertEquals("Single course with one student should return 1", 
                     1, instructor.getTotalEnrolledStudents());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: An Instructor with one course. That course has 25 enrolled students.
        
        // Setup: Add one course to instructor
        instructor.addCourse(course1);
        
        // Setup: Enroll 25 students in the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.setStudentId("S" + String.format("%03d", i));
            student.setName("Student" + i);
            student.setSurname("Test");
            student.enrolInCourse(course1);
        }
        
        // Verify: The total number of students in the instructor's courses should be 25
        assertEquals("Single course with 25 students should return 25", 
                     25, instructor.getTotalEnrolledStudents());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        
        // Setup: Add 3 courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Setup: Enroll one student in each course
        student1.enrolInCourse(course1);
        student2.enrolInCourse(course2);
        student3.enrolInCourse(course3);
        
        // Verify: The total number of students in the instructor's courses should be 3
        assertEquals("Three courses with one student each should return 3", 
                     3, instructor.getTotalEnrolledStudents());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: An Instructor with 4 courses. First course has 5 students, 
        // second has 10, third has 15, and fourth has 20 students.
        
        // Setup: Add 4 courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        instructor.addCourse(course4);
        
        // Setup: Enroll 5 students in first course
        for (int i = 0; i < 5; i++) {
            Student student = new Student();
            student.setStudentId("S1" + String.format("%02d", i));
            student.setName("Student1_" + i);
            student.setSurname("Test");
            student.enrolInCourse(course1);
        }
        
        // Setup: Enroll 10 students in second course
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setStudentId("S2" + String.format("%02d", i));
            student.setName("Student2_" + i);
            student.setSurname("Test");
            student.enrolInCourse(course2);
        }
        
        // Setup: Enroll 15 students in third course
        for (int i = 0; i < 15; i++) {
            Student student = new Student();
            student.setStudentId("S3" + String.format("%02d", i));
            student.setName("Student3_" + i);
            student.setSurname("Test");
            student.enrolInCourse(course3);
        }
        
        // Setup: Enroll 20 students in fourth course
        for (int i = 0; i < 20; i++) {
            Student student = new Student();
            student.setStudentId("S4" + String.format("%02d", i));
            student.setName("Student4_" + i);
            student.setSurname("Test");
            student.enrolInCourse(course4);
        }
        
        // Verify: The total number of students in the instructor's courses should be 50
        assertEquals("Four courses with 5+10+15+20 students should return 50", 
                     50, instructor.getTotalEnrolledStudents());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: "No courses"
        // Input: An Instructor with no courses assigned.
        
        // Setup: Instructor has no courses (default state)
        
        // Verify: The total number of students in the instructor's courses should be 0
        assertEquals("Instructor with no courses should return 0", 
                     0, instructor.getTotalEnrolledStudents());
    }
}