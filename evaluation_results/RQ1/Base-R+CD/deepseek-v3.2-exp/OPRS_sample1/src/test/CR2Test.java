import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup: Create Paper P14 with 3 reviewers all giving ACCEPT grades
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 review assignments with ACCEPT grades
        List<ReviewAssignment> reviews = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("revise");
            reviews.add(review);
        }
        paper.setReviews(reviews);
        
        // Expected Output: True (all reviews are exclusively Accept)
        assertTrue("All reviews should be ACCEPT", paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15 with 2 reviewers giving mixed grades (1 ACCEPT, 1 REJECT)
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // First reviewer gives ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        // Second reviewer gives REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        paper.setReviews(reviews);
        
        // Expected Output: False (mixed grades)
        assertFalse("Reviews have mixed grades (ACCEPT and REJECT)", paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16 with 4 reviewers all giving REJECT grades
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            reviews.add(review);
        }
        paper.setReviews(reviews);
        
        // Expected Output: True (all reviews are exclusively Reject)
        assertTrue("All reviews should be REJECT", paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17 with 3 reviewers: 1 ACCEPT, 1 REJECT, 1 UNDECIDED
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // First reviewer gives ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        // Second reviewer gives REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        // Third reviewer is UNDECIDED (no feedback given)
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED);
        reviews.add(review3);
        
        paper.setReviews(reviews);
        
        // Expected Output: False (mixed grades including UNDECIDED)
        assertFalse("Reviews have mixed grades including UNDECIDED", paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18 with 4 reviewers: 2 ACCEPT, 2 REJECT
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // 2 reviewers give ACCEPT
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            reviews.add(review);
        }
        
        // 2 reviewers give REJECT
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            reviews.add(review);
        }
        
        paper.setReviews(reviews);
        
        // Expected Output: False (mixed grades - 2 ACCEPT and 2 REJECT)
        assertFalse("Reviews have mixed grades (2 ACCEPT and 2 REJECT)", paper.isAllReviewsPositive());
    }
}