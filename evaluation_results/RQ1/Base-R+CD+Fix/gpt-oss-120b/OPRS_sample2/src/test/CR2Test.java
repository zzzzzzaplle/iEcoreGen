import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Create Paper P14
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 reviewers
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 3 review assignments with ACCEPT grade and "revise" feedback
        for (int i = 0; i < 3; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("revise");
            reviews.add(review);
        }
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Check if all reviews are the same
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: True
        assertTrue("All 3 reviews are ACCEPT, should return true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviewers
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // First reviewer gives ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("good paper");
        reviews.add(review1);
        
        // Second reviewer gives REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("needs major revisions");
        reviews.add(review2);
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Check if all reviews are the same
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: False
        assertFalse("Reviews have mixed grades (ACCEPT and REJECT), should return false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Create Paper P16
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 reviewers
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 4 review assignments with REJECT grade
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("reject reason " + (i + 1));
            reviews.add(review);
        }
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Check if all reviews are the same
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: True
        assertTrue("All 4 reviews are REJECT, should return true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviewers
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // First reviewer gives ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("accept feedback");
        reviews.add(review1);
        
        // Second reviewer gives REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("reject feedback");
        reviews.add(review2);
        
        // Third reviewer has not given feedback (UNDECIDED)
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED);
        // No feedback set for undecided reviewer
        
        reviews.add(review3);
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Check if all reviews are the same
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: False
        assertFalse("Reviews have mixed grades (ACCEPT, REJECT, UNDECIDED), should return false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Create Paper P18
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 reviewers
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // 2 reviewers give ACCEPT
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("accept " + (i + 1));
            reviews.add(review);
        }
        
        // 2 reviewers give REJECT
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("reject " + (i + 1));
            reviews.add(review);
        }
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Check if all reviews are the same
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: False
        assertFalse("50% ACCEPT and 50% REJECT, should return false", result);
    }
}