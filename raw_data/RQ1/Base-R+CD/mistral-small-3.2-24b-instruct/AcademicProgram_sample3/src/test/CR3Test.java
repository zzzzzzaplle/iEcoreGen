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
    private Classroom classroom1;
    private Classroom classroom2;
    private Student student1;
    private Student student2;
    private Student student3;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;

    @Before
    public void setUp() {
        // Initialize AcademicProgram
        academicProgram = new AcademicProgram();
        
        // Initialize Classrooms
        classroom1 = new Classroom();
        classroom1.setCapacity(30);
        
        classroom2 = new Classroom();
        classroom2.setCapacity(25);
        
        // Initialize Courses
        course1 = new Course();
        course1.setCode("CS101");
        course1.setClassroom(classroom1);
        course1.setEnrollments(new ArrayList<>());
        
        course2 = new Course();
        course2.setCode("MATH201");
        course2.setClassroom(classroom2);
        course2.setEnrollments(new ArrayList<>());
        
        // Initialize Students
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        // Initialize Enrollments
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
        // Setup: Create AcademicProgram with a specific Course having enrolled Students with different grades
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Set different grades for enrollments
        enrollment1.setGrade(85.5);
        enrollment2.setGrade(92.0);
        enrollment3.setGrade(78.5);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Execute: Calculate average grade
        double averageGrade = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade of all students in the course
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
        assertEquals(expectedAverage, averageGrade, 0.001);
    }

    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: Create AcademicProgram with a specific Course where only one student is enrolled
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Set grade for single enrollment
        enrollment1.setGrade(88.0);
        course1.getEnrollments().add(enrollment1);
        
        // Execute: Calculate average grade
        double averageGrade = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The grade of the single enrolled student
        assertEquals(88.0, averageGrade, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: Create AcademicProgram with a specific Course and no enrollments
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Course has empty enrollments list (default)
        
        // Execute: Calculate average grade - should throw exception
        academicProgram.calculateAverageGrade("CS101");
    }

    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: Create AcademicProgram with a specific Course where all students have same grade
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Set same grades for all enrollments
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Execute: Calculate average grade
        double averageGrade = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The common grade value as average
        assertEquals(90.0, averageGrade, 0.001);
    }

    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: Create AcademicProgram with a specific Course having mix of valid and zero grades
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Set mix of valid and zero grades
        enrollment1.setGrade(85.0);
        enrollment2.setGrade(0.0);
        enrollment3.setGrade(95.0);
        
        course1.getEnrollments().add(enrollment1);
        course1.getEnrollments().add(enrollment2);
        course1.getEnrollments().add(enrollment3);
        
        // Execute: Calculate average grade
        double averageGrade = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: Correct average considering all grades (including zeros)
        double expectedAverage = (85.0 + 0.0 + 95.0) / 3;
        assertEquals(expectedAverage, averageGrade, 0.001);
    }
}