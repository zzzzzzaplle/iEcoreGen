import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Paper paper;
    private List<ReviewAssignment> reviews;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        reviews = new ArrayList<>();
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14 with 3 ACCEPT reviews
        paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 reviewers and assign paper P14 to them
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
    public void testCase2_SplitDecision2to1() {
        // Setup: Create Paper P15 with 2 reviews (1 ACCEPT, 1 REJECT)
        paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviewers and assign paper P15 to them
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Should return false for split decision
        assertFalse("Paper with 1 ACCEPT and 1 REJECT review should return false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create Paper P16 with 4 REJECT reviews
        paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 reviewers and assign paper P16 to them
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            reviews.add(review);
        }
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Should return true for unanimous reject
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Setup: Create Paper P17 with 3 reviews (1 ACCEPT, 1 REJECT, 1 UNDECIDED)
        paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviewers and assign paper P17 to them
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Reviewer didn't give feedback
        reviews.add(review3);
        
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Should return false for mixed grades including UNDECIDED
        assertFalse("Paper with mixed grades (ACCEPT, REJECT, UNDECIDED) should return false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18 with 4 reviews (2 ACCEPT, 2 REJECT)
        paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 reviewers and assign paper P18 to them
        for (int i = 0; i < 2; i++) {
            ReviewAssignment reviewAccept = new ReviewAssignment();
            reviewAccept.setGrade(Grade.ACCEPT);
            reviews.add(reviewAccept);
        }
        
        for (int i = 0; i < 2; i++) {
            ReviewAssignment reviewReject = new ReviewAssignment();
            reviewReject.setGrade(Grade.REJECT);
            reviews.add(reviewReject);
        }
        
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Should return false for 50-50 split
        assertFalse("Paper with 2 ACCEPT and 2 REJECT reviews should return false", result);
    }
}