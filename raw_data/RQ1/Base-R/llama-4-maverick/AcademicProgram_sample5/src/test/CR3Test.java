import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course;
    private List<Student> students;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        course = new Course();
        students = new ArrayList<>();
        academicProgram.addCourse(course);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades.
        // Expected Output: The correct average grade of all the students in that specific Course within the Academic Program.
        
        // Create students with different grades
        Student student1 = new Student();
        student1.setGrade(85.0);
        Student student2 = new Student();
        student2.setGrade(92.0);
        Student student3 = new Student();
        student3.setGrade(78.0);
        
        // Enroll students in the course
        course.enrollStudent(student1);
        course.enrollStudent(student2);
        course.enrollStudent(student3);
        
        // Calculate expected average: (85.0 + 92.0 + 78.0) / 3 = 85.0
        double expectedAverage = 85.0;
        double actualAverage = academicProgram.averageStudentGradeInCourse(course);
        
        // Verify the average grade calculation
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                       expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade.
        // Expected Output: The grade of the single enrolled student, as it represents the average grade.
        
        // Create a single student with a specific grade
        Student student = new Student();
        student.setGrade(88.5);
        
        // Enroll the student in the course
        course.enrollStudent(student);
        
        // Expected average should be the same as the single student's grade
        double expectedAverage = 88.5;
        double actualAverage = academicProgram.averageStudentGradeInCourse(course);
        
        // Verify the average grade equals the single student's grade
        assertEquals("Average grade for single student should equal the student's grade", 
                       expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it.
        // Expected Output: An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from.
        
        // Course has no students enrolled
        // No students added to the course
        
        // Expected output should be 0 (as per the Course.averageStudentGrade() implementation)
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.averageStudentGradeInCourse(course);
        
        // Verify that average is 0 for a course with no students
        assertEquals("Average grade should be 0 for a course with no students", 
                       expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade.
        // Expected Output: The common grade value as the average grade.
        
        // Create students with identical grades
        Student student1 = new Student();
        student1.setGrade(75.0);
        Student student2 = new Student();
        student2.setGrade(75.0);
        Student student3 = new Student();
        student3.setGrade(75.0);
        Student student4 = new Student();
        student4.setGrade(75.0);
        
        // Enroll students in the course
        course.enrollStudent(student1);
        course.enrollStudent(student2);
        course.enrollStudent(student3);
        course.enrollStudent(student4);
        
        // Expected average should be the common grade value (75.0)
        double expectedAverage = 75.0;
        double actualAverage = academicProgram.averageStudentGradeInCourse(course);
        
        // Verify that average equals the common grade value
        assertEquals("Average grade should equal the common grade value when all students have same grade", 
                       expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0.
        // Expected Output: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students.
        
        // Create students with mix of valid and zero grades
        Student student1 = new Student();
        student1.setGrade(90.0);
        Student student2 = new Student();
        student2.setGrade(0.0);  // Zero grade
        Student student3 = new Student();
        student3.setGrade(80.0);
        Student student4 = new Student();
        student4.setGrade(0.0);  // Zero grade
        Student student5 = new Student();
        student5.setGrade(95.0);
        
        // Enroll students in the course
        course.enrollStudent(student1);
        course.enrollStudent(student2);
        course.enrollStudent(student3);
        course.enrollStudent(student4);
        course.enrollStudent(student5);
        
        // Calculate expected average: (90.0 + 0.0 + 80.0 + 0.0 + 95.0) / 5 = 53.0
        double expectedAverage = 53.0;
        double actualAverage = academicProgram.averageStudentGradeInCourse(course);
        
        // Verify the correct average including zero grades
        assertEquals("Average grade should correctly include zero grades in calculation", 
                       expectedAverage, actualAverage, 0.001);
    }
}