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
        paper1.setDecision(Decision.ACCEPTED);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P31");
        paper2.setDecision(Decision.ACCEPTED);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P32");
        paper3.setDecision(Decision.ACCEPTED);
        
        // Submit papers to author
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        author.submitPaper(paper3);
        
        // Calculate and verify acceptance rate
        double acceptanceRate = author.getAcceptanceRate();
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
        paper1.setDecision(Decision.ACCEPTED);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P34");
        paper2.setDecision(Decision.REJECTED);
        
        // Submit papers to author
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        
        // Calculate and verify acceptance rate
        double acceptanceRate = author.getAcceptanceRate();
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
        paper1.setDecision(Decision.REJECTED);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P36");
        paper2.setDecision(Decision.REJECTED);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P37");
        paper3.setDecision(Decision.REJECTED);
        
        // Submit papers to author
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        author.submitPaper(paper3);
        
        // Calculate and verify acceptance rate
        double acceptanceRate = author.getAcceptanceRate();
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
        paper1.setDecision(Decision.ACCEPTED);
        
        Paper paper2 = new Paper();
        paper2.setTitle("P39");
        paper2.setDecision(Decision.REJECTED);
        
        Paper paper3 = new Paper();
        paper3.setTitle("P40");
        paper3.setDecision(Decision.REJECTED);
        
        // Submit papers to author
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        author.submitPaper(paper3);
        
        // Calculate and verify acceptance rate
        double acceptanceRate = author.getAcceptanceRate();
        assertEquals(0.333, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Create Author A010
        Author author = new Author();
        author.setName("A010");
        
        // Create Paper P41 with ACCEPT decision
        Paper paper = new Paper();
        paper.setTitle("P41");
        paper.setDecision(Decision.ACCEPTED);
        
        // Submit paper to author
        author.submitPaper(paper);
        
        // Calculate and verify acceptance rate
        double acceptanceRate = author.getAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}