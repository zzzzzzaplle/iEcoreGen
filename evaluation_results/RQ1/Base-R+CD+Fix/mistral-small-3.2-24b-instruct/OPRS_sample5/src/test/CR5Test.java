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
        
        // Assign 5 reviews to 5 papers with ACCEPT grades
        List<ReviewAssignment> assignments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            assignments.add(assignment);
        }
        reviewer.setReviewAssignments(assignments);
        
        // Calculate acceptance proportion - should be 1.00
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced5050Ratio() {
        // Create Reviewer R007
        Reviewer reviewer = new Reviewer();
        
        // Assign 3 ACCEPT, 3 REJECT reviews to 6 papers
        List<ReviewAssignment> assignments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            assignments.add(assignment);
        }
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            assignments.add(assignment);
        }
        reviewer.setReviewAssignments(assignments);
        
        // Calculate acceptance proportion - should be 0.50
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Create Reviewer R008 (no completed reviews)
        Reviewer reviewer = new Reviewer();
        
        // Reviewer has no assignments by default
        // Calculate acceptance proportion - should be 0.00
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Create Reviewer R009
        Reviewer reviewer = new Reviewer();
        
        // Assign 1 ACCEPT, 4 REJECT reviews to 5 papers
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Add 1 ACCEPT
        ReviewAssignment acceptAssignment = new ReviewAssignment();
        acceptAssignment.setGrade(Grade.ACCEPT);
        assignments.add(acceptAssignment);
        
        // Add 4 REJECT
        for (int i = 0; i < 4; i++) {
            ReviewAssignment rejectAssignment = new ReviewAssignment();
            rejectAssignment.setGrade(Grade.REJECT);
            assignments.add(rejectAssignment);
        }
        reviewer.setReviewAssignments(assignments);
        
        // Calculate acceptance proportion - should be 0.20
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Create Reviewer R010
        Reviewer reviewer = new Reviewer();
        
        // Assign 1 REJECT review to 1 paper
        List<ReviewAssignment> assignments = new ArrayList<>();
        ReviewAssignment assignment = new ReviewAssignment();
        assignment.setGrade(Grade.REJECT);
        assignments.add(assignment);
        reviewer.setReviewAssignments(assignments);
        
        // Calculate acceptance proportion - should be 0.00
        double result = reviewer.calculateAcceptanceProportion();
        assertEquals(0.00, result, 0.001);
    }
}