import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {

    private AcademicProgram academicProgram;
    private Course course1;
    private Course course2;
    private Classroom classroom1;
    private Classroom classroom2;
    private Enrollment enrollment1;
    private Enrollment enrollment2;
    private Enrollment enrollment3;
    private Enrollment enrollment4;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;

    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();

        // Create classrooms
        classroom1 = new Classroom();
        classroom1.setCapacity(30);

        classroom2 = new Classroom();
        classroom2.setCapacity(40);

        // Create courses
        course1 = new Course();
        course1.setCode("CS101");
        course1.setClassroom(classroom1);

        course2 = new Course();
        course2.setCode("MATH201");
        course2.setClassroom(classroom2);

        // Create students
        student1 = new Student();
        student2 = new Student();
        student3 = new Student();
        student4 = new Student();

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

        enrollment4 = new Enrollment();
        enrollment4.setStudent(student4);
        enrollment4.setCourse(course2);
    }

    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Test Case 1: Average grade calculation for students in a specific course
        // Input: An AcademicProgram with a specific Course having a list of enrolled
        // Students with different grades

        // Setup enrollments with different grades for course1
        enrollment1.setGrade(85.5);
        enrollment2.setGrade(92.0);
        enrollment3.setGrade(78.5);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);

        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);

        // Calculate average grade
        double averageGrade = academicProgram.calculateAverageGrade("CS101");

        // Expected output: The correct average grade of all the students in that
        // specific Course
        // (85.5 + 92.0 + 78.5) / 3 = 256.0 / 3 = 85.333...
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3.0;
        assertEquals(expectedAverage, averageGrade, 0.001);
    }

    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Test Case 2: Average grade calculation for a single-student course
        // Input: An AcademicProgram with a specific Course where only one student is
        // enrolled with a specific grade

        // Setup single enrollment for course1
        enrollment1.setGrade(88.0);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        course1.setEnrollments(enrollments);

        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);

        // Calculate average grade
        double averageGrade = academicProgram.calculateAverageGrade("CS101");

        // Expected output: The grade of the single enrolled student, as it represents
        // the average grade
        assertEquals(88.0, averageGrade, 0.001);
    }

    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Test Case 3: Average grade calculation for a course with no students
        // Input: An AcademicProgram with a specific Course and no students are enrolled
        // in it

        // Setup course with no enrollments (empty list)
        course1.setEnrollments(new ArrayList<>());

        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);

        // Calculate average grade
        double averageGrade = academicProgram.calculateAverageGrade("CS101");

        // Expected output: An appropriate indication (0) that there are no grades to
        // calculate an average from
        assertEquals(0.0, averageGrade, 0.001);
    }

    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Test Case 4: Average grade calculation with all same grades
        // Input: An AcademicProgram with a specific Course where all enrolled students
        // have the same grade

        // Setup enrollments with same grades for course1
        enrollment1.setGrade(90.0);
        enrollment2.setGrade(90.0);
        enrollment3.setGrade(90.0);

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        course1.setEnrollments(enrollments);

        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);

        // Calculate average grade
        double averageGrade = academicProgram.calculateAverageGrade("CS101");

        // Expected output: The common grade value as the average grade
        assertEquals(90.0, averageGrade, 0.001);
    }

    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Test Case 5: Average grade calculation with a mix of valid and zero grades
        // Input: An AcademicProgram with a specific Course having some students with
        // valid grades and some with a grade of 0

        // Setup enrollments with mix of valid and zero grades for course1
        enrollment1.setGrade(85.0);
        enrollment2.setGrade(0.0);
        enrollment3.setGrade(92.5);
        enrollment4.setGrade(0.0);
        enrollment4.setCourse(course1); // Change course for enrollment4 to course1

        List<Enrollment> enrollments = new ArrayList<>();
        enrollments.add(enrollment1);
        enrollments.add(enrollment2);
        enrollments.add(enrollment3);
        enrollments.add(enrollment4);
        course1.setEnrollments(enrollments);

        // Add course to academic program
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        academicProgram.setCourses(courses);

        // Calculate average grade
        double averageGrade = academicProgram.calculateAverageGrade("CS101");

        // Expected output: The correct average grade obtained by considering all the
        // grades (valid and 0)
        // (85.0 + 0.0 + 92.5 + 0.0) / 4 = 177.5 / 4 = 44.375
        double expectedAverage = (85.0 + 0.0 + 92.5 + 0.0) / 4.0;
        assertEquals(expectedAverage, averageGrade, 0.001);
    }

}