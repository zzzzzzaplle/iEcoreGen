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
        // Initialize common objects for tests
        reviews = new ArrayList<>();
    }
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Create Paper P14
        paper = new Paper();
        
        // Create 3 reviewers and assign paper P14 to them
        // 3 reviewers give reviews: "grade:ACCEPT,feedback:'revise'"
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
        
        // Check consensus for Paper P14 - Expected Output: True
        assertTrue("All 3 reviews are ACCEPT - should return true", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Create Paper P15
        paper = new Paper();
        
        // Create 2 reviewers and assign paper P15 to them
        // 2 reviewers give reviews: 1 with ACCEPT, 1 with REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("good paper");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("needs major revisions");
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        paper.setReviews(reviews);
        
        // Check consensus for Paper P15 - Expected Output: False
        assertFalse("Reviews are split ACCEPT/REJECT - should return false", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Create Paper P16
        paper = new Paper();
        
        // Create 4 reviewers and assign paper P16 to them
        // 4 reviewers give the same reviews: grade=REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.REJECT);
        review1.setFeedback("poor methodology");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("insufficient data");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("weak conclusions");
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("not novel enough");
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paper.setReviews(reviews);
        
        // Check consensus for Paper P16 - Expected Output: True
        assertTrue("All 4 reviews are REJECT - should return true", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Create Paper P17
        paper = new Paper();
        
        // Create 3 reviewers and assign paper P17 to them
        // 2 reviewers have given feedback and grade: 1 ACCEPT, 1 REJECT
        // 1 reviewer do not give feedback to the paper (UNDECIDED)
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("well-written");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("flawed analysis");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Reviewer hasn't submitted review yet
        review3.setFeedback(null);
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paper.setReviews(reviews);
        
        // Check consensus for Paper P17 - Expected Output: False
        assertFalse("Reviews are mixed (ACCEPT, REJECT, UNDECIDED) - should return false", paper.isAllReviewsSame());
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Create Paper P18
        paper = new Paper();
        
        // Create 4 reviewers and assign paper P18 to them
        // 4 reviewers give reviews: 2 ACCEPT, 2 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("strong contribution");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("good results");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("limited scope");
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("methodology issues");
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paper.setReviews(reviews);
        
        // Check consensus for Paper P18 - Expected Output: False
        assertFalse("Reviews are split 2 ACCEPT / 2 REJECT - should return false", paper.isAllReviewsSame());
    }
}