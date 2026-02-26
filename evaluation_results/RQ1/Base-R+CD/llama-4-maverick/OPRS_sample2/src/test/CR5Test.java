import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR5Test {

    @Test
    public void testCase1_AllAcceptances() {
        // Setup: Create Reviewer R006
        Reviewer reviewer = new Reviewer();
        
        // Assign 5 reviews to 5 papers with ACCEPT grades
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }

    @Test
    public void testCase2_Balanced50_50Ratio() {
        // Setup: Create Reviewer R007
        Reviewer reviewer = new Reviewer();
        
        // Assign 3 ACCEPT, 3 REJECT reviews to 6 papers
        for (int i = 0; i < 3; i++) {
            ReviewAssignment acceptAssignment = new ReviewAssignment();
            acceptAssignment.setGrade(Grade.ACCEPT);
            reviewer.getReviewAssignments().add(acceptAssignment);
            
            ReviewAssignment rejectAssignment = new ReviewAssignment();
            rejectAssignment.setGrade(Grade.REJECT);
            reviewer.getReviewAssignments().add(rejectAssignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }

    @Test
    public void testCase3_NoCompletedReviews() {
        // Setup: Create Reviewer R008 (no completed reviews)
        Reviewer reviewer = new Reviewer();
        
        // No review assignments added
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }

    @Test
    public void testCase4_RecentRejectTendency() {
        // Setup: Create Reviewer R009
        Reviewer reviewer = new Reviewer();
        
        // Assign 1 ACCEPT, 4 REJECT reviews to 5 papers
        ReviewAssignment acceptAssignment = new ReviewAssignment();
        acceptAssignment.setGrade(Grade.ACCEPT);
        reviewer.getReviewAssignments().add(acceptAssignment);
        
        for (int i = 0; i < 4; i++) {
            ReviewAssignment rejectAssignment = new ReviewAssignment();
            rejectAssignment.setGrade(Grade.REJECT);
            reviewer.getReviewAssignments().add(rejectAssignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }

    @Test
    public void testCase5_SingleReviewCase() {
        // Setup: Create Reviewer R010
        Reviewer reviewer = new Reviewer();
        
        // Assign 1 REJECT review to 1 paper
        ReviewAssignment assignment = new ReviewAssignment();
        assignment.setGrade(Grade.REJECT);
        reviewer.getReviewAssignments().add(assignment);
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}