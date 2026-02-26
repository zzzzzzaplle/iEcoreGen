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
        // Setup for Test Case 1: Perfect acceptance rate
        authorA006 = new Author();
        Paper paperP30 = new Paper();
        paperP30.setDecision(Grade.ACCEPT);
        Paper paperP31 = new Paper();
        paperP31.setDecision(Grade.ACCEPT);
        Paper paperP32 = new Paper();
        paperP32.setDecision(Grade.ACCEPT);
        authorA006.submitPaper(paperP30);
        authorA006.submitPaper(paperP31);
        authorA006.submitPaper(paperP32);
        
        // Setup for Test Case 2: 50% acceptance rate
        authorA007 = new Author();
        Paper paperP33 = new Paper();
        paperP33.setDecision(Grade.ACCEPT);
        Paper paperP34 = new Paper();
        paperP34.setDecision(Grade.REJECT);
        authorA007.submitPaper(paperP33);
        authorA007.submitPaper(paperP34);
        
        // Setup for Test Case 3: No accepted papers
        authorA008 = new Author();
        Paper paperP35 = new Paper();
        paperP35.setDecision(Grade.REJECT);
        Paper paperP36 = new Paper();
        paperP36.setDecision(Grade.REJECT);
        Paper paperP37 = new Paper();
        paperP37.setDecision(Grade.REJECT);
        authorA008.submitPaper(paperP35);
        authorA008.submitPaper(paperP36);
        authorA008.submitPaper(paperP37);
        
        // Setup for Test Case 4: Mixed decisions with 1 acceptance
        authorA009 = new Author();
        Paper paperP38 = new Paper();
        paperP38.setDecision(Grade.ACCEPT);
        Paper paperP39 = new Paper();
        paperP39.setDecision(Grade.REJECT);
        Paper paperP40 = new Paper();
        paperP40.setDecision(Grade.REJECT);
        authorA009.submitPaper(paperP38);
        authorA009.submitPaper(paperP39);
        authorA009.submitPaper(paperP40);
        
        // Setup for Test Case 5: Single paper author
        authorA010 = new Author();
        Paper paperP41 = new Paper();
        paperP41.setDecision(Grade.ACCEPT);
        authorA010.submitPaper(paperP41);
    }
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Test perfect acceptance rate (all papers accepted)
        double acceptanceRate = authorA006.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Test 50% acceptance rate (1 accept, 1 reject)
        double acceptanceRate = authorA007.calculateAcceptanceRate();
        assertEquals(0.50, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Test 0% acceptance rate (all papers rejected)
        double acceptanceRate = authorA008.calculateAcceptanceRate();
        assertEquals(0.00, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Test mixed decisions with 1 acceptance out of 3 papers
        double acceptanceRate = authorA009.calculateAcceptanceRate();
        assertEquals(0.33, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Test single paper author with acceptance
        double acceptanceRate = authorA010.calculateAcceptanceRate();
        assertEquals(1.00, acceptanceRate, 0.01);
    }
}