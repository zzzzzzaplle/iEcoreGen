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
        // Create instructor
        instructor.setName("John");
        instructor.setSurname("Doe");
        
        // Create single course
        Course course = new Course();
        course.setCourseCode("CS101");
        course.setCourseName("Introduction to Computer Science");
        course.setQuota(30);
        
        // Create single student
        Student student = new Student();
        student.setStudentId("S001");
        student.setName("Alice");
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Enroll student in course
        student.enrollInCourse(course);
        
        // Calculate total students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Verify expected result
        assertEquals("Single course with one student should return 1", 1, totalStudents);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Create instructor
        instructor.setName("Jane");
        instructor.setSurname("Smith");
        
        // Create single course
        Course course = new Course();
        course.setCourseCode("MATH201");
        course.setCourseName("Advanced Mathematics");
        course.setQuota(30); // Set quota to accommodate 25 students
        
        // Create 25 students
        for (int i = 1; i <= 25; i++) {
            Student student = new Student();
            student.setStudentId("S" + String.format("%03d", i));
            student.setName("Student" + i);
            students.add(student);
            
            // Enroll each student in the course
            student.enrollInCourse(course);
        }
        
        // Add course to instructor
        instructor.addCourse(course);
        
        // Calculate total students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Verify expected result
        assertEquals("Single course with 25 students should return 25", 25, totalStudents);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Create instructor
        instructor.setName("Robert");
        instructor.setSurname("Johnson");
        
        // Create 3 courses
        for (int i = 1; i <= 3; i++) {
            Course course = new Course();
            course.setCourseCode("COURSE" + i);
        course.setCourseName("Course " + i);
        course.setQuota(10);
        courses.add(course);
        
        // Create one student for each course
        Student student = new Student();
        student.setStudentId("S00" + i);
        student.setName("Student" + i);
        students.add(student);
        
        // Enroll student in the course
        student.enrollInCourse(course);
        
        // Add course to instructor
        instructor.addCourse(course);
        }
        
        // Calculate total students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Verify expected result
        assertEquals("Three courses with one student each should return 3", 3, totalStudents);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Create instructor
        instructor.setName("Emily");
        instructor.setSurname("Wilson");
        
        // Course enrollment counts
        int[] enrollments = {5, 10, 15, 20};
        
        // Create 4 courses with varying student enrollments
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setCourseCode("C" + (i + 1));
        course.setCourseName("Course " + (i + 1));
        course.setQuota(enrollments[i] + 5); // Set quota slightly higher than enrollment
            
            // Create and enroll students for this course
            for (int j = 1; j <= enrollments[i]; j++) {
                Student student = new Student();
                student.setStudentId("S" + (i * 100 + j));
            student.setName("Student" + (i * 100 + j));
        students.add(student);
        
        // Enroll student in the course
        student.enrollInCourse(course);
            }
            
            courses.add(course);
            instructor.addCourse(course);
        }
        
        // Calculate total students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Verify expected result (5 + 10 + 15 + 20 = 50)
        assertEquals("Four courses with 5, 10, 15, and 20 students should return 50", 50, totalStudents);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Create instructor with no courses
        instructor.setName("Michael");
        instructor.setSurname("Brown");
        
        // Calculate total students in instructor's courses
        int totalStudents = instructor.getTotalStudentsInCourses();
        
        // Verify expected result
        assertEquals("Instructor with no courses should return 0", 0, totalStudents);
    }
}