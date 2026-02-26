import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Setup: Create Author A006
        Author author = new Author("A006");
        
        // Create Papers P30-P32 with final decision=ACCEPT
        Paper paper30 = new Paper("P30", true);
        paper30.setFinalDecision(Grade.Accept);
        
        Paper paper31 = new Paper("P31", true);
        paper31.setFinalDecision(Grade.Accept);
        
        Paper paper32 = new Paper("P32", true);
        paper32.setFinalDecision(Grade.Accept);
        
        // Add papers to author
        author.addSubmittedPaper(paper30);
        author.addSubmittedPaper(paper31);
        author.addSubmittedPaper(paper32);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Setup: Create Author A007
        Author author = new Author("A007");
        
        // Create Papers P33 (ACCEPT), P34 (REJECT)
        Paper paper33 = new Paper("P33", true);
        paper33.setFinalDecision(Grade.Accept);
        
        Paper paper34 = new Paper("P34", true);
        paper34.setFinalDecision(Grade.Reject);
        
        // Add papers to author
        author.addSubmittedPaper(paper33);
        author.addSubmittedPaper(paper34);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Setup: Create Author A008
        Author author = new Author("A008");
        
        // Create Papers P35-P37 with REJECT decisions
        Paper paper35 = new Paper("P35", true);
        paper35.setFinalDecision(Grade.Reject);
        
        Paper paper36 = new Paper("P36", true);
        paper36.setFinalDecision(Grade.Reject);
        
        Paper paper37 = new Paper("P37", true);
        paper37.setFinalDecision(Grade.Reject);
        
        // Add papers to author
        author.addSubmittedPaper(paper35);
        author.addSubmittedPaper(paper36);
        author.addSubmittedPaper(paper37);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Setup: Create Author A009
        Author author = new Author("A009");
        
        // Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Paper paper38 = new Paper("P38", true);
        paper38.setFinalDecision(Grade.Accept);
        
        Paper paper39 = new Paper("P39", true);
        paper39.setFinalDecision(Grade.Reject);
        
        Paper paper40 = new Paper("P40", true);
        paper40.setFinalDecision(Grade.Reject);
        
        // Add papers to author
        author.addSubmittedPaper(paper38);
        author.addSubmittedPaper(paper39);
        author.addSubmittedPaper(paper40);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.33
        assertEquals(0.33, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Setup: Create Author A010
        Author author = new Author("A010");
        
        // Create Paper P41 with ACCEPT decision
        Paper paper41 = new Paper("P41", true);
        paper41.setFinalDecision(Grade.Accept);
        
        // Add paper to author
        author.addSubmittedPaper(paper41);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}