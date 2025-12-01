import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Test Case 1: "Perfect acceptance rate"
        // Input: Calculate acceptance rate for Author A006
        // Setup: Create Author A006 and Papers P30-P32 with final decision=ACCEPT
        // Expected Output: 1.00
        
        // Create Author A006
        Author author = new Author();
        
        // Create Papers P30-P32 with ACCEPT decisions
        Paper paper30 = new Paper();
        paper30.setDecision(Grade.ACCEPT);
        
        Paper paper31 = new Paper();
        paper31.setDecision(Grade.ACCEPT);
        
        Paper paper32 = new Paper();
        paper32.setDecision(Grade.ACCEPT);
        
        // Submit papers to author
        author.submitPaper(paper30);
        author.submitPaper(paper31);
        author.submitPaper(paper32);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Test Case 2: "50% acceptance rate"
        // Input: Calculate acceptance rate for Author A007
        // Setup: Create Author A007 and Papers P33 (ACCEPT), P34 (REJECT)
        // Expected Output: 0.50
        
        // Create Author A007
        Author author = new Author();
        
        // Create Papers P33 (ACCEPT), P34 (REJECT)
        Paper paper33 = new Paper();
        paper33.setDecision(Grade.ACCEPT);
        
        Paper paper34 = new Paper();
        paper34.setDecision(Grade.REJECT);
        
        // Submit papers to author
        author.submitPaper(paper33);
        author.submitPaper(paper34);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Test Case 3: "No accepted papers"
        // Input: Calculate acceptance rate for Author A008
        // Setup: Create Author A008 and Papers P35-P37 with REJECT decisions
        // Expected Output: 0.00
        
        // Create Author A008
        Author author = new Author();
        
        // Create Papers P35-P37 with REJECT decisions
        Paper paper35 = new Paper();
        paper35.setDecision(Grade.REJECT);
        
        Paper paper36 = new Paper();
        paper36.setDecision(Grade.REJECT);
        
        Paper paper37 = new Paper();
        paper37.setDecision(Grade.REJECT);
        
        // Submit papers to author
        author.submitPaper(paper35);
        author.submitPaper(paper36);
        author.submitPaper(paper37);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Test Case 4: "Mixed decisions with 1 acceptance"
        // Input: Calculate acceptance rate for Author A009
        // Setup: Create Author A009 and Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        // Expected Output: 0.33
        
        // Create Author A009
        Author author = new Author();
        
        // Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Paper paper38 = new Paper();
        paper38.setDecision(Grade.ACCEPT);
        
        Paper paper39 = new Paper();
        paper39.setDecision(Grade.REJECT);
        
        Paper paper40 = new Paper();
        paper40.setDecision(Grade.REJECT);
        
        // Submit papers to author
        author.submitPaper(paper38);
        author.submitPaper(paper39);
        author.submitPaper(paper40);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output (1/3 = 0.333...)
        assertEquals(0.33, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Test Case 5: "Single paper author"
        // Input: Calculate acceptance rate for Author A010
        // Setup: Create Author A010 and Paper P41 with ACCEPT decision
        // Expected Output: 1.00
        
        // Create Author A010
        Author author = new Author();
        
        // Create Paper P41 with ACCEPT decision
        Paper paper41 = new Paper();
        paper41.setDecision(Grade.ACCEPT);
        
        // Submit paper to author
        author.submitPaper(paper41);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Verify expected output
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}