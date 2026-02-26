import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Create Paper P14
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create and assign 3 review assignments with ACCEPT grade
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
    public void testCase2_splitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create and assign 2 review assignments with different grades
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        paper.setReviews(reviews);
        
        // Check if all reviews are the same
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: False
        assertFalse("Reviews are split between ACCEPT and REJECT, so result should be false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Create Paper P16
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        // Create and assign 4 review assignments with REJECT grade
        List<ReviewAssignment> reviews = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            reviews.add(review);
        }
        paper.setReviews(reviews);
        
        // Check if all reviews are the same
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: True
        assertTrue("All 4 reviews should be REJECT, so result should be true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create and assign 3 review assignments with mixed grades
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // 1 reviewer hasn't given feedback
        reviews.add(review3);
        
        paper.setReviews(reviews);
        
        // Check if all reviews are the same
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: False
        assertFalse("Reviews are mixed with ACCEPT, REJECT, and UNDECIDED, so result should be false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Create Paper P18
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create and assign 4 review assignments with 2 ACCEPT and 2 REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            ReviewAssignment reviewAccept = new ReviewAssignment();
            reviewAccept.setGrade(Grade.ACCEPT);
            reviews.add(reviewAccept);
            
            ReviewAssignment reviewReject = new ReviewAssignment();
            reviewReject.setGrade(Grade.REJECT);
            reviews.add(reviewReject);
        }
        paper.setReviews(reviews);
        
        // Check if all reviews are the same
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: False
        assertFalse("Reviews are split 2 ACCEPT and 2 REJECT, so result should be false", result);
    }
}