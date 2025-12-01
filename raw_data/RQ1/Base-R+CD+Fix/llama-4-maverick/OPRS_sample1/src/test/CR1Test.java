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
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Create 3 review assignments with UNDECIDED grade (unsubmitted)
        ReviewAssignment ra1 = new ReviewAssignment();
        ra1.setGrade(Grade.UNDECIDED);
        
        ReviewAssignment ra2 = new ReviewAssignment();
        ra2.setGrade(Grade.UNDECIDED);
        
        ReviewAssignment ra3 = new ReviewAssignment();
        ra3.setGrade(Grade.UNDECIDED);
        
        reviewer.getReviewAssignments().add(ra1);
        reviewer.getReviewAssignments().add(ra2);
        reviewer.getReviewAssignments().add(ra3);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected output: 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Create 2 review assignments with ACCEPT grade (submitted)
        ReviewAssignment ra1 = new ReviewAssignment();
        ra1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment ra2 = new ReviewAssignment();
        ra2.setGrade(Grade.ACCEPT);
        
        reviewer.getReviewAssignments().add(ra1);
        reviewer.getReviewAssignments().add(ra2);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected output: 0
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers assigned
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // P6 and P7: UNDECIDED grade (unsubmitted)
        ReviewAssignment ra1 = new ReviewAssignment();
        ra1.setGrade(Grade.UNDECIDED);
        
        ReviewAssignment ra2 = new ReviewAssignment();
        ra2.setGrade(Grade.UNDECIDED);
        
        // P8, P9, P10: REJECT grade (submitted)
        ReviewAssignment ra3 = new ReviewAssignment();
        ra3.setGrade(Grade.REJECT);
        
        ReviewAssignment ra4 = new ReviewAssignment();
        ra4.setGrade(Grade.REJECT);
        
        ReviewAssignment ra5 = new ReviewAssignment();
        ra5.setGrade(Grade.REJECT);
        
        reviewer.getReviewAssignments().add(ra1);
        reviewer.getReviewAssignments().add(ra2);
        reviewer.getReviewAssignments().add(ra3);
        reviewer.getReviewAssignments().add(ra4);
        reviewer.getReviewAssignments().add(ra5);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected output: 2
        assertEquals("Reviewer with mixed submission status should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected output: 0
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers assigned
        reviewer.setReviewAssignments(new ArrayList<>());
        
        // P11: ACCEPT grade (submitted)
        ReviewAssignment ra1 = new ReviewAssignment();
        ra1.setGrade(Grade.ACCEPT);
        
        // P12: UNDECIDED grade (unsubmitted)
        ReviewAssignment ra2 = new ReviewAssignment();
        ra2.setGrade(Grade.UNDECIDED);
        
        // P13: REJECT grade (submitted)
        ReviewAssignment ra3 = new ReviewAssignment();
        ra3.setGrade(Grade.REJECT);
        
        reviewer.getReviewAssignments().add(ra1);
        reviewer.getReviewAssignments().add(ra2);
        reviewer.getReviewAssignments().add(ra3);
        
        // Calculate unsubmitted reviews
        int result = reviewer.calculateUnsubmittedReviews();
        
        // Expected output: 1
        assertEquals("Reviewer with partially submitted reviews should return 1", 1, result);
    }
}