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
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;
    
    @Before
    public void setUp() {
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
        // Setup: Create course with multiple students having different grades
        enrollment1.setGrade(85.0);
        enrollment2.setGrade(92.0);
        enrollment3.setGrade(78.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        academicProgram.addCourse(course1);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade (85 + 92 + 78) / 3 = 85.0
        assertEquals(85.0, result, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: Create course with only one student
        enrollment1.setGrade(88.5);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        course1.setEnrollments(enrollments);
        
        academicProgram.addCourse(course1);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The grade of the single enrolled student (88.5)
        assertEquals(88.5, result, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: Create course with no enrollments
        course1.setEnrollments(new ArrayList<>());
        academicProgram.addCourse(course1);
        
        // Execute: Calculate average grade for course with no students
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Returns 0.0 as specified in the method documentation
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: Create course with multiple students all having the same grade
        enrollment1.setGrade(75.0);
        enrollment2.setGrade(75.0);
        enrollment3.setGrade(75.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        academicProgram.addCourse(course1);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The common grade value (75.0)
        assertEquals(75.0, result, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: Create course with students having mix of valid grades and zero grades
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(0.0);  // Zero grade
        enrollment3.setGrade(80.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        academicProgram.addCourse(course1);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade considering all grades including zeros
        // (90 + 0 + 80) / 3 = 56.666...
        assertEquals(56.666, result, 0.001);
    }
}