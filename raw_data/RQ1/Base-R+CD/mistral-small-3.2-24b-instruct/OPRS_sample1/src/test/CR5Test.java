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
        reviewer.setReviewAssignments(new ArrayList<>());
        List<ReviewAssignment> assignments = reviewer.getReviewAssignments();
        
        // Create 5 review assignments with ACCEPT grade
        for (int i = 0; i < 5; i++) {
            ReviewAssignment assignment = new ReviewAssignment("Feedback " + (i + 1));
            assignment.setGrade(Grade.ACCEPT);
            assignments.add(assignment);
        }
        
        // Calculate acceptance proportion
        double result = reviewer.calculateAcceptanceProportion();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced5050Ratio() {
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        reviewer.setReviewAssignments(new ArrayList<>());
        List<ReviewAssignment> assignments = reviewer.getReviewAssignments();
        
        // Add 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment("Accept Feedback " + (i + 1));
            assignment.setGrade(Grade.ACCEPT);
            assignments.add(assignment);
        }
        
        // Add 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment("Reject Feedback " +