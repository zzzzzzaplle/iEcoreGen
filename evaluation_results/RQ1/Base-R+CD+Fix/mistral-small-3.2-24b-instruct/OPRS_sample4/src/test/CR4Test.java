import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    @Test
    public void testCase1_perfectAcceptanceRate() {
        // Setup: Create Author A006 and Papers P30-P32 with ACCEPT decisions
        Author author = new Author();
        
        // Create papers with ACCEPT decisions
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.ACCEPT);
        
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.ACCEPT);
        
        Paper paper3 = new Paper();
        paper3.setDecision(Grade.ACCEPT);
        
        // Submit papers to author
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_fiftyPercentAcceptanceRate() {
        // Setup: Create Author A007 with Papers P33 (ACCEPT) and P34 (REJECT)
        Author author = new Author();
        
        // Create papers with mixed decisions
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.ACCEPT);
        
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.REJECT);
        
        // Submit papers to author
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_noAcceptedPapers() {
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        Author author = new Author();
        
        // Create papers with REJECT decisions
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.REJECT);
        
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.REJECT);
        
        Paper paper3 = new Paper();
        paper3.setDecision(Grade.REJECT);
        
        // Submit papers to author
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_mixedDecisionsWithOneAcceptance() {
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author author = new Author();
        
        // Create papers with mixed decisions
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.ACCEPT);
        
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.REJECT);
        
        Paper paper3 = new Paper();
        paper3.setDecision(Grade.REJECT);
        
        // Submit papers to author
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(0.333, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase5_singlePaperAuthor() {
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        Author author = new Author();
        
        // Create single paper with ACCEPT decision
        Paper paper = new Paper();
        paper.setDecision(Grade.ACCEPT);
        
        // Submit paper to author
        author.submitPaper(paper);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}