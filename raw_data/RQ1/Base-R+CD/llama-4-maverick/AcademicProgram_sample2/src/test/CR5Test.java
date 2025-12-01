import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        instructor = new Instructor();
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Create a classroom (required for Course constructor)
        Classroom classroom = new Classroom("C101", 30, "1", "A");
        
        // Create a course with initial quota of 20
        Course course = new Course("Math", "MATH101", null, null, 
                                 new ArrayList<String>(), 20, 3, classroom);
        
        // Add course to instructor
        instructor.getCourses().add(course);
        
        // Increase quota by 5
        course.increaseQuotaBy(5);
        
        // Calculate remaining quota (should be 25 since no enrollments)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 25 after increasing quota by 5", 
                     25, remainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create a classroom (required for Course constructor)
        Classroom classroom = new Classroom("C102", 30, "1", "A");
        
        // Create a course with initial quota of 30
        Course course = new Course("Physics", "PHY101", null, null, 
                                 new ArrayList<String>(), 30, 3, classroom);
        
        // Add course to instructor
        instructor.getCourses().add(course);
        
        // Decrease quota by 8
        course.decreaseQuotaBy(8);
        
        // Calculate remaining quota (should be 22 since no enrollments)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 22 after decreasing quota by 8", 
                     22, remainingQuota);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Create classrooms for courses
        Classroom classroom1 = new Classroom("C101", 30, "1", "A");
        Classroom classroom2 = new Classroom("C102", 30, "1", "A");
        Classroom classroom3 = new Classroom("C103", 30, "1", "A");
        
        // Create three courses with quotas 15, 25, and 35
        Course course1 = new Course("Math", "MATH101", null, null, 
                                  new ArrayList<String>(), 15, 3, classroom1);
        Course course2 = new Course("Physics", "PHY101", null, null, 
                                  new ArrayList<String>(), 25, 3, classroom2);
        Course course3 = new Course("Chemistry", "CHEM101", null, null, 
                                  new ArrayList<String>(), 35, 3, classroom3);
        
        // Add all courses to instructor
        instructor.getCourses().add(course1);
        instructor.getCourses().add(course2);
        instructor.getCourses().add(course3);
        
        // Calculate remaining quota (should be 15 + 25 + 35 = 75)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 75 for courses with quotas 15, 25, and 35", 
                     75, remainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create classrooms for courses
        Classroom classroom1 = new Classroom("C101", 30, "1", "A");
        Classroom classroom2 = new Classroom("C102", 30, "1", "A");
        
        // Create two courses with initial quotas 20 and 25
        Course course1 = new Course("Math", "MATH101", null, null, 
                                  new ArrayList<String>(), 20, 3, classroom1);
        Course course2 = new Course("Physics", "PHY101", null, null, 
                                  new ArrayList<String>(), 25, 3, classroom2);
        
        // Add courses to instructor
        instructor.getCourses().add(course1);
        instructor.getCourses().add(course2);
        
        // Increase quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Calculate remaining quota (should be 30 + 25 = 55)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 55 after increasing first course quota by 10", 
                     55, remainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create classrooms for courses
        Classroom classroom1 = new Classroom("C101", 30, "1", "A");
        Classroom classroom2 = new Classroom("C102", 30, "1", "A");
        Classroom classroom3 = new Classroom("C103", 30, "1", "A");
        
        // Create three courses with initial quotas 18, 22, and 28
        Course course1 = new Course("Math", "MATH101", null, null, 
                                  new ArrayList<String>(), 18, 3, classroom1);
        Course course2 = new Course("Physics", "PHY101", null, null, 
                                  new ArrayList<String>(), 22, 3, classroom2);
        Course course3 = new Course("Chemistry", "CHEM101", null, null, 
                                  new ArrayList<String>(), 28, 3, classroom3);
        
        // Add courses to instructor
        instructor.getCourses().add(course1);
        instructor.getCourses().add(course2);
        instructor.getCourses().add(course3);
        
        // Increase quota of second course by 6 and decrease third course by 4
        course2.increaseQuotaBy(6);  // 22 + 6 = 28
        course3.decreaseQuotaBy(4);  // 28 - 4 = 24
        
        // Calculate remaining quota (should be 18 + 28 + 24 = 70)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the result
        assertEquals("Remaining quota should be 70 after quota adjustments", 
                     70, remainingQuota);
    }
}