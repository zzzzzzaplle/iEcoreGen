import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup: Create Paper P14 with 3 reviewers all giving ACCEPT grades
        Paper paper = new Paper("P14", true);
        
        // Create 3 reviews with ACCEPT grade
        Review review1 = new Review("revise", "ACCEPT");
        Review review2 = new Review("revise", "ACCEPT");
        Review review3 = new Review("revise", "ACCEPT");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check consensus - Expected Output: True
        assertTrue("All reviews should be consistent (ACCEPT)", paper.areReviewsConsistent());
    }
    
    @Test
    public void testCase2_splitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15 with 2 reviewers giving different grades
        Paper paper = new Paper("P15", true);
        
        // Create 2 reviews with different grades
        Review review1 = new Review("good paper", "ACCEPT");
        Review review2 = new Review("needs improvement", "REJECT");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        
        // Check consensus - Expected Output: False
        assertFalse("Reviews should not be consistent (mixed decisions)", paper.areReviewsConsistent());
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16 with 4 reviewers all giving REJECT grades
        Paper paper = new Paper("P16", true);
        
        // Create 4 reviews with REJECT grade
        Review review1 = new Review("poor methodology", "REJECT");
        Review review2 = new Review("insufficient data", "REJECT");
        Review review3 = new Review("weak conclusions", "REJECT");
        Review review4 = new Review("limited contribution", "REJECT");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check consensus - Expected Output: True
        assertTrue("All reviews should be consistent (REJECT)", paper.areReviewsConsistent());
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17 with 3 reviewers - 1 ACCEPT, 1 REJECT, 1 empty grade
        Paper paper = new Paper("P17", true);
        
        // Create 3 reviews with mixed grades (including one with empty grade)
        Review review1 = new Review("well-written", "ACCEPT");
        Review review2 = new Review("needs major revisions", "REJECT");
        Review review3 = new Review(); // Empty grade and feedback
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check consensus - Expected Output: False
        assertFalse("Reviews should not be consistent (mixed decisions including empty grade)", 
                     paper.areReviewsConsistent());
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18 with 4 reviewers - 2 ACCEPT, 2 REJECT
        Paper paper = new Paper("P18", true);
        
        // Create 4 reviews with 2 ACCEPT and 2 REJECT
        Review review1 = new Review("strong contribution", "ACCEPT");
        Review review2 = new Review("good methodology", "ACCEPT");
        Review review3 = new Review("limited novelty", "REJECT");
        Review review4 = new Review("narrow scope", "REJECT");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check consensus - Expected Output: False
        assertFalse("Reviews should not be consistent (50-50 split)", paper.areReviewsConsistent());
    }
}