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
    private Classroom classroom1;
    private Classroom classroom2;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        
        // Set up classrooms
        classroom1 = new Classroom();
        classroom1.setClassroomId("A101");
        classroom1.setCapacity(50);
        
        classroom2 = new Classroom();
        classroom2.setClassroomId("B202");
        classroom2.setCapacity(30);
        
        // Set up students
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
        
        student4 = new Student();
        student4.setName("Alice");
        student4.setSurname("Brown");
        student4.setStudentId("S004");
        
        // Set up courses
        course1 = new Course();
        course1.setCourseName("Mathematics");
        course1.setCourseCode("MATH101");
        course1.setClassroom(classroom1);
        course1.setDays(Arrays.asList("Monday", "Wednesday"));
        course1.setQuotaLimit(10);
        
        course2 = new Course();
        course2.setCourseName("Physics");
        course2.setCourseCode("PHYS101");
        course2.setClassroom(classroom2);
        course2.setDays(Arrays.asList("Tuesday", "Thursday"));
        course2.setQuotaLimit(5);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        // Expected Output: The correct average grade of all the students in that specific Course within the Academic Program
        
        // Set different grades for students
        student1.setGrade(85.0);
        student2.setGrade(92.0);
        student3.setGrade(78.0);
        
        // Add students to course
        course1.addStudent(student1);
        course1.addStudent(student2);
        course1.addStudent(student3);
        
        // Add course to academic program
        academicProgram.addCourse(course1);
        
        // Calculate expected average: (85 + 92 + 78) / 3 = 85.0
        double expectedAverage = (85.0 + 92.0 + 78.0) / 3;
        
        // Test the average grade calculation
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify the result
        assertEquals("Average grade should be correctly calculated for multiple students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        // Expected Output: The grade of the single enrolled student, as it represents the average grade
        
        // Set grade for single student
        student1.setGrade(88.5);
        
        // Add single student to course
        course1.addStudent(student1);
        
        // Add course to academic program
        academicProgram.addCourse(course1);
        
        // Test the average grade calculation
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify the result equals the single student's grade
        assertEquals("Average grade for single student course should equal the student's grade", 
                     88.5, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it
        // Expected Output: An appropriate indication (0) that there are no grades to calculate an average from
        
        // Add course with no students to academic program
        academicProgram.addCourse(course1);
        
        // Test the average grade calculation
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify the result is 0 for empty course
        assertEquals("Average grade for course with no students should be 0", 
                     0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade
        // Expected Output: The common grade value as the average grade
        
        // Set same grade for all students
        student1.setGrade(90.0);
        student2.setGrade(90.0);
        student3.setGrade(90.0);
        student4.setGrade(90.0);
        
        // Add students to course
        course1.addStudent(student1);
        course1.addStudent(student2);
        course1.addStudent(student3);
        course1.addStudent(student4);
        
        // Add course to academic program
        academicProgram.addCourse(course1);
        
        // Test the average grade calculation
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify the result equals the common grade value
        assertEquals("Average grade for course with all same grades should equal the common grade value", 
                     90.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        // Expected Output: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students
        
        // Set mix of valid and zero grades
        student1.setGrade(80.0);
        student2.setGrade(0.0);  // Zero grade
        student3.setGrade(95.0);
        student4.setGrade(0.0);  // Zero grade
        
        // Add students to course
        course1.addStudent(student1);
        course1.addStudent(student2);
        course1.addStudent(student3);
        course1.addStudent(student4);
        
        // Add course to academic program
        academicProgram.addCourse(course1);
        
        // Calculate expected average: (80 + 0 + 95 + 0) / 4 = 43.75
        double expectedAverage = (80.0 + 0.0 + 95.0 + 0.0) / 4;
        
        // Test the average grade calculation
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify the result correctly includes zero grades in calculation
        assertEquals("Average grade should correctly include zero grades in calculation", 
                     expectedAverage, actualAverage, 0.001);
    }
}