import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup: Create Paper P14, 3 reviewers, assign paper to reviewers, all give ACCEPT grade
        Paper paperP14 = new Paper();
        paperP14.setTitle("P14");
        
        // Create 3 review assignments with ACCEPT grade
        List<ReviewAssignment> reviews = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("revise");
            reviews.add(review);
        }
        paperP14.setReviews(reviews);
        
        // Expected Output: True (all reviews are ACCEPT)
        assertTrue("All reviews should be ACCEPT, consensus should be true", 
                   paperP14.isAllReviewsPositive());
    }
    
    @Test
    public void testCase2_SplitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15, 2 reviewers, assign paper to reviewers, 1 ACCEPT and 1 REJECT
        Paper paperP15 = new Paper();
        paperP15.setTitle("P15");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // First review: ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("good paper");
        reviews.add(review1);
        
        // Second review: REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("needs major revisions");
        reviews.add(review2);
        
        paperP15.setReviews(reviews);
        
        // Expected Output: False (mixed grades - not unanimous)
        assertFalse("Reviews have mixed grades (ACCEPT and REJECT), consensus should be false", 
                    paperP15.isAllReviewsPositive());
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16, 4 reviewers, assign paper to reviewers, all give REJECT grade
        Paper paperP16 = new Paper();
        paperP16.setTitle("P16");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("reject");
            reviews.add(review);
        }
        paperP16.setReviews(reviews);
        
        // Expected Output: True (all reviews are REJECT - unanimous)
        assertTrue("All reviews should be REJECT, consensus should be true", 
                   paperP16.isAllReviewsPositive());
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17, 3 reviewers, assign paper to reviewers, 
        // 2 reviewers give feedback (1 ACCEPT, 1 REJECT), 1 reviewer gives no feedback (UNDECIDED)
        Paper paperP17 = new Paper();
        paperP17.setTitle("P17");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // First review: ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("accept with minor revisions");
        reviews.add(review1);
        
        // Second review: REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("major issues");
        reviews.add(review2);
        
        // Third review: UNDECIDED (no feedback given)
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED);
        reviews.add(review3);
        
        paperP17.setReviews(reviews);
        
        // Expected Output: False (mixed grades among submitted reviews)
        assertFalse("Reviews have mixed grades (ACCEPT and REJECT), consensus should be false", 
                    paperP17.isAllReviewsPositive());
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18, 4 reviewers, assign paper to reviewers, 2 ACCEPT and 2 REJECT
        Paper paperP18 = new Paper();
        paperP18.setTitle("P18");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // First 2 reviews: ACCEPT
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("accept");
            reviews.add(review);
        }
        
        // Next 2 reviews: REJECT
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("reject");
            reviews.add(review);
        }
        
        paperP18.setReviews(reviews);
        
        // Expected Output: False (exactly 50-50 split, not unanimous)
        assertFalse("Reviews are split 2 ACCEPT and 2 REJECT, consensus should be false", 
                    paperP18.isAllReviewsPositive());
    }
}