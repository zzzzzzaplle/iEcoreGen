import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;
    private Student student3;
    
    @Before
    public void setUp() {
        // Setup common test objects
        academicProgram = new AcademicProgram();
        
        course1 = new Course();
        course1.setCode("CS101");
        course1.setName("Introduction to Programming");
        
        course2 = new Course();
        course2.setCode("CS102");
        course2.setName("Data Structures");
        
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        // Expected Output: The correct average grade of all the students in that specific Course within the Academic Program
        
        // Setup enrollments with different grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        enrollment1.setGrade(85.0);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course1);
        enrollment2.setGrade(92.5);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setCourse(course1);
        enrollment3.setGrade(78.0);
        
        // Add enrollments to course1
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        // Calculate expected average: (85.0 + 92.5 + 78.0) / 3 = 255.5 / 3 = 85.1666...
        double expectedAverage = (85.0 + 92.5 + 78.0) / 3.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the average grade is calculated correctly
        assertEquals(expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        // Expected Output: The grade of the single enrolled student, as it represents the average grade
        
        // Setup enrollment with single student
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student1);
        enrollment.setCourse(course2);
        enrollment.setGrade(88.5);
        
        // Add enrollment to course2
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment);
        course2.setEnrollments(enrollments);
        
        double expectedAverage = 88.5;
        double actualAverage = academicProgram.calculateAverageGrade("CS102");
        
        // Verify the average grade equals the single student's grade
        assertEquals(expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        // Expected Output: An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from
        
        // Course1 has no enrollments (empty list)
        course1.setEnrollments(new ArrayList<>());
        
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the average grade is 0.0 when no students are enrolled
        assertEquals(expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        // Expected Output: The common grade value as the average grade
        
        // Setup enrollments with same grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        enrollment1.setGrade(75.0);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course1);
        enrollment2.setGrade(75.0);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setCourse(course1);
        enrollment3.setGrade(75.0);
        
        // Add enrollments to course1
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        double expectedAverage = 75.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the average grade equals the common grade value
        assertEquals(expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        // Expected Output: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students
        
        // Setup enrollments with mix of valid and zero grades
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        enrollment1.setGrade(90.0); // Valid grade
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course1);
        enrollment2.setGrade(0.0);  // Zero grade
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setCourse(course1);
        enrollment3.setGrade(80.0); // Valid grade
        
        // Add enrollments to course1
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        // Calculate expected average: (90.0 + 0.0 + 80.0) / 3 = 170.0 / 3 = 56.666...
        double expectedAverage = (90.0 + 0.0 + 80.0) / 3.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the average grade considers both valid and zero grades
        assertEquals(expectedAverage, actualAverage, 0.001);
    }
}