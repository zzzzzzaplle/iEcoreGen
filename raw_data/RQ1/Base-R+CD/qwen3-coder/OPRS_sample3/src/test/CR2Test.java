import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Paper paper;
    
    @Before
    public void setUp() {
        // Reset paper before each test
        paper = new Paper();
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup: Create Paper P14, 3 reviewers, assign paper to reviewers, all give ACCEPT
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
        
        // Expected Output: True
        assertTrue("Paper with 3 unanimous ACCEPT reviews should return true", 
                   paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase2_SplitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15, 2 reviewers, assign paper to reviewers, 1 ACCEPT, 1 REJECT
        paper.setTitle("P15");
        
        // Create 2 review assignments with mixed grades
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        paper.setReviews(reviews);
        
        // Expected Output: False
        assertFalse("Paper with mixed ACCEPT/REJECT reviews should return false", 
                    paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16, 4 reviewers, assign paper to reviewers, all give REJECT
        paper.setTitle("P16");
        
        // Create 4 review assignments with REJECT grade
        List<ReviewAssignment> reviews = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            reviews.add(review);
        }
        paper.setReviews(reviews);
        
        // Expected Output: True
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", 
                   paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase4_MixedGradesWith3ReviewsOneUndecided() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17, 3 reviewers, assign paper to reviewers, 
        // 2 reviewers give feedback: 1 ACCEPT, 1 REJECT, 1 reviewer undecided
        paper.setTitle("P17");
        
        // Create 3 review assignments: 1 ACCEPT, 1 REJECT, 1 UNDECIDED
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Undecided review
        reviews.add(review3);
        
        paper.setReviews(reviews);
        
        // Expected Output: False
        assertFalse("Paper with mixed grades including UNDECIDED should return false", 
                    paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18, 4 reviewers, assign paper to reviewers, 2 ACCEPT, 2 REJECT
        paper.setTitle("P18");
        
        // Create 4 review assignments: 2 ACCEPT, 2 REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Add 2 ACCEPT reviews
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            reviews.add(review);
        }
        
        // Add 2 REJECT reviews
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            reviews.add(review);
        }
        
        paper.setReviews(reviews);
        
        // Expected Output: False
        assertFalse("Paper with 50% ACCEPT and 50% REJECT should return false", 
                    paper.isAllReviewsPositive());
    }
}