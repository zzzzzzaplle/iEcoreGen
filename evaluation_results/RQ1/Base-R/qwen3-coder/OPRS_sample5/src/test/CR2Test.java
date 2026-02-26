import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup: Create Paper P14, 3 reviewers, assign paper to reviewers, all give ACCEPT grade
        Paper paperP14 = new Paper();
        paperP14.setTitle("P14");
        
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        Review review1 = new Review();
        review1.setReviewer(reviewer1);
        review1.setPaper(paperP14);
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setReviewer(reviewer2);
        review2.setPaper(paperP14);
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setReviewer(reviewer3);
        review3.setPaper(paperP14);
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        review3.setSubmitted(true);
        
        paperP14.addReview(review1);
        paperP14.addReview(review2);
        paperP14.addReview(review3);
        
        // Execute: Check if all reviews are consistent
        boolean result = paperP14.areAllReviewsConsistent();
        
        // Verify: Expected output is True
        assertTrue("All 3 reviews should be consistent (all ACCEPT)", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15, 2 reviewers, assign paper to reviewers, 1 ACCEPT and 1 REJECT
        Paper paperP15 = new Paper();
        paperP15.setTitle("P15");
        
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        
        Review review1 = new Review();
        review1.setReviewer(reviewer1);
        review1.setPaper(paperP15);
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setReviewer(reviewer2);
        review2.setPaper(paperP15);
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        
        paperP15.addReview(review1);
        paperP15.addReview(review2);
        
        // Execute: Check if all reviews are consistent
        boolean result = paperP15.areAllReviewsConsistent();
        
        // Verify: Expected output is False
        assertFalse("Reviews should not be consistent (split decision)", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16, 4 reviewers, assign paper to reviewers, all give REJECT grade
        Paper paperP16 = new Paper();
        paperP16.setTitle("P16");
        
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        Review review1 = new Review();
        review1.setReviewer(reviewer1);
        review1.setPaper(paperP16);
        review1.setGrade(Grade.REJECT);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setReviewer(reviewer2);
        review2.setPaper(paperP16);
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setReviewer(reviewer3);
        review3.setPaper(paperP16);
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("feedback3");
        review3.setSubmitted(true);
        
        Review review4 = new Review();
        review4.setReviewer(reviewer4);
        review4.setPaper(paperP16);
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("feedback4");
        review4.setSubmitted(true);
        
        paperP16.addReview(review1);
        paperP16.addReview(review2);
        paperP16.addReview(review3);
        paperP16.addReview(review4);
        
        // Execute: Check if all reviews are consistent
        boolean result = paperP16.areAllReviewsConsistent();
        
        // Verify: Expected output is True
        assertTrue("All 4 reviews should be consistent (all REJECT)", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17, 3 reviewers, assign paper to reviewers, 1 ACCEPT, 1 REJECT, 1 not submitted
        Paper paperP17 = new Paper();
        paperP17.setTitle("P17");
        
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        Review review1 = new Review();
        review1.setReviewer(reviewer1);
        review1.setPaper(paperP17);
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setReviewer(reviewer2);
        review2.setPaper(paperP17);
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setReviewer(reviewer3);
        review3.setPaper(paperP17);
        review3.setGrade(Grade.ACCEPT); // Grade doesn't matter since not submitted
        review3.setFeedback(""); // No feedback given
        review3.setSubmitted(false); // Not submitted
        
        paperP17.addReview(review1);
        paperP17.addReview(review2);
        paperP17.addReview(review3);
        
        // Execute: Check if all reviews are consistent
        boolean result = paperP17.areAllReviewsConsistent();
        
        // Verify: Expected output is False (only considering submitted reviews: 1 ACCEPT, 1 REJECT)
        assertFalse("Reviews should not be consistent (mixed submitted reviews)", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18, 4 reviewers, assign paper to reviewers, 2 ACCEPT, 2 REJECT
        Paper paperP18 = new Paper();
        paperP18.setTitle("P18");
        
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        Review review1 = new Review();
        review1.setReviewer(reviewer1);
        review1.setPaper(paperP18);
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setReviewer(reviewer2);
        review2.setPaper(paperP18);
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("feedback2");
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setReviewer(reviewer3);
        review3.setPaper(paperP18);
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("feedback3");
        review3.setSubmitted(true);
        
        Review review4 = new Review();
        review4.setReviewer(reviewer4);
        review4.setPaper(paperP18);
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("feedback4");
        review4.setSubmitted(true);
        
        paperP18.addReview(review1);
        paperP18.addReview(review2);
        paperP18.addReview(review3);
        paperP18.addReview(review4);
        
        // Execute: Check if all reviews are consistent
        boolean result = paperP18.areAllReviewsConsistent();
        
        // Verify: Expected output is False (2 ACCEPT vs 2 REJECT)
        assertFalse("Reviews should not be consistent (50-50 split)", result);
    }
}