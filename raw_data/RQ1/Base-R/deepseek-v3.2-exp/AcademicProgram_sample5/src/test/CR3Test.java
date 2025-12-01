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
    private Student student4;
    private Student student5;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        academicProgram.setProgramName("Software Engineering");
        
        course1 = new Course();
        course1.setCourseCode("SE101");
        course1.setCourseName("Introduction to Software Engineering");
        
        course2 = new Course();
        course2.setCourseCode("SE102");
        course2.setCourseName("Advanced Software Engineering");
        
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
        
        student4 = new Student();
        student4.setStudentId("S004");
        student4.setName("Alice");
        student4.setSurname("Williams");
        
        student5 = new Student();
        student5.setStudentId("S005");
        student5.setName("Charlie");
        student5.setSurname("Brown");
    }
    
    @Test
    public void testCase1_AverageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        
        // Set up course with students having different grades
        student1.setGradeForCourse("SE101", 85.0);
        student2.setGradeForCourse("SE101", 92.5);
        student3.setGradeForCourse("SE101", 78.0);
        
        course1.setStudents(Arrays.asList(student1, student2, student3));
        academicProgram.addCourse(course1);
        
        // Calculate average grade
        double result = academicProgram.calculateAverageGradeInCourse("SE101");
        double expected = (85.0 + 92.5 + 78.0) / 3;
        
        // Verify the result matches expected average
        assertEquals("Average grade should be correctly calculated for multiple students with different grades", 
                     expected, result, 0.001);
    }
    
    @Test
    public void testCase2_AverageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        
        // Set up course with single student having a specific grade
        student1.setGradeForCourse("SE101", 88.0);
        course1.setStudents(Arrays.asList(student1));
        academicProgram.addCourse(course1);
        
        // Calculate average grade
        double result = academicProgram.calculateAverageGradeInCourse("SE101");
        double expected = 88.0;
        
        // Verify the result equals the single student's grade
        assertEquals("Average grade for single student should equal that student's grade", 
                     expected, result, 0.001);
    }
    
    @Test
    public void testCase3_AverageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        
        // Set up course with no students
        course1.setStudents(new ArrayList<Student>());
        academicProgram.addCourse(course1);
        
        // Calculate average grade
        double result = academicProgram.calculateAverageGradeInCourse("SE101");
        double expected = 0.0;
        
        // Verify the result is 0.0 as specified
        assertEquals("Average grade for course with no students should be 0.0", 
                     expected, result, 0.001);
    }
    
    @Test
    public void testCase4_AverageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        
        // Set up course with students having identical grades
        student1.setGradeForCourse("SE101", 90.0);
        student2.setGradeForCourse("SE101", 90.0);
        student3.setGradeForCourse("SE101", 90.0);
        student4.setGradeForCourse("SE101", 90.0);
        
        course1.setStudents(Arrays.asList(student1, student2, student3, student4));
        academicProgram.addCourse(course1);
        
        // Calculate average grade
        double result = academicProgram.calculateAverageGradeInCourse("SE101");
        double expected = 90.0;
        
        // Verify the result equals the common grade value
        assertEquals("Average grade should equal the common grade when all students have same grade", 
                     expected, result, 0.001);
    }
    
    @Test
    public void testCase5_AverageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        
        // Set up course with mix of valid and zero grades
        student1.setGradeForCourse("SE101", 85.0);
        student2.setGradeForCourse("SE101", 0.0);  // Zero grade
        student3.setGradeForCourse("SE101", 92.0);
        student4.setGradeForCourse("SE101", 0.0);  // Zero grade
        student5.setGradeForCourse("SE101", 78.0);
        
        course1.setStudents(Arrays.asList(student1, student2, student3, student4, student5));
        academicProgram.addCourse(course1);
        
        // Calculate average grade
        double result = academicProgram.calculateAverageGradeInCourse("SE101");
        double expected = (85.0 + 0.0 + 92.0 + 0.0 + 78.0) / 5;
        
        // Verify the result correctly includes zero grades in calculation
        assertEquals("Average grade should correctly include zero grades in calculation", 
                     expected, result, 0.001);
    }
}