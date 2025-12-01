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
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 3 review assignments with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        reviews.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        reviews.add(review3);
        
        paperP14.setReviews(reviews);
        
        // Check if all reviews are exclusively Accept
        boolean result = paperP14.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("All reviews should be ACCEPT, so should return true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15, 2 reviewers, assign paper to reviewers, 1 ACCEPT and 1 REJECT
        Paper paperP15 = new Paper();
        paperP15.setTitle("P15");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 2 review assignments with different grades
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        paperP15.setReviews(reviews);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paperP15.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Reviews have mixed grades (ACCEPT and REJECT), so should return false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16, 4 reviewers, assign paper to reviewers, all give REJECT grade
        Paper paperP16 = new Paper();
        paperP16.setTitle("P16");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 4 review assignments with REJECT grade
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            reviews.add(review);
        }
        
        paperP16.setReviews(reviews);
        
        // Check if all reviews are exclusively Reject
        boolean result = paperP16.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("All reviews should be REJECT, so should return true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17, 3 reviewers, assign paper to reviewers, 
        // 2 reviewers gave feedback (1 ACCEPT, 1 REJECT), 1 reviewer didn't give feedback (UNDECIDED)
        Paper paperP17 = new Paper();
        paperP17.setTitle("P17");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 2 review assignments with different grades
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        // Create 1 review assignment with UNDECIDED grade (no feedback given)
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED);
        reviews.add(review3);
        
        paperP17.setReviews(reviews);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paperP17.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Reviews have mixed grades including UNDECIDED, so should return false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18, 4 reviewers, assign paper to reviewers, 2 ACCEPT and 2 REJECT
        Paper paperP18 = new Paper();
        paperP18.setTitle("P18");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 2 review assignments with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        reviews.add(review2);
        
        // Create 2 review assignments with REJECT grade
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        reviews.add(review3);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        reviews.add(review4);
        
        paperP18.setReviews(reviews);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paperP18.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Reviews have mixed grades (2 ACCEPT and 2 REJECT), so should return false", result);
    }
}