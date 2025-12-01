import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Setup: Create Author A006 with Papers P30-P32 all ACCEPT
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
        
        // Verify expected output is 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
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
        
        // Verify expected output is 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Setup: Create Author A008 with Papers P35-P37 all REJECT
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
        
        // Verify expected output is 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
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
        
        // Verify expected output is 0.33 (approximately)
        assertEquals(0.333, result, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Setup: Create Author A010 with Paper P41 ACCEPT
        Author author = new Author();
        
        Paper paper = new Paper();
        paper.setDecision(Grade.ACCEPT);
        
        author.submitPaper(paper);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Verify expected output is 1.00
        assertEquals(1.00, result, 0.001);
    }
}