import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup paper P14
        Paper paperP14 = new Paper();
        paperP14.setTitle("P14");
        
        // Create 3 reviewers
        User reviewer1 = new User();
        User reviewer2 = new User();
        User reviewer3 = new User();
        
        // Create 3 reviews with ACCEPT grade
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        review1.setSubmitted(true);
        review1.setPaper(paperP14);
        
        Review review2 = new Review();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        review2.setSubmitted(true);
        review2.setPaper(paperP14);
        
        Review review3 = new Review();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        review3.setSubmitted(true);
        review3.setPaper(paperP14);
        
        // Add reviews to paper
        paperP14.getReviews().add(review1);
        paperP14.getReviews().add(review2);
        paperP14.getReviews().add(review3);
        
        // Check if reviews are unanimous
        boolean result = ReviewSystem.areReviewsUnanimous(paperP14);
        
        // Expected Output: True
        assertTrue("All 3 reviews should be ACCEPT, making them unanimous", result);
    }
    
    @Test
    public void testCase2_SplitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Setup paper P15
        Paper paperP15 = new Paper();
        paperP15.setTitle("P15");
        
        // Create 2 reviewers (as specified in the test case)
        User reviewer1 = new User();
        User reviewer2 = new User();
        
        // Create 2 reviews: 1 ACCEPT, 1 REJECT
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setSubmitted(true);
        review1.setPaper(paperP15);
        
        Review review2 = new Review();
        review2.setGrade(Grade.REJECT);
        review2.setSubmitted(true);
        review2.setPaper(paperP15);
        
        // Add reviews to paper
        paperP15.getReviews().add(review1);
        paperP15.getReviews().add(review2);
        
        // Check if reviews are unanimous
        boolean result = ReviewSystem.areReviewsUnanimous(paperP15);
        
        // Expected Output: False
        assertFalse("Reviews have split decision (1 ACCEPT, 1 REJECT)", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup paper P16
        Paper paperP16 = new Paper();
        paperP16.setTitle("P16");
        
        // Create 4 reviewers
        User reviewer1 = new User();
        User reviewer2 = new User();
        User reviewer3 = new User();
        User reviewer4 = new User();
        
        // Create 4 reviews with REJECT grade
        Review review1 = new Review();
        review1.setGrade(Grade.REJECT);
        review1.setSubmitted(true);
        review1.setPaper(paperP16);
        
        Review review2 = new Review();
        review2.setGrade(Grade.REJECT);
        review2.setSubmitted(true);
        review2.setPaper(paperP16);
        
        Review review3 = new Review();
        review3.setGrade(Grade.REJECT);
        review3.setSubmitted(true);
        review3.setPaper(paperP16);
        
        Review review4 = new Review();
        review4.setGrade(Grade.REJECT);
        review4.setSubmitted(true);
        review4.setPaper(paperP16);
        
        // Add reviews to paper
        paperP16.getReviews().add(review1);
        paperP16.getReviews().add(review2);
        paperP16.getReviews().add(review3);
        paperP16.getReviews().add(review4);
        
        // Check if reviews are unanimous
        boolean result = ReviewSystem.areReviewsUnanimous(paperP16);
        
        // Expected Output: True
        assertTrue("All 4 reviews should be REJECT, making them unanimous", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup paper P17
        Paper paperP17 = new Paper();
        paperP17.setTitle("P17");
        
        // Create 3 reviewers
        User reviewer1 = new User();
        User reviewer2 = new User();
        User reviewer3 = new User();
        
        // Create 2 submitted reviews: 1 ACCEPT, 1 REJECT
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setSubmitted(true);
        review1.setPaper(paperP17);
        
        Review review2 = new Review();
        review2.setGrade(Grade.REJECT);
        review2.setSubmitted(true);
        review2.setPaper(paperP17);
        
        // Create 1 unsubmitted review
        Review review3 = new Review();
        review3.setSubmitted(false); // Not submitted
        review3.setPaper(paperP17);
        
        // Add reviews to paper
        paperP17.getReviews().add(review1);
        paperP17.getReviews().add(review2);
        paperP17.getReviews().add(review3);
        
        // Check if reviews are unanimous
        boolean result = ReviewSystem.areReviewsUnanimous(paperP17);
        
        // Expected Output: False
        assertFalse("Reviews have mixed grades (1 ACCEPT, 1 REJECT, 1 unsubmitted)", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup paper P18
        Paper paperP18 = new Paper();
        paperP18.setTitle("P18");
        
        // Create 4 reviewers
        User reviewer1 = new User();
        User reviewer2 = new User();
        User reviewer3 = new User();
        User reviewer4 = new User();
        
        // Create 4 reviews: 2 ACCEPT, 2 REJECT
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setSubmitted(true);
        review1.setPaper(paperP18);
        
        Review review2 = new Review();
        review2.setGrade(Grade.ACCEPT);
        review2.setSubmitted(true);
        review2.setPaper(paperP18);
        
        Review review3 = new Review();
        review3.setGrade(Grade.REJECT);
        review3.setSubmitted(true);
        review3.setPaper(paperP18);
        
        Review review4 = new Review();
        review4.setGrade(Grade.REJECT);
        review4.setSubmitted(true);
        review4.setPaper(paperP18);
        
        // Add reviews to paper
        paperP18.getReviews().add(review1);
        paperP18.getReviews().add(review2);
        paperP18.getReviews().add(review3);
        paperP18.getReviews().add(review4);
        
        // Check if reviews are unanimous
        boolean result = ReviewSystem.areReviewsUnanimous(paperP18);
        
        // Expected Output: False
        assertFalse("Reviews are split 50-50 (2 ACCEPT, 2 REJECT)", result);
    }
    

}