import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
    }
    
    @Test
    public void testCase1_allAcceptances() {
        // Test Case 1: "All acceptances"
        // Setup: Create Reviewer R006 and assign 5 reviews with ACCEPT grades
        
        reviewer.setName("R006");
        List<Review> reviews = new ArrayList<>();
        
        // Create 5 reviews with ACCEPT grades
        for (int i = 0; i < 5; i++) {
            Review review = new Review();
            review.setGrade(Review.Grade.ACCEPT);
            review.setSubmitted(true);
            reviews.add(review);
        }
        
        reviewer.setAssignedReviews(reviews);
        
        // Calculate average grade
        double result = reviewer.calculateAverageGrade();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_balanced50_50Ratio() {
        // Test Case 2: "Balanced 50-50 ratio"
        // Setup: Create Reviewer R007 and assign 3 ACCEPT, 3 REJECT reviews
        
        reviewer.setName("R007");
        List<Review> reviews = new ArrayList<>();
        
        // Create 3 reviews with ACCEPT grades
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade(Review.Grade.ACCEPT);
            review.setSubmitted(true);
            reviews.add(review);
        }
        
        // Create 3 reviews with REJECT grades
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade(Review.Grade.REJECT);
            review.setSubmitted(true);
            reviews.add(review);
        }
        
        reviewer.setAssignedReviews(reviews);
        
        // Calculate average grade
        double result = reviewer.calculateAverageGrade();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noCompletedReviews() {
        // Test Case 3: "No completed reviews"
        // Setup: Create Reviewer R008 with no completed reviews
        
        reviewer.setName("R008");
        
        // No reviews assigned, so list is empty by default
        
        // Calculate average grade
        double result = reviewer.calculateAverageGrade();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_recentRejectTendency() {
        // Test Case 4: "Recent reject tendency"
        // Setup: Create Reviewer R009 and assign 1 ACCEPT, 4 REJECT reviews
        
        reviewer.setName("R009");
        List<Review> reviews = new ArrayList<>();
        
        // Create 1 review with ACCEPT grade
        Review acceptReview = new Review();
        acceptReview.setGrade(Review.Grade.ACCEPT);
        acceptReview.setSubmitted(true);
        reviews.add(acceptReview);
        
        // Create 4 reviews with REJECT grades
        for (int i = 0; i < 4; i++) {
            Review review = new Review();
            review.setGrade(Review.Grade.REJECT);
            review.setSubmitted(true);
            reviews.add(review);
        }
        
        reviewer.setAssignedReviews(reviews);
        
        // Calculate average grade
        double result = reviewer.calculateAverageGrade();
        
        // Expected Output: 0.20
        assertEquals(0.20, result, 0.001);
    }
    
    @Test
    public void testCase5_singleReviewCase() {
        // Test Case 5: "Single review case"
        // Setup: Create Reviewer R010 and assign 1 REJECT review
        
        reviewer.setName("R010");
        List<Review> reviews = new ArrayList<>();
        
        // Create 1 review with REJECT grade
        Review review = new Review();
        review.setGrade(Review.Grade.REJECT);
        review.setSubmitted(true);
        reviews.add(review);
        
        reviewer.setAssignedReviews(reviews);
        
        // Calculate average grade
        double result = reviewer.calculateAverageGrade();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
}