import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {

    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        Reviewer reviewer = new Reviewer();
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        
        // Create review assignments with UNDECIDED grade (no feedback)
        ReviewAssignment ra1 = new ReviewAssignment();
        ReviewAssignment ra2 = new ReviewAssignment();
        ReviewAssignment ra3 = new ReviewAssignment();
        
        // Add assignments to reviewer
        reviewer.getAssignments().add(ra1);
        reviewer.getAssignments().add(ra2);
        reviewer.getAssignments().add(ra3);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }

    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        Reviewer reviewer = new Reviewer();
        
        // Create review assignments with ACCEPT grade
        ReviewAssignment ra1 = new ReviewAssignment();
        ra1.setGrade(Grade.ACCEPT);
        ReviewAssignment ra2 = new ReviewAssignment();
        ra2.setGrade(Grade.ACCEPT);
        
        // Add assignments to reviewer
        reviewer.getAssignments().add(ra1);
        reviewer.getAssignments().add(ra2);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }

    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers
        Reviewer reviewer = new Reviewer();
        
        // Papers P6-P7: No feedback and grade (UNDECIDED)
        ReviewAssignment ra6 = new ReviewAssignment();
        ReviewAssignment ra7 = new ReviewAssignment();
        
        // Papers P8-P10: Grade=REJECT
        ReviewAssignment ra8 = new ReviewAssignment();
        ra8.setGrade(Grade.REJECT);
        ReviewAssignment ra9 = new ReviewAssignment();
        ra9.setGrade(Grade.REJECT);
        ReviewAssignment ra10 = new ReviewAssignment();
        ra10.setGrade(Grade.REJECT);
        
        // Add all assignments to reviewer
        reviewer.getAssignments().add(ra6);
        reviewer.getAssignments().add(ra7);
        reviewer.getAssignments().add(ra8);
        reviewer.getAssignments().add(ra9);
        reviewer.getAssignments().add(ra10);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with mixed submission status should return 2", 2, result);
    }

    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        Reviewer reviewer = new Reviewer();
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }

    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers
        Reviewer reviewer = new Reviewer();
        
        // P11: grade=ACCEPT
        ReviewAssignment ra11 = new ReviewAssignment();
        ra11.setGrade(Grade.ACCEPT);
        
        // P12: no grade and feedback (UNDECIDED)
        ReviewAssignment ra12 = new ReviewAssignment();
        
        // P13: grade=REJECT
        ReviewAssignment ra13 = new ReviewAssignment();
        ra13.setGrade(Grade.REJECT);
        
        // Add all assignments to reviewer
        reviewer.getAssignments().add(ra11);
        reviewer.getAssignments().add(ra12);
        reviewer.getAssignments().add(ra13);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Verify expected output
        assertEquals("Reviewer with partially submitted reviews should return 1", 1, result);
    }
}