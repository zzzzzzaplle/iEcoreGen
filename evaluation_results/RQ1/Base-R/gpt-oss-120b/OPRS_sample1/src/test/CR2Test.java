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
    private List<Reviewer> reviewers;
    
    @Before
    public void setUp() {
        // Initialize papers
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
        
        // Initialize reviewers list
        reviewers = new ArrayList<>();
    }
    
    @Test
    public void testCase1_UnanimousAcceptFrom3Reviews() {
        // Setup: Create 3 reviewers
        for (int i = 0; i < 3; i++) {
            Reviewer reviewer = new Reviewer();
            reviewers.add(reviewer);
        }
        
        // Assign paper P14 to 3 reviewers
        for (Reviewer reviewer : reviewers) {
            reviewer.assignPaper(paperP14);
        }
        
        // 3 reviewers give reviews: "grade:ACCEPT,feedback:'revise'"
        for (Review review : paperP14.getReviews()) {
            review.submit("revise", Grade.ACCEPT);
        }
        
        // Test: Check consensus for Paper P14
        boolean result = paperP14.areReviewsUniform();
        
        // Expected Output: True
        assertTrue("Paper P14 should have uniform reviews (all ACCEPT)", result);
    }
    
    @Test
    public void testCase2_SplitDecision2to1() {
        // Setup: Create 2 reviewers
        for (int i = 0; i < 2; i++) {
            Reviewer reviewer = new Reviewer();
            reviewers.add(reviewer);
        }
        
        // Assign paper P15 to 2 reviewers
        for (Reviewer reviewer : reviewers) {
            reviewer.assignPaper(paperP15);
        }
        
        // 2 reviewers give reviews: 1 with ACCEPT, 1 with REJECT
        List<Review> reviews = paperP15.getReviews();
        reviews.get(0).submit("feedback1", Grade.ACCEPT);
        reviews.get(1).submit("feedback2", Grade.REJECT);
        
        // Test: Check consensus for Paper P15
        boolean result = paperP15.areReviewsUniform();
        
        // Expected Output: False
        assertFalse("Paper P15 should not have uniform reviews (split decision)", result);
    }
    
    @Test
    public void testCase3_AllRejectWith4Reviews() {
        // Setup: Create 4 reviewers
        for (int i = 0; i < 4; i++) {
            Reviewer reviewer = new Reviewer();
            reviewers.add(reviewer);
        }
        
        // Assign paper P16 to 4 reviewers
        for (Reviewer reviewer : reviewers) {
            reviewer.assignPaper(paperP16);
        }
        
        // 4 reviewers give the same reviews: grade=REJECT
        for (Review review : paperP16.getReviews()) {
            review.submit("reject feedback", Grade.REJECT);
        }
        
        // Test: Check consensus for Paper P16
        boolean result = paperP16.areReviewsUniform();
        
        // Expected Output: True
        assertTrue("Paper P16 should have uniform reviews (all REJECT)", result);
    }
    
    @Test
    public void testCase4_MixedGradesWith3ReviewsOneUnsubmitted() {
        // Setup: Create 3 reviewers
        for (int i = 0; i < 3; i++) {
            Reviewer reviewer = new Reviewer();
            reviewers.add(reviewer);
        }
        
        // Assign paper P17 to 3 reviewers
        for (Reviewer reviewer : reviewers) {
            reviewer.assignPaper(paperP17);
        }
        
        // 2 reviewers have given feedback and grade: 1 ACCEPT, 1 REJECT
        List<Review> reviews = paperP17.getReviews();
        reviews.get(0).submit("feedback1", Grade.ACCEPT);
        reviews.get(1).submit("feedback2", Grade.REJECT);
        // 1 reviewer do not give feedback (remains unsubmitted)
        
        // Test: Check consensus for Paper P17
        boolean result = paperP17.areReviewsUniform();
        
        // Expected Output: False
        assertFalse("Paper P17 should not have uniform reviews (mixed grades + unsubmitted)", result);
    }
    
    @Test
    public void testCase5_EdgeCaseExactly50PercentAcceptance() {
        // Setup: Create 4 reviewers
        for (int i = 0; i < 4; i++) {
            Reviewer reviewer = new Reviewer();
            reviewers.add(reviewer);
        }
        
        // Assign paper P18 to 4 reviewers
        for (Reviewer reviewer : reviewers) {
            reviewer.assignPaper(paperP18);
        }
        
        // 4 reviewers give reviews: 2 ACCEPT, 2 REJECT
        List<Review> reviews = paperP18.getReviews();
        reviews.get(0).submit("feedback1", Grade.ACCEPT);
        reviews.get(1).submit("feedback2", Grade.ACCEPT);
        reviews.get(2).submit("feedback3", Grade.REJECT);
        reviews.get(3).submit("feedback4", Grade.REJECT);
        
        // Test: Check consensus for Paper P18
        boolean result = paperP18.areReviewsUniform();
        
        // Expected Output: False
        assertFalse("Paper P18 should not have uniform reviews (50/50 split)", result);
    }
}