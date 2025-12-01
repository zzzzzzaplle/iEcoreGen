import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 review assignments with ACCEPT grade
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        reviews.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        reviews.add(review3);
        
        paper.setReviews(reviews);
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("All 3 reviews should be ACCEPT (unanimous accept)", result);
    }
    
    @Test
    public void testCase2_SplitDecision2to1() {
        // Setup: Create Paper P15
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 review assignments: 1 ACCEPT, 1 REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        paper.setReviews(reviews);
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Reviews are split between ACCEPT and REJECT", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create Paper P16
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 review assignments with REJECT grade
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.REJECT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        reviews.add(review3);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        reviews.add(review4);
        
        paper.setReviews(reviews);
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: True
        assertTrue("All 4 reviews should be REJECT (unanimous reject)", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Setup: Create Paper P17
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 review assignments: 1 ACCEPT, 1 REJECT, 1 UNDECIDED
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Reviewer hasn't given feedback
        reviews.add(review3);
        
        paper.setReviews(reviews);
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Reviews have mixed grades (ACCEPT, REJECT, UNDECIDED)", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 review assignments: 2 ACCEPT, 2 REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        reviews.add(review2);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        reviews.add(review3);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        reviews.add(review4);
        
        paper.setReviews(reviews);
        
        // Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Expected Output: False
        assertFalse("Reviews are split equally between ACCEPT and REJECT (50-50)", result);
    }
}