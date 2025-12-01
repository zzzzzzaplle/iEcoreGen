import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Create Paper P14
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Create 3 reviews with ACCEPT grade
        Review review1 = new Review();
        review1.setReviewer(reviewer1);
        review1.setGrade("ACCEPT");
        review1.setFeedback("revise");
        
        Review review2 = new Review();
        review2.setReviewer(reviewer2);
        review2.setGrade("ACCEPT");
        review2.setFeedback("revise");
        
        Review review3 = new Review();
        review3.setReviewer(reviewer3);
        review3.setGrade("ACCEPT");
        review3.setFeedback("revise");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Verify final decision is not made (null)
        assertNull(paper.getFinalDecision());
        
        // Check if reviews are consistent - expected True
        assertTrue(paper.areReviewsConsistent());
    }
    
    @Test
    public void testCase2_splitDecision2_1() {
        // Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        
        // Create 2 reviews: 1 ACCEPT, 1 REJECT
        Review review1 = new Review();
        review1.setReviewer(reviewer1);
        review1.setGrade("ACCEPT");
        review1.setFeedback("feedback1");
        
        Review review2 = new Review();
        review2.setReviewer(reviewer2);
        review2.setGrade("REJECT");
        review2.setFeedback("feedback2");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        
        // Verify final decision is not made (null)
        assertNull(paper.getFinalDecision());
        
        // Check if reviews are consistent - expected False
        assertFalse(paper.areReviewsConsistent());
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Create Paper P16
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        // Create 4 reviews with REJECT grade
        Review review1 = new Review();
        review1.setReviewer(reviewer1);
        review1.setGrade("REJECT");
        review1.setFeedback("feedback1");
        
        Review review2 = new Review();
        review2.setReviewer(reviewer2);
        review2.setGrade("REJECT");
        review2.setFeedback("feedback2");
        
        Review review3 = new Review();
        review3.setReviewer(reviewer3);
        review3.setGrade("REJECT");
        review3.setFeedback("feedback3");
        
        Review review4 = new Review();
        review4.setReviewer(reviewer4);
        review4.setGrade("REJECT");
        review4.setFeedback("feedback4");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Verify final decision is not made (null)
        assertNull(paper.getFinalDecision());
        
        // Check if reviews are consistent - expected True
        assertTrue(paper.areReviewsConsistent());
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Create 2 reviews: 1 ACCEPT, 1 REJECT, 1 without grade (null)
        Review review1 = new Review();
        review1.setReviewer(reviewer1);
        review1.setGrade("ACCEPT");
        review1.setFeedback("feedback1");
        
        Review review2 = new Review();
        review2.setReviewer(reviewer2);
        review2.setGrade("REJECT");
        review2.setFeedback("feedback2");
        
        Review review3 = new Review();
        review3.setReviewer(reviewer3);
        // review3 has no grade (null) - simulating reviewer who hasn't submitted feedback
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Verify final decision is not made (null)
        assertNull(paper.getFinalDecision());
        
        // Check if reviews are consistent - expected False
        assertFalse(paper.areReviewsConsistent());
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Create Paper P18
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        // Create 4 reviews: 2 ACCEPT, 2 REJECT
        Review review1 = new Review();
        review1.setReviewer(reviewer1);
        review1.setGrade("ACCEPT");
        review1.setFeedback("feedback1");
        
        Review review2 = new Review();
        review2.setReviewer(reviewer2);
        review2.setGrade("ACCEPT");
        review2.setFeedback("feedback2");
        
        Review review3 = new Review();
        review3.setReviewer(reviewer3);
        review3.setGrade("REJECT");
        review3.setFeedback("feedback3");
        
        Review review4 = new Review();
        review4.setReviewer(reviewer4);
        review4.setGrade("REJECT");
        review4.setFeedback("feedback4");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Verify final decision is not made (null)
        assertNull(paper.getFinalDecision());
        
        // Check if reviews are consistent - expected False
        assertFalse(paper.areReviewsConsistent());
    }
}