import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
    public void setUp() {
        academicProgram = new AcademicProgram();
        course1 = new Course();
        course1.setCode("CS101");
        course1.setEnrollments(new ArrayList<>());
        
        course2 = new Course();
        course2.setCode("MATH201");
        course2.setEnrollments(new ArrayList<>());
        
        student1 = new Student();
        student1.setEnrollments(new ArrayList<>());
        student1.setEnrolledCourses(new ArrayList<>());
        
        student2 = new Student();
        student2.setEnrollments(new ArrayList<>());
        student2.setEnrolledCourses(new ArrayList<>());
        
        student3 = new Student();
        student3.setEnrollments(new ArrayList<>());
        student3.setEnrolledCourses(new ArrayList<>());
        
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
        
        // Setup course with students having different grades
        enrollment1.setGrade(85.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(95.0);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Calculate expected average: (85 + 90 + 95) / 3 = 90.0
        double expectedAverage = 90.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should be correctly calculated for students with different grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: "Average grade calculation for a single - student course"
        // Input: "An AcademicProgram with a specific Course where only one student is enrolled with a specific grade."
        // Expected Output: "The grade of the single enrolled student, as it represents the average grade."
        
        // Setup course with single student
        enrollment1.setGrade(88.5);
        course1.getEnrollments().add(enrollment1);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // For single student, average should equal that student's grade
        double expectedAverage = 88.5;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade for single student course should equal the student's grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: "Average grade calculation for a course with no students"
        // Input: "An AcademicProgram with a specific Course and no students are enrolled in it."
        // Expected Output: "An appropriate indication (e.g., 0 or a special value) that there are no grades to calculate an average from."
        
        // Setup course with no enrollments (empty enrollments list)
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // When no students are enrolled, should return 0.0
        double expectedAverage = 0.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade for course with no students should be 0.0", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: "Average grade calculation with all same grades"
        // Input: "An AcademicProgram with a specific Course where all enrolled students have the same grade."
        // Expected Output: "The common grade value as the average grade."
        
        // Setup course with students having identical grades
        enrollment1.setGrade(75.0);
        enrollment2.setGrade(75.0);
        enrollment3.setGrade(75.0);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // When all grades are the same, average should equal that common value
        double expectedAverage = 75.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade when all students have same grade should equal that common grade", 
                     expectedAverage, actualAverage, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: "Average grade calculation with a mix of valid and zero grades"
        // Input: "An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0."
        // Expected Output: "The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students."
        
        // Setup course with mix of valid grades and zero grades
        enrollment1.setGrade(80.0);
        enrollment2.setGrade(0.0);  // Zero grade
        enrollment3.setGrade(70.0);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Calculate expected average: (80 + 0 + 70) / 3 = 50.0
        double expectedAverage = 50.0;
        double actualAverage = academicProgram.calculateAverageGrade("CS101");
        
        assertEquals("Average grade should correctly handle mix of valid and zero grades", 
                     expectedAverage, actualAverage, 0.001);
    }
    

}