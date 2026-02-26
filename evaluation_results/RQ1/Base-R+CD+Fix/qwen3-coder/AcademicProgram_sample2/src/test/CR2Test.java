import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR2Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_singleCourseSingleStudent() {
        // Create instructor with one course that has one enrolled student
        Course course = new Course();
        course.setCode("CS101");
        
        // Create one student and enroll in the course
        Student student = new Student();
        student.setName("John");
        student.setSurname("Doe");
        
        // Create enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        
        // Add enrollment to course
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        course.setEnrollments(enrollments);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Create instructor with one course
        Course course = new Course();
        course.setCode("MATH201");
        
        // Create 25 students and enroll them in the course
        List<Enrollment> enrollments = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Student student = new Student();
            student.setName("Student" + i);
            student.setSurname("Surname" + i);
            
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollments.add(enrollment);
        }
        course.setEnrollments(enrollments);
        
        // Add course to instructor
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Create instructor with 3 courses
        List<Course> courses = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setCode("PHY" + (300 + i));
            
            // Create one student per course
            Student student = new Student();
            student.setName("Student" + i);
            student.setSurname("Course" + i);
            
            // Create enrollment
            List<Enrollment> enrollments = new ArrayList<>();
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollments.add(enrollment);
            course.setEnrollments(enrollments);
            
            courses.add(course);
        }
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Create instructor with 4 courses
        List<Course> courses = new ArrayList<>();
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int i = 0; i < 4; i++) {
            Course course = new Course();
            course.setCode("CHEM" + (400 + i));
            
            // Create varying number of students per course
            List<Enrollment> enrollments = new ArrayList<>();
            for (int j = 0; j < studentCounts[i]; j++) {
                Student student = new Student();
                student.setName("Student" + i + "_" + j);
                student.setSurname("Course" + i);
                
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                enrollments.add(enrollment);
            }
            course.setEnrollments(enrollments);
            courses.add(course);
        }
        instructor.setCourses(courses);
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result (5 + 10 + 15 + 20 = 50)
        assertEquals("Four courses with 5,10,15,20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Instructor has no courses assigned (already set by setUp method)
        
        // Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify the result
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}