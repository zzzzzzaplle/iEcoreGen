import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Setup: Create Reviewer R006 with 5 ACCEPT reviews
        Reviewer reviewer = new Reviewer();
        
        // Create 5 review assignments with ACCEPT grades
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.addReviewAssignment(assignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify: Expected output is 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Setup: Create Reviewer R007 with 3 ACCEPT and 3 REJECT reviews
        Reviewer reviewer = new Reviewer();
        
        // Add 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.addReviewAssignment(assignment);
        }
        
        // Add 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            reviewer.addReviewAssignment(assignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify: Expected output is 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Setup: Create Reviewer R008 with no completed reviews
        Reviewer reviewer = new Reviewer();
        
        // No assignments added - all reviews are UNDECIDED by default
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify: Expected output is 0.00 (no submitted reviews)
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Setup: Create Reviewer R009 with 1 ACCEPT and 4 REJECT reviews
        Reviewer reviewer = new Reviewer();
        
        // Add 1 ACCEPT review
        ReviewAssignment acceptAssignment = new ReviewAssignment();
        acceptAssignment.setGrade(Grade.ACCEPT);
        reviewer.addReviewAssignment(acceptAssignment);
        
        // Add 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            ReviewAssignment rejectAssignment = new ReviewAssignment();
            rejectAssignment.setGrade(Grade.REJECT);
            reviewer.addReviewAssignment(rejectAssignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify: Expected output is 0.20 (1/5 = 0.2)
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Setup: Create Reviewer R010 with 1 REJECT review
        Reviewer reviewer = new Reviewer();
        
        // Add 1 REJECT review
        ReviewAssignment assignment = new ReviewAssignment();
        assignment.setGrade(Grade.REJECT);
        reviewer.addReviewAssignment(assignment);
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify: Expected output is 0.00 (0/1 = 0.0)
        assertEquals(0.00, result, 0.001);
    }
}