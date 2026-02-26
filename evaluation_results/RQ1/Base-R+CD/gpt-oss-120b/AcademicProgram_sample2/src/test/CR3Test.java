import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;
    private Student student3;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;
    
    @Before
    public void setUp() throws Exception {
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course1.setCode("CS101");
        course1.setName("Introduction to Computer Science");
        
        course2 = new Course();
        course2.setCode("MATH201");
        course2.setName("Advanced Mathematics");
        
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
        
        enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        
        enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course1);
        
        enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setCourse(course1);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: "Average grade calculation for students in a specific course"
        // Input: "An AcademicProgram with a specific Course having a list of enrolled Students with different grades."
        // Expected Output: "The correct average grade of all the students in that specific Course within the Academic Program."
        
        // Setup
        enrollment1.setGrade(85.0);
        enrollment2.setGrade(92.0);
        enrollment3.setGrade(78.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        academicProgram.addCourse(course1);
        
        // Execute
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify
        double expectedAverage = (85.0 + 92.0 + 78.0) / 3.0;
        assertEquals("Average grade should be correctly calculated for multiple students with different grades", 
                     expectedAverage, result, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: "Average grade calculation for a single - student course"
        // Input: "An AcademicProgram with a specific Course where only one student is enrolled with a specific grade."
        // Expected Output: "The grade of the single enrolled student, as it represents the average grade."
        
        // Setup
        enrollment1.setGrade(88.5);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        course1.setEnrollments(enrollments);
        
        academicProgram.addCourse(course1);
        
        // Execute
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify
        assertEquals("Average grade for single student should equal that student's grade", 
                     88.5, result, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: "Average grade calculation for a course with no students"
        // Input: "An AcademicProgram with a specific Course and no students are enrolled in it."
        // Expected Output: "An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from."
        
        // Setup - course with empty enrollments list
        course1.setEnrollments(new ArrayList<>());
        academicProgram.addCourse(course1);
        
        // Execute
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify
        assertEquals("Average grade for course with no students should be 0.0", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: "Average grade calculation with all same grades"
        // Input: "An AcademicProgram with a specific Course where all enrolled students have the same grade."
        // Expected Output: "The common grade value as the average grade."
        
        // Setup
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        academicProgram.addCourse(course1);
        
        // Execute
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify
        assertEquals("Average grade should equal the common grade value when all students have same grade", 
                     90.0, result, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: "Average grade calculation with a mix of valid and zero grades"
        // Input: "An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0."
        // Expected Output: "The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students."
        
        // Setup
        enrollment1.setGrade(80.0);
        enrollment2.setGrade(0.0);  // Zero grade
        enrollment3.setGrade(70.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        academicProgram.addCourse(course1);
        
        // Execute
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify
        double expectedAverage = (80.0 + 0.0 + 70.0) / 3.0;
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                     expectedAverage, result, 0.001);
    }
}