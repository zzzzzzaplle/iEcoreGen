import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR2Test {
    
    private ReviewSystem reviewSystem;
    
    @Before
    public void setUp() {
        reviewSystem = new ReviewSystem();
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14
        Paper paperP14 = new Paper();
        paperP14.setTitle("P14");
        
        // Setup: Create 3 reviewers
        User reviewer1 = new User();
        User reviewer2 = new User();
        User reviewer3 = new User();
        
        // Setup: Assign paper P14 to 3 reviewers
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("revise");
        review1.setSubmitted(true);
        paperP14.addReview(review1);
        reviewer1.addReview(review1);
        
        Review review2 = new Review();
        review2.setGrade("Accept");
        review2.setFeedback("revise");
        review2.setSubmitted(true);
        paperP14.addReview(review2);
        reviewer2.addReview(review2);
        
        Review review3 = new Review();
        review3.setGrade("Accept");
        review3.setFeedback("revise");
        review3.setSubmitted(true);
        paperP14.addReview(review3);
        reviewer3.addReview(review3);
        
        // Execute: Check consensus for Paper P14
        boolean result = reviewSystem.areReviewsConsistent(paperP14);
        
        // Verify: Expected Output: True
        assertTrue("All reviews should be Accept - expected true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Setup: Create Paper P15
        Paper paperP15 = new Paper();
        paperP15.setTitle("P15");
        
        // Setup: Create 2 reviewers
        User reviewer1 = new User();
        User reviewer2 = new User();
        
        // Setup: Assign paper P15 to 2 reviewers
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        paperP15.addReview(review1);
        reviewer1.addReview(review1);
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        paperP15.addReview(review2);
        reviewer2.addReview(review2);
        
        // Execute: Check consensus for Paper P15
        boolean result = reviewSystem.areReviewsConsistent(paperP15);
        
        // Verify: Expected Output: False
        assertFalse("Reviews are mixed Accept/Reject - expected false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create Paper P16
        Paper paperP16 = new Paper();
        paperP16.setTitle("P16");
        
        // Setup: Create 4 reviewers
        User reviewer1 = new User();
        User reviewer2 = new User();
        User reviewer3 = new User();
        User reviewer4 = new User();
        
        // Setup: Assign paper P16 to 4 reviewers
        Review review1 = new Review();
        review1.setGrade("Reject");
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        paperP16.addReview(review1);
        reviewer1.addReview(review1);
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        paperP16.addReview(review2);
        reviewer2.addReview(review2);
        
        Review review3 = new Review();
        review3.setGrade("Reject");
        review3.setFeedback("feedback3");
        review3.setSubmitted(true);
        paperP16.addReview(review3);
        reviewer3.addReview(review3);
        
        Review review4 = new Review();
        review4.setGrade("Reject");
        review4.setFeedback("feedback4");
        review4.setSubmitted(true);
        paperP16.addReview(review4);
        reviewer4.addReview(review4);
        
        // Execute: Check consensus for Paper P16
        boolean result = reviewSystem.areReviewsConsistent(paperP16);
        
        // Verify: Expected Output: True
        assertTrue("All reviews should be Reject - expected true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Setup: Create Paper P17
        Paper paperP17 = new Paper();
        paperP17.setTitle("P17");
        
        // Setup: Create 3 reviewers
        User reviewer1 = new User();
        User reviewer2 = new User();
        User reviewer3 = new User();
        
        // Setup: Assign paper P17 to 3 reviewers
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        paperP17.addReview(review1);
        reviewer1.addReview(review1);
        
        Review review2 = new Review();
        review2.setGrade("Reject");
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        paperP17.addReview(review2);
        reviewer2.addReview(review2);
        
        // Third reviewer does not submit review (no review added)
        
        // Execute: Check consensus for Paper P17
        boolean result = reviewSystem.areReviewsConsistent(paperP17);
        
        // Verify: Expected Output: False
        assertFalse("Only 2 reviews with mixed Accept/Reject - expected false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18
        Paper paperP18 = new Paper();
        paperP18.setTitle("P18");
        
        // Setup: Create 4 reviewers
        User reviewer1 = new User();
        User reviewer2 = new User();
        User reviewer3 = new User();
        User reviewer4 = new User();
        
        // Setup: Assign paper P18 to 4 reviewers
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        paperP18.addReview(review1);
        reviewer1.addReview(review1);
        
        Review review2 = new Review();
        review2.setGrade("Accept");
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        paperP18.addReview(review2);
        reviewer2.addReview(review2);
        
        Review review3 = new Review();
        review3.setGrade("Reject");
        review3.setFeedback("feedback3");
        review3.setSubmitted(true);
        paperP18.addReview(review3);
        reviewer3.addReview(review3);
        
        Review review4 = new Review();
        review4.setGrade("Reject");
        review4.setFeedback("feedback4");
        review4.setSubmitted(true);
        paperP18.addReview(review4);
        reviewer4.addReview(review4);
        
        // Execute: Check consensus for Paper P18
        boolean result = reviewSystem.areReviewsConsistent(paperP18);
        
        // Verify: Expected Output: False
        assertFalse("2 Accept and 2 Reject - expected false", result);
    }
}