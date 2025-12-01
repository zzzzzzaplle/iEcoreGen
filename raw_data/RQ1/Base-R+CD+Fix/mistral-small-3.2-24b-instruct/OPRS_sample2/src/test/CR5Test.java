import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        // Reset reviewer before each test
        reviewer = null;
    }
    
    @Test
    public void testCase1_allAcceptances() {
        // Test Case 1: "All acceptances"
        // Input: Calculate rating tendency for Reviewer R006
        // Setup: Create Reviewer R006 and assign 5 reviews with ACCEPT grades
        
        // Step 1: Create Reviewer R006
        reviewer = new Reviewer();
        
        // Step 2: Assign 5 reviews to 5 papers with ACCEPT grades
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.getAssignments().add(assignment);
        }
        
        // Expected Output: 1.00
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Input: Calculate rating tendency for Reviewer R007
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        
        // Step 1: Create Reviewer R007
        reviewer = new Reviewer();
        
        // Step 2: Assign 3 ACCEPT and 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment acceptAssignment = new ReviewAssignment();
            acceptAssignment.setGrade(Grade.ACCEPT);
            reviewer.getAssignments().add(acceptAssignment);
        }
        
        for (int i = 0; i < 3; i++) {
            ReviewAssignment rejectAssignment = new ReviewAssignment();
            rejectAssignment.setGrade(Grade.REJECT);
            reviewer.getAssignments().add(rejectAssignment);
        }
        
        // Expected Output: 0.50
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Input: Calculate rating tendency for Reviewer R008
        // Setup: Create Reviewer R008 (no completed reviews)
        
        // Step 1: Create Reviewer R008
        reviewer = new Reviewer();
        
        // No assignments added (empty review list)
        
        // Expected Output: 0.00
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Input: Calculate rating tendency for Reviewer R009
        // Setup: Create Reviewer R009 and assign 1 ACCEPT, 4 REJECT reviews
        
        // Step 1: Create Reviewer R009
        reviewer = new Reviewer();
        
        // Step 2: Assign 1 ACCEPT and 4 REJECT reviews
        ReviewAssignment acceptAssignment = new ReviewAssignment();
        acceptAssignment.setGrade(Grade.ACCEPT);
        reviewer.getAssignments().add(acceptAssignment);
        
        for (int i = 0; i < 4; i++) {
            ReviewAssignment rejectAssignment = new ReviewAssignment();
            rejectAssignment.setGrade(Grade.REJECT);
            reviewer.getAssignments().add(rejectAssignment);
        }
        
        // Expected Output: 0.20
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Test Case 5: "Single review case"
        // Input: Calculate rating tendency for Reviewer R010
        // Setup: Create Reviewer R010 and assign 1 REJECT review
        
        // Step 1: Create Reviewer R010
        reviewer = new Reviewer();
        
        // Step 2: Assign 1 REJECT review
        ReviewAssignment rejectAssignment = new ReviewAssignment();
        rejectAssignment.setGrade(Grade.REJECT);
        reviewer.getAssignments().add(rejectAssignment);
        
        // Expected Output: 0.00
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(0.00, result, 0.001);
    }
}