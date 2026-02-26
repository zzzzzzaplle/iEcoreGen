import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    private Chair chair;
    
    @Before
    public void setUp() {
        chair = new Chair();
    }
    
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
        
        // Check consensus - should return true for unanimous accept
        boolean result = chair.checkConsistentReviews(paper);
        assertTrue("All reviews are ACCEPT, should return true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviews: 1 ACCEPT, 1 REJECT
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("good paper");
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setFeedback("needs major revisions");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        
        // Check consensus - should return false for split decision
        boolean result = chair.checkConsistentReviews(paper);
        assertFalse("Reviews are split between ACCEPT and REJECT, should return false", result);
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
        review1.setFeedback("poor methodology");
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setFeedback("insufficient data");
        
        Review review3 = new Review();
        review3.setGrade("Reject");
        review3.setFeedback("weak conclusions");
        
        Review review4 = new Review();
        review4.setGrade("Reject");
        review4.setFeedback("not novel");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check consensus - should return true for unanimous reject
        boolean result = chair.checkConsistentReviews(paper);
        assertTrue("All reviews are REJECT, should return true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviews: 1 ACCEPT, 1 REJECT, 1 without grade (empty)
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("well-written");
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setFeedback("flawed analysis");
        
        Review review3 = new Review();
        review3.setGrade(""); // Empty grade (not submitted)
        review3.setFeedback("");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check consensus - should return false for mixed grades
        boolean result = chair.checkConsistentReviews(paper);
        assertFalse("Reviews have mixed grades (ACCEPT, REJECT, and empty), should return false", result);
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
        review1.setFeedback("strong methodology");
        
        Review review2 = new Review();
        review2.setGrade("Accept");
        review2.setFeedback("good results");
        
        Review review3 = new Review();
        review3.setGrade("Reject");
        review3.setFeedback("limited scope");
        
        Review review4 = new Review();
        review4.setGrade("Reject");
        review4.setFeedback("poor presentation");
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check consensus - should return false for 50-50 split
        boolean result = chair.checkConsistentReviews(paper);
        assertFalse("Reviews are split 2 ACCEPT and 2 REJECT, should return false", result);
    }
}