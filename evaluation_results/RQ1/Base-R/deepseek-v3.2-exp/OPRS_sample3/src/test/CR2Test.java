import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private ReviewSystemService reviewSystemService;
    
    @Before
    public void setUp() {
        reviewSystemService = new ReviewSystemService();
    }
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14
        Paper paperP14 = new Paper();
        paperP14.setTitle("P14");
        
        // Create 3 reviewers
        User reviewer1 = new User();
        reviewer1.setName("Reviewer1");
        User reviewer2 = new User();
        reviewer2.setName("Reviewer2");
        User reviewer3 = new User();
        reviewer3.setName("Reviewer3");
        
        // Create 3 reviews with ACCEPT grade and 'revise' feedback
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        review1.setSubmitted(true);
        review1.setReviewer(reviewer1);
        review1.setPaper(paperP14);
        
        Review review2 = new Review();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        review2.setSubmitted(true);
        review2.setReviewer(reviewer2);
        review2.setPaper(paperP14);
        
        Review review3 = new Review();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        review3.setSubmitted(true);
        review3.setReviewer(reviewer3);
        review3.setPaper(paperP14);
        
        // Add reviews to paper
        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paperP14.setReviews(reviews);
        
        // Test: Check if reviews are unanimous
        boolean result = reviewSystemService.areReviewsUnanimous(paperP14);
        
        // Verify: Expected output is True
        assertTrue("All 3 reviews are ACCEPT - should return true", result);
    }
    
    @Test
    public void testCase2_splitDecision2To1() {
        // Setup: Create Paper P15
        Paper paperP15 = new Paper();
        paperP15.setTitle("P15");
        
        // Create 2 reviewers
        User reviewer1 = new User();
        reviewer1.setName("Reviewer1");
        User reviewer2 = new User();
        reviewer2.setName("Reviewer2");
        
        // Create 2 reviews: 1 ACCEPT and 1 REJECT
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        review1.setReviewer(reviewer1);
        review1.setPaper(paperP15);
        
        Review review2 = new Review();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        review2.setReviewer(reviewer2);
        review2.setPaper(paperP15);
        
        // Add reviews to paper
        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        paperP15.setReviews(reviews);
        
        // Test: Check if reviews are unanimous
        boolean result = reviewSystemService.areReviewsUnanimous(paperP15);
        
        // Verify: Expected output is False
        assertFalse("Mixed grades (ACCEPT and REJECT) - should return false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Setup: Create Paper P16
        Paper paperP16 = new Paper();
        paperP16.setTitle("P16");
        
        // Create 4 reviewers
        User reviewer1 = new User();
        reviewer1.setName("Reviewer1");
        User reviewer2 = new User();
        reviewer2.setName("Reviewer2");
        User reviewer3 = new User();
        reviewer3.setName("Reviewer3");
        User reviewer4 = new User();
        reviewer4.setName("Reviewer4");
        
        // Create 4 reviews with REJECT grade
        Review review1 = new Review();
        review1.setGrade(Grade.REJECT);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        review1.setReviewer(reviewer1);
        review1.setPaper(paperP16);
        
        Review review2 = new Review();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        review2.setReviewer(reviewer2);
        review2.setPaper(paperP16);
        
        Review review3 = new Review();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("feedback3");
        review3.setSubmitted(true);
        review3.setReviewer(reviewer3);
        review3.setPaper(paperP16);
        
        Review review4 = new Review();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("feedback4");
        review4.setSubmitted(true);
        review4.setReviewer(reviewer4);
        review4.setPaper(paperP16);
        
        // Add reviews to paper
        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paperP16.setReviews(reviews);
        
        // Test: Check if reviews are unanimous
        boolean result = reviewSystemService.areReviewsUnanimous(paperP16);
        
        // Verify: Expected output is True
        assertTrue("All 4 reviews are REJECT - should return true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Setup: Create Paper P17
        Paper paperP17 = new Paper();
        paperP17.setTitle("P17");
        
        // Create 3 reviewers
        User reviewer1 = new User();
        reviewer1.setName("Reviewer1");
        User reviewer2 = new User();
        reviewer2.setName("Reviewer2");
        User reviewer3 = new User();
        reviewer3.setName("Reviewer3");
        
        // Create 2 reviews: 1 ACCEPT and 1 REJECT (1 reviewer hasn't given feedback)
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        review1.setReviewer(reviewer1);
        review1.setPaper(paperP17);
        
        Review review2 = new Review();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        review2.setReviewer(reviewer2);
        review2.setPaper(paperP17);
        
        // Third review exists but has no grade/feedback (not submitted)
        Review review3 = new Review();
        review3.setSubmitted(false); // Not submitted - no grade assigned
        review3.setReviewer(reviewer3);
        review3.setPaper(paperP17);
        
        // Add reviews to paper
        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paperP17.setReviews(reviews);
        
        // Test: Check if reviews are unanimous
        boolean result = reviewSystemService.areReviewsUnanimous(paperP17);
        
        // Verify: Expected output is False
        assertFalse("Mixed grades (ACCEPT and REJECT) - should return false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18
        Paper paperP18 = new Paper();
        paperP18.setTitle("P18");
        
        // Create 4 reviewers
        User reviewer1 = new User();
        reviewer1.setName("Reviewer1");
        User reviewer2 = new User();
        reviewer2.setName("Reviewer2");
        User reviewer3 = new User();
        reviewer3.setName("Reviewer3");
        User reviewer4 = new User();
        reviewer4.setName("Reviewer4");
        
        // Create 4 reviews: 2 ACCEPT and 2 REJECT
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        review1.setReviewer(reviewer1);
        review1.setPaper(paperP18);
        
        Review review2 = new Review();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        review2.setReviewer(reviewer2);
        review2.setPaper(paperP18);
        
        Review review3 = new Review();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("feedback3");
        review3.setSubmitted(true);
        review3.setReviewer(reviewer3);
        review3.setPaper(paperP18);
        
        Review review4 = new Review();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("feedback4");
        review4.setSubmitted(true);
        review4.setReviewer(reviewer4);
        review4.setPaper(paperP18);
        
        // Add reviews to paper
        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paperP18.setReviews(reviews);
        
        // Test: Check if reviews are unanimous
        boolean result = reviewSystemService.areReviewsUnanimous(paperP18);
        
        // Verify: Expected output is False
        assertFalse("Mixed grades (2 ACCEPT and 2 REJECT) - should return false", result);
    }
}