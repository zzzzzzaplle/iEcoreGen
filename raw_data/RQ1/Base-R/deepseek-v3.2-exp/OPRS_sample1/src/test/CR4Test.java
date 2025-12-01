import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Test
    public void testCase1_perfectAcceptanceRate() {
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        User authorA006 = new User();
        authorA006.setName("A006");
        
        Paper paperP30 = new Paper();
        paperP30.setFinalDecision(Decision.ACCEPTED);
        
        Paper paperP31 = new Paper();
        paperP31.setFinalDecision(Decision.ACCEPTED);
        
        Paper paperP32 = new Paper();
        paperP32.setFinalDecision(Decision.ACCEPTED);
        
        authorA006.setSubmittedPapers(java.util.Arrays.asList(paperP30, paperP31, paperP32));
        
        // Test: Calculate acceptance rate for Author A006
        double result = ReviewSystem.calculateAcceptanceRate(authorA006);
        
        // Verify: Expected output is 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_fiftyPercentAcceptanceRate() {
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        User authorA007 = new User();
        authorA007.setName("A007");
        
        Paper paperP33 = new Paper();
        paperP33.setFinalDecision(Decision.ACCEPTED);
        
        Paper paperP34 = new Paper();
        paperP34.setFinalDecision(Decision.REJECTED);
        
        authorA007.setSubmittedPapers(java.util.Arrays.asList(paperP33, paperP34));
        
        // Test: Calculate acceptance rate for Author A007
        double result = ReviewSystem.calculateAcceptanceRate(authorA007);
        
        // Verify: Expected output is 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noAcceptedPapers() {
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        User authorA008 = new User();
        authorA008.setName("A008");
        
        Paper paperP35 = new Paper();
        paperP35.setFinalDecision(Decision.REJECTED);
        
        Paper paperP36 = new Paper();
        paperP36.setFinalDecision(Decision.REJECTED);
        
        Paper paperP37 = new Paper();
        paperP37.setFinalDecision(Decision.REJECTED);
        
        authorA008.setSubmittedPapers(java.util.Arrays.asList(paperP35, paperP36, paperP37));
        
        // Test: Calculate acceptance rate for Author A008
        double result = ReviewSystem.calculateAcceptanceRate(authorA008);
        
        // Verify: Expected output is 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_mixedDecisionsWithOneAcceptance() {
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        User authorA009 = new User();
        authorA009.setName("A009");
        
        Paper paperP38 = new Paper();
        paperP38.setFinalDecision(Decision.ACCEPTED);
        
        Paper paperP39 = new Paper();
        paperP39.setFinalDecision(Decision.REJECTED);
        
        Paper paperP40 = new Paper();
        paperP40.setFinalDecision(Decision.REJECTED);
        
        authorA009.setSubmittedPapers(java.util.Arrays.asList(paperP38, paperP39, paperP40));
        
        // Test: Calculate acceptance rate for Author A009
        double result = ReviewSystem.calculateAcceptanceRate(authorA009);
        
        // Verify: Expected output is 0.33 (1/3 ≈ 0.333...)
        assertEquals(0.33, result, 0.01);
    }
    
    @Test
    public void testCase5_singlePaperAuthor() {
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        User authorA010 = new User();
        authorA010.setName("A010");
        
        Paper paperP41 = new Paper();
        paperP41.setFinalDecision(Decision.ACCEPTED);
        
        authorA010.setSubmittedPapers(java.util.Arrays.asList(paperP41));
        
        // Test: Calculate acceptance rate for Author A010
        double result = ReviewSystem.calculateAcceptanceRate(authorA010);
        
        // Verify: Expected output is 1.00
        assertEquals(1.00, result, 0.001);
    }
}