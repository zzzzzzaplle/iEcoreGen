import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14
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
        
        // Test: Check if reviews are consistent
        boolean result = paper.areReviewsConsistent();
        
        // Verify: All reviews are ACCEPT - should be consistent
        assertTrue("All 3 reviews unanimously accept - should return true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Setup: Create Paper P15
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
        
        // Test: Check if reviews are consistent
        boolean result = paper.areReviewsConsistent();
        
        // Verify: Mixed grades (ACCEPT and REJECT) - should be inconsistent
        assertFalse("Split decision with ACCEPT and REJECT - should return false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Setup: Create Paper P16
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 reviews with REJECT grade
        Review review1 = new Review();
        review1.setGrade("Reject");
        review1.setFeedback("poor methodology");
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setFeedback("insufficient data");
        
        Review review3 = new Review();
        review3.setGrade("Reject");
        review3.setFeedback("weak conclusions");
        
        Review review4 = new Review();
        review4.setGrade("Reject");
        review4.setFeedback("not novel enough");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Test: Check if reviews are consistent
        boolean result = paper.areReviewsConsistent();
        
        // Verify: All reviews are REJECT - should be consistent
        assertTrue("All 4 reviews unanimously reject - should return true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Setup: Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviews: 1 ACCEPT, 1 REJECT, 1 without feedback/grade
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("well-written");
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setFeedback("methodology issues");
        
        Review review3 = new Review();
        // review3 has no grade or feedback (null values)
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Test: Check if reviews are consistent
        boolean result = paper.areReviewsConsistent();
        
        // Verify: Mixed grades and one review without grade - should be inconsistent
        assertFalse("Mixed grades with one review incomplete - should return false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 reviews: 2 ACCEPT, 2 REJECT
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("strong contribution");
        
        Review review2 = new Review();
        review2.setGrade("Accept");
        review2.setFeedback("good results");
        
        Review review3 = new Review();
        review3.setGrade("Reject");
        review3.setFeedback("limited scope");
        
        Review review4 = new Review();
        review4.setGrade("Reject");
        review4.setFeedback("replication study");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Test: Check if reviews are consistent
        boolean result = paper.areReviewsConsistent();
        
        // Verify: Split 2-2 with mixed grades - should be inconsistent
        assertFalse("Exactly 50% acceptance split - should return false", result);
    }
}