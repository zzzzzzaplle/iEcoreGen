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
        // Create test authors
        authorA006 = new Author();
        authorA007 = new Author();
        authorA008 = new Author();
        authorA009 = new Author();
        authorA010 = new Author();
        
        // Set up Test Case 1: Perfect acceptance rate
        Paper p30 = new Paper();
        p30.setDecision(Grade.ACCEPT);
        authorA006.submitPaper(p30);
        
        Paper p31 = new Paper();
        p31.setDecision(Grade.ACCEPT);
        authorA006.submitPaper(p31);
        
        Paper p32 = new Paper();
        p32.setDecision(Grade.ACCEPT);
        authorA006.submitPaper(p32);
        
        // Set up Test Case 2: 50% acceptance rate
        Paper p33 = new Paper();
        p33.setDecision(Grade.ACCEPT);
        authorA007.submitPaper(p33);
        
        Paper p34 = new Paper();
        p34.setDecision(Grade.REJECT);
        authorA007.submitPaper(p34);
        
        // Set up Test Case 3: No accepted papers
        Paper p35 = new Paper();
        p35.setDecision(Grade.REJECT);
        authorA008.submitPaper(p35);
        
        Paper p36 = new Paper();
        p36.setDecision(Grade.REJECT);
        authorA008.submitPaper(p36);
        
        Paper p37 = new Paper();
        p37.setDecision(Grade.REJECT);
        authorA008.submitPaper(p37);
        
        // Set up Test Case 4: Mixed decisions with 1 acceptance
        Paper p38 = new Paper();
        p38.setDecision(Grade.ACCEPT);
        authorA009.submitPaper(p38);
        
        Paper p39 = new Paper();
        p39.setDecision(Grade.REJECT);
        authorA009.submitPaper(p39);
        
        Paper p40 = new Paper();
        p40.setDecision(Grade.REJECT);
        authorA009.submitPaper(p40);
        
        // Set up Test Case 5: Single paper author
        Paper p41 = new Paper();
        p41.setDecision(Grade.ACCEPT);
        authorA010.submitPaper(p41);
    }
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Test perfect acceptance rate: all papers accepted
        double result = authorA006.calculateAcceptanceRate();
        assertEquals("Perfect acceptance rate should be 1.00", 1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Test 50% acceptance rate: 1 accepted, 1 rejected
        double result = authorA007.calculateAcceptanceRate();
        assertEquals("50% acceptance rate should be 0.50", 0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Test no accepted papers: all papers rejected
        double result = authorA008.calculateAcceptanceRate();
        assertEquals("No accepted papers should give 0.00", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Test mixed decisions: 1 accepted, 2 rejected (0.33)
        double result = authorA009.calculateAcceptanceRate();
        assertEquals("1 accepted out of 3 papers should give 0.33", 0.333, result, 0.001);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Test single paper author: 1 accepted paper
        double result = authorA010.calculateAcceptanceRate();
        assertEquals("Single accepted paper should give 1.00", 1.00, result, 0.001);
    }
}