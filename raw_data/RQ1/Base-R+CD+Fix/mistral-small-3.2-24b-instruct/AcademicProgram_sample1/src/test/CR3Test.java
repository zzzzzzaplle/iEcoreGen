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
        academicProgram = new AcademicProgram();
        
        // Create classrooms
        classroom1 = new Classroom();
        classroom1.setCapacity(50);
        
        classroom2 = new Classroom();
        classroom2.setCapacity(30);
        
        // Create courses
        course1 = new Course();
        course1.setCode("CS101");
        course1.setClassroom(classroom1);
        course1.setCourseDays(new ArrayList<>(Arrays.asList("Monday", "Wednesday")));
        
        course2 = new Course();
        course2.setCode("MATH202");
        course2.setClassroom(classroom2);
        course2.setCourseDays(new ArrayList<>(Arrays.asList("Tuesday", "Thursday")));
        
        // Create students
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        
        // Create enrollments
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
        // Setup: An AcademicProgram with a specific Course having a list of enrolled Students with different grades
        enrollment1.setGrade(85.5);
        enrollment2.setGrade(92.0);
        enrollment3.setGrade(78.5);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade of all the students in that specific Course
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
        assertEquals(expectedAverage, result, 0.001);
    }

    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: An AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        enrollment1.setGrade(88.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The grade of the single enrolled student, as it represents the average grade
        assertEquals(88.0, result, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: An AcademicProgram with a specific Course and no students are enrolled in it
        List<Enrollment> enrollments = new ArrayList<>();
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for course CS101
        // Expected: An IllegalArgumentException should be thrown
        academicProgram.calculateAverageGrade("CS101");
    }

    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: An AcademicProgram with a specific Course where all enrolled students have the same grade
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The common grade value as the average grade
        assertEquals(90.0, result, 0.001);
    }

    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: An AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        enrollment1.setGrade(85.0);
        enrollment2.setGrade(0.0);
        enrollment3.setGrade(95.0);
        
        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for course CS101
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade obtained by considering all the grades (valid and 0)
        double expectedAverage = (85.0 + 0.0 + 95.0) / 3;
        assertEquals(expectedAverage, result, 0.001);
    }
}