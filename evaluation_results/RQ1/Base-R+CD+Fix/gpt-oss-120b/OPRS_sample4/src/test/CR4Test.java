import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        Author author = new Author();
        Paper paper30 = new Paper("P30", true);
        Paper paper31 = new Paper("P31", true);
        Paper paper32 = new Paper("P32", true);
        
        // Submit papers to author
        author.submitPaper(paper30);
        author.submitPaper(paper31);
        author.submitPaper(paper32);
        
        // Set final decisions to ACCEPT (true)
        paper30.setFinalDecision(true);
        paper31.setFinalDecision(true);
        paper32.setFinalDecision(true);
        
        // Calculate acceptance rate
        double result = author.acceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        Author author = new Author();
        Paper paper33 = new Paper("P33", true);
        Paper paper34 = new Paper("P34", true);
        
        // Submit papers to author
        author.submitPaper(paper33);
        author.submitPaper(paper34);
        
        // Set final decisions: one ACCEPT, one REJECT
        paper33.setFinalDecision(true);
        paper34.setFinalDecision(false);
        
        // Calculate acceptance rate
        double result = author.acceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        Author author = new Author();
        Paper paper35 = new Paper("P35", true);
        Paper paper36 = new Paper("P36", true);
        Paper paper37 = new Paper("P37", true);
        
        // Submit papers to author
        author.submitPaper(paper35);
        author.submitPaper(paper36);
        author.submitPaper(paper37);
        
        // Set final decisions to REJECT (false)
        paper35.setFinalDecision(false);
        paper36.setFinalDecision(false);
        paper37.setFinalDecision(false);
        
        // Calculate acceptance rate
        double result = author.acceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author author = new Author();
        Paper paper38 = new Paper("P38", true);
        Paper paper39 = new Paper("P39", true);
        Paper paper40 = new Paper("P40", true);
        
        // Submit papers to author
        author.submitPaper(paper38);
        author.submitPaper(paper39);
        author.submitPaper(paper40);
        
        // Set final decisions: one ACCEPT, two REJECT
        paper38.setFinalDecision(true);
        paper39.setFinalDecision(false);
        paper40.setFinalDecision(false);
        
        // Calculate acceptance rate
        double result = author.acceptanceRate();
        
        // Expected Output: 0.33 (approximately)
        assertEquals(0.333, result, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        Author author = new Author();
        Paper paper41 = new Paper("P41", true);
        
        // Submit paper to author
        author.submitPaper(paper41);
        
        // Set final decision to ACCEPT (true)
        paper41.setFinalDecision(true);
        
        // Calculate acceptance rate
        double result = author.acceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
}