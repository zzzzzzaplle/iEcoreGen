import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Test Case 1: "All acceptances"
        // Create Reviewer R006
        Reviewer reviewer = new Reviewer("R006");
        
        // Assign 5 reviews to 5 papers with ACCEPT grades, respectively
        for (int i = 1; i <= 5; i++) {
            Paper paper = new Paper("Paper" + i, true);
            Review review = new Review("Feedback for Paper" + i, "Accept");
            paper.addReview(review);
            reviewer.addAssignedPaper(paper);
        }
        
        // Expected Output: 1.00
        double result = reviewer.calculateAverageScore();
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Create Reviewer R007
        Reviewer reviewer = new Reviewer("R007");
        
        // Assign 3 ACCEPT, 3 REJECT reviews to 6 papers, respectively
        for (int i = 1; i <= 3; i++) {
            Paper paper = new Paper("AcceptPaper" + i, true);
            Review review = new Review("Feedback for AcceptPaper" + i, "Accept");
            paper.addReview(review);
            reviewer.addAssignedPaper(paper);
        }
        
        for (int i = 1; i <= 3; i++) {
            Paper paper = new Paper("RejectPaper" + i, true);
            Review review = new Review("Feedback for RejectPaper" + i, "Reject");
            paper.addReview(review);
            reviewer.addAssignedPaper(paper);
        }
        
        // Expected Output: 0.50
        double result = reviewer.calculateAverageScore();
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Create Reviewer R008 (no completed reviews)
        Reviewer reviewer = new Reviewer("R008");
        
        // Expected Output: 0.00
        double result = reviewer.calculateAverageScore();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Create Reviewer R009
        Reviewer reviewer = new Reviewer("R009");
        
        // Assign 1 ACCEPT, 4 REJECT reviews to 5 papers, respectively
        Paper acceptPaper = new Paper("AcceptPaper", true);
        Review acceptReview = new Review("Feedback for AcceptPaper", "Accept");
        acceptPaper.addReview(acceptReview);
        reviewer.addAssignedPaper(acceptPaper);
        
        for (int i = 1; i <= 4; i++) {
            Paper paper = new Paper("RejectPaper" + i, true);
            Review review = new Review("Feedback for RejectPaper" + i, "Reject");
            paper.addReview(review);
            reviewer.addAssignedPaper(paper);
        }
        
        // Expected Output: 0.20
        double result = reviewer.calculateAverageScore();
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Test Case 5: "Single review case"
        // Create Reviewer R010
        Reviewer reviewer = new Reviewer("R010");
        
        // Assign 1 REJECT review to 1 paper
        Paper paper = new Paper("SinglePaper", true);
        Review review = new Review("Feedback for SinglePaper", "Reject");
        paper.addReview(review);
        reviewer.addAssignedPaper(paper);
        
        // Expected Output: 0.00
        double result = reviewer.calculateAverageScore();
        assertEquals(0.00, result, 0.001);
    }
}