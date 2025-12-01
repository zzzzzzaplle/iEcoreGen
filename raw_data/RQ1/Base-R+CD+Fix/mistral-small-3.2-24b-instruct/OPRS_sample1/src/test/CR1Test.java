import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        // Reset reviewer before each test
        reviewer = new Reviewer();
    }
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Create 3 papers and assign them to reviewer without grades (UNDECIDED status)
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.UNDECIDED); // Default is UNDECIDED, but explicit for clarity
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Expected Output: 3 unsubmitted reviews
        assertEquals(3, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Create 2 papers and assign them to reviewer with ACCEPT grades
        for (int i = 0; i < 2; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT);
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Expected Output: 0 unsubmitted reviews
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers - 2 without grades, 3 with REJECT grades
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Add 2 assignments without grades (UNDECIDED)
        for (int i = 0; i < 2; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.UNDECIDED);
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Add 3 assignments with REJECT grades
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT);
            reviewer.getReviewAssignments().add(assignment);
        }
        
        // Expected Output: 2 unsubmitted reviews
        assertEquals(2, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments (empty list)
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Expected Output: 0 unsubmitted reviews
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers: ACCEPT, UNDECIDED, REJECT
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Paper P11 - ACCEPT
        ReviewAssignment assignment1 = new ReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        reviewer.getReviewAssignments().add(assignment1);
        
        // Paper P12 - UNDECIDED (no grade/feedback)
        ReviewAssignment assignment2 = new ReviewAssignment();
        assignment2.setGrade(Grade.UNDECIDED);
        reviewer.getReviewAssignments().add(assignment2);
        
        // Paper P13 - REJECT
        ReviewAssignment assignment3 = new ReviewAssignment();
        assignment3.setGrade(Grade.REJECT);
        reviewer.getReviewAssignments().add(assignment3);
        
        // Expected Output: 1 unsubmitted review
        assertEquals(1, reviewer.calculateUnsubmittedReviews());
    }
}