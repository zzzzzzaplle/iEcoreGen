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
            Paper paper = new Paper("Paper" + i, PaperType.RESEARCH);
            Review review = new Review(reviewer, paper, "Good paper", Grade.ACCEPT);
            reviewer.getReviews().add(review);
        }
        
        // Expected Output: 1.00
        double result = reviewer.getAverageGradeScore();
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Setup: Create Reviewer R007
        Reviewer reviewer = new Reviewer("R007");
        
        // Assign 3 ACCEPT, 3 REJECT reviews to 6 papers
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper("PaperA" + i, PaperType.RESEARCH);
            Review review = new Review(reviewer, paper, "Good paper", Grade.ACCEPT);
            reviewer.getReviews().add(review);
        }
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper("PaperR" + i, PaperType.RESEARCH);
            Review review = new Review(reviewer, paper, "Needs improvement", Grade.REJECT);
            reviewer.getReviews().add(review);
        }
        
        // Expected Output: 0.50
        double result = reviewer.getAverageGradeScore();
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Setup: Create Reviewer R008 (no completed reviews)
        Reviewer reviewer = new Reviewer("R008");
        
        // Expected Output: 0.00
        double result = reviewer.getAverageGradeScore();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Setup: Create Reviewer R009
        Reviewer reviewer = new Reviewer("R009");
        
        // Assign 1 ACCEPT, 4 REJECT reviews to 5 papers
        Paper paper1 = new Paper("Paper1", PaperType.RESEARCH);
        Review review1 = new Review(reviewer, paper1, "Good paper", Grade.ACCEPT);
        reviewer.getReviews().add(review1);
        
        for (int i = 0; i < 4; i++) {
            Paper paper = new Paper("PaperR" + i, PaperType.RESEARCH);
            Review review = new Review(reviewer, paper, "Needs improvement", Grade.REJECT);
            reviewer.getReviews().add(review);
        }
        
        // Expected Output: 0.20
        double result = reviewer.getAverageGradeScore();
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Setup: Create Reviewer R010
        Reviewer reviewer = new Reviewer("R010");
        
        // Assign 1 REJECT review to 1 paper
        Paper paper = new Paper("Paper1", PaperType.RESEARCH);
        Review review = new Review(reviewer, paper, "Needs improvement", Grade.REJECT);
        reviewer.getReviews().add(review);
        
        // Expected Output: 0.00
        double result = reviewer.getAverageGradeScore();
        assertEquals(0.00, result, 0.001);
    }
}