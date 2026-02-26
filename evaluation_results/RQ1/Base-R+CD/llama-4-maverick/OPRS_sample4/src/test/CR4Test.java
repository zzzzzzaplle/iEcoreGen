import org.junit.Test;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_perfectAcceptanceRate() {
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        Author author = new Author();
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.ACCEPT);
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.ACCEPT);
        Paper paper3 = new Paper();
        paper3.setDecision(Grade.ACCEPT);
        
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_50PercentAcceptanceRate() {
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        Author author = new Author();
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.ACCEPT);
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.REJECT);
        
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noAcceptedPapers() {
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        Author author = new Author();
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.REJECT);
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.REJECT);
        Paper paper3 = new Paper();
        paper3.setDecision(Grade.REJECT);
        
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_mixedDecisionsWithOneAcceptance() {
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author author = new Author();
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.ACCEPT);
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.REJECT);
        Paper paper3 = new Paper();
        paper3.setDecision(Grade.REJECT);
        
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Expected Output: 0.33 (approximately)
        assertEquals(0.333, result, 0.001);
    }
    
    @Test
    public void testCase5_singlePaperAuthor() {
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        Author author = new Author();
        Paper paper = new Paper();
        paper.setDecision(Grade.ACCEPT);
        
        author.submitPaper(paper);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.001);
    }
}