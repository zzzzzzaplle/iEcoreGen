import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Setup: Create Reviewer R006
        Reviewer reviewer = new Reviewer("R006");
        
        // Assign 5 reviews to 5 papers with ACCEPT grades
        for (int i = 0; i < 5; i++) {
            Paper paper = new Paper("Paper" + i, true);
            Review review = new Review(paper);
            review.setGrade(Grade.Accept);
            review.setSubmitted(true);
            reviewer.addReview(review);
        }
        
        // Calculate rating tendency
        double result = reviewer.calculateAverageGrade();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced5050Ratio() {
        // Setup: Create Reviewer R007
        Reviewer reviewer = new Reviewer("R007");
        
        // Assign 3 ACCEPT, 3 REJECT reviews to 6 papers
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper("PaperA" + i, true);
            Review review = new Review(paper);
            review.setGrade(Grade.Accept);
            review.setSubmitted(true);
            reviewer.addReview(review);
        }
        
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper("PaperR" + i, true);
            Review review = new Review(paper);
            review.setGrade(Grade.Reject);
            review.setSubmitted(true);
            reviewer.addReview(review);
        }
        
        // Calculate rating tendency
        double result = reviewer.calculateAverageGrade();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Setup: Create Reviewer R008 (no completed reviews)
        Reviewer reviewer = new Reviewer("R008");
        
        // Calculate rating tendency
        double result = reviewer.calculateAverageGrade();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Setup: Create Reviewer R009
        Reviewer reviewer = new Reviewer("R009");
        
        // Assign 1 ACCEPT, 4 REJECT reviews to 5 papers
        Paper paper1 = new Paper("Paper1", true);
        Review review1 = new Review(paper1);
        review1.setGrade(Grade.Accept);
        review1.setSubmitted(true);
        reviewer.addReview(review1);
        
        for (int i = 0; i < 4; i++) {
            Paper paper = new Paper("PaperR" + i, true);
            Review review = new Review(paper);
            review.setGrade(Grade.Reject);
            review.setSubmitted(true);
            reviewer.addReview(review);
        }
        
        // Calculate rating tendency
        double result = reviewer.calculateAverageGrade();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Setup: Create Reviewer R010
        Reviewer reviewer = new Reviewer("R010");
        
        // Assign 1 REJECT review to 1 paper
        Paper paper = new Paper("Paper1", true);
        Review review = new Review(paper);
        review.setGrade(Grade.Reject);
        review.setSubmitted(true);
        reviewer.addReview(review);
        
        // Calculate rating tendency
        double result = reviewer.calculateAverageGrade();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}