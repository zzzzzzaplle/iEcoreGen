import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer;
    private List<Paper> papers;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
        papers = new ArrayList<>();
    }
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001 and Papers P1, P2, P3
        reviewer.setName("R001");
        
        // Create papers P1, P2, P3
        for (int i = 1; i <= 3; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            papers.add(paper);
            reviewer.addAssignedPaper(paper);
        }
        // No feedback or grade assigned (pending reviews)
        
        // Test: Calculate unsubmitted reviews for Reviewer R001
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected output is 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 and Papers P4, P5
        reviewer.setName("R002");
        
        // Create papers P4, P5
        for (int i = 4; i <= 5; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            
            // Create and assign review with ACCEPT grade
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setGrade("Accept");
            review.setFeedback("Good paper");
            paper.addReview(review);
            
            papers.add(paper);
            reviewer.addAssignedPaper(paper);
        }
        
        // Test: Calculate unsubmitted reviews for Reviewer R002
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected output is 0 (all reviews submitted)
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 and Papers P6-P10
        reviewer.setName("R003");
        
        // Create papers P6-P10
        for (int i = 6; i <= 10; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            
            if (i <= 7) {
                // P6, P7: No feedback or grade (unsubmitted)
                // No review added intentionally
            } else {
                // P8-P10: Assign review with REJECT grade
                Review review = new Review();
                review.setReviewer(reviewer);
                review.setGrade("Reject");
                review.setFeedback("Needs improvement");
                paper.addReview(review);
            }
            
            papers.add(paper);
            reviewer.addAssignedPaper(paper);
        }
        
        // Test: Calculate unsubmitted reviews for Reviewer R003
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected output is 2 (P6 and P7 are unsubmitted)
        assertEquals("Reviewer with mixed submission status should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        reviewer.setName("R004");
        // No papers assigned to reviewer
        
        // Test: Calculate unsubmitted reviews for Reviewer R004
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected output is 0 (no assigned papers)
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 and Papers P11-P13
        reviewer.setName("R005");
        
        // Create paper P11 with ACCEPT grade
        Paper paper11 = new Paper();
        paper11.setTitle("P11");
        Review review11 = new Review();
        review11.setReviewer(reviewer);
        review11.setGrade("Accept");
        review11.setFeedback("Excellent work");
        paper11.addReview(review11);
        papers.add(paper11);
        reviewer.addAssignedPaper(paper11);
        
        // Create paper P12 with no grade or feedback (unsubmitted)
        Paper paper12 = new Paper();
        paper12.setTitle("P12");
        // No review added intentionally
        papers.add(paper12);
        reviewer.addAssignedPaper(paper12);
        
        // Create paper P13 with REJECT grade
        Paper paper13 = new Paper();
        paper13.setTitle("P13");
        Review review13 = new Review();
        review13.setReviewer(reviewer);
        review13.setGrade("Reject");
        review13.setFeedback("Major revisions needed");
        paper13.addReview(review13);
        papers.add(paper13);
        reviewer.addAssignedPaper(paper13);
        
        // Test: Calculate unsubmitted reviews for Reviewer R005
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected output is 1 (only P12 is unsubmitted)
        assertEquals("Reviewer with partially submitted reviews should return 1", 1, result);
    }
}