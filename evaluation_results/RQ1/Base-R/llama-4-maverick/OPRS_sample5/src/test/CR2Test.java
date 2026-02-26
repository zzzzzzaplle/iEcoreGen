import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Create Paper P14
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Create 3 reviews with ACCEPT grade
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("revise");
        review1.setReviewer(reviewer1);
        
        Review review2 = new Review();
        review2.setGrade("Accept");
        review2.setFeedback("revise");
        review2.setReviewer(reviewer2);
        
        Review review3 = new Review();
        review3.setGrade("Accept");
        review3.setFeedback("revise");
        review3.setReviewer(reviewer3);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check if reviews are consistent (all ACCEPT)
        boolean result = paper.areReviewsConsistent();
        
        // Expected output: True
        assertTrue("All reviews should be ACCEPT - expected true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2to1() {
        // Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        
        // Create 2 reviews: 1 ACCEPT and 1 REJECT
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("feedback1");
        review1.setReviewer(reviewer1);
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setFeedback("feedback2");
        review2.setReviewer(reviewer2);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        
        // Check if reviews are consistent (mixed grades)
        boolean result = paper.areReviewsConsistent();
        
        // Expected output: False
        assertFalse("Mixed ACCEPT and REJECT reviews - expected false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
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
        review1.setGrade("Reject");
        review1.setFeedback("feedback1");
        review1.setReviewer(reviewer1);
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setFeedback("feedback2");
        review2.setReviewer(reviewer2);
        
        Review review3 = new Review();
        review3.setGrade("Reject");
        review3.setFeedback("feedback3");
        review3.setReviewer(reviewer3);
        
        Review review4 = new Review();
        review4.setGrade("Reject");
        review4.setFeedback("feedback4");
        review4.setReviewer(reviewer4);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check if reviews are consistent (all REJECT)
        boolean result = paper.areReviewsConsistent();
        
        // Expected output: True
        assertTrue("All reviews should be REJECT - expected true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Create 2 reviews: 1 ACCEPT and 1 REJECT
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("feedback1");
        review1.setReviewer(reviewer1);
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setFeedback("feedback2");
        review2.setReviewer(reviewer2);
        
        // 1 reviewer without feedback (null grade)
        Review review3 = new Review();
        review3.setReviewer(reviewer3);
        // grade and feedback remain null
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check if reviews are consistent (mixed grades with one null)
        boolean result = paper.areReviewsConsistent();
        
        // Expected output: False
        assertFalse("Mixed ACCEPT and REJECT reviews with one null - expected false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Create Paper P18
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        // Create 4 reviews: 2 ACCEPT and 2 REJECT
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("feedback1");
        review1.setReviewer(reviewer1);
        
        Review review2 = new Review();
        review2.setGrade("Accept");
        review2.setFeedback("feedback2");
        review2.setReviewer(reviewer2);
        
        Review review3 = new Review();
        review3.setGrade("Reject");
        review3.setFeedback("feedback3");
        review3.setReviewer(reviewer3);
        
        Review review4 = new Review();
        review4.setGrade("Reject");
        review4.setFeedback("feedback4");
        review4.setReviewer(reviewer4);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check if reviews are consistent (mixed grades)
        boolean result = paper.areReviewsConsistent();
        
        // Expected output: False
        assertFalse("50% ACCEPT and 50% REJECT - expected false", result);
    }
}