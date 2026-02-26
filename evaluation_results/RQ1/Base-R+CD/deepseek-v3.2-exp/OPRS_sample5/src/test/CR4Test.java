import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        Author author = new Author();
        
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.ACCEPT);
        author.submitPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.ACCEPT);
        author.submitPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setDecision(Grade.ACCEPT);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        Author author = new Author();
        
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.ACCEPT);
        author.submitPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.REJECT);
        author.submitPaper(paper2);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        Author author = new Author();
        
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.REJECT);
        author.submitPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.REJECT);
        author.submitPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setDecision(Grade.REJECT);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author author = new Author();
        
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.ACCEPT);
        author.submitPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.REJECT);
        author.submitPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setDecision(Grade.REJECT);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.33
        assertEquals(0.333, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        Author author = new Author();
        
        Paper paper = new Paper();
        paper.setDecision(Grade.ACCEPT);
        author.submitPaper(paper);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}