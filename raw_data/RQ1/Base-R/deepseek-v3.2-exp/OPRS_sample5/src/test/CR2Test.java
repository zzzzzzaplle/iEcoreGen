import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Paper paperP14;
    private Paper paperP15;
    private Paper paperP16;
    private Paper paperP17;
    private Paper paperP18;
    
    @Before
    public void setUp() {
        // Initialize papers for each test case
        paperP14 = new Paper();
        paperP14.setTitle("P14");
        
        paperP15 = new Paper();
        paperP15.setTitle("P15");
        
        paperP16 = new Paper();
        paperP16.setTitle("P16");
        
        paperP17 = new Paper();
        paperP17.setTitle("P17");
        
        paperP18 = new Paper();
        paperP18.setTitle("P18");
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Setup: Create 3 reviewers and assign paper P14 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create reviews with ACCEPT grade and feedback
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Review review = new Review();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("revise");
            review.setSubmitted(true);
            review.setReviewer(reviewers.get(i));
            review.setPaper(paperP14);
            reviews.add(review);
        }
        
        // Assign reviews to paper P14
        paperP14.setReviews(reviews);
        
        // Test: Check if all reviews are unanimous (all ACCEPT)
        assertTrue("Paper P14 should have unanimous ACCEPT reviews", paperP14.hasUnanimousReviews());
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Setup: Create 2 reviewers and assign paper P15 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create reviews: 1 ACCEPT, 1 REJECT
        List<Review> reviews = new ArrayList<>();
        
        Review acceptReview = new Review();
        acceptReview.setGrade(Grade.ACCEPT);
        acceptReview.setFeedback("feedback");
        acceptReview.setSubmitted(true);
        acceptReview.setReviewer(reviewers.get(0));
        acceptReview.setPaper(paperP15);
        reviews.add(acceptReview);
        
        Review rejectReview = new Review();
        rejectReview.setGrade(Grade.REJECT);
        rejectReview.setFeedback("feedback");
        rejectReview.setSubmitted(true);
        rejectReview.setReviewer(reviewers.get(1));
        rejectReview.setPaper(paperP15);
        reviews.add(rejectReview);
        
        // Assign reviews to paper P15
        paperP15.setReviews(reviews);
        
        // Test: Check if reviews are NOT unanimous (mixed grades)
        assertFalse("Paper P15 should NOT have unanimous reviews", paperP15.hasUnanimousReviews());
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create 4 reviewers and assign paper P16 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create reviews with REJECT grade
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Review review = new Review();
            review.setGrade(Grade.REJECT);
            review.setFeedback("feedback");
            review.setSubmitted(true);
            review.setReviewer(reviewers.get(i));
            review.setPaper(paperP16);
            reviews.add(review);
        }
        
        // Assign reviews to paper P16
        paperP16.setReviews(reviews);
        
        // Test: Check if all reviews are unanimous (all REJECT)
        assertTrue("Paper P16 should have unanimous REJECT reviews", paperP16.hasUnanimousReviews());
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Setup: Create 3 reviewers and assign paper P17 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create reviews: 1 ACCEPT, 1 REJECT, 1 unsubmitted
        List<Review> reviews = new ArrayList<>();
        
        Review acceptReview = new Review();
        acceptReview.setGrade(Grade.ACCEPT);
        acceptReview.setFeedback("feedback");
        acceptReview.setSubmitted(true);
        acceptReview.setReviewer(reviewers.get(0));
        acceptReview.setPaper(paperP17);
        reviews.add(acceptReview);
        
        Review rejectReview = new Review();
        rejectReview.setGrade(Grade.REJECT);
        rejectReview.setFeedback("feedback");
        rejectReview.setSubmitted(true);
        rejectReview.setReviewer(reviewers.get(1));
        rejectReview.setPaper(paperP17);
        reviews.add(rejectReview);
        
        Review unsubmittedReview = new Review();
        unsubmittedReview.setGrade(Grade.ACCEPT); // Grade doesn't matter since not submitted
        unsubmittedReview.setSubmitted(false);
        unsubmittedReview.setReviewer(reviewers.get(2));
        unsubmittedReview.setPaper(paperP17);
        reviews.add(unsubmittedReview);
        
        // Assign reviews to paper P17
        paperP17.setReviews(reviews);
        
        // Test: Check if reviews are NOT unanimous (mixed submitted grades)
        assertFalse("Paper P17 should NOT have unanimous reviews", paperP17.hasUnanimousReviews());
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create 4 reviewers and assign paper P18 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create reviews: 2 ACCEPT, 2 REJECT
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Review acceptReview = new Review();
            acceptReview.setGrade(Grade.ACCEPT);
            acceptReview.setFeedback("feedback");
            acceptReview.setSubmitted(true);
            acceptReview.setReviewer(reviewers.get(i));
            acceptReview.setPaper(paperP18);
            reviews.add(acceptReview);
        }
        
        for (int i = 2; i < 4; i++) {
            Review rejectReview = new Review();
            rejectReview.setGrade(Grade.REJECT);
            rejectReview.setFeedback("feedback");
            rejectReview.setSubmitted(true);
            rejectReview.setReviewer(reviewers.get(i));
            rejectReview.setPaper(paperP18);
            reviews.add(rejectReview);
        }
        
        // Assign reviews to paper P18
        paperP18.setReviews(reviews);
        
        // Test: Check if reviews are NOT unanimous (50-50 split)
        assertFalse("Paper P18 should NOT have unanimous reviews with 50-50 split", paperP18.hasUnanimousReviews());
    }
}