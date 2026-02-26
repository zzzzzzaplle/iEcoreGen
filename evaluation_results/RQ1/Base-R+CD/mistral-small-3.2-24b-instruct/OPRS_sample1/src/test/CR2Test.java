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
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Create Paper P14
        paper = new Paper("P14", PaperType.RESEARCH);
        
        // Create 3 review assignments with ACCEPT grade
        ReviewAssignment review1 = new ReviewAssignment("revise");
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment("revise");
        review2.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review3 = new ReviewAssignment("revise");
        review3.setGrade(Grade.ACCEPT);
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paper.setReviews(reviews);
        
        // Check if all reviews are exclusively Accept
        boolean result = paper.isAllReviewsPositive();
        assertTrue("Paper with 3 unanimous ACCEPT reviews should return true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Create Paper P15
        paper = new Paper("P15", PaperType.RESEARCH);
        
        // Create 2 review assignments with mixed grades (1 ACCEPT, 1 REJECT)
        ReviewAssignment review1 = new ReviewAssignment("good paper");
        review1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment review2 = new ReviewAssignment("needs improvement");
        review2.setGrade(Grade.REJECT);
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        paper.setReviews(reviews);
        
        // Check if all reviews are exclusively Accept or exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        assertFalse("Paper with mixed ACCEPT/REJECT reviews should return false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Create Paper P16
        paper = new Paper("P16", PaperType.RESEARCH);
        
        // Create 4 review assignments with REJECT grade
        ReviewAssignment review1 = new ReviewAssignment("poor methodology");
        review1.setGrade(Grade.REJECT);
        
        ReviewAssignment review2 = new ReviewAssignment("insufficient data");
        review2.setGrade(Grade.REJECT);
        
        ReviewAssignment review3 = new ReviewAssignment("weak conclusions");
        review3.setGrade(Grade.REJECT);
        
        ReviewAssignment review4 = new ReviewAssignment("not novel");
        review4.setGrade(Grade.REJECT);
        
        // Add reviews to paper
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paper.setReviews(reviews);
        
        // Check if all reviews are exclusively Reject
        boolean result = paper.isAllReviewsPositive();
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Create Paper P17
        paper = new Paper("P17", PaperType.RESEARCH);
        
        // Create 3 review assignments: 1 ACCEPT, 1 REJECT, 1 UNDECIDED
        ReviewAssignment review1 = new ReviewAssignment("well written");
        review1.setGrade(G