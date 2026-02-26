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
        // Common setup that runs before each test
        paper = new Paper();
        reviews = new ArrayList<>();
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
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
        
        // Verify the paper has exactly 3 reviews
        assertEquals(3, paper.getReviews().size());
        
        // Check if all reviews are exclusively Accept - Expected: True
        assertTrue("Paper with 3 unanimous ACCEPT reviews should return true", 
                   paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase2_SplitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15 with 2 reviews: 1 ACCEPT, 1 REJECT
        paper.setTitle("P15");
        
        // Create 2 review assignments with mixed grades
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("good paper");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("needs major revisions");
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        paper.setReviews(reviews);
        
        // Verify the paper has exactly 2 reviews
        assertEquals(2, paper.getReviews().size());
        
        // Check if all reviews are exclusively Accept or Reject - Expected: False
        assertFalse("Paper with mixed ACCEPT/REJECT reviews should return false", 
                    paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
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
        
        paper.setReviews(reviews);
        
        // Verify the paper has exactly 4 reviews
        assertEquals(4, paper.getReviews().size());
        
        // Check if all reviews are exclusively Reject - Expected: True
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", 
                   paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase4_MixedGradesWith3ReviewsIncludingUndecided() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17 with 2 decided reviews (1 ACCEPT, 1 REJECT) and 1 UNDECIDED
        paper.setTitle("P17");
        
        // Create 3 review assignments with mixed status
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("acceptable");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("not suitable");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Reviewer hasn't submitted feedback
        review3.setFeedback(null);
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paper.setReviews(reviews);
        
        // Verify the paper has exactly 3 reviews
        assertEquals(3, paper.getReviews().size());
        
        // Check if all reviews are exclusively Accept or Reject - Expected: False
        // (Since one review is UNDECIDED, they are not all the same)
        assertFalse("Paper with mixed grades including UNDECIDED should return false", 
                    paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18 with 4 reviews: 2 ACCEPT, 2 REJECT
        paper.setTitle("P18");
        
        // Create 4 review assignments: 2 ACCEPT, 2 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("accept 1");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("accept 2");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("reject 1");
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("reject 2");
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paper.setReviews(reviews);
        
        // Verify the paper has exactly 4 reviews
        assertEquals(4, paper.getReviews().size());
        
        // Check if all reviews are exclusively Accept or Reject - Expected: False
        // (Since we have both ACCEPT and REJECT, they are not all the same)
        assertFalse("Paper with 2 ACCEPT and 2 REJECT reviews should return false", 
                    paper.isAllReviewsPositive());
    }
}