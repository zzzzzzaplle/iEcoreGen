import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR5Test {
    
    private Instructor instructor;
    
    @Before
    public void setUp() {
        // Reset instructor before each test
        instructor = null;
    }
    
    @Test
    public void testCase1_SingleCourseWithSingleQuotaIncrease() {
        // Create classroom
        Classroom classroom = new Classroom("C101", 50, "1", "A");
        
        // Create course with initial quota of 20
        Course course = new Course("Mathematics", "MATH101", null, null, 
                                 new ArrayList<>(), 20, 3, classroom);
        
        // Create instructor and add course
        instructor = new Instructor("John", "Doe", "Dr.", "Mathematics");
        instructor.getCourses().add(course);
        
        // Increase quota by 5
        course.increaseQuotaBy(5);
        
        // Calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 25
        assertEquals("Remaining quota should be 25 after increasing by 5", 25, remainingQuota);
    }
    
    @Test
    public void testCase2_SingleCourseWithSingleQuotaDecrease() {
        // Create classroom
        Classroom classroom = new Classroom("C102", 50, "1", "A");
        
        // Create course with initial quota of 30
        Course course = new Course("Physics", "PHYS101", null, null, 
                                 new ArrayList<>(), 30, 3, classroom);
        
        // Create instructor and add course
        instructor = new Instructor("Jane", "Smith", "Prof.", "Physics");
        instructor.getCourses().add(course);
        
        // Decrease quota by 8
        course.decreaseQuotaBy(8);
        
        // Calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 22
        assertEquals("Remaining quota should be 22 after decreasing by 8", 22, remainingQuota);
    }
    
    @Test
    public void testCase3_MultipleCoursesWithNoQuotaChanges() {
        // Create classrooms
        Classroom classroom1 = new Classroom("C101", 50, "1", "A");
        Classroom classroom2 = new Classroom("C102", 50, "1", "A");
        Classroom classroom3 = new Classroom("C103", 50, "1", "A");
        
        // Create courses with initial quotas of 15, 25, and 35
        Course course1 = new Course("Mathematics", "MATH101", null, null, 
                                  new ArrayList<>(), 15, 3, classroom1);
        Course course2 = new Course("Physics", "PHYS101", null, null, 
                                  new ArrayList<>(), 25, 3, classroom2);
        Course course3 = new Course("Chemistry", "CHEM101", null, null, 
                                  new ArrayList<>(), 35, 3, classroom3);
        
        // Create instructor and add courses
        instructor = new Instructor("Robert", "Johnson", "Dr.", "Science");
        instructor.getCourses().addAll(Arrays.asList(course1, course2, course3));
        
        // Calculate remaining quota (no quota changes made)
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 75 (15 + 25 + 35)
        assertEquals("Remaining quota should be 75 for courses with quotas 15, 25, and 35", 
                     75, remainingQuota);
    }
    
    @Test
    public void testCase4_MultipleCoursesWithQuotaIncreaseOnOneCourse() {
        // Create classrooms
        Classroom classroom1 = new Classroom("C101", 50, "1", "A");
        Classroom classroom2 = new Classroom("C102", 50, "1", "A");
        
        // Create courses with initial quotas of 20 and 25
        Course course1 = new Course("Mathematics", "MATH101", null, null, 
                                  new ArrayList<>(), 20, 3, classroom1);
        Course course2 = new Course("Physics", "PHYS101", null, null, 
                                  new ArrayList<>(), 25, 3, classroom2);
        
        // Create instructor and add courses
        instructor = new Instructor("Sarah", "Wilson", "Prof.", "Mathematics");
        instructor.getCourses().addAll(Arrays.asList(course1, course2));
        
        // Increase quota of first course by 10
        course1.increaseQuotaBy(10);
        
        // Calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 55 (30 + 25)
        assertEquals("Remaining quota should be 55 after increasing first course by 10", 
                     55, remainingQuota);
    }
    
    @Test
    public void testCase5_MultipleCoursesWithQuotaIncreaseAndDecrease() {
        // Create classrooms
        Classroom classroom1 = new Classroom("C101", 50, "1", "A");
        Classroom classroom2 = new Classroom("C102", 50, "1", "A");
        Classroom classroom3 = new Classroom("C103", 50, "1", "A");
        
        // Create courses with initial quotas of 18, 22, and 28
        Course course1 = new Course("Mathematics", "MATH101", null, null, 
                                  new ArrayList<>(), 18, 3, classroom1);
        Course course2 = new Course("Physics", "PHYS101", null, null, 
                                  new ArrayList<>(), 22, 3, classroom2);
        Course course3 = new Course("Chemistry", "CHEM101", null, null, 
                                  new ArrayList<>(), 28, 3, classroom3);
        
        // Create instructor and add courses
        instructor = new Instructor("Michael", "Brown", "Dr.", "Science");
        instructor.getCourses().addAll(Arrays.asList(course1, course2, course3));
        
        // Increase quota of second course by 6 and decrease quota of third course by 4
        course2.increaseQuotaBy(6);
        course3.decreaseQuotaBy(4);
        
        // Calculate remaining quota
        int remainingQuota = instructor.calculateRemainingQuota();
        
        // Verify the remaining quota is 70 (18 + 28 + 24)
        assertEquals("Remaining quota should be 70 after increasing second course by 6 and decreasing third by 4", 
                     70, remainingQuota);
    }
}