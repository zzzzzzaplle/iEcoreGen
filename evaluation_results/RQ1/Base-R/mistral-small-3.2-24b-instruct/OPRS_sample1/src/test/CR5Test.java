import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    private ReviewSystem reviewSystem;
    
    @Before
    public void setUp() {
        reviewSystem = new ReviewSystem();
    }
    
    @Test
    public void testCase1_allAcceptances() {
        // Create Reviewer R006
        User reviewer = new User();
        reviewer.setName("R006");
        
        // Assign 5 reviews to 5 papers with ACCEPT grades
        for (int i = 0; i < 5; i++) {
            Review review = new Review();
            review.setGrade("Accept");
            review.setSubmitted(true);
            reviewer.addReview(review);
        }
        
        // Calculate rating tendency and verify expected output
        double result = reviewSystem.calculateReviewerScore(reviewer);
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Create Reviewer R007
        User reviewer = new User();
        reviewer.setName("R007");
        
        // Assign 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade("Accept");
            review.setSubmitted(true);
            reviewer.addReview(review);
        }
        
        // Assign 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade("Reject");
            review.setSubmitted(true);
            reviewer.addReview(review);
        }
        
        // Calculate rating tendency and verify expected output
        double result = reviewSystem.calculateReviewerScore(reviewer);
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Create Reviewer R008 (no completed reviews)
        User reviewer = new User();
        reviewer.setName("R008");
        
        // Calculate rating tendency and verify expected output
        double result = reviewSystem.calculateReviewerScore(reviewer);
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Create Reviewer R009
        User reviewer = new User();
        reviewer.setName("R009");
        
        // Assign 1 ACCEPT review
        Review acceptReview = new Review();
        acceptReview.setGrade("Accept");
        acceptReview.setSubmitted(true);
        reviewer.addReview(acceptReview);
        
        // Assign 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            Review rejectReview = new Review();
            rejectReview.setGrade("Reject");
            rejectReview.setSubmitted(true);
            reviewer.addReview(rejectReview);
        }
        
        // Calculate rating tendency and verify expected output
        double result = reviewSystem.calculateReviewerScore(reviewer);
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Create Reviewer R010
        User reviewer = new User();
        reviewer.setName("R010");
        
        // Assign 1 REJECT review to 1 paper
        Review review = new Review();
        review.setGrade("Reject");
        review.setSubmitted(true);
        reviewer.addReview(review);
        
        // Calculate rating tendency and verify expected output
        double result = reviewSystem.calculateReviewerScore(reviewer);
        assertEquals(0.00, result, 0.001);
    }
}