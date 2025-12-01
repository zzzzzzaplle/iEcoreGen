import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR3Test {
    private AcademicProgram academicProgram;
    private Course course;
    
    @Before
    public void setUp() {
        academicProgram = new AcademicProgram();
        course = new Course();
    }
    
    @Test
    public void testCase1_averageGradeCalculationForStudentsInSpecificCourse() {
        // Setup: Create AcademicProgram with a specific Course having a list of enrolled Students with different grades
        course.setCourseCode("CS101");
        
        Student student1 = new Student();
        student1.setGrade(85.5);
        Student student2 = new Student();
        student2.setGrade(92.0);
        Student student3 = new Student();
        student3.setGrade(78.5);
        
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        course.setStudents(students);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CS101");
        
        // Verify: The correct average grade of all the students in that specific Course
        double expectedAverage = (85.5 + 92.0 + 78.5) / 3;
        assertEquals(expectedAverage, result, 0.001);
    }
    
    @Test
    public void testCase2_averageGradeCalculationForSingleStudentCourse() {
        // Setup: Create AcademicProgram with a specific Course where only one student is enrolled with a specific grade
        course.setCourseCode("MATH201");
        
        Student student = new Student();
        student.setGrade(88.0);
        
        List<Student> students = new ArrayList<>();
        students.add(student);
        course.setStudents(students);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("MATH201");
        
        // Verify: The grade of the single enrolled student, as it represents the average grade
        assertEquals(88.0, result, 0.001);
    }
    
    @Test
    public void testCase3_averageGradeCalculationForCourseWithNoStudents() {
        // Setup: Create AcademicProgram with a specific Course and no students are enrolled in it
        course.setCourseCode("PHY301");
        course.setStudents(new ArrayList<>()); // Empty student list
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("PHY301");
        
        // Verify: An appropriate indication (0) that there are no grades to calculate an average from
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase4_averageGradeCalculationWithAllSameGrades() {
        // Setup: Create AcademicProgram with a specific Course where all enrolled students have the same grade
        course.setCourseCode("CHEM401");
        
        Student student1 = new Student();
        student1.setGrade(75.0);
        Student student2 = new Student();
        student2.setGrade(75.0);
        Student student3 = new Student();
        student3.setGrade(75.0);
        Student student4 = new Student();
        student4.setGrade(75.0);
        
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        course.setStudents(students);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("CHEM401");
        
        // Verify: The common grade value as the average grade
        assertEquals(75.0, result, 0.001);
    }
    
    @Test
    public void testCase5_averageGradeCalculationWithMixOfValidAndZeroGrades() {
        // Setup: Create AcademicProgram with a specific Course having some students with valid grades and some with a grade of 0
        course.setCourseCode("BIO501");
        
        Student student1 = new Student();
        student1.setGrade(90.0);
        Student student2 = new Student();
        student2.setGrade(0.0);
        Student student3 = new Student();
        student3.setGrade(85.0);
        Student student4 = new Student();
        student4.setGrade(0.0);
        Student student5 = new Student();
        student5.setGrade(95.0);
        
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);
        students.add(student5);
        course.setStudents(students);
        
        List<Course> courses = new ArrayList<>();
        courses.add(course);
        academicProgram.setCourses(courses);
        
        // Execute: Calculate average grade for the course
        double result = academicProgram.calculateAverageGrade("BIO501");
        
        // Verify: The correct average grade obtained by considering all the grades (valid and 0) of the enrolled students
        double expectedAverage = (90.0 + 0.0 + 85.0 + 0.0 + 95.0) / 5;
        assertEquals(expectedAverage, result, 0.001);
    }
}