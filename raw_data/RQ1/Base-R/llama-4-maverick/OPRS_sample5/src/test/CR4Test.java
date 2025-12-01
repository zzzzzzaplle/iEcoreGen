import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Create Author A006
        Author author = new Author();
        author.setName("A006");
        
        // Create Papers P30-P32 with final decision=ACCEPT
        Paper paper1 = new Paper();
        paper1.setTitle("P30");
        paper1.setFinalDecision("Accept");
        author.addPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P31");
        paper2.setFinalDecision("Accept");
        author.addPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P32");
        paper3.setFinalDecision("Accept");
        author.addPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Create Author A007
        Author author = new Author();
        author.setName("A007");
        
        // Create Papers P33 (ACCEPT), P34 (REJECT)
        Paper paper1 = new Paper();
        paper1.setTitle("P33");
        paper1.setFinalDecision("Accept");
        author.addPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P34");
        paper2.setFinalDecision("Reject");
        author.addPaper(paper2);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Create Author A008
        Author author = new Author();
        author.setName("A008");
        
        // Create Papers P35-P37 with REJECT decisions
        Paper paper1 = new Paper();
        paper1.setTitle("P35");
        paper1.setFinalDecision("Reject");
        author.addPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P36");
        paper2.setFinalDecision("Reject");
        author.addPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P37");
        paper3.setFinalDecision("Reject");
        author.addPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.00
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Create Author A009
        Author author = new Author();
        author.setName("A009");
        
        // Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Paper paper1 = new Paper();
        paper1.setTitle("P38");
        paper1.setFinalDecision("Accept");
        author.addPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P39");
        paper2.setFinalDecision("Reject");
        author.addPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P40");
        paper3.setFinalDecision("Reject");
        author.addPaper(paper3);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 0.33 (1/3 ≈ 0.333...)
        assertEquals(0.33, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Create Author A010
        Author author = new Author();
        author.setName("A010");
        
        // Create Paper P41 with ACCEPT decision
        Paper paper = new Paper();
        paper.setTitle("P41");
        paper.setFinalDecision("Accept");
        author.addPaper(paper);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}