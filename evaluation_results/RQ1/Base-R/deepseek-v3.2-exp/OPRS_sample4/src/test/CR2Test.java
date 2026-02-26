import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Test Case 1: "Unanimous accept from 3 reviews"
        // Setup
        Paper paper = new Paper();
        paper.setTitle("P14");
        
        List<Review> reviews = new ArrayList<>();
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Create 3 reviews with ACCEPT grade
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        review1.setReviewer(reviewer1);
        review1.setPaper(paper);
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("revise");
        review2.setReviewer(reviewer2);
        review2.setPaper(paper);
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setGrade(Grade.ACCEPT);
        review3.setFeedback("revise");
        review3.setReviewer(reviewer3);
        review3.setPaper(paper);
        review3.setSubmitted(true);
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paper.setReviews(reviews);
        
        // Execute method under test
        boolean result = paper.hasUnanimousReviews();
        
        // Verify expected output
        assertTrue("All reviews should be unanimous ACCEPT", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Test Case 2: "Split decision 2-1"
        // Setup
        Paper paper = new Paper();
        paper.setTitle("P15");
        
        List<Review> reviews = new ArrayList<>();
        
        // Create 2 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        
        // Create 2 reviews: 1 ACCEPT, 1 REJECT
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("revise");
        review1.setReviewer(reviewer1);
        review1.setPaper(paper);
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("reject");
        review2.setReviewer(reviewer2);
        review2.setPaper(paper);
        review2.setSubmitted(true);
        
        reviews.add(review1);
        reviews.add(review2);
        paper.setReviews(reviews);
        
        // Execute method under test
        boolean result = paper.hasUnanimousReviews();
        
        // Verify expected output
        assertFalse("Reviews should not be unanimous with split decision", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Test Case 3: "All reject with 4 reviews"
        // Setup
        Paper paper = new Paper();
        paper.setTitle("P16");
        
        List<Review> reviews = new ArrayList<>();
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        // Create 4 reviews with REJECT grade
        Review review1 = new Review();
        review1.setGrade(Grade.REJECT);
        review1.setFeedback("reject");
        review1.setReviewer(reviewer1);
        review1.setPaper(paper);
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("reject");
        review2.setReviewer(reviewer2);
        review2.setPaper(paper);
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("reject");
        review3.setReviewer(reviewer3);
        review3.setPaper(paper);
        review3.setSubmitted(true);
        
        Review review4 = new Review();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("reject");
        review4.setReviewer(reviewer4);
        review4.setPaper(paper);
        review4.setSubmitted(true);
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paper.setReviews(reviews);
        
        // Execute method under test
        boolean result = paper.hasUnanimousReviews();
        
        // Verify expected output
        assertTrue("All reviews should be unanimous REJECT", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Test Case 4: "Mixed grades with 3 reviews"
        // Setup
        Paper paper = new Paper();
        paper.setTitle("P17");
        
        List<Review> reviews = new ArrayList<>();
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Create 2 reviews: 1 ACCEPT, 1 REJECT (third reviewer doesn't give feedback)
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("accept");
        review1.setReviewer(reviewer1);
        review1.setPaper(paper);
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade(Grade.REJECT);
        review2.setFeedback("reject");
        review2.setReviewer(reviewer2);
        review2.setPaper(paper);
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setGrade(null); // Third reviewer hasn't given grade
        review3.setReviewer(reviewer3);
        review3.setPaper(paper);
        review3.setSubmitted(false); // Not submitted
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        paper.setReviews(reviews);
        
        // Execute method under test
        boolean result = paper.hasUnanimousReviews();
        
        // Verify expected output
        assertFalse("Reviews should not be unanimous with mixed grades and incomplete reviews", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Test Case 5: "Edge case: exactly 50% acceptance"
        // Setup
        Paper paper = new Paper();
        paper.setTitle("P18");
        
        List<Review> reviews = new ArrayList<>();
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        // Create 4 reviews: 2 ACCEPT, 2 REJECT
        Review review1 = new Review();
        review1.setGrade(Grade.ACCEPT);
        review1.setFeedback("accept");
        review1.setReviewer(reviewer1);
        review1.setPaper(paper);
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade(Grade.ACCEPT);
        review2.setFeedback("accept");
        review2.setReviewer(reviewer2);
        review2.setPaper(paper);
        review2.setSubmitted(true);
        
        Review review3 = new Review();
        review3.setGrade(Grade.REJECT);
        review3.setFeedback("reject");
        review3.setReviewer(reviewer3);
        review3.setPaper(paper);
        review3.setSubmitted(true);
        
        Review review4 = new Review();
        review4.setGrade(Grade.REJECT);
        review4.setFeedback("reject");
        review4.setReviewer(reviewer4);
        review4.setPaper(paper);
        review4.setSubmitted(true);
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        paper.setReviews(reviews);
        
        // Execute method under test
        boolean result = paper.hasUnanimousReviews();
        
        // Verify expected output
        assertFalse("Reviews should not be unanimous with 50-50 split", result);
    }
}