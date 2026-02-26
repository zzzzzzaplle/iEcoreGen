import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {

    /**
     * Test Case 1: "All acceptances"
     * Calculate rating tendency for Reviewer R006 with 5 ACCEPT reviews
     * Expected Output: 1.00
     */
    @Test
    public void testCase1_allAcceptances() {
        // Create Reviewer R006
        Reviewer reviewer = new Reviewer();

        // Assign 5 reviews to 5 papers with ACCEPT grades
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.getAssignments().add(assignment);
        }

        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();

        // Verify the result is 1.00 (all acceptances)
        assertEquals(1.00, result, 0.01);
    }

    /**
     * Test Case 2: "Balanced 50-50 ratio"
     * Calculate rating tendency for Reviewer R007 with 3 ACCEPT and 3 REJECT
     * reviews
     * Expected Output: 0.50
     */
    @Test
    public void testCase2_balanced5050Ratio() {
        // Create Reviewer R007
        Reviewer reviewer = new Reviewer();

        // Assign 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.getAssignments().add(assignment);
        }

        // Assign 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            reviewer.getAssignments().add(assignment);
        }

        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();

        // Verify the result is 0.50 (balanced ratio)
        assertEquals(0.50, result, 0.01);
    }

    /**
     * Test Case 3: "No completed reviews"
     * Calculate rating tendency for Reviewer R008 with no completed reviews
     * Expected Output: 0.00
     */
    @Test
    public void testCase3_noCompletedReviews() {
        // Create Reviewer R008 (no completed reviews)
        Reviewer reviewer = new Reviewer();

        // Calculate acceptance proportion with empty assignments
        double result = reviewer.calculateAcceptanceProportion();

        // Verify the result is 0.00 (no reviews)
        assertEquals(0.00, result, 0.01);
    }

    /**
     * Test Case 4: "Recent reject tendency"
     * Calculate rating tendency for Reviewer R009 with 1 ACCEPT and 4 REJECT
     * reviews
     * Expected Output: 0.20
     */
    @Test
    public void testCase4_recentRejectTendency() {
        // Create Reviewer R009
        Reviewer reviewer = new Reviewer();

        // Assign 1 ACCEPT review
        ReviewAssignment acceptAssignment = new ReviewAssignment();
        acceptAssignment.setGrade(Grade.ACCEPT);
        reviewer.getAssignments().add(acceptAssignment);

        // Assign 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            ReviewAssignment rejectAssignment = new ReviewAssignment();
            rejectAssignment.setGrade(Grade.REJECT);
            reviewer.getAssignments().add(rejectAssignment);
        }

        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();

        // Verify the result is 0.20 (1 accept out of 5 total)
        assertEquals(0.20, result, 0.01);
    }

    /**
     * Test Case 5: "Single review case"
     * Calculate rating tendency for Reviewer R010 with 1 REJECT review
     * Expected Output: 0.00
     */
    @Test
    public void testCase5_singleReviewCase() {
        // Create Reviewer R010
        Reviewer reviewer = new Reviewer();

        // Assign 1 REJECT review
        ReviewAssignment assignment = new ReviewAssignment();
        assignment.setGrade(Grade.REJECT);
        reviewer.getAssignments().add(assignment);

        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();

        // Verify the result is 0.00 (single reject review)
        assertEquals(0.00, result, 0.01);
    }
}