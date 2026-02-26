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
        paper = new Paper();
        reviews = new ArrayList<>();
    }
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14 with 3 ACCEPT reviews
        paper.setTitle("P14");
        
        // Create 3 review assignments with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Accept
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Should return true for unanimous accept
        assertTrue("Paper with 3 ACCEPT reviews should return true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Setup: Create Paper P15 with 1 ACCEPT and 1 REJECT review
        paper.setTitle("P15");
        
        // Create 2 review assignments with different grades
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Should return false for split decision
        assertFalse("Paper with split decision (1 ACCEPT, 1 REJECT) should return false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Setup: Create Paper P16 with 4 REJECT reviews
        paper.setTitle("P16");
        
        // Create 4 review assignments with REJECT grade
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            reviews.add(review);
        }
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Should return true for unanimous reject
        assertTrue("Paper with 4 REJECT reviews should return true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3ReviewsIncludingUndecided() {
        // Setup: Create Paper P17 with mixed reviews including UNDECIDED
        paper.setTitle("P17");
        
        // Create 3 review assignments: 1 ACCEPT, 1 REJECT, 1 UNDECIDED
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED);
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Should return false due to mixed grades including UNDECIDED
        assertFalse("Paper with mixed grades (ACCEPT, REJECT, UNDECIDED) should return false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18 with 2 ACCEPT and 2 REJECT reviews
        paper.setTitle("P18");
        
        // Create 4 review assignments: 2 ACCEPT, 2 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Should return false for 50-50 split
        assertFalse("Paper with 2 ACCEPT and 2 REJECT reviews should return false", result);
    }
}