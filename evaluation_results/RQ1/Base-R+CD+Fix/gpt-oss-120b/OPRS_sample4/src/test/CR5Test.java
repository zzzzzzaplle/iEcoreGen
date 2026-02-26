import org.junit.Test;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Test Case 1: "All acceptances"
        // Setup: Create Reviewer R006 and assign 5 reviews with ACCEPT grades
        Reviewer reviewer = new Reviewer();
        
        // Create 5 papers and assign reviews with ACCEPT grades
        for (int i = 0; i < 5; i++) {
            Paper paper = new Paper("Paper " + i, true);
            Review review = new Review(reviewer, paper);
            review.setGrade(Review.Grade.ACCEPT);
            review.setFeedback("Excellent paper " + i);
            review.submit();
            reviewer.assignReview(review);
        }
        
        // Expected Output: 1.00
        double expected = 1.00;
        double actual = reviewer.historicalAverageScore();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        Reviewer reviewer = new Reviewer();
        
        // Assign 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper("Accept Paper " + i, true);
            Review review = new Review(reviewer, paper);
            review.setGrade(Review.Grade.ACCEPT);
            review.setFeedback("Good paper " + i);
            review.submit();
            reviewer.assignReview(review);
        }
        
        // Assign 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper("Reject Paper " + i, true);
            Review review = new Review(reviewer, paper);
            review.setGrade(Review.Grade.REJECT);
            review.setFeedback("Needs improvement " + i);
            review.submit();
            reviewer.assignReview(review);
        }
        
        // Expected Output: 0.50
        double expected = 0.50;
        double actual = reviewer.historicalAverageScore();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Setup: Create Reviewer R008 (no completed reviews)
        Reviewer reviewer = new Reviewer();
        
        // Expected Output: 0.00
        double expected = 0.00;
        double actual = reviewer.historicalAverageScore();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Setup: Create Reviewer R009 and assign 1 ACCEPT, 4 REJECT reviews
        Reviewer reviewer = new Reviewer();
        
        // Assign 1 ACCEPT review
        Paper acceptPaper = new Paper("Accept Paper", true);
        Review acceptReview = new Review(reviewer, acceptPaper);
        acceptReview.setGrade(Review.Grade.ACCEPT);
        acceptReview.setFeedback("Good paper");
        acceptReview.submit();
        reviewer.assignReview(acceptReview);
        
        // Assign 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            Paper paper = new Paper("Reject Paper " + i, true);
            Review review = new Review(reviewer, paper);
            review.setGrade(Review.Grade.REJECT);
            review.setFeedback("Needs work " + i);
            review.submit();
            reviewer.assignReview(review);
        }
        
        // Expected Output: 0.20
        double expected = 0.20;
        double actual = reviewer.historicalAverageScore();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Test Case 5: "Single review case"
        // Setup: Create Reviewer R010 and assign 1 REJECT review
        Reviewer reviewer = new Reviewer();
        
        Paper paper = new Paper("Single Paper", true);
        Review review = new Review(reviewer, paper);
        review.setGrade(Review.Grade.REJECT);
        review.setFeedback("Poor quality");
        review.submit();
        reviewer.assignReview(review);
        
        // Expected Output: 0.00
        double expected = 0.00;
        double actual = reviewer.historicalAverageScore();
        assertEquals(expected, actual, 0.001);
    }
}