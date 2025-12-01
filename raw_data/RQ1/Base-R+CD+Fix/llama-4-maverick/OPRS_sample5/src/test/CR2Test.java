import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup: Create Paper P14, 3 reviewers, assign paper to reviewers, all give ACCEPT reviews
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
        
        // Check if all reviews are the same
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: True
        assertTrue("All 3 reviews should be ACCEPT, so result should be true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15, 2 reviewers, assign paper to reviewers, 1 ACCEPT and 1 REJECT
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 review assignments with different grades
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("Good paper");
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("Needs improvement");
        reviews.add(review2);
        
        paper.setReviews(reviews);
        
        // Check if all reviews are the same
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: False
        assertFalse("Reviews have different grades (ACCEPT and REJECT), so result should be false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16, 4 reviewers, assign paper to reviewers, all give REJECT reviews
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 review assignments with REJECT grade
        List<ReviewAssignment> reviews = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("Not suitable");
            reviews.add(review);
        }
        paper.setReviews(reviews);
        
        // Check if all reviews are the same
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: True
        assertTrue("All 4 reviews should be REJECT, so result should be true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17, 3 reviewers, assign paper to reviewers, 
        // 2 reviewers have given feedback: 1 ACCEPT, 1 REJECT, 1 reviewer has UNDECIDED
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 review assignments with mixed grades including UNDECIDED
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("Good work");
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("Needs major revisions");
        reviews.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED);
        review3.setFeedback("Still reviewing");
        reviews.add(review3);
        
        paper.setReviews(reviews);
        
        // Check if all reviews are the same
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: False
        assertFalse("Reviews have different grades (ACCEPT, REJECT, UNDECIDED), so result should be false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18, 4 reviewers, assign paper to reviewers, 2 ACCEPT and 2 REJECT
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 review assignments with 2 ACCEPT and 2 REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Add 2 ACCEPT reviews
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("Acceptable");
            reviews.add(review);
        }
        
        // Add 2 REJECT reviews
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("Reject");
            reviews.add(review);
        }
        
        paper.setReviews(reviews);
        
        // Check if all reviews are the same
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: False
        assertFalse("Reviews have both ACCEPT and REJECT grades, so result should be false", result);
    }
}