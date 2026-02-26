import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    @Test
    public void testCase1_allAcceptances() {
        // Test Case 1: "All acceptances"
        // Setup: Create Reviewer R006 and assign 5 reviews to 5 papers with ACCEPT grades
        
        // Create reviewer
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R006");
        
        // Create 5 papers and assign ACCEPT reviews
        for (int i = 1; i <= 5; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper" + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setGrade("Accept");
            review.setFeedback("Excellent paper " + i);
            
            paper.addReview(review);
            reviewer.addAssignedPaper(paper);
        }
        
        // Calculate average score
        double result = reviewer.calculateAverageScore();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews to 6 papers
        
        // Create reviewer
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R007");
        
        // Create 3 ACCEPT reviews
        for (int i = 1; i <= 3; i++) {
            Paper paper = new Paper();
            paper.setTitle("AcceptPaper" + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setGrade("Accept");
            review.setFeedback("Good paper " + i);
            
            paper.addReview(review);
            reviewer.addAssignedPaper(paper);
        }
        
        // Create 3 REJECT reviews
        for (int i = 1; i <= 3; i++) {
            Paper paper = new Paper();
            paper.setTitle("RejectPaper" + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setGrade("Reject");
            review.setFeedback("Needs improvement " + i);
            
            paper.addReview(review);
            reviewer.addAssignedPaper(paper);
        }
        
        // Calculate average score
        double result = reviewer.calculateAverageScore();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Setup: Create Reviewer R008 with no completed reviews
        
        // Create reviewer
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R008");
        
        // Create papers but don't assign any reviews with grades
        for (int i = 1; i <= 3; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper" + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            // No grade set (null)
            review.setFeedback("Pending review " + i);
            
            paper.addReview(review);
            reviewer.addAssignedPaper(paper);
        }
        
        // Calculate average score
        double result = reviewer.calculateAverageScore();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Setup: Create Reviewer R009 and assign 1 ACCEPT, 4 REJECT reviews to 5 papers
        
        // Create reviewer
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R009");
        
        // Create 1 ACCEPT review
        Paper acceptPaper = new Paper();
        acceptPaper.setTitle("AcceptPaper");
        
        Review acceptReview = new Review();
        acceptReview.setReviewer(reviewer);
        acceptReview.setGrade("Accept");
        acceptReview.setFeedback("Good paper");
        
        acceptPaper.addReview(acceptReview);
        reviewer.addAssignedPaper(acceptPaper);
        
        // Create 4 REJECT reviews
        for (int i = 1; i <= 4; i++) {
            Paper paper = new Paper();
            paper.setTitle("RejectPaper" + i);
            
            Review review = new Review();
            review.setReviewer(reviewer);
            review.setGrade("Reject");
            review.setFeedback("Needs work " + i);
            
            paper.addReview(review);
            reviewer.addAssignedPaper(paper);
        }
        
        // Calculate average score
        double result = reviewer.calculateAverageScore();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Test Case 5: "Single review case"
        // Setup: Create Reviewer R010 and assign 1 REJECT review to 1 paper
        
        // Create reviewer
        Reviewer reviewer = new Reviewer();
        reviewer.setName("R010");
        
        // Create 1 REJECT review
        Paper paper = new Paper();
        paper.setTitle("SinglePaper");
        
        Review review = new Review();
        review.setReviewer(reviewer);
        review.setGrade("Reject");
        review.setFeedback("Not acceptable");
        
        paper.addReview(review);
        reviewer.addAssignedPaper(paper);
        
        // Calculate average score
        double result = reviewer.calculateAverageScore();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}