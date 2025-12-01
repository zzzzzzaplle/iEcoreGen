import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private Reviewer reviewerR006;
    private Reviewer reviewerR007;
    private Reviewer reviewerR008;
    private Reviewer reviewerR009;
    private Reviewer reviewerR010;
    
    @Before
    public void setUp() {
        // Initialize reviewers for each test case
        reviewerR006 = new Reviewer();
        reviewerR007 = new Reviewer();
        reviewerR008 = new Reviewer();
        reviewerR009 = new Reviewer();
        reviewerR010 = new Reviewer();
    }
    
    @Test
    public void testCase1_allAcceptances() {
        // Setup: Create Reviewer R006 and assign 5 reviews with ACCEPT grades
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewerR006.addAssignment(assignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewerR006.calculateAcceptanceProportion();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment acceptAssignment = new ReviewAssignment();
            acceptAssignment.setGrade(Grade.ACCEPT);
            reviewerR007.addAssignment(acceptAssignment);
        }
        
        for (int i = 0; i < 3; i++) {
            ReviewAssignment rejectAssignment = new ReviewAssignment();
            rejectAssignment.setGrade(Grade.REJECT);
            reviewerR007.addAssignment(rejectAssignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewerR007.calculateAcceptanceProportion();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Setup: Create Reviewer R008 (no completed reviews)
        // No assignments added, so all reviews are UNDECIDED
        
        // Calculate acceptance proportion
        double result = reviewerR008.calculateAcceptanceProportion();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Setup: Create Reviewer R009 and assign 1 ACCEPT, 4 REJECT reviews
        ReviewAssignment acceptAssignment = new ReviewAssignment();
        acceptAssignment.setGrade(Grade.ACCEPT);
        reviewerR009.addAssignment(acceptAssignment);
        
        for (int i = 0; i < 4; i++) {
            ReviewAssignment rejectAssignment = new ReviewAssignment();
            rejectAssignment.setGrade(Grade.REJECT);
            reviewerR009.addAssignment(rejectAssignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewerR009.calculateAcceptanceProportion();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Setup: Create Reviewer R010 and assign 1 REJECT review
        ReviewAssignment rejectAssignment = new ReviewAssignment();
        rejectAssignment.setGrade(Grade.REJECT);
        reviewerR010.addAssignment(rejectAssignment);
        
        // Calculate acceptance proportion
        double result = reviewerR010.calculateAcceptanceProportion();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}