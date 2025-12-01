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
        // Clear the static list of authors before each test to avoid interference
        List<Author> allAuthors = Author.getAllAuthors();
        if (allAuthors instanceof ArrayList) {
            ((ArrayList<Author>) allAuthors).clear();
        }
    }
    
    /**
     * Test Case 1: "Unanimous accept from 3 reviews"
     * Expected Output: True
     */
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Setup: Create Paper P14
        paperP14 = new Paper();
        paperP14.setTitle("P14");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Assign paper P14 to 3 reviewers and create review assignments
        ReviewAssignment ra1 = new ReviewAssignment();
        ra1.setGrade(Grade.ACCEPT);
        ra1.setFeedback("revise");
        
        ReviewAssignment ra2 = new ReviewAssignment();
        ra2.setGrade(Grade.ACCEPT);
        ra2.setFeedback("revise");
        
        ReviewAssignment ra3 = new ReviewAssignment();
        ra3.setGrade(Grade.ACCEPT);
        ra3.setFeedback("revise");
        
        // Add reviews to paper
        paperP14.addReview(ra1);
        paperP14.addReview(ra2);
        paperP14.addReview(ra3);
        
        // Check consensus - all reviews are exclusively ACCEPT
        boolean result = checkConsensus(paperP14);
        assertTrue("Paper with 3 unanimous ACCEPT reviews should return true", result);
    }
    
    /**
     * Test Case 2: "Split decision 2-1"
     * Expected Output: False
     */
    @Test
    public void testCase2_splitDecision2to1() {
        // Setup: Create Paper P15
        paperP15 = new Paper();
        paperP15.setTitle("P15");
        
        // Create 2 reviewers (as specified in test case)
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        
        // Assign paper P15 to 2 reviewers and create review assignments
        ReviewAssignment ra1 = new ReviewAssignment();
        ra1.setGrade(Grade.ACCEPT);
        ra1.setFeedback("good paper");
        
        ReviewAssignment ra2 = new ReviewAssignment();
        ra2.setGrade(Grade.REJECT);
        ra2.setFeedback("needs improvement");
        
        // Add reviews to paper
        paperP15.addReview(ra1);
        paperP15.addReview(ra2);
        
        // Check consensus - mixed ACCEPT and REJECT reviews
        boolean result = checkConsensus(paperP15);
        assertFalse("Paper with split decision (1 ACCEPT, 1 REJECT) should return false", result);
    }
    
    /**
     * Test Case 3: "All reject with 4 reviews"
     * Expected Output: True
     */
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Setup: Create Paper P16
        paperP16 = new Paper();
        paperP16.setTitle("P16");
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        // Assign paper P16 to 4 reviewers and create review assignments
        ReviewAssignment ra1 = new ReviewAssignment();
        ra1.setGrade(Grade.REJECT);
        
        ReviewAssignment ra2 = new ReviewAssignment();
        ra2.setGrade(Grade.REJECT);
        
        ReviewAssignment ra3 = new ReviewAssignment();
        ra3.setGrade(Grade.REJECT);
        
        ReviewAssignment ra4 = new ReviewAssignment();
        ra4.setGrade(Grade.REJECT);
        
        // Add reviews to paper
        paperP16.addReview(ra1);
        paperP16.addReview(ra2);
        paperP16.addReview(ra3);
        paperP16.addReview(ra4);
        
        // Check consensus - all reviews are exclusively REJECT
        boolean result = checkConsensus(paperP16);
        assertTrue("Paper with 4 unanimous REJECT reviews should return true", result);
    }
    
    /**
     * Test Case 4: "Mixed grades with 3 reviews"
     * Expected Output: False
     */
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Setup: Create Paper P17
        paperP17 = new Paper();
        paperP17.setTitle("P17");
        
        // Create 3 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        
        // Assign paper P17 to 3 reviewers and create review assignments
        // 2 reviewers have given feedback and grade: 1 ACCEPT, 1 REJECT
        ReviewAssignment ra1 = new ReviewAssignment();
        ra1.setGrade(Grade.ACCEPT);
        ra1.setFeedback("acceptable");
        
        ReviewAssignment ra2 = new ReviewAssignment();
        ra2.setGrade(Grade.REJECT);
        ra2.setFeedback("reject");
        
        // 1 reviewer has not given feedback (UNDECIDED grade)
        ReviewAssignment ra3 = new ReviewAssignment();
        ra3.setGrade(Grade.UNDECIDED);
        
        // Add reviews to paper
        paperP17.addReview(ra1);
        paperP17.addReview(ra2);
        paperP17.addReview(ra3);
        
        // Check consensus - mixed grades including UNDECIDED
        boolean result = checkConsensus(paperP17);
        assertFalse("Paper with mixed grades (ACCEPT, REJECT, UNDECIDED) should return false", result);
    }
    
    /**
     * Test Case 5: "Edge case: exactly 50% acceptance"
     * Expected Output: False
     */
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Setup: Create Paper P18
        paperP18 = new Paper();
        paperP18.setTitle("P18");
        
        // Create 4 reviewers
        Reviewer reviewer1 = new Reviewer();
        Reviewer reviewer2 = new Reviewer();
        Reviewer reviewer3 = new Reviewer();
        Reviewer reviewer4 = new Reviewer();
        
        // Assign paper P18 to 4 reviewers and create review assignments
        // 2 ACCEPT, 2 REJECT
        ReviewAssignment ra1 = new ReviewAssignment();
        ra1.setGrade(Grade.ACCEPT);
        
        ReviewAssignment ra2 = new ReviewAssignment();
        ra2.setGrade(Grade.ACCEPT);
        
        ReviewAssignment ra3 = new ReviewAssignment();
        ra3.setGrade(Grade.REJECT);
        
        ReviewAssignment ra4 = new ReviewAssignment();
        ra4.setGrade(Grade.REJECT);
        
        // Add reviews to paper
        paperP18.addReview(ra1);
        paperP18.addReview(ra2);
        paperP18.addReview(ra3);
        paperP18.addReview(ra4);
        
        // Check consensus - split 2-2 decision
        boolean result = checkConsensus(paperP18);
        assertFalse("Paper with exactly 50% acceptance (2 ACCEPT, 2 REJECT) should return false", result);
    }
    
    /**
     * Helper method to check if all reviews for a paper are either exclusively Accept or exclusively Reject
     * @param paper the paper to check
     * @return true if all reviews are unanimous (all ACCEPT or all REJECT), false otherwise
     */
    private boolean checkConsensus(Paper paper) {
        if (paper == null || paper.getReviews() == null || paper.getReviews().isEmpty()) {
            return false;
        }
        
        List<ReviewAssignment> reviews = paper.getReviews();
        Grade firstGrade = null;
        
        for (ReviewAssignment review : reviews) {
            // Skip UNDECIDED reviews as they don't count toward consensus
            if (review.getGrade() == Grade.UNDECIDED) {
                continue;
            }
            
            // If this is the first non-UNDECIDED review, set the reference grade
            if (firstGrade == null) {
                firstGrade = review.getGrade();
                continue;
            }
            
            // If any subsequent non-UNDECIDED review doesn't match the first grade, no consensus
            if (review.getGrade() != firstGrade) {
                return false;
            }
        }
        
        // If we have at least one non-UNDECIDED review and all non-UNDECIDED reviews match
        return firstGrade != null;
    }
}