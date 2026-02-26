import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Setup: Create Author A006 with Papers P30-P32 with ACCEPT decisions
        Author author = new Author();
        author.setName("A006");
        
        // Create and add papers with ACCEPT decisions
        Paper paper1 = new Paper();
        paper1.setTitle("P30");
        paper1.setFinalDecision("Accept");
        author.addSubmittedPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P31");
        paper2.setFinalDecision("Accept");
        author.addSubmittedPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P32");
        paper3.setFinalDecision("Accept");
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Verify expected output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_50PercentAcceptanceRate() {
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        Author author = new Author();
        author.setName("A007");
        
        // Create and add papers with mixed decisions
        Paper paper1 = new Paper();
        paper1.setTitle("P33");
        paper1.setFinalDecision("Accept");
        author.addSubmittedPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P34");
        paper2.setFinalDecision("Reject");
        author.addSubmittedPaper(paper2);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        Author author = new Author();
        author.setName("A008");
        
        // Create and add papers with REJECT decisions
        Paper paper1 = new Paper();
        paper1.setTitle("P35");
        paper1.setFinalDecision("Reject");
        author.addSubmittedPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P36");
        paper2.setFinalDecision("Reject");
        author.addSubmittedPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P37");
        paper3.setFinalDecision("Reject");
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author author = new Author();
        author.setName("A009");
        
        // Create and add papers with mixed decisions
        Paper paper1 = new Paper();
        paper1.setTitle("P38");
        paper1.setFinalDecision("Accept");
        author.addSubmittedPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P39");
        paper2.setFinalDecision("Reject");
        author.addSubmittedPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P40");
        paper3.setFinalDecision("Reject");
        author.addSubmittedPaper(paper3);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.33
        assertEquals(0.333, result, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        Author author = new Author();
        author.setName("A010");
        
        // Create and add single paper with ACCEPT decision
        Paper paper = new Paper();
        paper.setTitle("P41");
        paper.setFinalDecision("Accept");
        author.addSubmittedPaper(paper);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Verify expected output: 1.00
        assertEquals(1.00, result, 0.001);
    }
}