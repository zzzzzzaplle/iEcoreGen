import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

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
        // Setup common test objects
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course1.setCode("CS101");
        course1.setName("Introduction to Computer Science");
        
        course2 = new Course();
        course2.setCode("MATH101");
        course2.setName("Calculus I");
        
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        enrollment1 = new Enrollment();
        enrollment1.setStudent(student1);
        enrollment1.setCourse(course1);
        
        enrollment2 = new Enrollment();
        enrollment2.setStudent(student2);
        enrollment2.setCourse(course1);
        
        enrollment3 = new Enrollment();
        enrollment3.setStudent(student3);
        enrollment3.setCourse(course1);
        
        // Set up course enrollments
        List<Enrollment> enrollmentsCourse1 = new ArrayList<>();
        course1.setEnrollments(enrollmentsCourse1);
        
        List<Enrollment> enrollmentsCourse2 = new ArrayList<>();
        course2.setEnrollments(enrollmentsCourse2);
        
        // Add courses to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        academicProgram.setCourses(courses);
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsWithDifferentGrades() {
        // Setup: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        enrollment1.setGrade(85.5);
        enrollment2.setGrade(92.0);
        enrollment3.setGrade(78.5);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade of all the students in that specific Course
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                     expectedAverage, result, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        enrollment1.setGrade(88.0);
        course1.getEnrollments().add(enrollment1);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The grade of the single enrolled student, as it represents the average grade
        assertEquals("Average grade for single student should equal the student's grade", 
                     88.0, result, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: An AcademicProgram with a specific Course and no students are enrolled in it
        // course1 has no enrollments (default empty list)
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: An appropriate indication (0) that there are no grades to calculate an average from
        assertEquals("Average grade for course with no students should be 0", 
                     0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: An AcademicProgram with a specific Course where all enrolled students have the same grade
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The common grade value as the average grade
        assertEquals("Average grade for students with all same grades should be the common grade", 
                     90.0, result, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        enrollment1.setGrade(80.0);
        enrollment2.setGrade(0.0);  // Zero grade
        enrollment3.setGrade(85.0);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students
        double expectedAverage = (80.0 + 0.0 + 85.0) / 3;
        assertEquals("Average grade should include both valid and zero grades in calculation", 
                     expectedAverage, result, 0.001);
    }
    

}