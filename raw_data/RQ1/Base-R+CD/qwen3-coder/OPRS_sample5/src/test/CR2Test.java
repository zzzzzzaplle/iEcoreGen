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
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14
        paper = new Paper();
        paper.setTitle("P14");
        
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
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paper.setReviews(reviews);
        
        // Expected Output: True
        assertTrue("Paper with 3 unanimous ACCEPT reviews should return true", 
                   paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Setup: Create Paper P15
        paper = new Paper();
        paper.setTitle("P15");
        
        // Create 2 reviewers and assign paper P15 to them
        // 2 reviewers give reviews: 1 with ACCEPT, 1 with REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("feedback1");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("feedback2");
        
        reviews.add(review1);
        reviews.add(review2);
        paper.setReviews(reviews);
        
        // Expected Output: False
        assertFalse("Paper with split decision (1 ACCEPT, 1 REJECT) should return false", 
                    paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create Paper P16
        paper = new Paper();
        paper.setTitle("P16");
        
        // Create 4 reviewers and assign paper P16 to 4 reviewers
        // 4 reviewers give the same reviews: grade=REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.REJECT);
        review1.setFeedback("reject1");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("reject2");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("reject3");
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("reject4");
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paper.setReviews(reviews);
        
        // Expected Output: True
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", 
                   paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Setup: Create Paper P17
        paper = new Paper();
        paper.setTitle("P17");
        
        // Create 3 reviewers and assign paper P17 to 3 reviewers
        // 2 reviewers have given feedback and grade: 1 ACCEPT, 1 REJECT
        // 1 reviewer do not give feedback to the paper (grade remains UNDECIDED)
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("accept");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("reject");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED); // Default value, no feedback given
        review3.setFeedback(null);
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paper.setReviews(reviews);
        
        // Expected Output: False
        assertFalse("Paper with mixed grades (ACCEPT, REJECT, UNDECIDED) should return false", 
                    paper.isAllReviewsPositive());
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18
        paper = new Paper();
        paper.setTitle("P18");
        
        // Create 4 reviewers and assign paper P18 to 4 reviewers
        // 4 reviewers give reviews: 2 ACCEPT, 2 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("accept1");
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("accept2");
        
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("reject1");
        
        ReviewAssignment review4 = new ReviewAssignment();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("reject2");
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paper.setReviews(reviews);
        
        // Expected Output: False
        assertFalse("Paper with exactly 50% acceptance (2 ACCEPT, 2 REJECT) should return false", 
                    paper.isAllReviewsPositive());
    }
}