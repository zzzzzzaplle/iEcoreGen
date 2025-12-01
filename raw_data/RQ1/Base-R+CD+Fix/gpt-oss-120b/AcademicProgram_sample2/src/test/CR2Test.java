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
        // Setup: An Instructor with one course. That course has one enrolled student.
        Course course = new Course();
        course.setCode("CS101");
        
        Student student = new Student();
        student.setName("John");
        student.setSurname("Doe");
        
        // Create enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setGrade(85.0);
        
        // Add enrollment to course and student
        List<Enrollment> courseEnrollments = new ArrayList<>();
        courseEnrollments.add(enrollment);
        course.setEnrollments(courseEnrollments);
        
        List<Enrollment> studentEnrollments = new ArrayList<>();
        studentEnrollments.add(enrollment);
        student.setEnrollments(studentEnrollments);
        
        // Add course to instructor
        courses.add(course);
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 1.
        assertEquals("Single course with one student should return 1", 1, result);
    }
    
    @Test
    public void testCase2_singleCourseMultipleStudents() {
        // Setup: An Instructor with one course. That course has 25 enrolled students.
        Course course = new Course();
        course.setCode("MATH201");
        
        List<Enrollment> courseEnrollments = new ArrayList<>();
        
        // Create 25 students and enrollments
        for (int i = 1; i <= 25; i++) {
            Student student = new Student();
            student.setName("Student" + i);
            student.setSurname("Smith");
            
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setGrade(75.0 + i);
            
            courseEnrollments.add(enrollment);
        }
        
        course.setEnrollments(courseEnrollments);
        
        // Add course to instructor
        courses.add(course);
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 25.
        assertEquals("Single course with 25 students should return 25", 25, result);
    }
    
    @Test
    public void testCase3_multipleCoursesSingleStudentPerCourse() {
        // Setup: An Instructor with 3 courses. Each course has one enrolled student.
        for (int i = 1; i <= 3; i++) {
            Course course = new Course();
            course.setCode("COURSE" + i);
            
            Student student = new Student();
            student.setName("Student" + i);
            student.setSurname("Johnson");
            
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setGrade(80.0 + i);
            
            List<Enrollment> courseEnrollments = new ArrayList<>();
            courseEnrollments.add(enrollment);
            course.setEnrollments(courseEnrollments);
            
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 3.
        assertEquals("Three courses with one student each should return 3", 3, result);
    }
    
    @Test
    public void testCase4_multipleCoursesVaryingStudentsPerCourse() {
        // Setup: An Instructor with 4 courses. First course has 5 students, second has 10, third has 15, and fourth has 20 students.
        int[] studentCounts = {5, 10, 15, 20};
        
        for (int i = 0; i < studentCounts.length; i++) {
            Course course = new Course();
            course.setCode("COURSE" + (i + 1));
            
            List<Enrollment> courseEnrollments = new ArrayList<>();
            
            // Create specified number of students for this course
            for (int j = 1; j <= studentCounts[i]; j++) {
                Student student = new Student();
                student.setName("Student" + j);
                student.setSurname("Course" + (i + 1));
                
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                enrollment.setGrade(70.0 + j);
                
                courseEnrollments.add(enrollment);
            }
            
            course.setEnrollments(courseEnrollments);
            courses.add(course);
        }
        
        instructor.setCourses(courses);
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 50.
        assertEquals("Four courses with 5+10+15+20 students should return 50", 50, result);
    }
    
    @Test
    public void testCase5_noCourses() {
        // Setup: An Instructor with no courses assigned.
        instructor.setCourses(new ArrayList<Course>()); // Empty course list
        
        // Execute: Calculate total enrolled students
        int result = instructor.calculateTotalEnrolledStudents();
        
        // Verify: The total number of students in the instructor's courses should be 0.
        assertEquals("Instructor with no courses should return 0", 0, result);
    }
}