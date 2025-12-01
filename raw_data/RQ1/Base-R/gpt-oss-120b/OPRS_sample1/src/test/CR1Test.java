import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private Reviewer reviewer;
    
    @Before
    public void setUp() {
        // Reset reviewer before each test
        reviewer = null;
    }
    
    @Test
    public void testCase1_reviewerWithThreePendingReviews() {
        // Test Case 1: "Reviewer with 3 pending reviews"
        // Setup: Create Reviewer R001, Papers P1, P2, P3, assign them without feedback and grade
        reviewer = new Reviewer();
        reviewer.setName("R001");
        
        Paper p1 = new Paper();
        p1.setTitle("P1");
        Paper p2 = new Paper();
        p2.setTitle("P2");
        Paper p3 = new Paper();
        p3.setTitle("P3");
        
        // Assign papers without submitting reviews (no feedback/grade)
        reviewer.assignPaper(p1);
        reviewer.assignPaper(p2);
        reviewer.assignPaper(p3);
        
        // Expected Output: 3
        assertEquals(3, reviewer.getUnsubmittedReviewCount());
    }
    
    @Test
    public void testCase2_allReviewsSubmitted() {
        // Test Case 2: "All reviews submitted"
        // Setup: Create Reviewer R002, Papers P4, P5, assign with grades=ACCEPT
        reviewer = new Reviewer();
        reviewer.setName("R002");
        
        Paper p4 = new Paper();
        p4.setTitle("P4");
        Paper p5 = new Paper();
        p5.setTitle("P5");
        
        // Assign papers
        reviewer.assignPaper(p4);
        reviewer.assignPaper(p5);
        
        // Submit all reviews with ACCEPT grade
        for (Review review : reviewer.getReviews()) {
            review.submit("Good paper", Grade.ACCEPT);
        }
        
        // Expected Output: 0
        assertEquals(0, reviewer.getUnsubmittedReviewCount());
    }
    
    @Test
    public void testCase3_mixedSubmissionStatus() {
        // Test Case 3: "Mixed submission status"
        // Setup: Create Reviewer R003, Papers P6-P10
        reviewer = new Reviewer();
        reviewer.setName("R003");
        
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
        
        // Assign all papers
        reviewer.assignPaper(p6);
        reviewer.assignPaper(p7);
        reviewer.assignPaper(p8);
        reviewer.assignPaper(p9);
        reviewer.assignPaper(p10);
        
        // P6 and P7: no feedback and grade (unsubmitted)
        // P8, P9, P10: submitted with REJECT grade
        reviewer.getReviews().get(2).submit("Needs improvement", Grade.REJECT); // P8
        reviewer.getReviews().get(3).submit("Not suitable", Grade.REJECT);     // P9
        reviewer.getReviews().get(4).submit("Poor quality", Grade.REJECT);     // P10
        
        // Expected Output: 2 (P6 and P7 are unsubmitted)
        assertEquals(2, reviewer.getUnsubmittedReviewCount());
    }
    
    @Test
    public void testCase4_noAssignedPapers() {
        // Test Case 4: "No assigned papers"
        // Setup: Create Reviewer R004 (no assignments)
        reviewer = new Reviewer();
        reviewer.setName("R004");
        
        // Expected Output: 0
        assertEquals(0, reviewer.getUnsubmittedReviewCount());
    }
    
    @Test
    public void testCase5_partiallySubmittedReviews() {
        // Test Case 5: "Partially submitted reviews"
        // Setup: Create Reviewer R005, Papers P11-P13
        reviewer = new Reviewer();
        reviewer.setName("R005");
        
        Paper p11 = new Paper();
        p11.setTitle("P11");
        Paper p12 = new Paper();
        p12.setTitle("P12");
        Paper p13 = new Paper();
        p13.setTitle("P13");
        
        // Assign all papers
        reviewer.assignPaper(p11);
        reviewer.assignPaper(p12);
        reviewer.assignPaper(p13);
        
        // P11: submitted with ACCEPT grade
        reviewer.getReviews().get(0).submit("Excellent work", Grade.ACCEPT);
        
        // P12: no feedback and grade (unsubmitted)
        
        // P13: submitted with REJECT grade
        reviewer.getReviews().get(2).submit("Not acceptable", Grade.REJECT);
        
        // Expected Output: 1 (only P12 is unsubmitted)
        assertEquals(1, reviewer.getUnsubmittedReviewCount());
    }
}