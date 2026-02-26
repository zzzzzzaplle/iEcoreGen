import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup
        Paper paper = new Paper("P14", PaperType.RESEARCH);
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        Review review1 = new Review(reviewer1);
        Review review2 = new Review(reviewer2);
        Review review3 = new Review(reviewer3);
        
        // Assign paper to reviewers and submit reviews
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        review1.submit("revise", Grade.ACCEPT);
        review2.submit("revise", Grade.ACCEPT);
        review3.submit("revise", Grade.ACCEPT);
        
        // Check consensus
        boolean result = paper.areReviewsUniform();
        
        // Verify expected output
        assertTrue("All reviews should be ACCEPT - consensus should be true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Setup
        Paper paper = new Paper("P15", PaperType.RESEARCH);
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        
        Review review1 = new Review(reviewer1);
        Review review2 = new Review(reviewer2);
        
        // Assign paper to reviewers and submit reviews
        paper.addReview(review1);
        paper.addReview(review2);
        
        review1.submit("feedback", Grade.ACCEPT);
        review2.submit("feedback", Grade.REJECT);
        
        // Check consensus
        boolean result = paper.areReviewsUniform();
        
        // Verify expected output
        assertFalse("Reviews have mixed grades - consensus should be false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup
        Paper paper = new Paper("P16", PaperType.RESEARCH);
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        Review review1 = new Review(reviewer1);
        Review review2 = new Review(reviewer2);
        Review review3 = new Review(reviewer3);
        Review review4 = new Review(reviewer4);
        
        // Assign paper to reviewers and submit reviews
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        review1.submit("feedback", Grade.REJECT);
        review2.submit("feedback", Grade.REJECT);
        review3.submit("feedback", Grade.REJECT);
        review4.submit("feedback", Grade.REJECT);
        
        // Check consensus
        boolean result = paper.areReviewsUniform();
        
        // Verify expected output
        assertTrue("All reviews should be REJECT - consensus should be true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup
        Paper paper = new Paper("P17", PaperType.RESEARCH);
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        Review review1 = new Review(reviewer1);
        Review review2 = new Review(reviewer2);
        Review review3 = new Review(reviewer3);
        
        // Assign paper to reviewers
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Only 2 reviewers submit reviews (1 ACCEPT, 1 REJECT), 1 reviewer doesn't submit
        review1.submit("feedback", Grade.ACCEPT);
        review2.submit("feedback", Grade.REJECT);
        // review3 remains unsubmitted
        
        // Check consensus
        boolean result = paper.areReviewsUniform();
        
        // Verify expected output
        assertFalse("One review is unsubmitted and grades are mixed - consensus should be false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup
        Paper paper = new Paper("P18", PaperType.RESEARCH);
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        Review review1 = new Review(reviewer1);
        Review review2 = new Review(reviewer2);
        Review review3 = new Review(reviewer3);
        Review review4 = new Review(reviewer4);
        
        // Assign paper to reviewers and submit reviews
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        review1.submit("feedback", Grade.ACCEPT);
        review2.submit("feedback", Grade.ACCEPT);
        review3.submit("feedback", Grade.REJECT);
        review4.submit("feedback", Grade.REJECT);
        
        // Check consensus
        boolean result = paper.areReviewsUniform();
        
        // Verify expected output
        assertFalse("Reviews are split 2 ACCEPT vs 2 REJECT - consensus should be false", result);
    }
}