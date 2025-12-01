import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private ReviewSystemService reviewSystemService;
    
    @Before
    public void setUp() {
        reviewSystemService = new ReviewSystemService();
    }
    
    @Test
    public void testCase1_perfectAcceptanceRate() {
        // Create Author A006
        User authorA006 = new User();
        authorA006.setName("A006");
        
        // Create Papers P30-P32 with final decision=ACCEPT
        Paper paperP30 = new Paper();
        paperP30.setTitle("P30");
        paperP30.setFinalDecision(Decision.ACCEPTED);
        
        Paper paperP31 = new Paper();
        paperP31.setTitle("P31");
        paperP31.setFinalDecision(Decision.ACCEPTED);
        
        Paper paperP32 = new Paper();
        paperP32.setTitle("P32");
        paperP32.setFinalDecision(Decision.ACCEPTED);
        
        // Add papers to author's submitted papers list
        authorA006.setSubmittedPapers(java.util.Arrays.asList(paperP30, paperP31, paperP32));
        
        // Calculate acceptance rate
        double result = reviewSystemService.calculateAcceptanceRate(authorA006);
        
        // Verify expected output: 1.00
        assertEquals(1.00, result, 0.001);
    }
    
    @Test
    public void testCase2_50PercentAcceptanceRate() {
        // Create Author A007
        User authorA007 = new User();
        authorA007.setName("A007");
        
        // Create Papers P33 (ACCEPT), P34 (REJECT)
        Paper paperP33 = new Paper();
        paperP33.setTitle("P33");
        paperP33.setFinalDecision(Decision.ACCEPTED);
        
        Paper paperP34 = new Paper();
        paperP34.setTitle("P34");
        paperP34.setFinalDecision(Decision.REJECTED);
        
        // Add papers to author's submitted papers list
        authorA007.setSubmittedPapers(java.util.Arrays.asList(paperP33, paperP34));
        
        // Calculate acceptance rate
        double result = reviewSystemService.calculateAcceptanceRate(authorA007);
        
        // Verify expected output: 0.50
        assertEquals(0.50, result, 0.001);
    }
    
    @Test
    public void testCase3_noAcceptedPapers() {
        // Create Author A008
        User authorA008 = new User();
        authorA008.setName("A008");
        
        // Create Papers P35-P37 with REJECT decisions
        Paper paperP35 = new Paper();
        paperP35.setTitle("P35");
        paperP35.setFinalDecision(Decision.REJECTED);
        
        Paper paperP36 = new Paper();
        paperP36.setTitle("P36");
        paperP36.setFinalDecision(Decision.REJECTED);
        
        Paper paperP37 = new Paper();
        paperP37.setTitle("P37");
        paperP37.setFinalDecision(Decision.REJECTED);
        
        // Add papers to author's submitted papers list
        authorA008.setSubmittedPapers(java.util.Arrays.asList(paperP35, paperP36, paperP37));
        
        // Calculate acceptance rate
        double result = reviewSystemService.calculateAcceptanceRate(authorA008);
        
        // Verify expected output: 0.00
        assertEquals(0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_mixedDecisionsWithOneAcceptance() {
        // Create Author A009
        User authorA009 = new User();
        authorA009.setName("A009");
        
        // Create Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Paper paperP38 = new Paper();
        paperP38.setTitle("P38");
        paperP38.setFinalDecision(Decision.ACCEPTED);
        
        Paper paperP39 = new Paper();
        paperP39.setTitle("P39");
        paperP39.setFinalDecision(Decision.REJECTED);
        
        Paper paperP40 = new Paper();
        paperP40.setTitle("P40");
        paperP40.setFinalDecision(Decision.REJECTED);
        
        // Add papers to author's submitted papers list
        authorA009.setSubmittedPapers(java.util.Arrays.asList(paperP38, paperP39, paperP40));
        
        // Calculate acceptance rate
        double result = reviewSystemService.calculateAcceptanceRate(authorA009);
        
        // Verify expected output: 0.33 (1/3 ≈ 0.333...)
        assertEquals(0.33, result, 0.01);
    }
    
    @Test
    public void testCase5_singlePaperAuthor() {
        // Create Author A010
        User authorA010 = new User();
        authorA010.setName("A010");
        
        // Create Paper P41 with ACCEPT decision
        Paper paperP41 = new Paper();
        paperP41.setTitle("P41");
        paperP41.setFinalDecision(Decision.ACCEPTED);
        
        // Add paper to author's submitted papers list
        authorA010.setSubmittedPapers(java.util.Arrays.asList(paperP41));
        
        // Calculate acceptance rate
        double result = reviewSystemService.calculateAcceptanceRate(authorA010);
        
        // Verify expected output: 1.00
        assertEquals(1.00, result, 0.001);
    }
}