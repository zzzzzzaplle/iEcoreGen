import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Paper paperP14;
    private Paper paperP15;
    private Paper paperP16;
    private Paper paperP17;
    private Paper paperP18;
    
    @Before
    public void setUp() {
        // Initialize papers for each test case
        paperP14 = new Paper();
        paperP14.setTitle("P14");
        
        paperP15 = new Paper();
        paperP15.setTitle("P15");
        
        paperP16 = new Paper();
        paperP16.setTitle("P16");
        
        paperP17 = new Paper();
        paperP17.setTitle("P17");
        
        paperP18 = new Paper();
        paperP18.setTitle("P18");
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Setup: Create 3 reviewers and assign paper P14 to them
        // All 3 reviewers give ACCEPT grade with feedback
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create first review with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        reviews.add(review1);
        
        // Create second review with ACCEPT grade
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        reviews.add(review2);
        
        // Create third review with ACCEPT grade
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        reviews.add(review3);
        
        // Assign reviews to paper P14
        paperP14.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP14.isAllReviewsPositive();
        
        // Verify: Should return true for unanimous accept
        assertTrue("All 3 reviews are ACCEPT - should return true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Setup: Create 2 reviewers and assign paper P15 to them
        // One gives ACCEPT, one gives REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create first review with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        reviews.add(review1);
        
        // Create second review with REJECT grade
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        reviews.add(review2);
        
        // Assign reviews to paper P15
        paperP15.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP15.isAllReviewsPositive();
        
        // Verify: Should return false for split decision
        assertFalse("Reviews are split 1 ACCEPT and 1 REJECT - should return false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create 4 reviewers and assign paper P16 to them
        // All 4 reviewers give REJECT grade
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 4 reviews with REJECT grade
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("reject feedback " + (i + 1));
            reviews.add(review);
        }
        
        // Assign reviews to paper P16
        paperP16.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP16.isAllReviewsPositive();
        
        // Verify: Should return true for unanimous reject
        assertTrue("All 4 reviews are REJECT - should return true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Setup: Create 3 reviewers and assign paper P17 to them
        // 2 reviewers give grades (1 ACCEPT, 1 REJECT), 1 reviewer doesn't give feedback (UNDECIDED)
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create first review with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("accept feedback");
        reviews.add(review1);
        
        // Create second review with REJECT grade
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("reject feedback");
        reviews.add(review2);
        
        // Create third review with UNDECIDED grade (no feedback given)
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED);
        // No feedback set for this review
        reviews.add(review3);
        
        // Assign reviews to paper P17
        paperP17.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP17.isAllReviewsPositive();
        
        // Verify: Should return false for mixed grades including undecided
        assertFalse("Reviews are mixed: 1 ACCEPT, 1 REJECT, 1 UNDECIDED - should return false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create 4 reviewers and assign paper P18 to them
        // 2 reviewers give ACCEPT, 2 reviewers give REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 2 reviews with ACCEPT grade
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("accept feedback " + (i + 1));
            reviews.add(review);
        }
        
        // Create 2 reviews with REJECT grade
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("reject feedback " + (i + 1));
            reviews.add(review);
        }
        
        // Assign reviews to paper P18
        paperP18.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP18.isAllReviewsPositive();
        
        // Verify: Should return false for exactly 50% split
        assertFalse("Reviews are exactly 2 ACCEPT and 2 REJECT - should return false", result);
    }
}