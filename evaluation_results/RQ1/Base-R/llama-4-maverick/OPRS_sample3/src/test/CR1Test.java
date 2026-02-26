import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
    }
    
    @Test
    public void testCase1_ReviewerWith3PendingReviews() {
        // Test Case 1: "Reviewer with 3 pending reviews"
        // Setup: Create Reviewer R001, Papers P1, P2, P3, assign to R001 without feedback and grade
        reviewer.setName("R001");
        
        List<Review> reviews = new ArrayList<>();
        
        // Create 3 reviews without setting submitted status (default is false)
        Review review1 = new Review();
        Review review2 = new Review();
        Review review3 = new Review();
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        
        reviewer.setReviews(reviews);
        
        // Expected Output: 3
        assertEquals(3, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Setup: Create Reviewer R002, Papers P4, P5, assign to R002 with grades=ACCEPT
        reviewer.setName("R002");
        
        List<Review> reviews = new ArrayList<>();
        
        // Create 2 reviews with submitted status set to true
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setSubmitted(true);
        
        Review review2 = new Review();
        review2.setGrade("Accept");
        review2.setSubmitted(true);
        
        reviews.add(review1);
        reviews.add(review2);
        
        reviewer.setReviews(reviews);
        
        // Expected Output: 0
        assertEquals(0, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Setup: Create Reviewer R003, Papers P6-P10, 
        // Assign P6/P7 without feedback and grade, P8-P10 with grade=REJECT
        reviewer.setName("R003");
        
        List<Review> reviews = new ArrayList<>();
        
        // P6 and P7 - not submitted (no grade/feedback set, submitted=false by default)
        Review review1 = new Review();
        Review review2 = new Review();
        
        // P8, P9, P10 - submitted with REJECT grade
        Review review3 = new Review();
        review3.setGrade("Reject");
        review3.setSubmitted(true);
        
        Review review4 = new Review();
        review4.setGrade("Reject");
        review4.setSubmitted(true);
        
        Review review5 = new Review();
        review5.setGrade("Reject");
        review5.setSubmitted(true);
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        reviews.add(review4);
        reviews.add(review5);
        
        reviewer.setReviews(reviews);
        
        // Expected Output: 2
        assertEquals(2, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Setup: Create Reviewer R004 (no assignments)
        reviewer.setName("R004");
        
        // No reviews assigned (empty list)
        reviewer.setReviews(new ArrayList<Review>());
        
        // Expected Output: 0
        assertEquals(0, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Test Case 5: "Partially submitted reviews"
        // Setup: Create Reviewer R005, Papers P11-P13
        // Assign P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        reviewer.setName("R005");
        
        List<Review> reviews = new ArrayList<>();
        
        // P11 - submitted with ACCEPT grade
        Review review1 = new Review();
        review1.setGrade("Accept");
        review1.setSubmitted(true);
        
        // P12 - not submitted (no grade/feedback)
        Review review2 = new Review();
        
        // P13 - submitted with REJECT grade
        Review review3 = new Review();
        review3.setGrade("Reject");
        review3.setSubmitted(true);
        
        reviews.add(review1);
        reviews.add(review2);
        reviews.add(review3);
        
        reviewer.setReviews(reviews);
        
        // Expected Output: 1
        assertEquals(1, reviewer.countUnsubmittedReviews());
    }
}