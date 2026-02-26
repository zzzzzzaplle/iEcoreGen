import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_perfectAcceptanceRate() {
        // Setup: Create Author A006 and Papers P30-P32 with final decision=ACCEPT
        Author author = new Author();
        
        Paper paper1 = new Paper("P30", PaperType.RESEARCH);
        paper1.setDecision(Grade.ACCEPT);
        
        Paper paper2 = new Paper("P31", PaperType.RESEARCH);
        paper2.setDecision(Grade.ACCEPT);
        
        Paper paper3 = new Paper("P32", PaperType.RESEARCH);
        paper3.setDecision(Grade.ACCEPT);
        
        // Submit papers to author
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_fiftyPercentAcceptanceRate() {
        // Setup: Create Author A007 and Papers P33 (ACCEPT), P34 (REJECT)
        Author author = new Author();
        
        Paper paper1 = new Paper("P33", PaperType.RESEARCH);
        paper1.setDecision(Grade.ACCEPT);
        
        Paper paper2 = new Paper("P34", PaperType.RESEARCH);
        paper2.setDecision(Grade.REJECT);
        
        // Submit papers to author
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_noAcceptedPapers() {
        // Setup: Create Author A008 and Papers P35-P37 with REJECT decisions
        Author author = new Author();
        
        Paper paper1 = new Paper("P35", PaperType.RESEARCH);
        paper1.setDecision(Grade.REJECT);
        
        Paper paper2 = new Paper("P36", PaperType.RESEARCH);
        paper2.setDecision(Grade.REJECT);
        
        Paper paper3 = new Paper("P37", PaperType.RESEARCH);
        paper3.setDecision(Grade.REJECT);
        
        // Submit papers to author
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_mixedDecisionsWithOneAcceptance() {
        // Setup: Create Author A009 and Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author author = new Author();
        
        Paper paper1 = new Paper("P38", PaperType.RESEARCH);
        paper1.setDecision(Grade.ACCEPT);
        
        Paper paper2 = new Paper("P39", PaperType.RESEARCH);
        paper2.setDecision(Grade.REJECT);
        
        Paper paper3 = new Paper("P40", PaperType.RESEARCH);
        paper3.setDecision(Grade.REJECT);
        
        // Submit papers to author
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.33 (1 acceptance out of 3 papers)
        assertEquals(0.333, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase5_singlePaperAuthor() {
        // Setup: Create Author A010 and Paper P41 with ACCEPT decision
        Author author = new Author();
        
        Paper paper1 = new Paper("P41", PaperType.RESEARCH);
        paper1.setDecision(Grade.ACCEPT);
        
        // Submit paper to author
        author.submitPaper(paper1);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}