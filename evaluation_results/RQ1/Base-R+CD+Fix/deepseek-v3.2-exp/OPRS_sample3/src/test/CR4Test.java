import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;

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
        
        // Create and add papers with ACCEPT decisions
        Paper paper30 = new Paper();
        paper30.setDecision(Grade.ACCEPT);
        author.submitPaper(paper30);
        
        Paper paper31 = new Paper();
        paper31.setDecision(Grade.ACCEPT);
        author.submitPaper(paper31);
        
        Paper paper32 = new Paper();
        paper32.setDecision(Grade.ACCEPT);
        author.submitPaper(paper32);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.01);
    }
    
    @Test
    public void testCase2_50PercentAcceptanceRate() {
        // Test Case 2: "50% acceptance rate"
        // Setup: Create Author A007 and Papers P33 (ACCEPT), P34 (REJECT)
        author = new Author();
        
        // Create and add papers with mixed decisions
        Paper paper33 = new Paper();
        paper33.setDecision(Grade.ACCEPT);
        author.submitPaper(paper33);
        
        Paper paper34 = new Paper();
        paper34.setDecision(Grade.REJECT);
        author.submitPaper(paper34);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, result, 0.01);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Test Case 3: "No accepted papers"
        // Setup: Create Author A008 and Papers P35-P37 with REJECT decisions
        author = new Author();
        
        // Create and add papers with REJECT decisions
        Paper paper35 = new Paper();
        paper35.setDecision(Grade.REJECT);
        author.submitPaper(paper35);
        
        Paper paper36 = new Paper();
        paper36.setDecision(Grade.REJECT);
        author.submitPaper(paper36);
        
        Paper paper37 = new Paper();
        paper37.setDecision(Grade.REJECT);
        author.submitPaper(paper37);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.01);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Test Case 4: "Mixed decisions with 1 acceptance"
        // Setup: Create Author A009 and Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        author = new Author();
        
        // Create and add papers with mixed decisions
        Paper paper38 = new Paper();
        paper38.setDecision(Grade.ACCEPT);
        author.submitPaper(paper38);
        
        Paper paper39 = new Paper();
        paper39.setDecision(Grade.REJECT);
        author.submitPaper(paper39);
        
        Paper paper40 = new Paper();
        paper40.setDecision(Grade.REJECT);
        author.submitPaper(paper40);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Expected Output: 0.33 (1/3 ≈ 0.333...)
        assertEquals(0.33, result, 0.01);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Test Case 5: "Single paper author"
        // Setup: Create Author A010 and Paper P41 with ACCEPT decision
        author = new Author();
        
        // Create and add single paper with ACCEPT decision
        Paper paper41 = new Paper();
        paper41.setDecision(Grade.ACCEPT);
        author.submitPaper(paper41);
        
        // Calculate acceptance rate
        double result = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, result, 0.01);
    }
    
    @Test
    public void testCase6_AuthorWithNoPapers() {
        // Additional edge case: Author with no papers should return 0.0
        author = new Author();
        
        // Calculate acceptance rate for author with no papers
        double result = author.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, result, 0.01);
    }
}