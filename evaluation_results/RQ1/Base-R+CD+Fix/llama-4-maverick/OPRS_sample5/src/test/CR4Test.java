import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Author authorA006;
    private Author authorA007;
    private Author authorA008;
    private Author authorA009;
    private Author authorA010;
    
    @Before
    public void setUp() {
        // Initialize authors
        authorA006 = new Author();
        authorA007 = new Author();
        authorA008 = new Author();
        authorA009 = new Author();
        authorA010 = new Author();
        
        // Setup papers for Author A006 (Perfect acceptance rate)
        Paper p30 = new Paper();
        p30.setDecision(Grade.ACCEPT);
        authorA006.submitPaper(p30);
        
        Paper p31 = new Paper();
        p31.setDecision(Grade.ACCEPT);
        authorA006.submitPaper(p31);
        
        Paper p32 = new Paper();
        p32.setDecision(Grade.ACCEPT);
        authorA006.submitPaper(p32);
        
        // Setup papers for Author A007 (50% acceptance rate)
        Paper p33 = new Paper();
        p33.setDecision(Grade.ACCEPT);
        authorA007.submitPaper(p33);
        
        Paper p34 = new Paper();
        p34.setDecision(Grade.REJECT);
        authorA007.submitPaper(p34);
        
        // Setup papers for Author A008 (No accepted papers)
        Paper p35 = new Paper();
        p35.setDecision(Grade.REJECT);
        authorA008.submitPaper(p35);
        
        Paper p36 = new Paper();
        p36.setDecision(Grade.REJECT);
        authorA008.submitPaper(p36);
        
        Paper p37 = new Paper();
        p37.setDecision(Grade.REJECT);
        authorA008.submitPaper(p37);
        
        // Setup papers for Author A009 (Mixed decisions with 1 acceptance)
        Paper p38 = new Paper();
        p38.setDecision(Grade.ACCEPT);
        authorA009.submitPaper(p38);
        
        Paper p39 = new Paper();
        p39.setDecision(Grade.REJECT);
        authorA009.submitPaper(p39);
        
        Paper p40 = new Paper();
        p40.setDecision(Grade.REJECT);
        authorA009.submitPaper(p40);
        
        // Setup paper for Author A010 (Single paper author)
        Paper p41 = new Paper();
        p41.setDecision(Grade.ACCEPT);
        authorA010.submitPaper(p41);
    }
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Test perfect acceptance rate for Author A006
        double acceptanceRate = authorA006.calculateAcceptanceRate();
        assertEquals("Perfect acceptance rate should be 1.00", 1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Test 50% acceptance rate for Author A007
        double acceptanceRate = authorA007.calculateAcceptanceRate();
        assertEquals("50% acceptance rate should be 0.50", 0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Test no accepted papers for Author A008
        double acceptanceRate = authorA008.calculateAcceptanceRate();
        assertEquals("No accepted papers should give 0.00", 0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Test mixed decisions with 1 acceptance for Author A009
        double acceptanceRate = authorA009.calculateAcceptanceRate();
        assertEquals("1 acceptance out of 3 papers should give 0.33", 0.33, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Test single paper author with acceptance for Author A010
        double acceptanceRate = authorA010.calculateAcceptanceRate();
        assertEquals("Single accepted paper should give 1.00", 1.00, acceptanceRate, 0.001);
    }
}