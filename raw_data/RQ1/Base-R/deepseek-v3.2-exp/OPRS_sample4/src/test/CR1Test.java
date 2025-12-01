import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer;
    private List<Paper> papers;
    private List<Review> reviews;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
        papers = new ArrayList<>();
        reviews = new ArrayList<>();
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Test Case 1: "Reviewer with 3 pending reviews"
        // Setup: Create Reviewer R001, Papers P1, P2, P3 assigned without feedback and grade
        reviewer.setName("R001");
        
        // Create papers
        Paper p1 = new Paper();
        p1.setTitle("P1");
        Paper p2 = new Paper();
        p2.setTitle("P2");
        Paper p3 = new Paper();
        p3.setTitle("P3");
        
        // Create reviews without feedback and grade (not submitted)
        Review rev1 = new Review();
        rev1.setReviewer(reviewer);
        rev1.setPaper(p1);
        rev1.setSubmitted(false);
        
        Review rev2 = new Review();
        rev2.setReviewer(reviewer);
        rev2.setPaper(p2);
        rev2.setSubmitted(false);
        
        Review rev3 = new Review();
        rev3.setReviewer(reviewer);
        rev3.setPaper(p3);
        rev3.setSubmitted(false);
        
        // Add reviews to reviewer
        List<Review> reviewerReviews = new ArrayList<>();
        reviewerReviews.add(rev1);
        reviewerReviews.add(rev2);
        reviewerReviews.add(rev3);
        reviewer.setReviews(reviewerReviews);
        
        // Expected Output: 3
        assertEquals(3, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Setup: Create Reviewer R002, Papers P4, P5 assigned with grades=ACCEPT
        reviewer.setName("R002");
        
        // Create papers
        Paper p4 = new Paper();
        p4.setTitle("P4");
        Paper p5 = new Paper();
        p5.setTitle("P5");
        
        // Create reviews with grades and submitted status
        Review rev4 = new Review();
        rev4.setReviewer(reviewer);
        rev4.setPaper(p4);
        rev4.setGrade(Grade.ACCEPT);
        rev4.setSubmitted(true);
        
        Review rev5 = new Review();
        rev5.setReviewer(reviewer);
        rev5.setPaper(p5);
        rev5.setGrade(Grade.ACCEPT);
        rev5.setSubmitted(true);
        
        // Add reviews to reviewer
        List<Review> reviewerReviews = new ArrayList<>();
        reviewerReviews.add(rev4);
        reviewerReviews.add(rev5);
        reviewer.setReviews(reviewerReviews);
        
        // Expected Output: 0
        assertEquals(0, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Setup: Create Reviewer R003, Papers P6-P10
        // P6/P7 without feedback and grade, P8-P10 with grade=REJECT
        reviewer.setName("R003");
        
        // Create papers P6-P10
        Paper p6 = new Paper();
        p6.setTitle("P6");
        Paper p7 = new Paper();
        p7.setTitle("P7");
        Paper p8 = new Paper();
        p8.setTitle("P8");
        Paper p9 = new Paper();
        p9.setTitle("P9");
        Paper p10 = new Paper();
        p10.setTitle("P10");
        
        // Create reviews for P6, P7 (not submitted)
        Review rev6 = new Review();
        rev6.setReviewer(reviewer);
        rev6.setPaper(p6);
        rev6.setSubmitted(false);
        
        Review rev7 = new Review();
        rev7.setReviewer(reviewer);
        rev7.setPaper(p7);
        rev7.setSubmitted(false);
        
        // Create reviews for P8, P9, P10 (submitted with REJECT grade)
        Review rev8 = new Review();
        rev8.setReviewer(reviewer);
        rev8.setPaper(p8);
        rev8.setGrade(Grade.REJECT);
        rev8.setSubmitted(true);
        
        Review rev9 = new Review();
        rev9.setReviewer(reviewer);
        rev9.setPaper(p9);
        rev9.setGrade(Grade.REJECT);
        rev9.setSubmitted(true);
        
        Review rev10 = new Review();
        rev10.setReviewer(reviewer);
        rev10.setPaper(p10);
        rev10.setGrade(Grade.REJECT);
        rev10.setSubmitted(true);
        
        // Add all reviews to reviewer
        List<Review> reviewerReviews = new ArrayList<>();
        reviewerReviews.add(rev6);
        reviewerReviews.add(rev7);
        reviewerReviews.add(rev8);
        reviewerReviews.add(rev9);
        reviewerReviews.add(rev10);
        reviewer.setReviews(reviewerReviews);
        
        // Expected Output: 2 (P6 and P7 are unsubmitted)
        assertEquals(2, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Setup: Create Reviewer R004 with no assignments
        reviewer.setName("R004");
        reviewer.setReviews(new ArrayList<>()); // Empty reviews list
        
        // Expected Output: 0
        assertEquals(0, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Test Case 5: "Partially submitted reviews"
        // Setup: Create Reviewer R005, Papers P11-P13
        // P11 (grade=ACCEPT), P12 (no grade and feedback), P13 (grade=REJECT)
        reviewer.setName("R005");
        
        // Create papers
        Paper p11 = new Paper();
        p11.setTitle("P11");
        Paper p12 = new Paper();
        p12.setTitle("P12");
        Paper p13 = new Paper();
        p13.setTitle("P13");
        
        // Create review for P11 (submitted with ACCEPT grade)
        Review rev11 = new Review();
        rev11.setReviewer(reviewer);
        rev11.setPaper(p11);
        rev11.setGrade(Grade.ACCEPT);
        rev11.setSubmitted(true);
        
        // Create review for P12 (not submitted)
        Review rev12 = new Review();
        rev12.setReviewer(reviewer);
        rev12.setPaper(p12);
        rev12.setSubmitted(false);
        
        // Create review for P13 (submitted with REJECT grade)
        Review rev13 = new Review();
        rev13.setReviewer(reviewer);
        rev13.setPaper(p13);
        rev13.setGrade(Grade.REJECT);
        rev13.setSubmitted(true);
        
        // Add reviews to reviewer
        List<Review> reviewerReviews = new ArrayList<>();
        reviewerReviews.add(rev11);
        reviewerReviews.add(rev12);
        reviewerReviews.add(rev13);
        reviewer.setReviews(reviewerReviews);
        
        // Expected Output: 1 (only P12 is unsubmitted)
        assertEquals(1, reviewer.countUnsubmittedReviews());
    }
}