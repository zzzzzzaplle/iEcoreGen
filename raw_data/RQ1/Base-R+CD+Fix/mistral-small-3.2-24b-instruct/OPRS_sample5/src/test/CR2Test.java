import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 review assignments with ACCEPT grade
        List<ReviewAssignment> reviews = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("revise");
            reviews.add(review);
        }
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Accept
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Should return true for unanimous accept
        assertTrue("Paper with 3 unanimous ACCEPT reviews should return true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2To1() {
        // Setup: Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 review assignments: 1 ACCEPT, 1 REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment acceptReview = new ReviewAssignment();
        acceptReview.setGrade(Grade.ACCEPT);
        acceptReview.setFeedback("good paper");
        reviews.add(acceptReview);
        
        ReviewAssignment rejectReview = new ReviewAssignment();
        rejectReview.setGrade(Grade.REJECT);
        rejectReview.setFeedback("needs major revisions");
        reviews.add(rejectReview);
        
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Should return false for split decision
        assertFalse("Paper with split decision (1 ACCEPT, 1 REJECT) should return false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create Paper P16
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 review assignments with REJECT grade
        List<ReviewAssignment> reviews = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("not suitable");
            reviews.add(review);
        }
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Should return true for unanimous reject (all same grade)
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Setup: Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 review assignments: 1 ACCEPT, 1 REJECT, 1 UNDECIDED
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment acceptReview = new ReviewAssignment();
        acceptReview.setGrade(Grade.ACCEPT);
        acceptReview.setFeedback("accept");
        reviews.add(acceptReview);
        
        ReviewAssignment rejectReview = new ReviewAssignment();
        rejectReview.setGrade(Grade.REJECT);
        rejectReview.setFeedback("reject");
        reviews.add(rejectReview);
        
        ReviewAssignment undecidedReview = new ReviewAssignment();
        undecidedReview.setGrade(Grade.UNDECIDED);
        // No feedback since reviewer didn't submit
        reviews.add(undecidedReview);
        
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Should return false for mixed grades including UNDECIDED
        assertFalse("Paper with mixed grades (ACCEPT, REJECT, UNDECIDED) should return false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 review assignments: 2 ACCEPT, 2 REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        for (int i = 0; i < 2; i++) {
            ReviewAssignment acceptReview = new ReviewAssignment();
            acceptReview.setGrade(Grade.ACCEPT);
            acceptReview.setFeedback("accept");
            reviews.add(acceptReview);
        }
        
        for (int i = 0; i < 2; i++) {
            ReviewAssignment rejectReview = new ReviewAssignment();
            rejectReview.setGrade(Grade.REJECT);
            rejectReview.setFeedback("reject");
            reviews.add(rejectReview);
        }
        
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Should return false for 50/50 split decision
        assertFalse("Paper with 2 ACCEPT and 2 REJECT reviews should return false", result);
    }
}