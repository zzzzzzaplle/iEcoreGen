import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CR2Test {
    
    private Instructor instructor;
    private Course course1;
    private Course course2;
    private Course course3;
    private Course course4;
    private Student student;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
        course4 = new Course();
        student = new Student();
    }
    
    @Test
    public void testCase1_SingleCourseSingleStudent() {
        // Test Case 1: "Single course, single student"
        // Input: An Instructor with one course. That course has one enrolled student.
        
        // Setup course with one student
        Map<Student, Integer> studentGrades = new HashMap<>();
        studentGrades.put(student, 85);
        course1.setStudentGrades(studentGrades);
        
        // Add course to instructor
        instructor.addCourse(course1);
        
        // Verify total number of students is 1
        assertEquals("The total number of students in the instructor's courses should be 1", 
                     1, instructor.getTotalNumberOfStudents());
    }
    
    @Test
    public void testCase2_SingleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: An Instructor with one course. That course has 25 enrolled students.
        
        // Setup course with 25 students
        Map<Student, Integer> studentGrades = new HashMap<>();
        for (int i = 0; i < 25; i++) {
            Student s = new Student();
            studentGrades.put(s, 80 + i);
        }
        course1.setStudentGrades(studentGrades);
        
        // Add course to instructor
        instructor.addCourse(course1);
        
        // Verify total number of students is 25
        assertEquals("The total number of students in the instructor's courses should be 25", 
                     25, instructor.getTotalNumberOfStudents());
    }
    
    @Test
    public void testCase3_MultipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: An Instructor with 3 courses. Each course has one enrolled student.
        
        // Setup three courses, each with one student
        Map<Student, Integer> grades1 = new HashMap<>();
        Map<Student, Integer> grades2 = new HashMap<>();
        Map<Student, Integer> grades3 = new HashMap<>();
        
        Student s1 = new Student();
        Student s2 = new Student();
        Student s3 = new Student();
        
        grades1.put(s1, 90);
        grades2.put(s2, 85);
        grades3.put(s3, 88);
        
        course1.setStudentGrades(grades1);
        course2.setStudentGrades(grades2);
        course3.setStudentGrades(grades3);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Verify total number of students is 3
        assertEquals("The total number of students in the instructor's courses should be 3", 
                     3, instructor.getTotalNumberOfStudents());
    }
    
    @Test
    public void testCase4_MultipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: An Instructor with 4 courses. First course has 5 students, second has 10, 
        // third has 15, and fourth has 20 students.
        
        // Setup four courses with different numbers of students
        Map<Student, Integer> grades1 = createStudentsWithGrades(5, 80);
        Map<Student, Integer> grades2 = createStudentsWithGrades(10, 75);
        Map<Student, Integer> grades3 = createStudentsWithGrades(15, 85);
        Map<Student, Integer> grades4 = createStudentsWithGrades(20, 90);
        
        course1.setStudentGrades(grades1);
        course2.setStudentGrades(grades2);
        course3.setStudentGrades(grades3);
        course4.setStudentGrades(grades4);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        instructor.addCourse(course4);
        
        // Verify total number of students is 50 (5 + 10 + 15 + 20)
        assertEquals("The total number of students in the instructor's courses should be 50", 
                     50, instructor.getTotalNumberOfStudents());
    }
    
    @Test
    public void testCase5_NoCourses() {
        // Test Case 5: "No courses"
        // Input: An Instructor with no courses assigned.
        
        // Instructor has no courses by default (empty courses list)
        // Verify total number of students is 0
        assertEquals("The total number of students in the instructor's courses should be 0", 
                     0, instructor.getTotalNumberOfStudents());
    }
    
    // Helper method to create students with grades
    private Map<Student, Integer> createStudentsWithGrades(int count, int baseGrade) {
        Map<Student, Integer> studentGrades = new HashMap<>();
        for (int i = 0; i < count; i++) {
            Student s = new Student();
            studentGrades.put(s, baseGrade + i);
        }
        return studentGrades;
    }
}