import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Test Case 1: "All acceptances"
        // Create Reviewer R006
        Reviewer reviewer = new Reviewer();
        reviewer.setAssignments(new ArrayList<ReviewAssignment>());
        
        // Assign 5 reviews to 5 papers with ACCEPT grades, respectively
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate rating tendency for Reviewer R006
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced5050Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Create Reviewer R007
        Reviewer reviewer = new Reviewer();
        reviewer.setAssignments(new ArrayList<ReviewAssignment>());
        
        // Assign 3 ACCEPT, 3 REJECT reviews to 6 papers, respectively
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.getAssignments().add(assignment);
        }
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate rating tendency for Reviewer R007
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Create Reviewer R008 (no completed reviews)
        Reviewer reviewer = new Reviewer();
        reviewer.setAssignments(new ArrayList<ReviewAssignment>());
        
        // Calculate rating tendency for Reviewer R008
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Create Reviewer R009
        Reviewer reviewer = new Reviewer();
        reviewer.setAssignments(new ArrayList<ReviewAssignment>());
        
        // Assign 1 ACCEPT, 4 REJECT reviews to 5 papers, respectively
        ReviewAssignment acceptAssignment = new ReviewAssignment();
        acceptAssignment.setGrade(Grade.ACCEPT);
        reviewer.getAssignments().add(acceptAssignment);
        
        for (int i = 0; i < 4; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate rating tendency for Reviewer R009
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Test Case 5: "Single review case"
        // Create Reviewer R010
        Reviewer reviewer = new Reviewer();
        reviewer.setAssignments(new ArrayList<ReviewAssignment>());
        
        // Assign 1 REJECT review to 1 paper
        ReviewAssignment assignment = new ReviewAssignment();
        assignment.setGrade(Grade.REJECT);
        reviewer.getAssignments().add(assignment);
        
        // Calculate rating tendency for Reviewer R010
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}