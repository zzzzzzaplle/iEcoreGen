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
    private Student student1;
    private Student student2;
    private Student student3;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;
    
    @Before
    public void setUp() throws Exception {
        // Initialize academic program
        academicProgram = new AcademicProgram();
        
        // Initialize courses
        course1 = new Course();
        course1.setCode("CS101");
        course1.setName("Introduction to Computer Science");
        
        course2 = new Course();
        course2.setCode("MATH201");
        course2.setName("Advanced Mathematics");
        
        // Initialize students
        student1 = new Student();
        student1.setStudentId("S001");
        student1.setName("John");
        student1.setSurname("Doe");
        
        student2 = new Student();
        student2.setStudentId("S002");
        student2.setName("Jane");
        student2.setSurname("Smith");
        
        student3 = new Student();
        student3.setStudentId("S003");
        student3.setName("Bob");
        student3.setSurname("Johnson");
        
        // Initialize enrollments
        enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        
        enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course1);
        
        enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setCourse(course1);
        
        // Set up course enrollments
        List<Enrollment> course1Enrollments = new ArrayList<>();
        course1.setEnrollments(course1Enrollments);
        
        List<Enrollment> course2Enrollments = new ArrayList<>();
        course2.setEnrollments(course2Enrollments);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsWithDifferentGrades() {
        // Test Case 1: Average grade calculation for students in a specific course with different grades
        // Setup: Course with multiple students having different grades
        enrollment1.setGrade(85.0);
        enrollment2.setGrade(92.0);
        enrollment3.setGrade(78.0);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Calculate expected average: (85 + 92 + 78) / 3 = 85.0
        double expectedAverage = (85.0 + 92.0 + 78.0) / 3;
        
        // Execute the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Setup: Course with only one student enrolled with a specific grade
        enrollment1.setGrade(88.5);
        course1.getEnrollments().add(enrollment1);
        
        // Expected output: The grade of the single enrolled student
        double expectedAverage = 88.5;
        
        // Execute the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result
        assertEquals("Average grade for single student should be the student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Setup: Course with no enrolled students (empty enrollments list)
        // No enrollments added to course1
        
        // Expected output: 0.0 (as specified in the method implementation)
        double expectedAverage = 0.0;
        
        // Execute the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result
        assertEquals("Average grade for course with no students should be 0.0", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Setup: Course where all enrolled students have the same grade
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Expected output: The common grade value (90.0)
        double expectedAverage = 90.0;
        
        // Execute the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result
        assertEquals("Average grade for students with all same grades should be the common grade value", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Setup: Course having some students with valid grades and some with grade of 0
        enrollment1.setGrade(80.0);
        enrollment2.setGrade(0.0);  // Zero grade
        enrollment3.setGrade(70.0);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Calculate expected average: (80 + 0 + 70) / 3 = 50.0
        double expectedAverage = (80.0 + 0.0 + 70.0) / 3;
        
        // Execute the method under test
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        // Verify the result
        assertEquals("Average grade should correctly consider both valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
}