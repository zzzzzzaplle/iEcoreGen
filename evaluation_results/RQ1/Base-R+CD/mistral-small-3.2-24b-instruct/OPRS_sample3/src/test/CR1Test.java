import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
    }
    
    @Test
    public void testCase1_reviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        Reviewer r001 = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Create 3 review assignments with UNDECIDED grade (unsubmitted)
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.UNDECIDED); // Unsubmitted review
            assignments.add(assignment);
        }
        
        r001.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r001.calculateUnsubmittedReviews();
        
        // Expected output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase2_allReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grade
        Reviewer r002 = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // Create 2 review assignments with ACCEPT grade (submitted)
        for (int i = 0; i < 2; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.ACCEPT); // Submitted review
            assignments.add(assignment);
        }
        
        r002.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r002.calculateUnsubmittedReviews();
        
        // Expected output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_mixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers assigned
        Reviewer r003 = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // P6 and P7: unsubmitted reviews (UNDECIDED)
        for (int i = 0; i < 2; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.UNDECIDED);
            assignments.add(assignment);
        }
        
        // P8-P10: submitted reviews with REJECT grade
        for (int i = 0; i < 3; i++) {
            ReviewAssignment assignment = new ReviewAssignment();
            assignment.setGrade(Grade.REJECT); // Submitted review
            assignments.add(assignment);
        }
        
        r003.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r003.calculateUnsubmittedReviews();
        
        // Expected output: 2 (only P6 and P7 are unsubmitted)
        assertEquals(2, result);
    }
    
    @Test
    public void testCase4_noAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        Reviewer r004 = new Reviewer();
        // No assignments set - using default empty list
        
        // Calculate unsubmitted reviews
        int result = r004.calculateUnsubmittedReviews();
        
        // Expected output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_partiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers assigned
        Reviewer r005 = new Reviewer();
        List<ReviewAssignment> assignments = new ArrayList<>();
        
        // P11: submitted with ACCEPT grade
        ReviewAssignment assignment1 = new ReviewAssignment();
        assignment1.setGrade(Grade.ACCEPT);
        assignments.add(assignment1);
        
        // P12: unsubmitted (UNDECIDED)
        ReviewAssignment assignment2 = new ReviewAssignment();
        assignment2.setGrade(Grade.UNDECIDED);
        assignments.add(assignment2);
        
        // P13: submitted with REJECT grade
        ReviewAssignment assignment3 = new ReviewAssignment();
        assignment3.setGrade(Grade.REJECT);
        assignments.add(assignment3);
        
        r005.setReviewAssignments(assignments);
        
        // Calculate unsubmitted reviews
        int result = r005.calculateUnsubmittedReviews();
        
        // Expected output: 1 (only P12 is unsubmitted)
        assertEquals(1, result);
    }
}