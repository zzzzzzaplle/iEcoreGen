import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Test Case 1: "All acceptances"
        // Setup: Create Reviewer R006 and assign 5 reviews with ACCEPT grades
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R006");
        
        // Create 5 papers and 5 reviews with ACCEPT grades
        for (int i = 0; i < 5; i++) {
            Paper paper = new Paper("Paper" + i, PaperType.RESEARCH);
            Review review = new Review(reviewer);
            review.setGrade(Grade.ACCEPT);
            review.submit("Good paper", Grade.ACCEPT); // This marks as submitted
            reviewer.assignReview(review);
        }
        
        // Expected Output: 1.00
        double result = reviewer.historicalAverageScore();
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced5050Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R007");
        
        // Create 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper("AcceptPaper" + i, PaperType.RESEARCH);
            Review review = new Review(reviewer);
            review.setGrade(Grade.ACCEPT);
            review.submit("Acceptable", Grade.ACCEPT);
            reviewer.assignReview(review);
        }
        
        // Create 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper("RejectPaper" + i, PaperType.RESEARCH);
            Review review = new Review(reviewer);
            review.setGrade(Grade.REJECT);
            review.submit("Needs improvement", Grade.REJECT);
            reviewer.assignReview(review);
        }
        
        // Expected Output: 0.50
        double result = reviewer.historicalAverageScore();
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Setup: Create Reviewer R008 with no completed reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R008");
        
        // No reviews assigned or all reviews are unsubmitted
        // Expected Output: 0.00
        double result = reviewer.historicalAverageScore();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Setup: Create Reviewer R009 with 1 ACCEPT, 4 REJECT reviews
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R009");
        
        // Create 1 ACCEPT review
        Paper paper1 = new Paper("AcceptPaper", PaperType.RESEARCH);
        Review review1 = new Review(reviewer);
        review1.setGrade(Grade.ACCEPT);
        review1.submit("Good work", Grade.ACCEPT);
        reviewer.assignReview(review1);
        
        // Create 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            Paper paper = new Paper("RejectPaper" + i, PaperType.RESEARCH);
            Review review = new Review(reviewer);
            review.setGrade(Grade.REJECT);
            review.submit("Needs major revision", Grade.REJECT);
            reviewer.assignReview(review);
        }
        
        // Expected Output: 0.20
        double result = reviewer.historicalAverageScore();
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Test Case 5: "Single review case"
        // Setup: Create Reviewer R010 with 1 REJECT review
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R010");
        
        Paper paper = new Paper("SinglePaper", PaperType.RESEARCH);
        Review review = new Review(reviewer);
        review.setGrade(Grade.REJECT);
        review.submit("Not acceptable", Grade.REJECT);
        reviewer.assignReview(review);
        
        // Expected Output: 0.00
        double result = reviewer.historicalAverageScore();
        assertEquals(0.00, result, 0.001);
    }
}