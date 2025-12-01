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
        reviewer = new Reviewer();
    }

    @Test
    public void testCase1_allAcceptances() {
        // Test Case 1: "All acceptances"
        // Setup: Create Reviewer R006 and assign 5 reviews with ACCEPT grades
        reviewer.setAssignments(new ArrayList<>());

        // Create 5 review assignments with ACCEPT grade
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.getAssignments().add(assignment);
        }

        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();

        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }

    @Test
    public void testCase2_balanced50_50Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        reviewer.setAssignments(new ArrayList<>());

        // Add 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.getAssignments().add(assignment);
        }

        // Add 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            reviewer.getAssignments().add(assignment);
        }

        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();

        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }

    @Test
    public void testCase3_noCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Setup: Create Reviewer R008 with no completed reviews
        reviewer.setAssignments(new ArrayList<>());

        // Reviewer has empty assignments list (no reviews)

        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();

        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }

    @Test
    public void testCase4_recentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Setup: Create Reviewer R009 and assign 1 ACCEPT, 4 REJECT reviews
        reviewer.setAssignments(new ArrayList<>());

        // Add 1 ACCEPT review
        ReviewAssignment acceptAssignment = new ReviewAssignment();
        acceptAssignment.setGrade(Grade.ACCEPT);
        reviewer.getAssignments().add(acceptAssignment);

        // Add 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            reviewer.getAssignments().add(assignment);
        }

        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();

        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }

    @Test
    public void testCase5_singleReviewCase() {
        // Test Case 5: "Single review case"
        // Setup: Create Reviewer R010 and assign 1 REJECT review
        reviewer.setAssignments(new ArrayList<>());

        // Add 1 REJECT review
        ReviewAssignment assignment = new ReviewAssignment();
        assignment.setGrade(Grade.REJECT);
        reviewer.getAssignments().add(assignment);

        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();

        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }

}