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
    private List<Student> students;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
        students = new ArrayList<>();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Setup: An Instructor with one course. That course has one enrolled student.
        course1 = new Course();
        course1.setCourseCode("CS101");
        
        Student student1 = new Student();
        student1.setStudentId("S001");
        List<Student> course1Students = new ArrayList<>();
        course1Students.add(student1);
        course1.setStudents(course1Students);
        
        instructor.addCourse(course1);
        
        // Execute: Get total number of students
        int result = instructor.getTotalNumberOfStudents();
        
        // Verify: The total number of students in the instructor's courses should be 1.
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Setup: An Instructor with one course. That course has 25 enrolled students.
        course1 = new Course();
        course1.setCourseCode("CS101");
        
        List<Student> course1Students = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            Student student = new Student();
            student.setStudentId("S" + String.format("%03d", i));
            course1Students.add(student);
        }
        course1.setStudents(course1Students);
        
        instructor.addCourse(course1);
        
        // Execute: Get total number of students
        int result = instructor.getTotalNumberOfStudents();
        
        // Verify: The total number of students in the instructor's courses should be 25.
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Setup: An Instructor with 3 courses. Each course has one enrolled student.
        course1 = new Course();
        course1.setCourseCode("CS101");
        Student student1 = new Student();
        student1.setStudentId("S001");
        List<Student> course1Students = new ArrayList<>();
        course1Students.add(student1);
        course1.setStudents(course1Students);
        
        course2 = new Course();
        course2.setCourseCode("CS102");
        Student student2 = new Student();
        student2.setStudentId("S002");
        List<Student> course2Students = new ArrayList<>();
        course2Students.add(student2);
        course2.setStudents(course2Students);
        
        course3 = new Course();
        course3.setCourseCode("CS103");
        Student student3 = new Student();
        student3.setStudentId("S003");
        List<Student> course3Students = new ArrayList<>();
        course3Students.add(student3);
        course3.setStudents(course3Students);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Execute: Get total number of students
        int result = instructor.getTotalNumberOfStudents();
        
        // Verify: The total number of students in the instructor's courses should be 3.
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Setup: An Instructor with 4 courses. First course has 5 students, second has 10, 
        // third has 15, and fourth has 20 students.
        course1 = new Course();
        course1.setCourseCode("CS101");
        List<Student> course1Students = createStudents(5, "A");
        course1.setStudents(course1Students);
        
        course2 = new Course();
        course2.setCourseCode("CS102");
        List<Student> course2Students = createStudents(10, "B");
        course2.setStudents(course2Students);
        
        course3 = new Course();
        course3.setCourseCode("CS103");
        List<Student> course3Students = createStudents(15, "C");
        course3.setStudents(course3Students);
        
        course4 = new Course();
        course4.setCourseCode("CS104");
        List<Student> course4Students = createStudents(20, "D");
        course4.setStudents(course4Students);
        
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        instructor.addCourse(course4);
        
        // Execute: Get total number of students
        int result = instructor.getTotalNumberOfStudents();
        
        // Verify: The total number of students in the instructor's courses should be 50.
        assertEquals("Four courses with 5+10+15+20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Setup: An Instructor with no courses assigned.
        // No courses added to instructor
        
        // Execute: Get total number of students
        int result = instructor.getTotalNumberOfStudents();
        
        // Verify: The total number of students in the instructor's courses should be 0.
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
    
    // Helper method to create students
    private List<Student> createStudents(int count, String prefix) {
        List<Student> studentList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Student student = new Student();
            student.setStudentId(prefix + String.format("%03d", i));
            studentList.add(student);
        }
        return studentList;
    }
}