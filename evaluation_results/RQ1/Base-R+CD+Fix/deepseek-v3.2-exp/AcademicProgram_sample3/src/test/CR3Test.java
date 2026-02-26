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
    private Classroom classroom1;
    private Classroom classroom2;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course2 = new Course();
        classroom1 = new Classroom();
        classroom2 = new Classroom();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Setup classroom1
        classroom1.setId("C101");
        classroom1.setCapacity(30);
        classroom1.setFloor("1");
        classroom1.setBlock("A");
        
        // Setup classroom2
        classroom2.setId("C102");
        classroom2.setCapacity(25);
        classroom2.setFloor("1");
        classroom2.setBlock("B");
        
        // Setup course1
        course1.setName("Mathematics");
        course1.setCode("MATH101");
        course1.setStartTime(dateFormat.parse("2024-01-15 09:00:00"));
        course1.setEndTime(dateFormat.parse("2024-05-15 10:30:00"));
        course1.setCourseDays(Arrays.asList("Monday", "Wednesday"));
        course1.setQuota(30);
        course1.setWeeklyHours(3);
        course1.setClassroom(classroom1);
        
        // Setup course2
        course2.setName("Physics");
        course2.setCode("PHYS101");
        course2.setStartTime(dateFormat.parse("2024-01-15 11:00:00"));
        course2.setEndTime(dateFormat.parse("2024-05-15 12:30:00"));
        course2.setCourseDays(Arrays.asList("Tuesday", "Thursday"));
        course2.setQuota(25);
        course2.setWeeklyHours(3);
        course2.setClassroom(classroom2);
        
        academicProgram.setCourses(Arrays.asList(course1, course2));
    }
    
    @Test
    public void testCase1_AverageGradeCalculationForStudentsInSpecificCourse() {
        // Setup enrollments for course1 with different grades
        List<Enrollment> enrollments = new ArrayList<>();
        
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(85.5);
        enrollments.add(enrollment1);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(92.0);
        enrollments.add(enrollment2);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(78.5);
        enrollments.add(enrollment3);
        
        course1.setEnrollments(enrollments);
        
        // Calculate expected average: (85.5 + 92.0 + 78.5) / 3 = 85.333...
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        // Assert with delta for floating point precision
        assertEquals("Average grade should be correctly calculated for multiple students", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_AverageGradeCalculationForSingleStudentCourse() {
        // Setup enrollment for course1 with single student
        List<Enrollment> enrollments = new ArrayList<>();
        
        Enrollment enrollment = new Enrollment();
        enrollment.setGrade(88.0);
        enrollments.add(enrollment);
        
        course1.setEnrollments(enrollments);
        
        // Expected average should be the single student's grade
        double expectedAverage = 88.0;
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        assertEquals("Average grade for single student should equal the student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_AverageGradeCalculationForCourseWithNoStudents() {
        // Ensure course1 has no enrollments (empty list)
        course1.setEnrollments(new ArrayList<>());
        
        // Expected output is 0.0 as specified in the method implementation
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        assertEquals("Average grade for course with no students should be 0.0", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_AverageGradeCalculationWithAllSameGrades() {
        // Setup enrollments for course1 with all same grades
        List<Enrollment> enrollments = new ArrayList<>();
        
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(75.0);
        enrollments.add(enrollment1);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(75.0);
        enrollments.add(enrollment2);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(75.0);
        enrollments.add(enrollment3);
        
        course1.setEnrollments(enrollments);
        
        // Expected average should be the common grade value
        double expectedAverage = 75.0;
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        assertEquals("Average grade with all same grades should equal the common grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_AverageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup enrollments for course1 with mix of valid and zero grades
        List<Enrollment> enrollments = new ArrayList<>();
        
        Enrollment enrollment1 = new Enrollment();
        enrollment1.setGrade(90.0);
        enrollments.add(enrollment1);
        
        Enrollment enrollment2 = new Enrollment();
        enrollment2.setGrade(0.0);
        enrollments.add(enrollment2);
        
        Enrollment enrollment3 = new Enrollment();
        enrollment3.setGrade(85.0);
        enrollments.add(enrollment3);
        
        Enrollment enrollment4 = new Enrollment();
        enrollment4.setGrade(0.0);
        enrollments.add(enrollment4);
        
        course1.setEnrollments(enrollments);
        
        // Calculate expected average: (90.0 + 0.0 + 85.0 + 0.0) / 4 = 43.75
        double expectedAverage = (90.0 + 0.0 + 85.0 + 0.0) / 4;
        double actualAverage = academicProgram.calculateAverageGrade("MATH101");
        
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    

}