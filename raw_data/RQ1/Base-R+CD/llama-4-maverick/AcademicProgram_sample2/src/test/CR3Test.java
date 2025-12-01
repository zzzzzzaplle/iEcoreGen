import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR3Test {
    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;
    private Enrollment enrollment4;

    @Before
    public void setUp() throws Exception {
        // Setup common test data
        academicProgram = new AcademicProgram();
        List<Course> courses = new ArrayList<>();
        academicProgram.setCourses(courses);
        
        // Create a classroom
        Classroom classroom = new Classroom("A101", 50, "1", "A");
        
        // Create course days
        List<String> courseDays = Arrays.asList("Monday", "Wednesday");
        
        // Create course
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse("2023-09-01 09:00:00");
        Date endTime = sdf.parse("2023-12-15 10:30:00");
        
        course1 = new Course("Mathematics", "MATH101", startTime, endTime, courseDays, 50, 3, classroom);
        course2 = new Course("Physics", "PHYS101", startTime, endTime, courseDays, 50, 3, classroom);
        
        // Add courses to academic program
        courses.add(course1);
        courses.add(course2);
        
        // Create students
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        student4 = new Student();
        
        // Create enrollments with grades
        enrollment1 = new Enrollment(student1, course1);
        enrollment1.setGrade(85.5);
        
        enrollment2 = new Enrollment(student2, course1);
        enrollment2.setGrade(92.0);
        
        enrollment3 = new Enrollment(student3, course1);
        enrollment3.setGrade(78.5);
        
        enrollment4 = new Enrollment(student4, course1);
        enrollment4.setGrade(0.0);
    }

    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Setup: Add multiple students with different grades to course1
        course1.getEnrollments().add(enrollment1); // Grade: 85.5
        course1.getEnrollments().add(enrollment2); // Grade: 92.0
        course1.getEnrollments().add(enrollment3); // Grade: 78.5
        
        // Execute: Calculate average grade for MATH101 course
        double result = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify: (85.5 + 92.0 + 78.5) / 3 = 256.0 / 3 = 85.333...
        assertEquals(85.333, result, 0.001);
    }

    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: Add only one student to course1
        course1.getEnrollments().add(enrollment1); // Grade: 85.5
        
        // Execute: Calculate average grade for MATH101 course
        double result = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify: Single student grade should be the average
        assertEquals(85.5, result, 0.001);
    }

    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: No students enrolled in course1 (enrollments list is empty)
        
        // Execute: Calculate average grade for MATH101 course
        double result = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify: Should return 0 when no students are enrolled
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: Add multiple students with identical grades to course1
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);
        
        course1.getEnrollments().add(enrollment1); // Grade: 90.0
        course1.getEnrollments().add(enrollment2); // Grade: 90.0
        course1.getEnrollments().add(enrollment3); // Grade: 90.0
        
        // Execute: Calculate average grade for MATH101 course
        double result = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify: All same grades should result in that same value as average
        assertEquals(90.0, result, 0.001);
    }

    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: Add students with valid grades and zero grade to course1
        course1.getEnrollments().add(enrollment1); // Grade: 85.5
        course1.getEnrollments().add(enrollment2); // Grade: 92.0
        course1.getEnrollments().add(enrollment4); // Grade: 0.0
        
        // Execute: Calculate average grade for MATH101 course
        double result = academicProgram.calculateAverageGrade("MATH101");
        
        // Verify: (85.5 + 92.0 + 0.0) / 3 = 177.5 / 3 = 59.166...
        assertEquals(59.166, result, 0.001);
    }
}