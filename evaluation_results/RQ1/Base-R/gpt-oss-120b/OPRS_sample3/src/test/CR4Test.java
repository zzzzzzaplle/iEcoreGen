import org.junit.Test;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Create Author A006
        Author author = new Author("A006");
        
        // Create Papers P30-P32 with final decision=ACCEPT
        Paper paper1 = new Paper();
        paper1.setTitle("P30");
        paper1.setFinalDecision(Grade.ACCEPT);
        author.submitPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P31");
        paper2.setFinalDecision(Grade.ACCEPT);
        author.submitPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P32");
        paper3.setFinalDecision(Grade.ACCEPT);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.getAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_50PercentAcceptanceRate() {
        // Create Author A007
        Author author = new Author("A007");
        
        // Create Papers P33 (ACCEPT), P34 (REJECT)
        Paper paper1 = new Paper();
        paper1.setTitle("P33");
        paper1.setFinalDecision(Grade.ACCEPT);
        author.submitPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P34");
        paper2.setFinalDecision(Grade.REJECT);
        author.submitPaper(paper2);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.getAcceptanceRate();
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Create Author A008
        Author author = new Author("A008");
        
        // Create Papers P35-P37 with REJECT decisions
        Paper paper1 = new Paper();
        paper1.setTitle("P35");
        paper1.setFinalDecision(Grade.REJECT);
        author.submitPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P36");
        paper2.setFinalDecision(Grade.REJECT);
        author.submitPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P37");
        paper3.setFinalDecision(Grade.REJECT);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.getAcceptanceRate();
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Create Author A009
        Author author = new Author("A009");
        
        // Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Paper paper1 = new Paper();
        paper1.setTitle("P38");
        paper1.setFinalDecision(Grade.ACCEPT);
        author.submitPaper(paper1);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P39");
        paper2.setFinalDecision(Grade.REJECT);
        author.submitPaper(paper2);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P40");
        paper3.setFinalDecision(Grade.REJECT);
        author.submitPaper(paper3);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.getAcceptanceRate();
        assertEquals(0.333, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Create Author A010
        Author author = new Author("A010");
        
        // Create Paper P41 with ACCEPT decision
        Paper paper = new Paper();
        paper.setTitle("P41");
        paper.setFinalDecision(Grade.ACCEPT);
        author.submitPaper(paper);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = author.getAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}