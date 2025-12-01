import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        // Initialize reviewer before each test
        reviewer = new Reviewer();
    }
    
    @Test
    public void testCase1_AllAcceptances() {
        // Setup: Create Reviewer R006 and assign 5 reviews with ACCEPT grades
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Create 5 review assignments with ACCEPT grades
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.01);
    }
    
    @Test
    public void testCase2_Balanced50_50Ratio() {
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Add 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Add 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.01);
    }
    
    @Test
    public void testCase3_NoCompletedReviews() {
        // Setup: Create Reviewer R008 with no completed reviews (empty assignments)
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.01);
    }
    
    @Test
    public void testCase4_RecentRejectTendency() {
        // Setup: Create Reviewer R009 and assign 1 ACCEPT, 4 REJECT reviews
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Add 1 ACCEPT review
        ReviewAssignment acceptAssignment = new ReviewAssignment();
        acceptAssignment.setGrade(Grade.ACCEPT);
        reviewer.getReviewAssignments().add(acceptAssignment);
        
        // Add 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.01);
    }
    
    @Test
    public void testCase5_SingleReviewCase() {
        // Setup: Create Reviewer R010 and assign 1 REJECT review
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Add 1 REJECT review
        ReviewAssignment assignment = new ReviewAssignment();
        assignment.setGrade(Grade.REJECT);
        reviewer.getReviewAssignments().add(assignment);
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.01);
    }
}