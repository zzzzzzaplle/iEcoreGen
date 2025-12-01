import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Author authorA006;
    private Author authorA007;
    private Author authorA008;
    private Author authorA009;
    private Author authorA010;
    
    @Before
    public void setUp() {
        // Initialize authors for each test case
        authorA006 = new Author();
        authorA007 = new Author();
        authorA008 = new Author();
        authorA009 = new Author();
        authorA010 = new Author();
    }
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Test Case 1: "Perfect acceptance rate"
        // Setup: Create Papers P30-P32 with final decision=ACCEPT
        Paper paperP30 = new Paper();
        paperP30.setDecision(Grade.ACCEPT);
        authorA006.submitPaper(paperP30);
        
        Paper paperP31 = new Paper();
        paperP31.setDecision(Grade.ACCEPT);
        authorA006.submitPaper(paperP31);
        
        Paper paperP32 = new Paper();
        paperP32.setDecision(Grade.ACCEPT);
        authorA006.submitPaper(paperP32);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = authorA006.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Test Case 2: "50% acceptance rate"
        // Setup: Create Papers P33 (ACCEPT), P34 (REJECT)
        Paper paperP33 = new Paper();
        paperP33.setDecision(Grade.ACCEPT);
        authorA007.submitPaper(paperP33);
        
        Paper paperP34 = new Paper();
        paperP34.setDecision(Grade.REJECT);
        authorA007.submitPaper(paperP34);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = authorA007.calculateAcceptanceRate();
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Test Case 3: "No accepted papers"
        // Setup: Create Papers P35-P37 with REJECT decisions
        Paper paperP35 = new Paper();
        paperP35.setDecision(Grade.REJECT);
        authorA008.submitPaper(paperP35);
        
        Paper paperP36 = new Paper();
        paperP36.setDecision(Grade.REJECT);
        authorA008.submitPaper(paperP36);
        
        Paper paperP37 = new Paper();
        paperP37.setDecision(Grade.REJECT);
        authorA008.submitPaper(paperP37);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = authorA008.calculateAcceptanceRate();
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Test Case 4: "Mixed decisions with 1 acceptance"
        // Setup: Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Paper paperP38 = new Paper();
        paperP38.setDecision(Grade.ACCEPT);
        authorA009.submitPaper(paperP38);
        
        Paper paperP39 = new Paper();
        paperP39.setDecision(Grade.REJECT);
        authorA009.submitPaper(paperP39);
        
        Paper paperP40 = new Paper();
        paperP40.setDecision(Grade.REJECT);
        authorA009.submitPaper(paperP40);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = authorA009.calculateAcceptanceRate();
        assertEquals(0.33, acceptanceRate, 0.01); // Using delta of 0.01 for floating point precision
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Test Case 5: "Single paper author"
        // Setup: Create Paper P41 with ACCEPT decision
        Paper paperP41 = new Paper();
        paperP41.setDecision(Grade.ACCEPT);
        authorA010.submitPaper(paperP41);
        
        // Calculate acceptance rate and verify expected output
        double acceptanceRate = authorA010.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}