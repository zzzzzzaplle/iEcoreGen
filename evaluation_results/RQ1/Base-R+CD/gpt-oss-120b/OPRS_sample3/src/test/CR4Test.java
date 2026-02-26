import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    @Before
    public void setUp() {
        // Clear the static list of authors before each test to ensure isolation
        Author.getAllAuthors().clear();
    }
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        Author authorA006 = new Author();
        
        Paper paperP30 = new Paper("P30", PaperType.RESEARCH);
        paperP30.setDecision(Grade.ACCEPT);
        authorA006.submitPaper(paperP30);
        
        Paper paperP31 = new Paper("P31", PaperType.RESEARCH);
        paperP31.setDecision(Grade.ACCEPT);
        authorA006.submitPaper(paperP31);
        
        Paper paperP32 = new Paper("P32", PaperType.RESEARCH);
        paperP32.setDecision(Grade.ACCEPT);
        authorA006.submitPaper(paperP32);
        
        // Calculate acceptance rate
        double acceptanceRate = authorA006.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_50PercentAcceptanceRate() {
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        Author authorA007 = new Author();
        
        Paper paperP33 = new Paper("P33", PaperType.RESEARCH);
        paperP33.setDecision(Grade.ACCEPT);
        authorA007.submitPaper(paperP33);
        
        Paper paperP34 = new Paper("P34", PaperType.RESEARCH);
        paperP34.setDecision(Grade.REJECT);
        authorA007.submitPaper(paperP34);
        
        // Calculate acceptance rate
        double acceptanceRate = authorA007.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        Author authorA008 = new Author();
        
        Paper paperP35 = new Paper("P35", PaperType.RESEARCH);
        paperP35.setDecision(Grade.REJECT);
        authorA008.submitPaper(paperP35);
        
        Paper paperP36 = new Paper("P36", PaperType.RESEARCH);
        paperP36.setDecision(Grade.REJECT);
        authorA008.submitPaper(paperP36);
        
        Paper paperP37 = new Paper("P37", PaperType.RESEARCH);
        paperP37.setDecision(Grade.REJECT);
        authorA008.submitPaper(paperP37);
        
        // Calculate acceptance rate
        double acceptanceRate = authorA008.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        Author authorA009 = new Author();
        
        Paper paperP38 = new Paper("P38", PaperType.RESEARCH);
        paperP38.setDecision(Grade.ACCEPT);
        authorA009.submitPaper(paperP38);
        
        Paper paperP39 = new Paper("P39", PaperType.RESEARCH);
        paperP39.setDecision(Grade.REJECT);
        authorA009.submitPaper(paperP39);
        
        Paper paperP40 = new Paper("P40", PaperType.RESEARCH);
        paperP40.setDecision(Grade.REJECT);
        authorA009.submitPaper(paperP40);
        
        // Calculate acceptance rate
        double acceptanceRate = authorA009.calculateAcceptanceRate();
        
        // Expected Output: 0.33 (1 acceptance out of 3 papers)
        assertEquals(0.33, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        Author authorA010 = new Author();
        
        Paper paperP41 = new Paper("P41", PaperType.RESEARCH);
        paperP41.setDecision(Grade.ACCEPT);
        authorA010.submitPaper(paperP41);
        
        // Calculate acceptance rate
        double acceptanceRate = authorA010.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}