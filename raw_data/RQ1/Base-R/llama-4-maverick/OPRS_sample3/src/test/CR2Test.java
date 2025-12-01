import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Paper paper;
    private List<Review> reviews;
    
    @Before
    public void setUp() {
        paper = new Paper();
        reviews = new ArrayList<>();
    }
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14
        paper.setTitle("P14");
        
        // Create 3 reviews with ACCEPT grade
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("revise");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade("Accept");
        review2.setFeedback("revise");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setGrade("Accept");
        review3.setFeedback("revise");
        review3.setSubmitted(true);
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paper.setReviews(reviews);
        
        // Test: Check consensus for Paper P14
        boolean result = paper.checkReviews();
        
        // Verify: Expected output is True
        assertTrue("Unanimous accept from 3 reviews should return true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Setup: Create Paper P15
        paper.setTitle("P15");
        
        // Create 2 reviews: 1 ACCEPT, 1 REJECT
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setSubmitted(true);
        
        reviews.add(review1);
        reviews.add(review2);
        paper.setReviews(reviews);
        
        // Test: Check consensus for Paper P15
        boolean result = paper.checkReviews();
        
        // Verify: Expected output is False
        assertFalse("Split decision 2-1 should return false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Setup: Create Paper P16
        paper.setTitle("P16");
        
        // Create 4 reviews with REJECT grade
        Review review1 = new Review();
        review1.setGrade("Reject");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setGrade("Reject");
        review3.setSubmitted(true);
        
        Review review4 = new Review();
        review4.setGrade("Reject");
        review4.setSubmitted(true);
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paper.setReviews(reviews);
        
        // Test: Check consensus for Paper P16
        boolean result = paper.checkReviews();
        
        // Verify: Expected output is True
        assertTrue("All reject with 4 reviews should return true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Setup: Create Paper P17
        paper.setTitle("P17");
        
        // Create 3 reviews: 1 ACCEPT, 1 REJECT, 1 without grade/feedback
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setSubmitted(false); // Reviewer did not give feedback
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paper.setReviews(reviews);
        
        // Test: Check consensus for Paper P17
        boolean result = paper.checkReviews();
        
        // Verify: Expected output is False
        assertFalse("Mixed grades with 3 reviews should return false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18
        paper.setTitle("P18");
        
        // Create 4 reviews: 2 ACCEPT, 2 REJECT
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade("Accept");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setGrade("Reject");
        review3.setSubmitted(true);
        
        Review review4 = new Review();
        review4.setGrade("Reject");
        review4.setSubmitted(true);
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paper.setReviews(reviews);
        
        // Test: Check consensus for Paper P18
        boolean result = paper.checkReviews();
        
        // Verify: Expected output is False
        assertFalse("Exactly 50% acceptance should return false", result);
    }
}