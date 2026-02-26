import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Create Author A006
        Author author = new Author();
        
        // Create Papers P30-P32 with final decision=ACCEPT
        Paper paper30 = new Paper();
        paper30.setDecision(Grade.ACCEPT);
        author.submitPaper(paper30);
        
        Paper paper31 = new Paper();
        paper31.setDecision(Grade.ACCEPT);
        author.submitPaper(paper31);
        
        Paper paper32 = new Paper();
        paper32.setDecision(Grade.ACCEPT);
        author.submitPaper(paper32);
        
        // Calculate acceptance rate and verify expected output
        double result = author.calculateAcceptanceRate();
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Create Author A007
        Author author = new Author();
        
        // Create Papers P33 (ACCEPT), P34 (REJECT)
        Paper paper33 = new Paper();
        paper33.setDecision(Grade.ACCEPT);
        author.submitPaper(paper33);
        
        Paper paper34 = new Paper();
        paper34.setDecision(Grade.REJECT);
        author.submitPaper(paper34);
        
        // Calculate acceptance rate and verify expected output
        double result = author.calculateAcceptanceRate();
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Create Author A008
        Author author = new Author();
        
        // Create Papers P35-P37 with REJECT decisions
        Paper paper35 = new Paper();
        paper35.setDecision(Grade.REJECT);
        author.submitPaper(paper35);
        
        Paper paper36 = new Paper();
        paper36.setDecision(Grade.REJECT);
        author.submitPaper(paper36);
        
        Paper paper37 = new Paper();
        paper37.setDecision(Grade.REJECT);
        author.submitPaper(paper37);
        
        // Calculate acceptance rate and verify expected output
        double result = author.calculateAcceptanceRate();
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Create Author A009
        Author author = new Author();
        
        // Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Paper paper38 = new Paper();
        paper38.setDecision(Grade.ACCEPT);
        author.submitPaper(paper38);
        
        Paper paper39 = new Paper();
        paper39.setDecision(Grade.REJECT);
        author.submitPaper(paper39);
        
        Paper paper40 = new Paper();
        paper40.setDecision(Grade.REJECT);
        author.submitPaper(paper40);
        
        // Calculate acceptance rate and verify expected output
        double result = author.calculateAcceptanceRate();
        assertEquals(0.333, result, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Create Author A010
        Author author = new Author();
        
        // Create Paper P41 with ACCEPT decision
        Paper paper41 = new Paper();
        paper41.setDecision(Grade.ACCEPT);
        author.submitPaper(paper41);
        
        // Calculate acceptance rate and verify expected output
        double result = author.calculateAcceptanceRate();
        assertEquals(1.00, result, 0.001);
    }
}