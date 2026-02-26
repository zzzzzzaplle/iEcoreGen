import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Paper paper;
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Setup
        paper = new Paper();
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
        
        // Execute and verify
        assertTrue("All 3 reviews are ACCEPT, should return true", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Setup
        paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 review assignments with different grades
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        
        // Add reviews to paper
        paper.getReviews().add(review1);
        paper.getReviews().add(review2);
        
        // Execute and verify
        assertFalse("Reviews have ACCEPT and REJECT, should return false", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Setup
        paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 review assignments with REJECT grade
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            paper.getReviews().add(review);
        }
        
        // Execute and verify
        assertTrue("All 4 reviews are REJECT, should return true", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Setup
        paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 review assignments
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        
        ReviewAssignment review3 = new ReviewAssignment(); // No grade set (default UNDECIDED)
        
        // Add reviews to paper
        paper.getReviews().add(review1);
        paper.getReviews().add(review2);
        paper.getReviews().add(review3);
        
        // Execute and verify
        assertFalse("Reviews have ACCEPT, REJECT and UNDECIDED, should return false", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Setup
        paper = new Paper();
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
        
        // Execute and verify
        assertFalse("50% ACCEPT and 50% REJECT, should return false", paper.isAllReviewsSame());
    }
}