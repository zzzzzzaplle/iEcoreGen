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
        // Create instructor with one course that has one enrolled student
        instructor = new Instructor("John", "Doe", "Dr.", "Computer Science");
        
        // Create classroom for the course
        Classroom classroom = new Classroom("C101", 30, "1", "A");
        
        // Create course
        Course course = new Course("Java Programming", "CS101", new Date(), new Date(), 30, 3, classroom);
        courses.add(course);
        instructor.setCourses(courses);
        
        // Create and enroll one student
        Student student = new Student();
        student.enrollInCourse(course);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Create instructor with one course that has 25 enrolled students
        instructor = new Instructor("Jane", "Smith", "Prof.", "Mathematics");
        
        // Create classroom for the course
        Classroom classroom = new Classroom("M201", 30, "2", "B");
        
        // Create course
        Course course = new Course("Calculus", "MATH201", new Date(), new Date(), 30, 4, classroom);
        courses.add(course);
        instructor.setCourses(courses);
        
        // Create and enroll 25 students
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.enrollInCourse(course);
            students.add(student);
        }
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Create instructor with 3 courses, each with one enrolled student
        instructor = new Instructor("Bob", "Wilson", "Mr.", "Physics");
        
        // Create classrooms for the courses
        Classroom classroom1 = new Classroom("P101", 25, "1", "C");
        Classroom classroom2 = new Classroom("P102", 25, "1", "C");
        Classroom classroom3 = new Classroom("P103", 25, "1", "C");
        
        // Create 3 courses
        Course course1 = new Course("Physics I", "PHY101", new Date(), new Date(), 25, 3, classroom1);
        Course course2 = new Course("Physics II", "PHY102", new Date(), new Date(), 25, 3, classroom2);
        Course course3 = new Course("Physics III", "PHY103", new Date(), new Date(), 25, 3, classroom3);
        
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        instructor.setCourses(courses);
        
        // Create and enroll one student in each course
        Student student1 = new Student();
        student1.enrollInCourse(course1);
        
        Student student2 = new Student();
        student2.enrollInCourse(course2);
        
        Student student3 = new Student();
        student3.enrollInCourse(course3);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Create instructor with 4 courses with varying student counts
        instructor = new Instructor("Alice", "Johnson", "Dr.", "Chemistry");
        
        // Create classrooms for the courses
        Classroom classroom1 = new Classroom("CH101", 30, "1", "D");
        Classroom classroom2 = new Classroom("CH102", 30, "1", "D");
        Classroom classroom3 = new Classroom("CH103", 30, "1", "D");
        Classroom classroom4 = new Classroom("CH104", 30, "1", "D");
        
        // Create 4 courses
        Course course1 = new Course("Chemistry I", "CHEM101", new Date(), new Date(), 30, 3, classroom1);
        Course course2 = new Course("Chemistry II", "CHEM102", new Date(), new Date(), 30, 3, classroom2);
        Course course3 = new Course("Chemistry III", "CHEM103", new Date(), new Date(), 30, 3, classroom3);
        Course course4 = new Course("Chemistry IV", "CHEM104", new Date(), new Date(), 30, 3, classroom4);
        
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        instructor.setCourses(courses);
        
        // Enroll students: course1=5, course2=10, course3=15, course4=20
        enrollStudentsInCourse(course1, 5);
        enrollStudentsInCourse(course2, 10);
        enrollStudentsInCourse(course3, 15);
        enrollStudentsInCourse(course4, 20);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result (5 + 10 + 15 + 20 = 50)
        assertEquals("Four courses with 5, 10, 15, and 20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Create instructor with no courses assigned
        instructor = new Instructor("Charlie", "Brown", "Mr.", "Biology");
        
        // Instructor has no courses by default, so no need to set courses
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
    
    // Helper method to enroll specified number of students in a course
    private void enrollStudentsInCourse(Course course, int numberOfStudents) {
        for (int i = 0; i < numberOfStudents; i++) {
            Student student = new Student();
            student.enrollInCourse(course);
            students.add(student);
        }
    }
}