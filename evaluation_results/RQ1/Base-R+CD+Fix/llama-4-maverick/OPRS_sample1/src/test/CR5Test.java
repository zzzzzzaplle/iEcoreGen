import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Create Reviewer R006
        Reviewer reviewer = new Reviewer();
        reviewer.setReviewAssignments(new ArrayList<ReviewAssignment>());
        
        // Assign 5 reviews to 5 papers with ACCEPT grades
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Calculate rating tendency and verify expected output
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced5050Ratio() {
        // Create Reviewer R007
        Reviewer reviewer = new Reviewer();
        reviewer.setReviewAssignments(new ArrayList<ReviewAssignment>());
        
        // Assign 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Assign 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Calculate rating tendency and verify expected output
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Create Reviewer R008 with no completed reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setReviewAssignments(new ArrayList<ReviewAssignment>());
        
        // Calculate rating tendency and verify expected output
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Create Reviewer R009
        Reviewer reviewer = new Reviewer();
        reviewer.setReviewAssignments(new ArrayList<ReviewAssignment>());
        
        // Assign 1 ACCEPT review
        ReviewAssignment acceptAssignment = new ReviewAssignment();
        acceptAssignment.setGrade(Grade.ACCEPT);
        reviewer.getReviewAssignments().add(acceptAssignment);
        
        // Assign 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Calculate rating tendency and verify expected output
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Create Reviewer R010
        Reviewer reviewer = new Reviewer();
        reviewer.setReviewAssignments(new ArrayList<ReviewAssignment>());
        
        // Assign 1 REJECT review
        ReviewAssignment assignment = new ReviewAssignment();
        assignment.setGrade(Grade.REJECT);
        reviewer.getReviewAssignments().add(assignment);
        
        // Calculate rating tendency and verify expected output
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(0.00, result, 0.001);
    }
}