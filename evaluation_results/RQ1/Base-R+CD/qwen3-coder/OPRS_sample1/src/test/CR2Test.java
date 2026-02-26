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
        // Initialize common objects before each test
        paper = new Paper();
        reviews = new ArrayList<>();
        paper.setReviews(reviews);
    }
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup: Create Paper P14 with 3 ACCEPT reviews
        paper.setTitle("P14");
        
        // Create 3 review assignments with ACCEPT grade
        for (int i = 0; i < 3; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("revise");
            reviews.add(review);
        }
        
        // Verify the paper has 3 reviews
        assertEquals(3, paper.getReviews().size());
        
        // Test the isAllReviewsPositive method
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True (all reviews are ACCEPT)
        assertTrue("Paper with 3 unanimous ACCEPT reviews should return true", result);
    }
    
    @Test
    public void testCase2_splitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15 with 2 reviews (1 ACCEPT, 1 REJECT)
        paper.setTitle("P15");
        
        // Create first review with ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("good paper");
        reviews.add(review1);
        
        // Create second review with REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("needs major revisions");
        reviews.add(review2);
        
        // Verify the paper has 2 reviews
        assertEquals(2, paper.getReviews().size());
        
        // Test the isAllReviewsPositive method
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False (reviews are mixed)
        assertFalse("Paper with mixed ACCEPT/REJECT reviews should return false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16 with 4 REJECT reviews
        paper.setTitle("P16");
        
        // Create 4 review assignments with REJECT grade
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("reject reason " + (i + 1));
            reviews.add(review);
        }
        
        // Verify the paper has 4 reviews
        assertEquals(4, paper.getReviews().size());
        
        // Test the isAllReviewsPositive method
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True (all reviews are REJECT)
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17 with 3 reviews (1 ACCEPT, 1 REJECT, 1 UNDECIDED)
        paper.setTitle("P17");
        
        // Create first review with ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("accept feedback");
        reviews.add(review1);
        
        // Create second review with REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("reject feedback");
        reviews.add(review2);
        
        // Create third review with UNDECIDED (no feedback given)
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED);
        reviews.add(review3);
        
        // Verify the paper has 3 reviews
        assertEquals(3, paper.getReviews().size());
        
        // Test the isAllReviewsPositive method
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False (contains UNDECIDED review and mixed grades)
        assertFalse("Paper with UNDECIDED review and mixed grades should return false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18 with 4 reviews (2 ACCEPT, 2 REJECT)
        paper.setTitle("P18");
        
        // Create 2 ACCEPT reviews
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("accept " + (i + 1));
            reviews.add(review);
        }
        
        // Create 2 REJECT reviews
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("reject " + (i + 1));
            reviews.add(review);
        }
        
        // Verify the paper has 4 reviews
        assertEquals(4, paper.getReviews().size());
        
        // Test the isAllReviewsPositive method
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False (mixed ACCEPT/REJECT reviews)
        assertFalse("Paper with 2 ACCEPT and 2 REJECT reviews should return false", result);
    }
}