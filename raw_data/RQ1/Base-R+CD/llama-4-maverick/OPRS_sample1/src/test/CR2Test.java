import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Paper paper;
    private List<ReviewAssignment> reviews;
    
    @Before
    public void setUp() {
        reviews = new ArrayList<>();
    }
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14
        paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 reviewers and assign paper P14 to them
        // 3 reviewers give reviews: "grade:ACCEPT,feedback:'revise'"
        for (int i = 0; i < 3; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("revise");
            reviews.add(review);
        }
        
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: True
        assertTrue("All reviews should be ACCEPT - expected true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Setup: Create Paper P15
        paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviewers and assign paper P15 to them
        // 2 reviewers give reviews: 1 with ACCEPT, 1 with REJECT
        
        // First reviewer: ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("good paper");
        reviews.add(review1);
        
        // Second reviewer: REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("needs improvement");
        reviews.add(review2);
        
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: False
        assertFalse("Reviews have mixed grades (ACCEPT and REJECT) - expected false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Setup: Create Paper P16
        paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 reviewers and assign paper P16 to them
        // 4 reviewers give the same reviews: grade=REJECT
        for (int i = 0; i < 4; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("reject reason " + (i + 1));
            reviews.add(review);
        }
        
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: True
        assertTrue("All reviews should be REJECT - expected true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Setup: Create Paper P17
        paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviewers and assign paper P17 to them
        // 2 reviewers have given feedback and grade: 1 ACCEPT, 1 REJECT
        // 1 reviewer do not give feedback to the paper (UNDECIDED)
        
        // First reviewer: ACCEPT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("acceptable");
        reviews.add(review1);
        
        // Second reviewer: REJECT
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("not acceptable");
        reviews.add(review2);
        
        // Third reviewer: UNDECIDED (no feedback)
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED);
        reviews.add(review3);
        
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: False
        assertFalse("Reviews have mixed grades (ACCEPT, REJECT, and UNDECIDED) - expected false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18
        paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 reviewers and assign paper P18 to them
        // 4 reviewers give reviews: 2 ACCEPT, 2 REJECT
        
        // First 2 reviewers: ACCEPT
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.ACCEPT);
            review.setFeedback("accept " + (i + 1));
            reviews.add(review);
        }
        
        // Next 2 reviewers: REJECT
        for (int i = 0; i < 2; i++) {
            ReviewAssignment review = new ReviewAssignment();
            review.setGrade(Grade.REJECT);
            review.setFeedback("reject " + (i + 1));
            reviews.add(review);
        }
        
        paper.setReviews(reviews);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsSame();
        
        // Expected Output: False
        assertFalse("Reviews have mixed grades (2 ACCEPT and 2 REJECT) - expected false", result);
    }
}