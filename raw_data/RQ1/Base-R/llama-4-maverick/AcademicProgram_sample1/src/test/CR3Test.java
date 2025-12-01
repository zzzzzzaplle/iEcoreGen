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
        course = new Course();
        academicProgram.setCourses(new ArrayList<>());
        course.setEnrolledStudents(new ArrayList<>());
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Setup: Create an AcademicProgram with a specific Course having a list of enrolled Students with different grades
        Student student1 = new Student();
        student1.setGrade(85.0);
        Student student2 = new Student();
        student2.setGrade(92.0);
        Student student3 = new Student();
        student3.setGrade(78.0);
        
        course.getEnrolledStudents().addAll(Arrays.asList(student1, student2, student3));
        course.setCode("CS101");
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the specific course
        double result = academicProgram.averageStudentGrade("CS101");
        
        // Verify: The correct average grade of all the students in that specific Course within the Academic Program
        double expectedAverage = (85.0 + 92.0 + 78.0) / 3;
        assertEquals("Average grade should be correctly calculated for multiple students with different grades", 
                     expectedAverage, result, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: Create an AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        Student student = new Student();
        student.setGrade(88.5);
        
        course.getEnrolledStudents().add(student);
        course.setCode("MATH201");
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the specific course
        double result = academicProgram.averageStudentGrade("MATH201");
        
        // Verify: The grade of the single enrolled student, as it represents the average grade
        assertEquals("Average grade for single student should equal that student's grade", 
                     88.5, result, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: Create an AcademicProgram with a specific Course and no students are enrolled in it
        course.setCode("PHY301");
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the specific course
        double result = academicProgram.averageStudentGrade("PHY301");
        
        // Verify: An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from
        assertEquals("Average grade for course with no students should be 0", 
                     0, result, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: Create an AcademicProgram with a specific Course where all enrolled students have the same grade
        Student student1 = new Student();
        student1.setGrade(75.0);
        Student student2 = new Student();
        student2.setGrade(75.0);
        Student student3 = new Student();
        student3.setGrade(75.0);
        
        course.getEnrolledStudents().addAll(Arrays.asList(student1, student2, student3));
        course.setCode("CHEM101");
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the specific course
        double result = academicProgram.averageStudentGrade("CHEM101");
        
        // Verify: The common grade value as the average grade
        assertEquals("Average grade for students with same grades should equal the common grade", 
                     75.0, result, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: Create an AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        Student student1 = new Student();
        student1.setGrade(90.0);
        Student student2 = new Student();
        student2.setGrade(0.0);
        Student student3 = new Student();
        student3.setGrade(85.0);
        Student student4 = new Student();
        student4.setGrade(0.0);
        
        course.getEnrolledStudents().addAll(Arrays.asList(student1, student2, student3, student4));
        course.setCode("BIO202");
        academicProgram.getCourses().add(course);
        
        // Execute: Calculate average grade for the specific course
        double result = academicProgram.averageStudentGrade("BIO202");
        
        // Verify: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students
        double expectedAverage = (90.0 + 0.0 + 85.0 + 0.0) / 4;
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                     expectedAverage, result, 0.001);
    }
}