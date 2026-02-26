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
        // Initialize academic program and course before each test
        academicProgram = new AcademicProgram();
        course = new Course();
        course.setCode("CS101");
        academicProgram.addCourse(course);
    }
    
    @Test
    public void testCase1_AverageGradeCalculationForStudentsWithDifferentGrades() {
        // Test Case 1: Average grade calculation for students in a specific course with different grades
        Student student1 = new Student();
        student1.setGrade(85.5);
        
        Student student2 = new Student();
        student2.setGrade(92.0);
        
        Student student3 = new Student();
        student3.setGrade(78.5);
        
        List<Student> students = Arrays.asList(student1, student2, student3);
        course.setStudents(students);
        
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
        double actualAverage = academicProgram.getAverageGradeInCourse("CS101");
        
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_AverageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        Student student = new Student();
        student.setGrade(88.0);
        
        List<Student> students = Arrays.asList(student);
        course.setStudents(students);
        
        double expectedAverage = 88.0;
        double actualAverage = academicProgram.getAverageGradeInCourse("CS101");
        
        assertEquals("Average grade should equal the single student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_AverageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        course.setStudents(new ArrayList<Student>());
        
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.getAverageGradeInCourse("CS101");
        
        assertEquals("Average grade should be 0 when there are no students", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_AverageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        Student student1 = new Student();
        student1.setGrade(75.0);
        
        Student student2 = new Student();
        student2.setGrade(75.0);
        
        Student student3 = new Student();
        student3.setGrade(75.0);
        
        List<Student> students = Arrays.asList(student1, student2, student3);
        course.setStudents(students);
        
        double expectedAverage = 75.0;
        double actualAverage = academicProgram.getAverageGradeInCourse("CS101");
        
        assertEquals("Average grade should equal the common grade value", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_AverageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        Student student1 = new Student();
        student1.setGrade(90.0);
        
        Student student2 = new Student();
        student2.setGrade(0.0);
        
        Student student3 = new Student();
        student3.setGrade(80.0);
        
        Student student4 = new Student();
        student4.setGrade(0.0);
        
        List<Student> students = Arrays.asList(student1, student2, student3, student4);
        course.setStudents(students);
        
        double expectedAverage = (90.0 + 0.0 + 80.0 + 0.0) / 4;
        double actualAverage = academicProgram.getAverageGradeInCourse("CS101");
        
        assertEquals("Average grade should consider both valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
}