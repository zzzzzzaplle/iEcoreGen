import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Instructor instructor;
    private Course course1, course2, course3, course4;
    private Student student;
    private List<Student> students;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
        course4 = new Course();
        students = new ArrayList<>();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Test Case 1: "Single course, single student"
        // Input: "An Instructor with one course. That course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 1."
        
        // Create one student
        student = new Student();
        student.setName("John");
        student.setSurname("Doe");
        
        // Add student to course
        course1.getStudents().add(student);
        
        // Add course to instructor
        instructor.addCourse(course1);
        
        // Verify the total number of students in instructor's courses
        assertEquals("Single course with one student should return 1", 1, instructor.numberOfStudentsInCourses());
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        
        // Create 25 students
        for (int i = 0; i < 25; i++) {
            Student s = new Student();
            s.setName("Student" + i);
            s.setSurname("Surname" + i);
            students.add(s);
        }
        
        // Add all students to course
        course1.setStudents(students);
        
        // Add course to instructor
        instructor.addCourse(course1);
        
        // Verify the total number of students in instructor's courses
        assertEquals("Single course with 25 students should return 25", 25, instructor.numberOfStudentsInCourses());
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        
        // Create 3 students
        Student student1 = new Student();
        student1.setName("Student1");
        student1.setSurname("Surname1");
        
        Student student2 = new Student();
        student2.setName("Student2");
        student2.setSurname("Surname2");
        
        Student student3 = new Student();
        student3.setName("Student3");
        student3.setSurname("Surname3");
        
        // Add one student to each course
        course1.getStudents().add(student1);
        course2.getStudents().add(student2);
        course3.getStudents().add(student3);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        
        // Verify the total number of students in instructor's courses
        assertEquals("Three courses with one student each should return 3", 3, instructor.numberOfStudentsInCourses());
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        
        // Create students for first course (5 students)
        List<Student> students1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Student s = new Student();
            s.setName("Course1Student" + i);
            s.setSurname("Surname");
            students1.add(s);
        }
        
        // Create students for second course (10 students)
        List<Student> students2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Student s = new Student();
            s.setName("Course2Student" + i);
            s.setSurname("Surname");
            students2.add(s);
        }
        
        // Create students for third course (15 students)
        List<Student> students3 = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Student s = new Student();
            s.setName("Course3Student" + i);
            s.setSurname("Surname");
            students3.add(s);
        }
        
        // Create students for fourth course (20 students)
        List<Student> students4 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Student s = new Student();
            s.setName("Course4Student" + i);
            s.setSurname("Surname");
            students4.add(s);
        }
        
        // Add students to respective courses
        course1.setStudents(students1);
        course2.setStudents(students2);
        course3.setStudents(students3);
        course4.setStudents(students4);
        
        // Add all courses to instructor
        instructor.addCourse(course1);
        instructor.addCourse(course2);
        instructor.addCourse(course3);
        instructor.addCourse(course4);
        
        // Verify the total number of students in instructor's courses
        assertEquals("Four courses with 5+10+15+20 students should return 50", 50, instructor.numberOfStudentsInCourses());
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: "No courses"
        // Input: "An Instructor with no courses assigned."
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        
        // Instructor has no courses by default
        
        // Verify the total number of students in instructor's courses
        assertEquals("Instructor with no courses should return 0", 0, instructor.numberOfStudentsInCourses());
    }
}