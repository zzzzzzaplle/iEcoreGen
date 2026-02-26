import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Setup: Create Reviewer R006
        Reviewer reviewer = new Reviewer();
        
        // Assign 5 reviews to 5 papers with ACCEPT grades
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.getAssignments().add(assignment);
        }
        
        // Calculate rating tendency
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify expected output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Setup: Create Reviewer R007
        Reviewer reviewer = new Reviewer();
        
        // Assign 3 ACCEPT, 3 REJECT reviews to 6 papers
        for (int i = 0; i < 3; i++) {
            ReviewAssignment acceptAssignment = new ReviewAssignment();
            acceptAssignment.setGrade(Grade.ACCEPT);
            reviewer.getAssignments().add(acceptAssignment);
            
            ReviewAssignment rejectAssignment = new ReviewAssignment();
            rejectAssignment.setGrade(Grade.REJECT);
            reviewer.getAssignments().add(rejectAssignment);
        }
        
        // Calculate rating tendency
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify expected output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Setup: Create Reviewer R008 (no completed reviews)
        Reviewer reviewer = new Reviewer();
        // No assignments added
        
        // Calculate rating tendency
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify expected output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Setup: Create Reviewer R009
        Reviewer reviewer = new Reviewer();
        
        // Assign 1 ACCEPT, 4 REJECT reviews to 5 papers
        ReviewAssignment acceptAssignment = new ReviewAssignment();
        acceptAssignment.setGrade(Grade.ACCEPT);
        reviewer.getAssignments().add(acceptAssignment);
        
        for (int i = 0; i < 4; i++) {
            ReviewAssignment rejectAssignment = new ReviewAssignment();
            rejectAssignment.setGrade(Grade.REJECT);
            reviewer.getAssignments().add(rejectAssignment);
        }
        
        // Calculate rating tendency
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify expected output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Setup: Create Reviewer R010
        Reviewer reviewer = new Reviewer();
        
        // Assign 1 REJECT review to 1 paper
        ReviewAssignment rejectAssignment = new ReviewAssignment();
        rejectAssignment.setGrade(Grade.REJECT);
        reviewer.getAssignments().add(rejectAssignment);
        
        // Calculate rating tendency
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify expected output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}