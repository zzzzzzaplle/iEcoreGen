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
        // Clear any existing authors from previous tests
        Author.getAllAuthors().clear();
        
        // Initialize papers for each test case
        paperP14 = new Paper("P14", PaperType.RESEARCH);
        paperP15 = new Paper("P15", PaperType.RESEARCH);
        paperP16 = new Paper("P16", PaperType.RESEARCH);
        paperP17 = new Paper("P17", PaperType.RESEARCH);
        paperP18 = new Paper("P18", PaperType.RESEARCH);
    }
    
    @Test
    public void testCase1_unanimousAcceptFrom3Reviews() {
        // Setup: Create 3 reviewers and assign paper P14 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create review assignments with ACCEPT grade and feedback
        for (Reviewer reviewer : reviewers) {
            ReviewAssignment assignment = new ReviewAssignment("revise");
            assignment.setGrade(Grade.ACCEPT);
            paperP14.addReview(assignment);
        }
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP14.isAllReviewsPositive();
        
        // Verify: Expected output is True (all reviews are ACCEPT)
        assertTrue("All 3 reviews are ACCEPT, should return true", result);
    }
    
    @Test
    public void testCase2_splitDecision2to1() {
        // Setup: Create 2 reviewers and assign paper P15 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create review assignments: 1 ACCEPT, 1 REJECT
        ReviewAssignment assignment1 = new ReviewAssignment("good paper");
        assignment1.setGrade(Grade.ACCEPT);
        paperP15.addReview(assignment1);
        
        ReviewAssignment assignment2 = new ReviewAssignment("needs improvement");
        assignment2.setGrade(Grade.REJECT);
        paperP15.addReview(assignment2);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP15.isAllReviewsPositive();
        
        // Verify: Expected output is False (mixed grades)
        assertFalse("Reviews are split 1 ACCEPT and 1 REJECT, should return false", result);
    }
    
    @Test
    public void testCase3_allRejectWith4Reviews() {
        // Setup: Create 4 reviewers and assign paper P16 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create review assignments with REJECT grade
        for (Reviewer reviewer : reviewers) {
            ReviewAssignment assignment = new ReviewAssignment("poor quality");
            assignment.setGrade(Grade.REJECT);
            paperP16.addReview(assignment);
        }
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP16.isAllReviewsPositive();
        
        // Verify: Expected output is True (all reviews are REJECT)
        assertTrue("All 4 reviews are REJECT, should return true", result);
    }
    
    @Test
    public void testCase4_mixedGradesWith3Reviews() {
        // Setup: Create 3 reviewers and assign paper P17 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create review assignments: 1 ACCEPT, 1 REJECT, 1 UNDECIDED
        ReviewAssignment assignment1 = new ReviewAssignment("excellent work");
        assignment1.setGrade(Grade.ACCEPT);
        paperP17.addReview(assignment1);
        
        ReviewAssignment assignment2 = new ReviewAssignment("not suitable");
        assignment2.setGrade(Grade.REJECT);
        paperP17.addReview(assignment2);
        
        ReviewAssignment assignment3 = new ReviewAssignment(); // No feedback, UNDECIDED grade
        paperP17.addReview(assignment3);
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP17.isAllReviewsPositive();
        
        // Verify: Expected output is False (mixed grades and undecided review)
        assertFalse("Reviews are mixed (ACCEPT, REJECT, UNDECIDED), should return false", result);
    }
    
    @Test
    public void testCase5_edgeCaseExactly50PercentAcceptance() {
        // Setup: Create 4 reviewers and assign paper P18 to them
        List<Reviewer> reviewers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            reviewers.add(new Reviewer());
        }
        
        // Create review assignments: 2 ACCEPT, 2 REJECT
        for (int i = 0; i < 2; i++) {
            ReviewAssignment acceptAssignment = new ReviewAssignment("good paper");
            acceptAssignment.setGrade(Grade.ACCEPT);
            paperP18.addReview(acceptAssignment);
        }
        
        for (int i = 0; i < 2; i++) {
            ReviewAssignment rejectAssignment = new ReviewAssignment("needs work");
            rejectAssignment.setGrade(Grade.REJECT);
            paperP18.addReview(rejectAssignment);
        }
        
        // Test: Check if all reviews are either exclusively Accept or exclusively Reject
        boolean result = paperP18.isAllReviewsPositive();
        
        // Verify: Expected output is False (mixed grades - 2 ACCEPT and 2 REJECT)
        assertFalse("Reviews are split 2 ACCEPT and 2 REJECT, should return false", result);
    }
}