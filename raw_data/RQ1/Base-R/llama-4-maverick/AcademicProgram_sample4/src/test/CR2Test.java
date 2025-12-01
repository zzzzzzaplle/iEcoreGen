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
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    private Student student5;
    private List<Student> studentsList;
    
    @Before
    public void setUp() {
        // Initialize common objects for tests
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
        course4 = new Course();
        
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        student4 = new Student();
        student5 = new Student();
        
        studentsList = new ArrayList<>();
    }
    
    @Test
    public void testCase1_SingleCourseSingleStudent() {
        // Input: An Instructor with one course. That course has one enrolled student.
        
        // Create and set up student
        student1.setName("John");
        student1.setSurname("Doe");
        student1.setGrade(85.0);
        
        // Create and set up course
        course1.setName("Mathematics");
        course1.setCode("MATH101");
        
        // Add student to course
        course1.enrollStudent(student1);
        
        // Add course to instructor
        instructor.addCourse(course1);
        
        // Expected Output: The total number of students in the instructor's courses should be 1.
        int result = instructor.numberOfStudentsInCourses();
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_SingleCourseMultipleStudents() {
        // Input: An Instructor with one course. That course has 25 enrolled students.
        
        // Create and set up course
        course1.setName("Physics");
        course1.setCode("PHYS101");
        
        // Add 25 students to course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.setName("Student" + i);
            student.setSurname("Surname" + i);
            student.setGrade(75.0 + i);
            course1.enrollStudent(student);
        }
        
        // Add course to instructor
        instructor.addCourse(course1);
        
        // Expected Output: The total number of students in the instructor's courses should be 25.
        int result = instructor.numberOfStudentsInCourses();
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_MultipleCoursesSingleStudentPerCourse() {
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        
        // Create and set up students
        student1.setName("Alice");
        student1.setSurname("Smith");
        student1.setGrade(90.0);
        
        student2.setName("Bob");
        student2.setSurname("Johnson");
        student2.setGrade(85.0);
        
        student3.setName("Charlie");
        student3.setSurname("Brown");
        student3.setGrade(80.0);
        
        // Create and set up courses
        course1.setName("Chemistry");
        course1.setCode("CHEM101");
        course1.enrollStudent(student1);
        
        course2.setName("Biology");
        course2.setCode("BIO101");
        course2.enrollStudent(student2);
        
        course3.setName("Computer Science");
        course3.setCode("CS101");
        course3.enrollStudent(student3);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Expected Output: The total number of students in the instructor's courses should be 3.
        int result = instructor.numberOfStudentsInCourses();
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_MultipleCoursesVaryingStudentsPerCourse() {
        // Input: An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students.
        
        // Create and set up courses
        course1.setName("Mathematics");
        course1.setCode("MATH101");
        
        course2.setName("Physics");
        course2.setCode("PHYS101");
        
        course3.setName("Chemistry");
        course3.setCode("CHEM101");
        
        course4.setName("Biology");
        course4.setCode("BIO101");
        
        // Add students to course1 (5 students)
        for (int i = 0; i < 5; i++) {
            Student student = new Student();
            student.setName("MathStudent" + i);
            student.setSurname("Surname" + i);
            student.setGrade(70.0 + i);
            course1.enrollStudent(student);
        }
        
        // Add students to course2 (10 students)
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setName("PhysicsStudent" + i);
            student.setSurname("Surname" + i);
            student.setGrade(75.0 + i);
            course2.enrollStudent(student);
        }
        
        // Add students to course3 (15 students)
        for (int i = 0; i < 15; i++) {
            Student student = new Student();
            student.setName("ChemStudent" + i);
            student.setSurname("Surname" + i);
            student.setGrade(80.0 + i);
            course3.enrollStudent(student);
        }
        
        // Add students to course4 (20 students)
        for (int i = 0; i < 20; i++) {
            Student student = new Student();
            student.setName("BioStudent" + i);
            student.setSurname("Surname" + i);
            student.setGrade(85.0 + i);
            course4.enrollStudent(student);
        }
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        instructor.addCourse(course4);
        
        // Expected Output: The total number of students in the instructor's courses should be 50.
        int result = instructor.numberOfStudentsInCourses();
        assertEquals("Four courses with 5+10+15+20=50 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_NoCourses() {
        // Input: An Instructor with no courses assigned.
        
        // Instructor has no courses by default
        
        // Expected Output: The total number of students in the instructor's courses should be 0.
        int result = instructor.numberOfStudentsInCourses();
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}