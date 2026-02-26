import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Classroom classroom1;
    private Classroom classroom2;
    private Student student1;
    private Student student2;
    private Student student3;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;
    
    @Before
    public void setUp() throws Exception {
        academicProgram = new AcademicProgram();
        
        // Set up classrooms
        classroom1 = new Classroom();
        classroom1.setId("C101");
        classroom1.setCapacity(30);
        
        classroom2 = new Classroom();
        classroom2.setId("C102");
        classroom2.setCapacity(25);
        
        // Set up course1
        course1 = new Course();
        course1.setCode("CS101");
        course1.setName("Introduction to Computer Science");
        course1.setClassroom(classroom1);
        
        // Set up course2
        course2 = new Course();
        course2.setCode("MATH201");
        course2.setName("Advanced Mathematics");
        course2.setClassroom(classroom2);
        
        // Set up students
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        // Set up enrollments
        enrollment1 = new Enrollment();
        enrollment2 = new Enrollment();
        enrollment3 = new Enrollment();
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        // Expected Output: The correct average grade of all the students in that specific Course within the Academic Program
        
        // Setup enrollments with different grades for course1
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        enrollment1.setGrade(85.5);
        
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course1);
        enrollment2.setGrade(92.0);
        
        enrollment3.setStudent(student3);
        enrollment3.setCourse(course1);
        enrollment3.setGrade(78.5);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        // Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Expected average: (85.5 + 92.0 + 78.5) / 3 = 85.333...
        assertEquals(85.333, result, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        // Expected Output: The grade of the single enrolled student, as it represents the average grade
        
        // Setup enrollment with single student for course1
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        enrollment1.setGrade(88.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        course1.setEnrollments(enrollments);
        
        // Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Expected average: 88.0 (single student's grade)
        assertEquals(88.0, result, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        // Expected Output: An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from
        
        // Setup course with no enrollments
        course1.setEnrollments(new ArrayList<>());
        
        // Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Expected average: 0.0 (no students enrolled)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        // Expected Output: The common grade value as the average grade
        
        // Setup enrollments with same grades for course1
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        enrollment1.setGrade(90.0);
        
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course1);
        enrollment2.setGrade(90.0);
        
        enrollment3.setStudent(student3);
        enrollment3.setCourse(course1);
        enrollment3.setGrade(90.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        // Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Expected average: 90.0 (all students have same grade)
        assertEquals(90.0, result, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        // Expected Output: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students
        
        // Setup enrollments with mix of valid and zero grades for course1
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        enrollment1.setGrade(85.0);
        
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course1);
        enrollment2.setGrade(0.0);  // Zero grade
        
        enrollment3.setStudent(student3);
        enrollment3.setCourse(course1);
        enrollment3.setGrade(95.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        // Calculate average grade
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Expected average: (85.0 + 0.0 + 95.0) / 3 = 60.0
        assertEquals(60.0, result, 0.001);
    }

}