import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram program;
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        program = new AcademicProgram();
        program.setProgramName("Software Engineering");
        
        course1 = new Course();
        course1.setCourseCode("CS101");
        course1.setCourseName("Introduction to Programming");
        
        course2 = new Course();
        course2.setCourseCode("CS102");
        course2.setCourseName("Data Structures");
        
        student1 = new Student();
        student1.setName("John");
        student1.setSurname("Doe");
        
        student2 = new Student();
        student2.setName("Jane");
        student2.setSurname("Smith");
        
        student3 = new Student();
        student3.setName("Bob");
        student3.setSurname("Johnson");
        
        student4 = new Student();
        student4.setName("Alice");
        student4.setSurname("Brown");
    }
    
    @Test
    public void testCase1_AverageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        
        // Setup students with different grades
        student1.setGrade(85.5);
        student2.setGrade(92.0);
        student3.setGrade(78.0);
        
        // Enroll students in course1
        course1.setQuotaLimit(5);
        course1.enrollStudent(student1);
        course1.enrollStudent(student2);
        course1.enrollStudent(student3);
        
        // Add course to program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        program.setCourses(courses);
        
        // Calculate expected average: (85.5 + 92.0 + 78.0) / 3 = 255.5 / 3 = 85.1666...
        double expectedAverage = (85.5 + 92.0 + 78.0) / 3;
        
        // Test the method
        double actualAverage = program.getAverageGradeInCourse("CS101");
        
        // Verify the result with delta for floating point comparison
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                    expectedAverage, actualAverage, 0.0001);
    }
    
    @Test
    public void testCase2_AverageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        
        // Setup single student with grade
        student1.setGrade(88.0);
        
        // Enroll single student in course1
        course1.setQuotaLimit(5);
        course1.enrollStudent(student1);
        
        // Add course to program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        program.setCourses(courses);
        
        // Expected output: The grade of the single enrolled student (88.0)
        double expectedAverage = 88.0;
        
        // Test the method
        double actualAverage = program.getAverageGradeInCourse("CS101");
        
        // Verify the result
        assertEquals("Average grade for single student course should equal the student's grade", 
                    expectedAverage, actualAverage, 0.0001);
    }
    
    @Test
    public void testCase3_AverageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        
        // Course with no enrolled students
        course1.setQuotaLimit(5);
        // No students enrolled
        
        // Add course to program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        program.setCourses(courses);
        
        // Expected output: 0.0 (as specified in the method implementation)
        double expectedAverage = 0.0;
        
        // Test the method
        double actualAverage = program.getAverageGradeInCourse("CS101");
        
        // Verify the result
        assertEquals("Average grade for course with no students should be 0.0", 
                    expectedAverage, actualAverage, 0.0001);
    }
    
    @Test
    public void testCase4_AverageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        
        // Setup students with same grade
        student1.setGrade(90.0);
        student2.setGrade(90.0);
        student3.setGrade(90.0);
        student4.setGrade(90.0);
        
        // Enroll students in course1
        course1.setQuotaLimit(5);
        course1.enrollStudent(student1);
        course1.enrollStudent(student2);
        course1.enrollStudent(student3);
        course1.enrollStudent(student4);
        
        // Add course to program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        program.setCourses(courses);
        
        // Expected output: The common grade value (90.0)
        double expectedAverage = 90.0;
        
        // Test the method
        double actualAverage = program.getAverageGradeInCourse("CS101");
        
        // Verify the result
        assertEquals("Average grade for students with all same grades should equal the common grade", 
                    expectedAverage, actualAverage, 0.0001);
    }
    
    @Test
    public void testCase5_AverageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        
        // Setup students with mix of valid and zero grades
        student1.setGrade(80.0);   // Valid grade
        student2.setGrade(0.0);    // Zero grade
        student3.setGrade(95.0);   // Valid grade
        student4.setGrade(0.0);    // Zero grade
        
        // Enroll students in course1
        course1.setQuotaLimit(5);
        course1.enrollStudent(student1);
        course1.enrollStudent(student2);
        course1.enrollStudent(student3);
        course1.enrollStudent(student4);
        
        // Add course to program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        program.setCourses(courses);
        
        // Calculate expected average: (80.0 + 0.0 + 95.0 + 0.0) / 4 = 175.0 / 4 = 43.75
        double expectedAverage = (80.0 + 0.0 + 95.0 + 0.0) / 4;
        
        // Test the method
        double actualAverage = program.getAverageGradeInCourse("CS101");
        
        // Verify the result
        assertEquals("Average grade should correctly calculate with mix of valid and zero grades", 
                    expectedAverage, actualAverage, 0.0001);
    }
}