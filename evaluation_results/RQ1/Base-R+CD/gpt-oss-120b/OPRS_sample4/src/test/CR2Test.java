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
        Paper paper = new Paper("P14", PaperType.RESEARCH);
        
        // Create 3 review assignments with ACCEPT grades
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(new ReviewAssignment("revise", Grade.ACCEPT));
        reviews.add(new ReviewAssignment("revise", Grade.ACCEPT));
        reviews.add(new ReviewAssignment("revise", Grade.ACCEPT));
        
        paper.setReviews(reviews);
        
        // Check consensus - should return true for unanimous ACCEPT
        boolean result = paper.isAllReviewsPositive();
        assertTrue("Paper with 3 unanimous ACCEPT reviews should return true", result);
    }
    
    @Test
    public void testCase2_SplitDecision2_1() {
        // Test Case 2: "Split decision 2-1"
        // Setup: Create Paper P15 with 2 reviewers giving mixed grades (1 ACCEPT, 1 REJECT)
        Paper paper = new Paper("P15", PaperType.RESEARCH);
        
        // Create 2 review assignments with mixed grades
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(new ReviewAssignment("good paper", Grade.ACCEPT));
        reviews.add(new ReviewAssignment("needs improvement", Grade.REJECT));
        
        paper.setReviews(reviews);
        
        // Check consensus - should return false for mixed grades
        boolean result = paper.isAllReviewsPositive();
        assertFalse("Paper with mixed ACCEPT/REJECT reviews should return false", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup: Create Paper P16 with 4 reviewers all giving REJECT grades
        Paper paper = new Paper("P16", PaperType.RESEARCH);
        
        // Create 4 review assignments with REJECT grades
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(new ReviewAssignment("poor methodology", Grade.REJECT));
        reviews.add(new ReviewAssignment("insufficient data", Grade.REJECT));
        reviews.add(new ReviewAssignment("weak conclusions", Grade.REJECT));
        reviews.add(new ReviewAssignment("not novel", Grade.REJECT));
        
        paper.setReviews(reviews);
        
        // Check consensus - should return true for unanimous REJECT
        boolean result = paper.isAllReviewsPositive();
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup: Create Paper P17 with 3 reviewers: 1 ACCEPT, 1 REJECT, 1 UNDECIDED
        Paper paper = new Paper("P17", PaperType.RESEARCH);
        
        // Create 3 review assignments with mixed grades including UNDECIDED
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(new ReviewAssignment("excellent work", Grade.ACCEPT));
        reviews.add(new ReviewAssignment("major flaws", Grade.REJECT));
        reviews.add(new ReviewAssignment(null, Grade.UNDECIDED)); // Reviewer hasn't submitted feedback
        
        paper.setReviews(reviews);
        
        // Check consensus - should return false for mixed grades (ACCEPT and REJECT)
        boolean result = paper.isAllReviewsPositive();
        assertFalse("Paper with mixed ACCEPT/REJECT reviews (including UNDECIDED) should return false", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup: Create Paper P18 with 4 reviewers: 2 ACCEPT, 2 REJECT
        Paper paper = new Paper("P18", PaperType.RESEARCH);
        
        // Create 4 review assignments with exactly 2 ACCEPT and 2 REJECT
        List<ReviewAssignment> reviews = new ArrayList<>();
        reviews.add(new ReviewAssignment("well-written", Grade.ACCEPT));
        reviews.add(new ReviewAssignment("good contribution", Grade.ACCEPT));
        reviews.add(new ReviewAssignment("limited scope", Grade.REJECT));
        reviews.add(new ReviewAssignment("weak evaluation", Grade.REJECT));
        
        paper.setReviews(reviews);
        
        // Check consensus - should return false for split decision (2-2)
        boolean result = paper.isAllReviewsPositive();
        assertFalse("Paper with 2 ACCEPT and 2 REJECT reviews should return false", result);
    }
}