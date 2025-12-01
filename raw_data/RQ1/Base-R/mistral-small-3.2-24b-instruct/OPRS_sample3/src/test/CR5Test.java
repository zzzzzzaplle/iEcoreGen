import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Test Case 1: "All acceptances"
        // Setup: Create Reviewer R006 and assign 5 reviews with ACCEPT grades
        Reviewer reviewer = new Reviewer("R006");
        
        // Create 5 papers and add ACCEPT reviews for each
        for (int i = 1; i <= 5; i++) {
            Paper paper = new Paper("Paper" + i, true);
            Review review = new Review(paper, "Good paper", Grade.ACCEPT);
            reviewer.addReview(review);
        }
        
        // Expected Output: 1.00
        double expected = 1.00;
        double actual = reviewer.calculateNumericalAverageScore();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase2_balanced5050Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        Reviewer reviewer = new Reviewer("R007");
        
        // Add 3 ACCEPT reviews
        for (int i = 1; i <= 3; i++) {
            Paper paper = new Paper("PaperA" + i, true);
            Review review = new Review(paper, "Acceptable", Grade.ACCEPT);
            reviewer.addReview(review);
        }
        
        // Add 3 REJECT reviews
        for (int i = 1; i <= 3; i++) {
            Paper paper = new Paper("PaperR" + i, true);
            Review review = new Review(paper, "Needs improvement", Grade.REJECT);
            reviewer.addReview(review);
        }
        
        // Expected Output: 0.50
        double expected = 0.50;
        double actual = reviewer.calculateNumericalAverageScore();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Setup: Create Reviewer R008 with no completed reviews
        Reviewer reviewer = new Reviewer("R008");
        
        // Expected Output: 0.00
        double expected = 0.00;
        double actual = reviewer.calculateNumericalAverageScore();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Setup: Create Reviewer R009 and assign 1 ACCEPT, 4 REJECT reviews
        Reviewer reviewer = new Reviewer("R009");
        
        // Add 1 ACCEPT review
        Paper paper1 = new Paper("Paper1", true);
        Review review1 = new Review(paper1, "Good work", Grade.ACCEPT);
        reviewer.addReview(review1);
        
        // Add 4 REJECT reviews
        for (int i = 2; i <= 5; i++) {
            Paper paper = new Paper("Paper" + i, true);
            Review review = new Review(paper, "Not suitable", Grade.REJECT);
            reviewer.addReview(review);
        }
        
        // Expected Output: 0.20
        double expected = 0.20;
        double actual = reviewer.calculateNumericalAverageScore();
        assertEquals(expected, actual, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Test Case 5: "Single review case"
        // Setup: Create Reviewer R010 and assign 1 REJECT review
        Reviewer reviewer = new Reviewer("R010");
        
        Paper paper = new Paper("SinglePaper", true);
        Review review = new Review(paper, "Rejected", Grade.REJECT);
        reviewer.addReview(review);
        
        // Expected Output: 0.00
        double expected = 0.00;
        double actual = reviewer.calculateNumericalAverageScore();
        assertEquals(expected, actual, 0.001);
    }
}