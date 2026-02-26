import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup
        Paper p14 = new Paper();
        p14.setTitle("P14");
        
        // Create 3 reviews with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        
        p14.setReviews(reviews);
        
        // Test the isAllReviewsPositive method
        boolean result = p14.isAllReviewsPositive();
        
        // Verify expected output
        assertTrue("Paper P14 should have unanimous ACCEPT reviews", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Setup
        Paper p15 = new Paper();
        p15.setTitle("P15");
        
        // Create 2 reviews with mixed grades (1 ACCEPT, 1 REJECT)
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("good paper");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("needs improvement");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        
        p15.setReviews(reviews);
        
        // Test the isAllReviewsPositive method
        boolean result = p15.isAllReviewsPositive();
        
        // Verify expected output
        assertFalse("Paper P15 should not have unanimous reviews (split decision)", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup
        Paper p16 = new Paper();
        p16.setTitle("P16");
        
        // Create 4 reviews with REJECT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.REJECT);
        review1.setFeedback("poor methodology");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("insufficient data");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("weak conclusions");
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("not novel");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        
        p16.setReviews(reviews);
        
        // Test the isAllReviewsPositive method
        boolean result = p16.isAllReviewsPositive();
        
        // Verify expected output
        assertTrue("Paper P16 should have unanimous REJECT reviews", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup
        Paper p17 = new Paper();
        p17.setTitle("P17");
        
        // Create 3 reviews: 1 ACCEPT, 1 REJECT, 1 UNDECIDED
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("well-written");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("needs more experiments");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Reviewer did not give final grade
        review3.setFeedback("still reviewing");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        
        p17.setReviews(reviews);
        
        // Test the isAllReviewsPositive method
        boolean result = p17.isAllReviewsPositive();
        
        // Verify expected output
        assertFalse("Paper P17 should not have unanimous reviews (mixed grades including UNDECIDED)", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup
        Paper p18 = new Paper();
        p18.setTitle("P18");
        
        // Create 4 reviews: 2 ACCEPT, 2 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("strong contribution");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("good results");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("limited scope");
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("replication needed");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        
        p18.setReviews(reviews);
        
        // Test the isAllReviewsPositive method
        boolean result = p18.isAllReviewsPositive();
        
        // Verify expected output
        assertFalse("Paper P18 should not have unanimous reviews (50-50 split)", result);
    }
}