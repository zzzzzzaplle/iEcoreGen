import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup: Create Paper P14, 3 reviewers, assign paper to reviewers, all give ACCEPT grade
        Paper paper = new Paper("P14", PaperType.RESEARCH);
        
        ReviewAssignment review1 = new ReviewAssignment("revise");
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment("revise");
        review2.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review3 = new ReviewAssignment("revise");
        review3.setGrade(Grade.ACCEPT);
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check consensus for Paper P14
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("Paper with 3 unanimous ACCEPT reviews should return true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15, 2 reviewers, assign paper to reviewers, 1 ACCEPT and 1 REJECT
        Paper paper = new Paper("P15", PaperType.RESEARCH);
        
        ReviewAssignment review1 = new ReviewAssignment("good paper");
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment("needs improvement");
        review2.setGrade(Grade.REJECT);
        
        paper.addReview(review1);
        paper.addReview(review2);
        
        // Check consensus for Paper P15
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with mixed ACCEPT and REJECT reviews should return false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16, 4 reviewers, assign paper to reviewers, all give REJECT grade
        Paper paper = new Paper("P16", PaperType.RESEARCH);
        
        ReviewAssignment review1 = new ReviewAssignment("poor quality");
        review1.setGrade(Grade.REJECT);
        
        ReviewAssignment review2 = new ReviewAssignment("not suitable");
        review2.setGrade(Grade.REJECT);
        
        ReviewAssignment review3 = new ReviewAssignment("needs major revisions");
        review3.setGrade(Grade.REJECT);
        
        ReviewAssignment review4 = new ReviewAssignment("reject");
        review4.setGrade(Grade.REJECT);
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check consensus for Paper P16
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3ReviewsAndOneUndecided() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17, 3 reviewers, assign paper to reviewers
        // 1 ACCEPT, 1 REJECT, 1 UNDECIDED
        Paper paper = new Paper("P17", PaperType.RESEARCH);
        
        ReviewAssignment review1 = new ReviewAssignment("good work");
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment("needs improvement");
        review2.setGrade(Grade.REJECT);
        
        ReviewAssignment review3 = new ReviewAssignment("still reviewing");
        review3.setGrade(Grade.UNDECIDED); // 1 reviewer hasn't submitted feedback
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check consensus for Paper P17
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with mixed grades and UNDECIDED review should return false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18, 4 reviewers, assign paper to reviewers, 2 ACCEPT and 2 REJECT
        Paper paper = new Paper("P18", PaperType.RESEARCH);
        
        ReviewAssignment review1 = new ReviewAssignment("excellent");
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment("good");
        review2.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review3 = new ReviewAssignment("poor methodology");
        review3.setGrade(Grade.REJECT);
        
        ReviewAssignment review4 = new ReviewAssignment("not novel");
        review4.setGrade(Grade.REJECT);
        
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check consensus for Paper P18
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Paper with exactly 50% ACCEPT and 50% REJECT reviews should return false", result);
    }
}