import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Test Case 1: "Perfect acceptance rate"
        // Setup: Create Author A006 with Papers P30-P32 all with ACCEPT decisions
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
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_50PercentAcceptanceRate() {
        // Test Case 2: "50% acceptance rate"
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        Author author = new Author();
        
        // Create papers with mixed decisions
        Paper paper1 = new Paper();
        paper1.setDecision(Grade.ACCEPT);
        Paper paper2 = new Paper();
        paper2.setDecision(Grade.REJECT);
        
        // Submit papers to author
        author.submitPaper(paper1);
        author.submitPaper(paper2);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Test Case 3: "No accepted papers"
        // Setup: Create Author A008 with Papers P35-P37 all with REJECT decisions
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
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Test Case 4: "Mixed decisions with 1 acceptance"
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
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.33 (approximately 1/3)
        assertEquals(0.333, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Test Case 5: "Single paper author"
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        Author author = new Author();
        
        // Create single paper with ACCEPT decision
        Paper paper = new Paper();
        paper.setDecision(Grade.ACCEPT);
        
        // Submit paper to author
        author.submitPaper(paper);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}