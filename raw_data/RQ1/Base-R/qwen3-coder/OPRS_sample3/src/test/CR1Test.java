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
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        reviewer.setName("R001");
        
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        
        Review r1 = new Review();
        r1.setPaper(p1);
        r1.setReviewer(reviewer);
        r1.setSubmitted(false);
        
        Review r2 = new Review();
        r2.setPaper(p2);
        r2.setReviewer(reviewer);
        r2.setSubmitted(false);
        
        Review r3 = new Review();
        r3.setPaper(p3);
        r3.setReviewer(reviewer);
        r3.setSubmitted(false);
        
        reviewer.addAssignedReview(r1);
        reviewer.addAssignedReview(r2);
        reviewer.addAssignedReview(r3);
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected output is 3
        assertEquals("Reviewer with 3 pending reviews should return 3", 3, result);
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with grades=ACCEPT
        reviewer.setName("R002");
        
        Paper p4 = new Paper();
        Paper p5 = new Paper();
        
        Review r4 = new Review();
        r4.setPaper(p4);
        r4.setReviewer(reviewer);
        r4.setGrade(Review.Grade.ACCEPT);
        r4.setSubmitted(true);
        
        Review r5 = new Review();
        r5.setPaper(p5);
        r5.setReviewer(reviewer);
        r5.setGrade(Review.Grade.ACCEPT);
        r5.setSubmitted(true);
        
        reviewer.addAssignedReview(r4);
        reviewer.addAssignedReview(r5);
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected output is 0
        assertEquals("Reviewer with all reviews submitted should return 0", 0, result);
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers - 2 unsubmitted, 3 submitted with REJECT
        reviewer.setName("R003");
        
        Paper p6 = new Paper();
        Paper p7 = new Paper();
        Paper p8 = new Paper();
        Paper p9 = new Paper();
        Paper p10 = new Paper();
        
        // P6 and P7 without feedback and grade (unsubmitted)
        Review r6 = new Review();
        r6.setPaper(p6);
        r6.setReviewer(reviewer);
        r6.setSubmitted(false);
        
        Review r7 = new Review();
        r7.setPaper(p7);
        r7.setReviewer(reviewer);
        r7.setSubmitted(false);
        
        // P8-P10 with grade=REJECT (submitted)
        Review r8 = new Review();
        r8.setPaper(p8);
        r8.setReviewer(reviewer);
        r8.setGrade(Review.Grade.REJECT);
        r8.setSubmitted(true);
        
        Review r9 = new Review();
        r9.setPaper(p9);
        r9.setReviewer(reviewer);
        r9.setGrade(Review.Grade.REJECT);
        r9.setSubmitted(true);
        
        Review r10 = new Review();
        r10.setPaper(p10);
        r10.setReviewer(reviewer);
        r10.setGrade(Review.Grade.REJECT);
        r10.setSubmitted(true);
        
        reviewer.addAssignedReview(r6);
        reviewer.addAssignedReview(r7);
        reviewer.addAssignedReview(r8);
        reviewer.addAssignedReview(r9);
        reviewer.addAssignedReview(r10);
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected output is 2
        assertEquals("Reviewer with mixed submission status should return 2", 2, result);
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        reviewer.setName("R004");
        
        // No reviews added to reviewer
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected output is 0
        assertEquals("Reviewer with no assigned papers should return 0", 0, result);
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers - 1 unsubmitted, 2 submitted
        reviewer.setName("R005");
        
        Paper p11 = new Paper();
        Paper p12 = new Paper();
        Paper p13 = new Paper();
        
        // P11 with grade=ACCEPT (submitted)
        Review r11 = new Review();
        r11.setPaper(p11);
        r11.setReviewer(reviewer);
        r11.setGrade(Review.Grade.ACCEPT);
        r11.setSubmitted(true);
        
        // P12 without grade and feedback (unsubmitted)
        Review r12 = new Review();
        r12.setPaper(p12);
        r12.setReviewer(reviewer);
        r12.setSubmitted(false);
        
        // P13 with grade=REJECT (submitted)
        Review r13 = new Review();
        r13.setPaper(p13);
        r13.setReviewer(reviewer);
        r13.setGrade(Review.Grade.REJECT);
        r13.setSubmitted(true);
        
        reviewer.addAssignedReview(r11);
        reviewer.addAssignedReview(r12);
        reviewer.addAssignedReview(r13);
        
        // Execute: Calculate unsubmitted reviews
        int result = reviewer.countUnsubmittedReviews();
        
        // Verify: Expected output is 1
        assertEquals("Reviewer with partially submitted reviews should return 1", 1, result);
    }
}