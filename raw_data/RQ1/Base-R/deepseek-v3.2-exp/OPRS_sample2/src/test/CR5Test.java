import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        // Reset reviewer before each test
        reviewer = null;
    }
    
    @Test
    public void testCase1_allAcceptances() {
        // Test Case 1: "All acceptances"
        // Setup: Create Reviewer R006 and assign 5 reviews with ACCEPT grades
        reviewer = new Reviewer();
        reviewer.setName("R006");
        
        // Create 5 papers and 5 ACCEPT reviews
        for (int i = 0; i < 5; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper " + (i + 1));
            
            Review review = new Review();
            review.setGrade(Grade.ACCEPT);
            review.setSubmitted(true);
            review.setPaper(paper);
            review.setReviewer(reviewer);
            
            reviewer.addReview(review);
        }
        
        // Calculate historical grade average
        double result = reviewer.calculateHistoricalGradeAverage();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        reviewer = new Reviewer();
        reviewer.setName("R007");
        
        // Create 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper Accept " + (i + 1));
            
            Review review = new Review();
            review.setGrade(Grade.ACCEPT);
            review.setSubmitted(true);
            review.setPaper(paper);
            review.setReviewer(reviewer);
            
            reviewer.addReview(review);
        }
        
        // Create 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper Reject " + (i + 1));
            
            Review review = new Review();
            review.setGrade(Grade.REJECT);
            review.setSubmitted(true);
            review.setPaper(paper);
            review.setReviewer(reviewer);
            
            reviewer.addReview(review);
        }
        
        // Calculate historical grade average
        double result = reviewer.calculateHistoricalGradeAverage();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Setup: Create Reviewer R008 with no completed reviews
        reviewer = new Reviewer();
        reviewer.setName("R008");
        
        // No reviews added - all reviews are unsubmitted by default
        
        // Calculate historical grade average
        double result = reviewer.calculateHistoricalGradeAverage();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Setup: Create Reviewer R009 and assign 1 ACCEPT, 4 REJECT reviews
        reviewer = new Reviewer();
        reviewer.setName("R009");
        
        // Create 1 ACCEPT review
        Paper paper1 = new Paper();
        paper1.setTitle("Paper Accept 1");
        
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setSubmitted(true);
        review1.setPaper(paper1);
        review1.setReviewer(reviewer);
        
        reviewer.addReview(review1);
        
        // Create 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            Paper paper = new Paper();
            paper.setTitle("Paper Reject " + (i + 1));
            
            Review review = new Review();
            review.setGrade(Grade.REJECT);
            review.setSubmitted(true);
            review.setPaper(paper);
            review.setReviewer(reviewer);
            
            reviewer.addReview(review);
        }
        
        // Calculate historical grade average
        double result = reviewer.calculateHistoricalGradeAverage();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Test Case 5: "Single review case"
        // Setup: Create Reviewer R010 and assign 1 REJECT review
        reviewer = new Reviewer();
        reviewer.setName("R010");
        
        // Create 1 REJECT review
        Paper paper = new Paper();
        paper.setTitle("Single Paper");
        
        Review review = new Review();
        review.setGrade(Grade.REJECT);
        review.setSubmitted(true);
        review.setPaper(paper);
        review.setReviewer(reviewer);
        
        reviewer.addReview(review);
        
        // Calculate historical grade average
        double result = reviewer.calculateHistoricalGradeAverage();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}