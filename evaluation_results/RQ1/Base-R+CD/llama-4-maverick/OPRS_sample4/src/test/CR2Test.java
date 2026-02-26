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
        // Create Paper P14
        paper = new Paper();
        paper.setTitle("P14");
        
        // Create 3 review assignments with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paper.setReviews(reviews);
        
        // Check if all reviews are same (should be true for unanimous accept)
        assertTrue("All 3 reviews should be ACCEPT - expected true", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Create Paper P15
        paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 review assignments: 1 ACCEPT, 1 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        paper.setReviews(reviews);
        
        // Check if all reviews are same (should be false for split decision)
        assertFalse("Reviews are split ACCEPT/REJECT - expected false", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Create Paper P16
        paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 review assignments with REJECT grade
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.REJECT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paper.setReviews(reviews);
        
        // Check if all reviews are same (should be true for unanimous reject)
        assertTrue("All 4 reviews should be REJECT - expected true", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase4_mixedGradesWith3ReviewsOneUndecided() {
        // Create Paper P17
        paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 review assignments: 1 ACCEPT, 1 REJECT, 1 UNDECIDED (no feedback)
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Reviewer hasn't submitted feedback
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paper.setReviews(reviews);
        
        // Check if all reviews are same (should be false for mixed grades including UNDECIDED)
        assertFalse("Reviews are mixed ACCEPT/REJECT/UNDECIDED - expected false", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Create Paper P18
        paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 review assignments: 2 ACCEPT, 2 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paper.setReviews(reviews);
        
        // Check if all reviews are same (should be false for 50-50 split)
        assertFalse("Reviews are split 2 ACCEPT vs 2 REJECT - expected false", paper.isAllReviewsSame());
    }
}