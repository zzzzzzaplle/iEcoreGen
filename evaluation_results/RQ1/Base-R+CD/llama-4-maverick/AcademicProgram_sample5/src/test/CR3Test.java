import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class CR3Test {
    private AcademicProgram academicProgram;
    private Course testCourse;
    private Student student1;
    private Student student2;
    private Student student3;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;
    
    @Before
    public void setUp() throws Exception {
        // Initialize common test objects
        academicProgram = new AcademicProgram();
        Classroom classroom = new Classroom("A101", 50, "1", "A");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse("2023-09-01 09:00:00");
        Date endTime = sdf.parse("2023-09-01 10:30:00");
        testCourse = new Course("Test Course", "CS101", startTime, endTime, 50, 3, classroom);
        
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        enrollment1 = new Enrollment();
        enrollment2 = new Enrollment();
        enrollment3 = new Enrollment();
    }

    @Test
    public void testCase1_averageGradeCalculationForStudentsWithDifferentGrades() {
        // Test Case 1: Average grade calculation for students with different grades
        List<Course> courses = new ArrayList<>();
        courses.add(testCourse);
        academicProgram.setCourses(courses);
        
        // Create enrollments with different grades
        enrollment1.setGrade(85.5);
        enrollment2.setGrade(92.0);
        enrollment3.setGrade(78.3);
        
        testCourse.getEnrollments().add(enrollment1);
        testCourse.getEnrollments().add(enrollment2);
        testCourse.getEnrollments().add(enrollment3);
        
        double expectedAverage = (85.5 + 92.0 + 78.3) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase2_averageGradeCalculationForSingleStudent() {
        // Test Case 2: Average grade calculation for a single-student course
        List<Course> courses = new ArrayList<>();
        courses.add(testCourse);
        academicProgram.setCourses(courses);
        
        // Create enrollment with a specific grade for a single student
        enrollment1.setGrade(88.7);
        testCourse.getEnrollments().add(enrollment1);
        
        double expectedAverage = 88.7;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade for single student should equal the student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        List<Course> courses = new ArrayList<>();
        courses.add(testCourse);
        academicProgram.setCourses(courses);
        
        // No enrollments added to the course
        
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade for course with no students should be 0", 
                     expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        List<Course> courses = new ArrayList<>();
        courses.add(testCourse);
        academicProgram.setCourses(courses);
        
        // Create enrollments with identical grades
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);
        
        testCourse.getEnrollments().add(enrollment1);
        testCourse.getEnrollments().add(enrollment2);
        testCourse.getEnrollments().add(enrollment3);
        
        double expectedAverage = 90.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should equal the common grade value when all students have the same grade", 
                     expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void testCase5_averageGradeCalculationWithMixedValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        List<Course> courses = new ArrayList<>();
        courses.add(testCourse);
        academicProgram.setCourses(courses);
        
        // Create enrollments with mix of valid grades and zeros
        enrollment1.setGrade(85.0);
        enrollment2.setGrade(0.0);
        enrollment3.setGrade(92.5);
        
        testCourse.getEnrollments().add(enrollment1);
        testCourse.getEnrollments().add(enrollment2);
        testCourse.getEnrollments().add(enrollment3);
        
        double expectedAverage = (85.0 + 0.0 + 92.5) / 3;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should correctly calculate with mix of valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
}