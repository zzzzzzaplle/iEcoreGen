import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup: Create Paper P14
        Paper paper = new Paper();
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
        paper.getReviews().add(review1);
        paper.getReviews().add(review2);
        paper.getReviews().add(review3);
        
        // Check consensus using isAllReviewsPositive method
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("Paper with 3 unanimous ACCEPT reviews should return true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 review assignments with split decision
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        
        // Add reviews to paper
        paper.getReviews().add(review1);
        paper.getReviews().add(review2);
        
        // Check consensus using isAllReviewsPositive method
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with split decision (1 ACCEPT, 1 REJECT) should return false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 review assignments with REJECT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.REJECT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        
        // Add reviews to paper
        paper.getReviews().add(review1);
        paper.getReviews().add(review2);
        paper.getReviews().add(review3);
        paper.getReviews().add(review4);
        
        // Check consensus using isAllReviewsPositive method
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 review assignments: 1 ACCEPT, 1 REJECT, 1 UNDECIDED
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Reviewer hasn't given feedback
        
        // Add reviews to paper
        paper.getReviews().add(review1);
        paper.getReviews().add(review2);
        paper.getReviews().add(review3);
        
        // Check consensus using isAllReviewsPositive method
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with mixed grades (ACCEPT, REJECT, UNDECIDED) should return false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18
        Paper paper = new Paper();
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
        paper.getReviews().add(review1);
        paper.getReviews().add(review2);
        paper.getReviews().add(review3);
        paper.getReviews().add(review4);
        
        // Check consensus using isAllReviewsPositive method
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with 50-50 split (2 ACCEPT, 2 REJECT) should return false", result);
    }
}