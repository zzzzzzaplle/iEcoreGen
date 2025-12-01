import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
    }
    
    @Test
    public void testCase1_AllAcceptances() {
        // Setup: Create Reviewer R006 with 5 ACCEPT reviews
        reviewer = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            assignments.add(assignment);
        }
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify: Expected output is 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_Balanced5050Ratio() {
        // Setup: Create Reviewer R007 with 3 ACCEPT and 3 REJECT reviews
        reviewer = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Add 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            assignments.add(assignment);
        }
        
        // Add 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            assignments.add(assignment);
        }
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify: Expected output is 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_NoCompletedReviews() {
        // Setup: Create Reviewer R008 with no completed reviews
        reviewer = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        reviewer.setReviewAssignments(assignments);
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify: Expected output is 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_RecentRejectTendency() {
        // Setup: Create Reviewer R009 with 1 ACCEPT and 4 REJECT reviews
        reviewer = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Add 1 ACCEPT review
        ReviewAssignment acceptAssignment = new ReviewAssignment();
        acceptAssignment.setGrade(Grade.ACCEPT);
        assignments.add(acceptAssignment);
        
        // Add 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            assignments.add(assignment);
        }
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify: Expected output is 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_SingleReviewCase() {
        // Setup: Create Reviewer R010 with 1 REJECT review
        reviewer = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        ReviewAssignment assignment = new ReviewAssignment();
        assignment.setGrade(Grade.REJECT);
        assignments.add(assignment);
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify: Expected output is 0.00
        assertEquals(0.00, result, 0.001);
    }
}