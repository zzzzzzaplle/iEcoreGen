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
    public void testCase1_allAcceptances() {
        // Setup: Create Reviewer R006 and assign 5 reviews with ACCEPT grades
        reviewer.setReviewAssignments(createReviewAssignments(5, 0));
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify expected output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced5050Ratio() {
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
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
        
        // Verify expected output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Setup: Create Reviewer R008 with no completed reviews
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify expected output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Setup: Create Reviewer R009 and assign 1 ACCEPT, 4 REJECT reviews
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
        
        // Verify expected output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Setup: Create Reviewer R010 and assign 1 REJECT review
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        ReviewAssignment assignment = new ReviewAssignment();
        assignment.setGrade(Grade.REJECT);
        assignments.add(assignment);
        
        reviewer.setReviewAssignments(assignments);
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Verify expected output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    // Helper method to create review assignments with specified number of ACCEPT reviews
    private List<ReviewAssignment> createReviewAssignments(int acceptCount, int rejectCount) {
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Add ACCEPT reviews
        for (int i = 0; i < acceptCount; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            assignments.add(assignment);
        }
        
        // Add REJECT reviews
        for (int i = 0; i < rejectCount; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            assignments.add(assignment);
        }
        
        return assignments;
    }
}