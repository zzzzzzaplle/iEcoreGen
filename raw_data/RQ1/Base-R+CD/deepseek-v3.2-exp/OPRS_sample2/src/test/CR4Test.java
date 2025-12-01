import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Author author;
    
    @Before
    public void setUp() {
        author = new Author();
    }
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Test Case 1: "Perfect acceptance rate"
        // Setup: Create Author A006 and Papers P30-P32 with final decision=ACCEPT
        author = new Author();
        
        // Create papers with ACCEPT decisions
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
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Test Case 2: "50% acceptance rate"
        // Setup: Create Author A007 and Papers P33 (ACCEPT), P34 (REJECT)
        author = new Author();
        
        // Create papers with mixed decisions
        Paper paper33 = new Paper();
        paper33.setDecision(Grade.ACCEPT);
        Paper paper34 = new Paper();
        paper34.setDecision(Grade.REJECT);
        
        // Submit papers to author
        author.submitPaper(paper33);
        author.submitPaper(paper34);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Test Case 3: "No accepted papers"
        // Setup: Create Author A008 and Papers P35-P37 with REJECT decisions
        author = new Author();
        
        // Create papers with REJECT decisions
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
        
        // Expected Output: 0.00
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Test Case 4: "Mixed decisions with 1 acceptance"
        // Setup: Create Author A009 and Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        author = new Author();
        
        // Create papers with mixed decisions (1 ACCEPT, 2 REJECT)
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
        
        // Expected Output: 0.33 (1/3 ≈ 0.333...)
        assertEquals(0.33, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Test Case 5: "Single paper author"
        // Setup: Create Author A010 and Paper P41 with ACCEPT decision
        author = new Author();
        
        // Create single paper with ACCEPT decision
        Paper paper41 = new Paper();
        paper41.setDecision(Grade.ACCEPT);
        
        // Submit paper to author
        author.submitPaper(paper41);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}