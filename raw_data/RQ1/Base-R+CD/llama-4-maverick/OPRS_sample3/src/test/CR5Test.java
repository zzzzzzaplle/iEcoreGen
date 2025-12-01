import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
    }
    
    @Test
    public void testCase1_AllAcceptances() {
        // Setup: Create Reviewer R006 and assign 5 reviews with ACCEPT grades
        Reviewer r006 = new Reviewer();
        
        // Create 5 review assignments with ACCEPT grade
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            r006.getAssignments().add(assignment);
        }
        
        // Calculate acceptance proportion
        double result = r006.calculateAcceptanceProportion();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_Balanced5050Ratio() {
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        Reviewer r007 = new Reviewer();
        
        // Create 3 ACCEPT review assignments
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            r007.getAssignments().add(assignment);
        }
        
        // Create 3 REJECT review assignments
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            r007.getAssignments().add(assignment);
        }
        
        // Calculate acceptance proportion
        double result = r007.calculateAcceptanceProportion();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_NoCompletedReviews() {
        // Setup: Create Reviewer R008 with no completed reviews
        Reviewer r008 = new Reviewer();
        
        // Calculate acceptance proportion - no reviews assigned
        double result = r008.calculateAcceptanceProportion();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_RecentRejectTendency() {
        // Setup: Create Reviewer R009 with 1 ACCEPT and 4 REJECT reviews
        Reviewer r009 = new Reviewer();
        
        // Create 1 ACCEPT review assignment
        ReviewAssignment acceptAssignment = new ReviewAssignment();
        acceptAssignment.setGrade(Grade.ACCEPT);
        r009.getAssignments().add(acceptAssignment);
        
        // Create 4 REJECT review assignments
        for (int i = 0; i < 4; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            r009.getAssignments().add(assignment);
        }
        
        // Calculate acceptance proportion
        double result = r009.calculateAcceptanceProportion();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_SingleReviewCase() {
        // Setup: Create Reviewer R010 with 1 REJECT review
        Reviewer r010 = new Reviewer();
        
        // Create 1 REJECT review assignment
        ReviewAssignment assignment = new ReviewAssignment();
        assignment.setGrade(Grade.REJECT);
        r010.getAssignments().add(assignment);
        
        // Calculate acceptance proportion
        double result = r010.calculateAcceptanceProportion();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}