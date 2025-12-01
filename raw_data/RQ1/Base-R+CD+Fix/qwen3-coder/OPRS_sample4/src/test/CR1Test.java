import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Reviewer reviewer;
    private List<Paper> papers;
    private List<ReviewAssignment> assignments;
    
    @Before
    public void setUp() {
        reviewer = new Reviewer();
        papers = new ArrayList<>();
        assignments = new ArrayList<>();
    }
    
    @Test
    public void testCase1_ReviewerWithThreePendingReviews() {
        // Setup: Create Reviewer R001 with 3 papers assigned without feedback and grade
        reviewer.setAssignments(new ArrayList<>());
        
        // Create 3 papers
        Paper p1 = new Paper();
        Paper p2 = new Paper();
        Paper p3 = new Paper();
        papers.add(p1);
        papers.add(p2);
        papers.add(p3);
        
        // Create review assignments without grades (UNDECIDED by default)
        ReviewAssignment ra1 = new ReviewAssignment();
        ReviewAssignment ra2 = new ReviewAssignment();
        ReviewAssignment ra3 = new ReviewAssignment();
        
        // Add assignments to reviewer
        reviewer.getAssignments().add(ra1);
        reviewer.getAssignments().add(ra2);
        reviewer.getAssignments().add(ra3);
        
        // Expected Output: 3 unsubmitted reviews
        assertEquals(3, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase2_AllReviewsSubmitted() {
        // Setup: Create Reviewer R002 with 2 papers assigned with ACCEPT grades
        reviewer.setAssignments(new ArrayList<>());
        
        // Create 2 papers
        Paper p4 = new Paper();
        Paper p5 = new Paper();
        papers.add(p4);
        papers.add(p5);
        
        // Create review assignments with ACCEPT grades
        ReviewAssignment ra4 = new ReviewAssignment();
        ra4.setGrade(Grade.ACCEPT);
        ReviewAssignment ra5 = new ReviewAssignment();
        ra5.setGrade(Grade.ACCEPT);
        
        // Add assignments to reviewer
        reviewer.getAssignments().add(ra4);
        reviewer.getAssignments().add(ra5);
        
        // Expected Output: 0 unsubmitted reviews
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase3_MixedSubmissionStatus() {
        // Setup: Create Reviewer R003 with 5 papers - 2 without grades, 3 with REJECT grades
        reviewer.setAssignments(new ArrayList<>());
        
        // Create 5 papers
        for (int i = 6; i <= 10; i++) {
            papers.add(new Paper());
        }
        
        // Create 2 review assignments without grades (UNDECIDED by default)
        ReviewAssignment ra6 = new ReviewAssignment();
        ReviewAssignment ra7 = new ReviewAssignment();
        
        // Create 3 review assignments with REJECT grades
        ReviewAssignment ra8 = new ReviewAssignment();
        ra8.setGrade(Grade.REJECT);
        ReviewAssignment ra9 = new ReviewAssignment();
        ra9.setGrade(Grade.REJECT);
        ReviewAssignment ra10 = new ReviewAssignment();
        ra10.setGrade(Grade.REJECT);
        
        // Add all assignments to reviewer
        reviewer.getAssignments().add(ra6);
        reviewer.getAssignments().add(ra7);
        reviewer.getAssignments().add(ra8);
        reviewer.getAssignments().add(ra9);
        reviewer.getAssignments().add(ra10);
        
        // Expected Output: 2 unsubmitted reviews
        assertEquals(2, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase4_NoAssignedPapers() {
        // Setup: Create Reviewer R004 with no assignments
        reviewer.setAssignments(new ArrayList<>());
        
        // Expected Output: 0 unsubmitted reviews
        assertEquals(0, reviewer.calculateUnsubmittedReviews());
    }
    
    @Test
    public void testCase5_PartiallySubmittedReviews() {
        // Setup: Create Reviewer R005 with 3 papers: 
        // P11 (ACCEPT), P12 (no grade), P13 (REJECT)
        reviewer.setAssignments(new ArrayList<>());
        
        // Create 3 papers
        Paper p11 = new Paper();
        Paper p12 = new Paper();
        Paper p13 = new Paper();
        papers.add(p11);
        papers.add(p12);
        papers.add(p13);
        
        // Create review assignments with mixed status
        ReviewAssignment ra11 = new ReviewAssignment();
        ra11.setGrade(Grade.ACCEPT);
        ReviewAssignment ra12 = new ReviewAssignment(); // UNDECIDED by default
        ReviewAssignment ra13 = new ReviewAssignment();
        ra13.setGrade(Grade.REJECT);
        
        // Add assignments to reviewer
        reviewer.getAssignments().add(ra11);
        reviewer.getAssignments().add(ra12);
        reviewer.getAssignments().add(ra13);
        
        // Expected Output: 1 unsubmitted review
        assertEquals(1, reviewer.calculateUnsubmittedReviews());
    }
}