import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private CoChair coChair;
    
    @Before
    public void setUp() {
        // Initialize CoChair before each test
        coChair = new CoChair();
    }
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Create Paper P14
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Create 3 reviews with ACCEPT grade and feedback
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        review3.setSubmitted(true);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check if reviews are unanimous
        boolean result = coChair.areReviewsUnanimous(paper);
        
        // Expected Output: True
        assertTrue("All 3 reviews have ACCEPT grade, should return true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviewers (as specified in test case)
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        
        // Create 2 reviews: 1 ACCEPT, 1 REJECT
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade(Grade.REJECT);
        review2.setSubmitted(true);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        
        // Check if reviews are unanimous
        boolean result = coChair.areReviewsUnanimous(paper);
        
        // Expected Output: False
        assertFalse("Mixed grades (ACCEPT and REJECT), should return false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
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
        review1.setGrade(Grade.REJECT);
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade(Grade.REJECT);
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setGrade(Grade.REJECT);
        review3.setSubmitted(true);
        
        Review review4 = new Review();
        review4.setGrade(Grade.REJECT);
        review4.setSubmitted(true);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check if reviews are unanimous
        boolean result = coChair.areReviewsUnanimous(paper);
        
        // Expected Output: True
        assertTrue("All 4 reviews have REJECT grade, should return true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3ReviewsOneMissingFeedback() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Create 2 reviews: 1 ACCEPT, 1 REJECT (one reviewer didn't give feedback)
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade(Grade.REJECT);
        review2.setSubmitted(true);
        
        // Third review exists but not submitted (no grade assigned yet)
        Review review3 = new Review();
        review3.setSubmitted(false); // Not submitted, so grade is null
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        
        // Check if reviews are unanimous
        boolean result = coChair.areReviewsUnanimous(paper);
        
        // Expected Output: False
        // The method compares grades, and with one review having null grade, it won't match the others
        assertFalse("Mixed grades and one review not submitted (null grade), should return false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
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
        review1.setGrade(Grade.ACCEPT);
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade(Grade.ACCEPT);
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setGrade(Grade.REJECT);
        review3.setSubmitted(true);
        
        Review review4 = new Review();
        review4.setGrade(Grade.REJECT);
        review4.setSubmitted(true);
        
        // Add reviews to paper
        paper.addReview(review1);
        paper.addReview(review2);
        paper.addReview(review3);
        paper.addReview(review4);
        
        // Check if reviews are unanimous
        boolean result = coChair.areReviewsUnanimous(paper);
        
        // Expected Output: False
        assertFalse("Mixed grades (2 ACCEPT, 2 REJECT), should return false", result);
    }
}