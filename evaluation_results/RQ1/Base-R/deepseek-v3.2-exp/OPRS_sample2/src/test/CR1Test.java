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
    public void testCase1_ReviewerWith3PendingReviews() {
        // Setup: Create Reviewer R001
        reviewer.setName("R001");
        
        // Create Paper P1, P2, P3
        Paper p1 = new Paper();
        p1.setTitle("P1");
        Paper p2 = new Paper();
        p2.setTitle("P2");
        Paper p3 = new Paper();
        p3.setTitle("P3");
        
        // Assign P1/P2/P3 to R001 without feedback and grade from R001
        Review rev1 = new Review();
        rev1.setReviewer(reviewer);
        rev1.setPaper(p1);
        rev1.setSubmitted(false); // Not submitted
        
        Review rev2 = new Review();
        rev2.setReviewer(reviewer);
        rev2.setPaper(p2);
        rev2.setSubmitted(false); // Not submitted
        
        Review rev3 = new Review();
        rev3.setReviewer(reviewer);
        rev3.setPaper(p3);
        rev3.setSubmitted(false); // Not submitted
        
        // Add reviews to reviewer
        reviewer.addReview(rev1);
        reviewer.addReview(rev2);
        reviewer.addReview(rev3);
        
        // Expected Output: 3
        assertEquals(3, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002
        reviewer.setName("R002");
        
        // Create Paper P4, P5
        Paper p4 = new Paper();
        p4.setTitle("P4");
        Paper p5 = new Paper();
        p5.setTitle("P5");
        
        // Assign P4/P5 to R002 with grades=ACCEPT
        Review rev4 = new Review();
        rev4.setReviewer(reviewer);
        rev4.setPaper(p4);
        rev4.setGrade(Grade.ACCEPT);
        rev4.setSubmitted(true); // Submitted
        
        Review rev5 = new Review();
        rev5.setReviewer(reviewer);
        rev5.setPaper(p5);
        rev5.setGrade(Grade.ACCEPT);
        rev5.setSubmitted(true); // Submitted
        
        // Add reviews to reviewer
        reviewer.addReview(rev4);
        reviewer.addReview(rev5);
        
        // Expected Output: 0
        assertEquals(0, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003
        reviewer.setName("R003");
        
        // Create Papers P6-P10
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
        
        // Assign P6/P7 to R003 without feedback and grade from R003 (unsubmitted)
        Review rev6 = new Review();
        rev6.setReviewer(reviewer);
        rev6.setPaper(p6);
        rev6.setSubmitted(false); // Not submitted
        
        Review rev7 = new Review();
        rev7.setReviewer(reviewer);
        rev7.setPaper(p7);
        rev7.setSubmitted(false); // Not submitted
        
        // Assign P8-P10 to R003 with grade=REJECT (submitted)
        Review rev8 = new Review();
        rev8.setReviewer(reviewer);
        rev8.setPaper(p8);
        rev8.setGrade(Grade.REJECT);
        rev8.setSubmitted(true); // Submitted
        
        Review rev9 = new Review();
        rev9.setReviewer(reviewer);
        rev9.setPaper(p9);
        rev9.setGrade(Grade.REJECT);
        rev9.setSubmitted(true); // Submitted
        
        Review rev10 = new Review();
        rev10.setReviewer(reviewer);
        rev10.setPaper(p10);
        rev10.setGrade(Grade.REJECT);
        rev10.setSubmitted(true); // Submitted
        
        // Add reviews to reviewer
        reviewer.addReview(rev6);
        reviewer.addReview(rev7);
        reviewer.addReview(rev8);
        reviewer.addReview(rev9);
        reviewer.addReview(rev10);
        
        // Expected Output: 2
        assertEquals(2, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 (no assignments)
        reviewer.setName("R004");
        
        // Expected Output: 0
        assertEquals(0, reviewer.countUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005
        reviewer.setName("R005");
        
        // Create Papers P11-P13
        Paper p11 = new Paper();
        p11.setTitle("P11");
        Paper p12 = new Paper();
        p12.setTitle("P12");
        Paper p13 = new Paper();
        p13.setTitle("P13");
        
        // Assign P11 (grade=ACCEPT) - submitted
        Review rev11 = new Review();
        rev11.setReviewer(reviewer);
        rev11.setPaper(p11);
        rev11.setGrade(Grade.ACCEPT);
        rev11.setSubmitted(true); // Submitted
        
        // Assign P12 (no grade and feedback) - unsubmitted
        Review rev12 = new Review();
        rev12.setReviewer(reviewer);
        rev12.setPaper(p12);
        rev12.setSubmitted(false); // Not submitted
        
        // Assign P13 (grade=REJECT) - submitted
        Review rev13 = new Review();
        rev13.setReviewer(reviewer);
        rev13.setPaper(p13);
        rev13.setGrade(Grade.REJECT);
        rev13.setSubmitted(true); // Submitted
        
        // Add reviews to reviewer
        reviewer.addReview(rev11);
        reviewer.addReview(rev12);
        reviewer.addReview(rev13);
        
        // Expected Output: 1
        assertEquals(1, reviewer.countUnsubmittedReviews());
    }
}