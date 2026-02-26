import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR2Test {
    
    private Instructor instructor;
    private Course course1, course2, course3, course4;
    private Student student1, student2, student3, student4, student5;
    
    @Before
    public void setUp() {
        // Initialize instructor and courses for test setup
        instructor = new Instructor();
        course1 = new Course();
        course2 = new Course();
        course3 = new Course();
        course4 = new Course();
        
        // Initialize some sample students
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        student4 = new Student();
        student5 = new Student();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Test Case 1: "Single course, single student"
        // Input: "An Instructor with one course. That course has one enrolled student."
        
        // Setup: Create one course with one student
        List<Student> studentsForCourse1 = new ArrayList<>();
        studentsForCourse1.add(student1);
        course1.setStudents(studentsForCourse1);
        
        // Add the course to instructor
        List<Course> instructorCourses = new ArrayList<>();
        instructorCourses.add(course1);
        instructor.setCourses(instructorCourses);
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalStudents();
        
        // Verify: The total number of students in the instructor's courses should be 1
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Test Case 2: "Single course, multiple students"
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        
        // Setup: Create one course with 25 students
        List<Student> studentsForCourse1 = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            studentsForCourse1.add(student);
        }
        course1.setStudents(studentsForCourse1);
        
        // Add the course to instructor
        List<Course> instructorCourses = new ArrayList<>();
        instructorCourses.add(course1);
        instructor.setCourses(instructorCourses);
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalStudents();
        
        // Verify: The total number of students in the instructor's courses should be 25
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Test Case 3: "Multiple courses, single student per course"
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        
        // Setup: Create 3 courses, each with one student
        List<Student> studentsForCourse1 = new ArrayList<>();
        studentsForCourse1.add(student1);
        course1.setStudents(studentsForCourse1);
        
        List<Student> studentsForCourse2 = new ArrayList<>();
        studentsForCourse2.add(student2);
        course2.setStudents(studentsForCourse2);
        
        List<Student> studentsForCourse3 = new ArrayList<>();
        studentsForCourse3.add(student3);
        course3.setStudents(studentsForCourse3);
        
        // Add all courses to instructor
        List<Course> instructorCourses = new ArrayList<>();
        instructorCourses.add(course1);
        instructorCourses.add(course2);
        instructorCourses.add(course3);
        instructor.setCourses(instructorCourses);
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalStudents();
        
        // Verify: The total number of students in the instructor's courses should be 3
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Test Case 4: "Multiple courses, varying students per course"
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        
        // Setup: Create 4 courses with specified number of students
        // Course 1: 5 students
        List<Student> studentsForCourse1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            studentsForCourse1.add(new Student());
        }
        course1.setStudents(studentsForCourse1);
        
        // Course 2: 10 students
        List<Student> studentsForCourse2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            studentsForCourse2.add(new Student());
        }
        course2.setStudents(studentsForCourse2);
        
        // Course 3: 15 students
        List<Student> studentsForCourse3 = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            studentsForCourse3.add(new Student());
        }
        course3.setStudents(studentsForCourse3);
        
        // Course 4: 20 students
        List<Student> studentsForCourse4 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            studentsForCourse4.add(new Student());
        }
        course4.setStudents(studentsForCourse4);
        
        // Add all courses to instructor
        List<Course> instructorCourses = new ArrayList<>();
        instructorCourses.add(course1);
        instructorCourses.add(course2);
        instructorCourses.add(course3);
        instructorCourses.add(course4);
        instructor.setCourses(instructorCourses);
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalStudents();
        
        // Verify: The total number of students in the instructor's courses should be 50
        assertEquals("Four courses with 5+10+15+20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Test Case 5: "No courses"
        // Input: "An Instructor with no courses assigned."
        
        // Setup: Instructor has no courses (empty list)
        List<Course> instructorCourses = new ArrayList<>();
        instructor.setCourses(instructorCourses);
        
        // Execute: Get total number of students in instructor's courses
        int result = instructor.getTotalStudents();
        
        // Verify: The total number of students in the instructor's courses should be 0
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}