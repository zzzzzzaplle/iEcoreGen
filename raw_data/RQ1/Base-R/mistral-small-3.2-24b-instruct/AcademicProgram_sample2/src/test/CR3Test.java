import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        // Initialize common test objects
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course1.setCourseCode("CS101");
        course1.setCourseName("Introduction to Computer Science");
        
        course2 = new Course();
        course2.setCourseCode("MATH201");
        course2.setCourseName("Advanced Mathematics");
        
        student1 = new Student();
        student1.setName("John");
        student1.setSurname("Doe");
        student1.setStudentId("S001");
        
        student2 = new Student();
        student2.setName("Jane");
        student2.setSurname("Smith");
        student2.setStudentId("S002");
        
        student3 = new Student();
        student3.setName("Bob");
        student3.setSurname("Johnson");
        student3.setStudentId("S003");
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        // Expected Output: The correct average grade of all the students in that specific Course within the Academic Program
        
        // Set up students with different grades
        student1.setGrade(85.0);
        student2.setGrade(92.0);
        student3.setGrade(78.0);
        
        // Add students to course
        course1.addStudent(student1);
        course1.addStudent(student2);
        course1.addStudent(student3);
        
        // Add course to academic program
        academicProgram.addCourse(course1);
        
        // Calculate expected average: (85.0 + 92.0 + 78.0) / 3 = 85.0
        double expectedAverage = 85.0;
        double actualAverage = academicProgram.calculateAverageStudentGrade("CS101");
        
        assertEquals("Average grade should be correctly calculated for multiple students with different grades", 
                         expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        // Expected Output: The grade of the single enrolled student, as it represents the average grade
        
        // Set up single student with specific grade
        student1.setGrade(88.5);
        
        // Add single student to course
        course1.addStudent(student1);
        
        // Add course to academic program
        academicProgram.addCourse(course1);
        
        // Calculate expected average: 88.5 (same as the single student's grade)
        double expectedAverage = 88.5;
        double actualAverage = academicProgram.calculateAverageStudentGrade("CS101");
        
        assertEquals("Average grade should equal the single student's grade", 
                         expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        // Expected Output: An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from
        
        // Course has no students (empty student list)
        // Add course to academic program
        academicProgram.addCourse(course1);
        
        // Expected output: 0.0 (as specified in the Course.calculateAverageGrade() method)
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.calculateAverageStudentGrade("CS101");
        
        assertEquals("Average grade should be 0.0 for a course with no students", 
                         expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        // Expected Output: The common grade value as the average grade
        
        // Set up students with same grades
        student1.setGrade(95.0);
        student2.setGrade(95.0);
        student3.setGrade(95.0);
        
        // Add students to course
        course1.addStudent(student1);
        course1.addStudent(student2);
        course1.addStudent(student3);
        
        // Add course to academic program
        academicProgram.addCourse(course1);
        
        // Calculate expected average: 95.0 (same as all individual grades)
        double expectedAverage = 95.0;
        double actualAverage = academicProgram.calculateAverageStudentGrade("CS101");
        
        assertEquals("Average grade should equal the common grade value when all students have same grade", 
                         expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        // Expected Output: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students
        
        // Set up students with mix of valid and zero grades
        student1.setGrade(80.0);
        student2.setGrade(0.0);  // Zero grade student
        student3.setGrade(90.0);
        
        // Add students to course
        course1.addStudent(student1);
        course1.addStudent(student2);
        course1.addStudent(student3);
        
        // Add course to academic program
        academicProgram.addCourse(course1);
        
        // Calculate expected average: (80.0 + 0.0 + 90.0) / 3 = 56.666...
        double expectedAverage = 56.666666666666664;
        double actualAverage = academicProgram.calculateAverageStudentGrade("CS101");
        
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                         expectedAverage, actualAverage, 0.001);
    }
}