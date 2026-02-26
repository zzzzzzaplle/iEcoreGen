import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
    }
    
    // Test Case 1: "Average grade calculation for students in a specific course"
    @Test
    public void testCase1_averageGradeCalculationForMultipleStudents() {
        // Create a course with multiple students having different grades
        course = new Course();
        course.setCourseCode("CS101");
        
        // Create students with different grades
        Student student1 = new Student();
        student1.setGrade(85.0);
        
        Student student2 = new Student();
        student2.setGrade(92.5);
        
        Student student3 = new Student();
        student3.setGrade(78.0);
        
        // Add students to the course
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        course.setStudents(students);
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Calculate average grade and verify result
        double expectedAverage = (85.0 + 92.5 + 78.0) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should be correctly calculated for multiple students", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    // Test Case 2: "Average grade calculation for a single-student course"
    @Test
    public void testCase2_averageGradeCalculationForSingleStudent() {
        // Create a course with only one student
        course = new Course();
        course.setCourseCode("MATH201");
        
        // Create a single student with specific grade
        Student student = new Student();
        student.setGrade(88.5);
        
        // Add student to the course
        List<Student> students = new ArrayList<>();
        students.add(student);
        course.setStudents(students);
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Calculate average grade and verify result
        double expectedAverage = 88.5; // Should equal the single student's grade
        double actualAverage = academicProgram.calculateAverageGrade("MATH201");
        
        assertEquals("Average grade should equal the single student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    // Test Case 3: "Average grade calculation for a course with no students"
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Create a course with no students enrolled
        course = new Course();
        course.setCourseCode("PHY301");
        
        // Empty student list
        course.setStudents(new ArrayList<>());
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Calculate average grade and verify result is 0.0
        double actualAverage = academicProgram.calculateAverageGrade("PHY301");
        
        assertEquals("Average grade should be 0.0 when no students are enrolled", 
                     0.0, actualAverage, 0.001);
    }
    
    // Test Case 4: "Average grade calculation with all same grades"
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Create a course with students all having the same grade
        course = new Course();
        course.setCourseCode("CHEM101");
        
        // Create students with identical grades
        Student student1 = new Student();
        student1.setGrade(75.0);
        
        Student student2 = new Student();
        student2.setGrade(75.0);
        
        Student student3 = new Student();
        student3.setGrade(75.0);
        
        Student student4 = new Student();
        student4.setGrade(75.0);
        
        // Add students to the course
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        course.setStudents(students);
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Calculate average grade and verify result equals the common grade
        double expectedAverage = 75.0;
        double actualAverage = academicProgram.calculateAverageGrade("CHEM101");
        
        assertEquals("Average grade should equal the common grade when all students have same grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    // Test Case 5: "Average grade calculation with a mix of valid and zero grades"
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Create a course with students having mix of valid and zero grades
        course = new Course();
        course.setCourseCode("BIO201");
        
        // Create students with mix of valid grades and zero grades
        Student student1 = new Student();
        student1.setGrade(90.0);  // Valid grade
        
        Student student2 = new Student();
        student2.setGrade(0.0);   // Zero grade
        
        Student student3 = new Student();
        student3.setGrade(85.5);  // Valid grade
        
        Student student4 = new Student();
        student4.setGrade(0.0);   // Zero grade
        
        // Add students to the course
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        course.setStudents(students);
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Calculate average grade and verify result considers all grades including zeros
        double expectedAverage = (90.0 + 0.0 + 85.5 + 0.0) / 4;
        double actualAverage = academicProgram.calculateAverageGrade("BIO201");
        
        assertEquals("Average grade should correctly calculate with mix of valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    // Additional test for IllegalArgumentException when course not found
    @Test(expected = IllegalArgumentException.class)
    public void testCalculateAverageGradeCourseNotFound() {
        // Create academic program with no courses
        academicProgram.setCourses(new ArrayList<>());
        
        // Try to calculate average for non-existent course
        academicProgram.calculateAverageGrade("NONEXISTENT");
    }
}