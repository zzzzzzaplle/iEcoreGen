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
        
        // Set up the course
        course.setName("Software Engineering");
        course.setCode("SE101");
        course.setQuota(10);
        
        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForMultipleStudents() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled Students with different grades.
        
        // Create students with different grades
        Student student1 = new Student();
        student1.setName("John");
        student1.setStudentId("S001");
        
        Student student2 = new Student();
        student2.setName("Jane");
        student2.setStudentId("S002");
        
        Student student3 = new Student();
        student3.setName("Bob");
        student3.setStudentId("S003");
        
        // Enroll students in the course
        student1.enrollCourse(course);
        student2.enrollCourse(course);
        student3.enrollCourse(course);
        
        // Set different grades for each student
        student1.setCourseGrade(course, 85.0);
        student2.setCourseGrade(course, 92.0);
        student3.setCourseGrade(course, 78.0);
        
        // Calculate expected average: (85 + 92 + 78) / 3 = 85.0
        double expectedAverage = (85.0 + 92.0 + 78.0) / 3;
        
        // Get actual average from the course
        double actualAverage = course.getAverageGrade();
        
        // Verify the average grade calculation
        assertEquals("Average grade should be correctly calculated for multiple students", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudent() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade.
        
        // Create a single student
        Student student = new Student();
        student.setName("Alice");
        student.setStudentId("S004");
        
        // Enroll the student in the course
        student.enrollCourse(course);
        
        // Set a specific grade
        double expectedGrade = 95.5;
        student.setCourseGrade(course, expectedGrade);
        
        // Get actual average from the course
        double actualAverage = course.getAverageGrade();
        
        // Verify the average grade equals the single student's grade
        assertEquals("Average grade should equal the single student's grade", 
                     expectedGrade, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled in it.
        
        // No students enrolled - course is empty
        // Get actual average from the course
        double actualAverage = course.getAverageGrade();
        
        // Verify the average grade is 0.0 when no students are enrolled
        assertEquals("Average grade should be 0.0 when no students are enrolled", 
                     0.0, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students have the same grade.
        
        // Create students with same grades
        Student student1 = new Student();
        student1.setName("Charlie");
        student1.setStudentId("S005");
        
        Student student2 = new Student();
        student2.setName("David");
        student2.setStudentId("S006");
        
        // Enroll students in the course
        student1.enrollCourse(course);
        student2.enrollCourse(course);
        
        // Set same grade for all students
        double commonGrade = 88.0;
        student1.setCourseGrade(course, commonGrade);
        student2.setCourseGrade(course, commonGrade);
        
        // Get actual average from the course
        double actualAverage = course.getAverageGrade();
        
        // Verify the average grade equals the common grade value
        assertEquals("Average grade should equal the common grade value when all students have same grade", 
                     commonGrade, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0.
        
        // Create students with mix of valid and zero grades
        Student student1 = new Student();
        student1.setName("Eve");
        student1.setStudentId("S007");
        
        Student student2 = new Student();
        student2.setName("Frank");
        student2.setStudentId("S008");
        
        Student student3 = new Student();
        student3.setName("Grace");
        student3.setStudentId("S009");
        
        // Enroll students in the course
        student1.enrollCourse(course);
        student2.enrollCourse(course);
        student3.enrollCourse(course);
        
        // Set grades: one valid, one zero, one valid
        student1.setCourseGrade(course, 90.0);
        student2.setCourseGrade(course, 0.0);
        student3.setCourseGrade(course, 80.0);
        
        // Calculate expected average: (90 + 0 + 80) / 3 = 56.666...
        double expectedAverage = (90.0 + 0.0 + 80.0) / 3;
        
        // Get actual average from the course
        double actualAverage = course.getAverageGrade();
        
        // Verify the average grade calculation includes zero grades
        assertEquals("Average grade should be correctly calculated including zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
}