import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Paper paperP14;
    private Paper paperP15;
    private Paper paperP16;
    private Paper paperP17;
    private Paper paperP18;
    
    @Before
    public void setUp() {
        // Initialize papers for test cases
        paperP14 = new Paper();
        paperP14.setTitle("P14");
        
        paperP15 = new Paper();
        paperP15.setTitle("P15");
        
        paperP16 = new Paper();
        paperP16.setTitle("P16");
        
        paperP17 = new Paper();
        paperP17.setTitle("P17");
        
        paperP18 = new Paper();
        paperP18.setTitle("P18");
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14
        Paper paper = paperP14;
        
        // Create 3 reviewers and their review assignments
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 3 review assignments with ACCEPT grade
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
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Execute: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Expected output is True
        assertTrue("All 3 reviews should be ACCEPT, so result should be true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2to1() {
        // Setup: Create Paper P15
        Paper paper = paperP15;
        
        // Create 2 reviewers and their review assignments
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 2 review assignments: 1 ACCEPT, 1 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Execute: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Expected output is False
        assertFalse("Reviews have mixed grades (ACCEPT and REJECT), so result should be false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create Paper P16
        Paper paper = paperP16;
        
        // Create 4 reviewers and their review assignments
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 4 review assignments with REJECT grade
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
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Execute: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Expected output is True
        assertTrue("All 4 reviews should be REJECT, so result should be true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Setup: Create Paper P17
        Paper paper = paperP17;
        
        // Create 3 reviewers and their review assignments
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 2 review assignments with grades: 1 ACCEPT, 1 REJECT
        ReviewAssignment review1 = new ReviewAssignment();
        review1.setGrade(Grade.ACCEPT);
        reviews.add(review1);
        
        ReviewAssignment review2 = new ReviewAssignment();
        review2.setGrade(Grade.REJECT);
        reviews.add(review2);
        
        // 1 reviewer does not give feedback (UNDECIDED grade)
        ReviewAssignment review3 = new ReviewAssignment();
        review3.setGrade(Grade.UNDECIDED);
        reviews.add(review3);
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Execute: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Expected output is False
        assertFalse("Submitted reviews have mixed grades (ACCEPT and REJECT), so result should be false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18
        Paper paper = paperP18;
        
        // Create 4 reviewers and their review assignments
        List<ReviewAssignment> reviews = new ArrayList<>();
        
        // Create 4 review assignments: 2 ACCEPT, 2 REJECT
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
        
        // Assign reviews to paper
        paper.setReviews(reviews);
        
        // Execute: Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        
        // Verify: Expected output is False
        assertFalse("Reviews have mixed grades (2 ACCEPT, 2 REJECT), so result should be false", result);
    }
}