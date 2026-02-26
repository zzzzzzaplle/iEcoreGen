import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        Author authorA006 = new Author();
        authorA006.setName("A006");
        
        Paper paperP30 = new Paper();
        paperP30.setFinalDecision(Paper.Decision.ACCEPT);
        
        Paper paperP31 = new Paper();
        paperP31.setFinalDecision(Paper.Decision.ACCEPT);
        
        Paper paperP32 = new Paper();
        paperP32.setFinalDecision(Paper.Decision.ACCEPT);
        
        authorA006.addSubmittedPaper(paperP30);
        authorA006.addSubmittedPaper(paperP31);
        authorA006.addSubmittedPaper(paperP32);
        
        // Calculate acceptance rate for Author A006
        double acceptanceRate = authorA006.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        Author authorA007 = new Author();
        authorA007.setName("A007");
        
        Paper paperP33 = new Paper();
        paperP33.setFinalDecision(Paper.Decision.ACCEPT);
        
        Paper paperP34 = new Paper();
        paperP34.setFinalDecision(Paper.Decision.REJECT);
        
        authorA007.addSubmittedPaper(paperP33);
        authorA007.addSubmittedPaper(paperP34);
        
        // Calculate acceptance rate for Author A007
        double acceptanceRate = authorA007.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        Author authorA008 = new Author();
        authorA008.setName("A008");
        
        Paper paperP35 = new Paper();
        paperP35.setFinalDecision(Paper.Decision.REJECT);
        
        Paper paperP36 = new Paper();
        paperP36.setFinalDecision(Paper.Decision.REJECT);
        
        Paper paperP37 = new Paper();
        paperP37.setFinalDecision(Paper.Decision.REJECT);
        
        authorA008.addSubmittedPaper(paperP35);
        authorA008.addSubmittedPaper(paperP36);
        authorA008.addSubmittedPaper(paperP37);
        
        // Calculate acceptance rate for Author A008
        double acceptanceRate = authorA008.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author authorA009 = new Author();
        authorA009.setName("A009");
        
        Paper paperP38 = new Paper();
        paperP38.setFinalDecision(Paper.Decision.ACCEPT);
        
        Paper paperP39 = new Paper();
        paperP39.setFinalDecision(Paper.Decision.REJECT);
        
        Paper paperP40 = new Paper();
        paperP40.setFinalDecision(Paper.Decision.REJECT);
        
        authorA009.addSubmittedPaper(paperP38);
        authorA009.addSubmittedPaper(paperP39);
        authorA009.addSubmittedPaper(paperP40);
        
        // Calculate acceptance rate for Author A009
        double acceptanceRate = authorA009.calculateAcceptanceRate();
        
        // Expected Output: 0.33
        assertEquals(0.33, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        Author authorA010 = new Author();
        authorA010.setName("A010");
        
        Paper paperP41 = new Paper();
        paperP41.setFinalDecision(Paper.Decision.ACCEPT);
        
        authorA010.addSubmittedPaper(paperP41);
        
        // Calculate acceptance rate for Author A010
        double acceptanceRate = authorA010.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}