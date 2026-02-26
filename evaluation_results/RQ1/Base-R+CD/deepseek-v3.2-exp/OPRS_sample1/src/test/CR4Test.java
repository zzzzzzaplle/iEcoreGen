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
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        author.setName("A006");
        
        Paper paper30 = new Paper();
        paper30.setTitle("P30");
        paper30.setDecision(Grade.ACCEPT);
        
        Paper paper31 = new Paper();
        paper31.setTitle("P31");
        paper31.setDecision(Grade.ACCEPT);
        
        Paper paper32 = new Paper();
        paper32.setTitle("P32");
        paper32.setDecision(Grade.ACCEPT);
        
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
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        author.setName("A007");
        
        Paper paper33 = new Paper();
        paper33.setTitle("P33");
        paper33.setDecision(Grade.ACCEPT);
        
        Paper paper34 = new Paper();
        paper34.setTitle("P34");
        paper34.setDecision(Grade.REJECT);
        
        author.submitPaper(paper33);
        author.submitPaper(paper34);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        author.setName("A008");
        
        Paper paper35 = new Paper();
        paper35.setTitle("P35");
        paper35.setDecision(Grade.REJECT);
        
        Paper paper36 = new Paper();
        paper36.setTitle("P36");
        paper36.setDecision(Grade.REJECT);
        
        Paper paper37 = new Paper();
        paper37.setTitle("P37");
        paper37.setDecision(Grade.REJECT);
        
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
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        author.setName("A009");
        
        Paper paper38 = new Paper();
        paper38.setTitle("P38");
        paper38.setDecision(Grade.ACCEPT);
        
        Paper paper39 = new Paper();
        paper39.setTitle("P39");
        paper39.setDecision(Grade.REJECT);
        
        Paper paper40 = new Paper();
        paper40.setTitle("P40");
        paper40.setDecision(Grade.REJECT);
        
        author.submitPaper(paper38);
        author.submitPaper(paper39);
        author.submitPaper(paper40);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 0.33 (1 acceptance out of 3 papers)
        assertEquals(0.333, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        author.setName("A010");
        
        Paper paper41 = new Paper();
        paper41.setTitle("P41");
        paper41.setDecision(Grade.ACCEPT);
        
        author.submitPaper(paper41);
        
        // Calculate acceptance rate
        double acceptanceRate = author.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}