import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course;
    private Student student1;
    private Student student2;
    private Student student3;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        course = new Course();
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: "Average grade calculation for students in a specific course"
        // Input: "An AcademicProgram with a specific Course having a list of enrolled Students with different grades."
        
        // Setup course with students having different grades
        course.setName("Mathematics");
        course.setCode("MATH101");
        
        student1.setName("John");
        student1.setSurname("Doe");
        student1.enrollInCourse(course);
        student1.setGrade(course, 85.5);
        
        student2.setName("Jane");
        student2.setSurname("Smith");
        student2.enrollInCourse(course);
        student2.setGrade(course, 92.0);
        
        student3.setName("Bob");
        student3.setSurname("Johnson");
        student3.enrollInCourse(course);
        student3.setGrade(course, 78.5);
        
        academicProgram.addCourse(course);
        
        // Calculate expected average: (85.5 + 92.0 + 78.5) / 3 = 85.333...
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3.0;
        double actualAverage = academicProgram.averageGradeInCourse(course);
        
        // Expected Output: "The correct average grade of all the students in that specific Course within the Academic Program."
        assertEquals("Average grade should be correctly calculated for multiple students with different grades", 
                    expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: "Average grade calculation for a single-student course"
        // Input: "An AcademicProgram with a specific Course where only one student is enrolled with a specific grade."
        
        // Setup course with single student
        course.setName("Physics");
        course.setCode("PHYS101");
        
        student1.setName("Alice");
        student1.setSurname("Brown");
        student1.enrollInCourse(course);
        student1.setGrade(course, 88.0);
        
        academicProgram.addCourse(course);
        
        double expectedAverage = 88.0;
        double actualAverage = academicProgram.averageGradeInCourse(course);
        
        // Expected Output: "The grade of the single enrolled student, as it represents the average grade."
        assertEquals("Average grade for single student should equal that student's grade", 
                    expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: "Average grade calculation for a course with no students"
        // Input: "An AcademicProgram with a specific Course and no students are enrolled in it."
        
        // Setup course without any students
        course.setName("Chemistry");
        course.setCode("CHEM101");
        
        academicProgram.addCourse(course);
        
        double actualAverage = academicProgram.averageGradeInCourse(course);
        
        // Expected Output: "An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from."
        // Based on the Course.averageStudentGrade() implementation, it returns 0 when no students are enrolled
        assertEquals("Average grade for course with no students should be 0", 
                    0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: "Average grade calculation with all same grades"
        // Input: "An AcademicProgram with a specific Course where all enrolled students have the same grade."
        
        // Setup course with students having same grades
        course.setName("Biology");
        course.setCode("BIO101");
        
        student1.setName("Charlie");
        student1.setSurname("Wilson");
        student1.enrollInCourse(course);
        student1.setGrade(course, 95.0);
        
        student2.setName("Diana");
        student2.setSurname("Lee");
        student2.enrollInCourse(course);
        student2.setGrade(course, 95.0);
        
        student3.setName("Eve");
        student3.setSurname("Davis");
        student3.enrollInCourse(course);
        student3.setGrade(course, 95.0);
        
        academicProgram.addCourse(course);
        
        double expectedAverage = 95.0;
        double actualAverage = academicProgram.averageGradeInCourse(course);
        
        // Expected Output: "The common grade value as the average grade."
        assertEquals("Average grade for students with same grades should equal the common grade", 
                    expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: "Average grade calculation with a mix of valid and zero grades"
        // Input: "An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0."
        
        // Setup course with mix of valid and zero grades
        course.setName("Computer Science");
        course.setCode("CS101");
        
        student1.setName("Frank");
        student1.setSurname("Miller");
        student1.enrollInCourse(course);
        student1.setGrade(course, 75.0); // Valid grade
        
        student2.setName("Grace");
        student2.setSurname("Taylor");
        student2.enrollInCourse(course);
        student2.setGrade(course, 0.0); // Zero grade
        
        student3.setName("Henry");
        student3.setSurname("Clark");
        student3.enrollInCourse(course);
        student3.setGrade(course, 82.5); // Valid grade
        
        academicProgram.addCourse(course);
        
        // Calculate expected average: (75.0 + 0.0 + 82.5) / 3 = 52.5
        double expectedAverage = (75.0 + 0.0 + 82.5) / 3.0;
        double actualAverage = academicProgram.averageGradeInCourse(course);
        
        // Expected Output: "The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students."
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                    expectedAverage, actualAverage, 0.001);
    }
}