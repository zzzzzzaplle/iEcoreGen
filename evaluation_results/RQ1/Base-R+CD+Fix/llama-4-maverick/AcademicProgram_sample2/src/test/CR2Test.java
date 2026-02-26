import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
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
        // Input: "An Instructor with one course. That course has one enrolled student."
        
        // Create a classroom
        Classroom classroom = new Classroom("A101", 30, "1", "A");
        
        // Create a course
        Course course = new Course();
        course.setName("Mathematics");
        course.setCode("MATH101");
        course.setQuota(30);
        course.setWeeklyHours(4);
        course.setClassroom(classroom);
        
        // Create a student and enroll in the course
        Student student = new Student();
        student.enrollInCourse(course);
        
        // Add course to instructor
        List<Course> instructorCourses = new ArrayList<>();
        instructorCourses.add(course);
        instructor.setCourses(instructorCourses);
        
        // Calculate total enrolled students
        int totalStudents = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 1."
        assertEquals("Single course with one student should return 1", 1, totalStudents);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Input: "An Instructor with one course. That course has 25 enrolled students."
        
        // Create a classroom
        Classroom classroom = new Classroom("B202", 40, "2", "B");
        
        // Create a course
        Course course = new Course();
        course.setName("Physics");
        course.setCode("PHYS101");
        course.setQuota(40);
        course.setWeeklyHours(3);
        course.setClassroom(classroom);
        
        // Create 25 students and enroll them in the course
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.enrollInCourse(course);
        }
        
        // Add course to instructor
        List<Course> instructorCourses = new ArrayList<>();
        instructorCourses.add(course);
        instructor.setCourses(instructorCourses);
        
        // Calculate total enrolled students
        int totalStudents = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 25."
        assertEquals("Single course with 25 students should return 25", 25, totalStudents);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Input: "An Instructor with 3 courses. Each course has one enrolled student."
        
        // Create classrooms
        Classroom classroom1 = new Classroom("C101", 35, "1", "C");
        Classroom classroom2 = new Classroom("C102", 40, "1", "C");
        Classroom classroom3 = new Classroom("C103", 45, "1", "C");
        
        // Create 3 courses
        Course course1 = new Course();
        course1.setName("Chemistry");
        course1.setCode("CHEM101");
        course1.setQuota(35);
        course1.setWeeklyHours(4);
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        course2.setName("Biology");
        course2.setCode("BIO101");
        course2.setQuota(40);
        course2.setWeeklyHours(3);
        course2.setClassroom(classroom2);
        
        Course course3 = new Course();
        course3.setName("Computer Science");
        course3.setCode("CS101");
        course3.setQuota(45);
        course3.setWeeklyHours(5);
        course3.setClassroom(classroom3);
        
        // Create one student for each course and enroll them
        Student student1 = new Student();
        student1.enrollInCourse(course1);
        
        Student student2 = new Student();
        student2.enrollInCourse(course2);
        
        Student student3 = new Student();
        student3.enrollInCourse(course3);
        
        // Add all courses to instructor
        List<Course> instructorCourses = new ArrayList<>();
        instructorCourses.add(course1);
        instructorCourses.add(course2);
        instructorCourses.add(course3);
        instructor.setCourses(instructorCourses);
        
        // Calculate total enrolled students
        int totalStudents = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 3."
        assertEquals("Three courses with one student each should return 3", 3, totalStudents);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Input: "An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students."
        
        // Create classrooms
        Classroom classroom1 = new Classroom("D101", 30, "1", "D");
        Classroom classroom2 = new Classroom("D102", 35, "1", "D");
        Classroom classroom3 = new Classroom("D201", 40, "2", "D");
        Classroom classroom4 = new Classroom("D202", 50, "2", "D");
        
        // Create 4 courses
        Course course1 = new Course();
        course1.setName("History");
        course1.setCode("HIST101");
        course1.setQuota(30);
        course1.setWeeklyHours(3);
        course1.setClassroom(classroom1);
        
        Course course2 = new Course();
        course2.setName("Geography");
        course2.setCode("GEOG101");
        course2.setQuota(35);
        course2.setWeeklyHours(2);
        course2.setClassroom(classroom2);
        
        Course course3 = new Course();
        course3.setName("Economics");
        course3.setCode("ECON101");
        course3.setQuota(40);
        course3.setWeeklyHours(4);
        course3.setClassroom(classroom3);
        
        Course course4 = new Course();
        course4.setName("Psychology");
        course4.setCode("PSYC101");
        course4.setQuota(50);
        course4.setWeeklyHours(3);
        course4.setClassroom(classroom4);
        
        // Create and enroll students for each course
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
        
        // Add all courses to instructor
        List<Course> instructorCourses = new ArrayList<>();
        instructorCourses.add(course1);
        instructorCourses.add(course2);
        instructorCourses.add(course3);
        instructorCourses.add(course4);
        instructor.setCourses(instructorCourses);
        
        // Calculate total enrolled students
        int totalStudents = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 50."
        assertEquals("Four courses with 5, 10, 15, and 20 students should return 50", 50, totalStudents);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Input: "An Instructor with no courses assigned."
        
        // Instructor has no courses (default empty list)
        
        // Calculate total enrolled students
        int totalStudents = instructor.calculateTotalEnrolledStudents();
        
        // Expected Output: "The total number of students in the instructor's courses should be 0."
        assertEquals("Instructor with no courses should return 0", 0, totalStudents);
    }
}