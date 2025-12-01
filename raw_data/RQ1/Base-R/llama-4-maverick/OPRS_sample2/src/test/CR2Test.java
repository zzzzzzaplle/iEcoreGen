import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Create Paper P14
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 reviews with ACCEPT grade
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("revise");
        
        Review review2 = new Review();
        review2.setGrade("Accept");
        review2.setFeedback("revise");
        
        Review review3 = new Review();
        review3.setGrade("Accept");
        review3.setFeedback("revise");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check if reviews are consistent (should be true for unanimous accept)
        boolean result = paper.areReviewsConsistent();
        assertTrue("All reviews should be consistent (unanimous Accept)", result);
    }
    
    @Test
    public void testCase2_splitDecision2To1() {
        // Test Case 2: "Split decision 2-1"
        // Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviews with different grades
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("good paper");
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setFeedback("needs major revisions");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        
        // Check if reviews are consistent (should be false for split decision)
        boolean result = paper.areReviewsConsistent();
        assertFalse("Reviews should not be consistent (split decision)", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Create Paper P16
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 reviews with REJECT grade
        Review review1 = new Review();
        review1.setGrade("Reject");
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        
        Review review3 = new Review();
        review3.setGrade("Reject");
        
        Review review4 = new Review();
        review4.setGrade("Reject");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check if reviews are consistent (should be true for unanimous reject)
        boolean result = paper.areReviewsConsistent();
        assertTrue("All reviews should be consistent (unanimous Reject)", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviews: 1 ACCEPT, 1 REJECT, 1 without grade
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("acceptable");
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setFeedback("needs work");
        
        Review review3 = new Review(); // Reviewer did not give feedback/grade
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check if reviews are consistent (should be false due to mixed grades)
        boolean result = paper.areReviewsConsistent();
        assertFalse("Reviews should not be consistent (mixed grades)", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Create Paper P18
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 reviews: 2 ACCEPT, 2 REJECT
        Review review1 = new Review();
        review1.setGrade("Accept");
        
        Review review2 = new Review();
        review2.setGrade("Accept");
        
        Review review3 = new Review();
        review3.setGrade("Reject");
        
        Review review4 = new Review();
        review4.setGrade("Reject");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check if reviews are consistent (should be false for split decision)
        boolean result = paper.areReviewsConsistent();
        assertFalse("Reviews should not be consistent (50-50 split)", result);
    }
}