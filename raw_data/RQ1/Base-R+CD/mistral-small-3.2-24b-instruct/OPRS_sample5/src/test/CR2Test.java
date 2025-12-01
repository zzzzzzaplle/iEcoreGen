import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup: Create Paper P14, 3 reviewers, assign paper, all give ACCEPT grade
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
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("All 3 reviews should be ACCEPT, so result should be true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15, 2 reviewers, assign paper, 1 ACCEPT and 1 REJECT
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 review assignments with different grades
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        
        // Add reviews to paper
        paper.getReviews().add(review1);
        paper.getReviews().add(review2);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Reviews have mixed grades (ACCEPT and REJECT), so result should be false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16, 4 reviewers, assign paper, all give REJECT grade
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
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("All 4 reviews should be REJECT, so result should be true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17, 3 reviewers, assign paper, 1 ACCEPT, 1 REJECT, 1 UNDECIDED
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 review assignments with mixed grades
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
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Reviews have mixed grades (ACCEPT, REJECT, UNDECIDED), so result should be false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18, 4 reviewers, assign paper, 2 ACCEPT, 2 REJECT
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 review assignments with 2 ACCEPT and 2 REJECT
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
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Reviews have mixed grades (2 ACCEPT and 2 REJECT), so result should be false", result);
    }
}