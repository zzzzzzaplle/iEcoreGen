import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Create Author A006
        Author author = new Author();
        
        // Create Papers P30-P32 with final decision=ACCEPT
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
        double result = author.calculateAcceptanceRate();
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_50PercentAcceptanceRate() {
        // Create Author A007
        Author author = new Author();
        
        // Create Papers P33 (ACCEPT), P34 (REJECT)
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.ACCEPT);
        
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.REJECT);
        
        // Submit papers to author
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        
        // Calculate acceptance rate and verify expected output
        double result = author.calculateAcceptanceRate();
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Create Author A008
        Author author = new Author();
        
        // Create Papers P35-P37 with REJECT decisions
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
        double result = author.calculateAcceptanceRate();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Create Author A009
        Author author = new Author();
        
        // Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
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
        double result = author.calculateAcceptanceRate();
        assertEquals(0.333, result, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Create Author A010
        Author author = new Author();
        
        // Create Paper P41 with ACCEPT decision
        Paper paper = new Paper();
        paper.setDecision(Grade.ACCEPT);
        
        // Submit paper to author
        author.submitPaper(paper);
        
        // Calculate acceptance rate and verify expected output
        double result = author.calculateAcceptanceRate();
        assertEquals(1.00, result, 0.001);
    }
}