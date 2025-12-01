import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR5Test {
    
    private ReviewSystemService reviewSystemService;
    
    @Before
    public void setUp() {
        reviewSystemService = new ReviewSystemService();
    }
    
    @Test
    public void testCase1_allAcceptances() {
        // Setup: Create Reviewer R006 with 5 ACCEPT reviews
        User reviewer = new User();
        reviewer.setName("R006");
        
        // Create 5 reviews with ACCEPT grade and mark them as submitted
        for (int i = 0; i < 5; i++) {
            Review review = new Review();
            review.setGrade(Grade.ACCEPT);
            review.setSubmitted(true);
            reviewer.getReviews().add(review);
        }
        
        // Calculate average score
        double result = reviewSystemService.calculateReviewerAverageScore(reviewer);
        
        // Verify expected output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Setup: Create Reviewer R007 with 3 ACCEPT and 3 REJECT reviews
        User reviewer = new User();
        reviewer.setName("R007");
        
        // Add 3 ACCEPT reviews
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade(Grade.ACCEPT);
            review.setSubmitted(true);
            reviewer.getReviews().add(review);
        }
        
        // Add 3 REJECT reviews
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade(Grade.REJECT);
            review.setSubmitted(true);
            reviewer.getReviews().add(review);
        }
        
        // Calculate average score
        double result = reviewSystemService.calculateReviewerAverageScore(reviewer);
        
        // Verify expected output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Setup: Create Reviewer R008 with no reviews
        User reviewer = new User();
        reviewer.setName("R008");
        
        // Calculate average score
        double result = reviewSystemService.calculateReviewerAverageScore(reviewer);
        
        // Verify expected output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Setup: Create Reviewer R009 with 1 ACCEPT and 4 REJECT reviews
        User reviewer = new User();
        reviewer.setName("R009");
        
        // Add 1 ACCEPT review
        Review acceptReview = new Review();
        acceptReview.setGrade(Grade.ACCEPT);
        acceptReview.setSubmitted(true);
        reviewer.getReviews().add(acceptReview);
        
        // Add 4 REJECT reviews
        for (int i = 0; i < 4; i++) {
            Review review = new Review();
            review.setGrade(Grade.REJECT);
            review.setSubmitted(true);
            reviewer.getReviews().add(review);
        }
        
        // Calculate average score
        double result = reviewSystemService.calculateReviewerAverageScore(reviewer);
        
        // Verify expected output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Setup: Create Reviewer R010 with 1 REJECT review
        User reviewer = new User();
        reviewer.setName("R010");
        
        // Add 1 REJECT review
        Review review = new Review();
        review.setGrade(Grade.REJECT);
        review.setSubmitted(true);
        reviewer.getReviews().add(review);
        
        // Calculate average score
        double result = reviewSystemService.calculateReviewerAverageScore(reviewer);
        
        // Verify expected output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}