import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Test Case 1: "Perfect acceptance rate"
        // Setup: Create Author A006 and Papers P30-P32 with final decision=ACCEPT
        Author author = new Author("A006");
        
        Paper paper1 = new Paper("P30", true);
        paper1.setFinalDecision(Decision.ACCEPT);
        
        Paper paper2 = new Paper("P31", true);
        paper2.setFinalDecision(Decision.ACCEPT);
        
        Paper paper3 = new Paper("P32", true);
        paper3.setFinalDecision(Decision.ACCEPT);
        
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Test Case 2: "50% acceptance rate"
        // Setup: Create Author A007 and Papers P33 (ACCEPT), P34 (REJECT)
        Author author = new Author("A007");
        
        Paper paper1 = new Paper("P33", true);
        paper1.setFinalDecision(Decision.ACCEPT);
        
        Paper paper2 = new Paper("P34", true);
        paper2.setFinalDecision(Decision.REJECT);
        
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Test Case 3: "No accepted papers"
        // Setup: Create Author A008 and Papers P35-P37 with REJECT decisions
        Author author = new Author("A008");
        
        Paper paper1 = new Paper("P35", true);
        paper1.setFinalDecision(Decision.REJECT);
        
        Paper paper2 = new Paper("P36", true);
        paper2.setFinalDecision(Decision.REJECT);
        
        Paper paper3 = new Paper("P37", true);
        paper3.setFinalDecision(Decision.REJECT);
        
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Test Case 4: "Mixed decisions with 1 acceptance"
        // Setup: Create Author A009 and Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author author = new Author("A009");
        
        Paper paper1 = new Paper("P38", true);
        paper1.setFinalDecision(Decision.ACCEPT);
        
        Paper paper2 = new Paper("P39", true);
        paper2.setFinalDecision(Decision.REJECT);
        
        Paper paper3 = new Paper("P40", true);
        paper3.setFinalDecision(Decision.REJECT);
        
        author.addSubmittedPaper(paper1);
        author.addSubmittedPaper(paper2);
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.33
        assertEquals(0.33, acceptanceRate, 0.01); // Using delta of 0.01 for floating point precision
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Test Case 5: "Single paper author"
        // Setup: Create Author A010 and Paper P41 with ACCEPT decision
        Author author = new Author("A010");
        
        Paper paper = new Paper("P41", true);
        paper.setFinalDecision(Decision.ACCEPT);
        
        author.addSubmittedPaper(paper);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}