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
        // Clear the static list of authors before each test to ensure isolation
        // Note: Since ALL_AUTHORS is private, we'll create fresh authors in each test
        // to avoid interference between tests
    }
    
    @Test
    public void testCase1_PerfectAcceptanceRate() {
        // Setup: Create Author A006 with Papers P30-P32 with final decision=ACCEPT
        authorA006 = new Author();
        
        Paper p30 = new Paper("P30", PaperType.RESEARCH);
        p30.setDecision(Grade.ACCEPT);
        Paper p31 = new Paper("P31", PaperType.RESEARCH);
        p31.setDecision(Grade.ACCEPT);
        Paper p32 = new Paper("P32", PaperType.RESEARCH);
        p32.setDecision(Grade.ACCEPT);
        
        authorA006.submitPaper(p30);
        authorA006.submitPaper(p31);
        authorA006.submitPaper(p32);
        
        // Calculate acceptance rate
        double acceptanceRate = authorA006.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase2_FiftyPercentAcceptanceRate() {
        // Setup: Create Author A007 with Papers P33 (ACCEPT), P34 (REJECT)
        authorA007 = new Author();
        
        Paper p33 = new Paper("P33", PaperType.RESEARCH);
        p33.setDecision(Grade.ACCEPT);
        Paper p34 = new Paper("P34", PaperType.RESEARCH);
        p34.setDecision(Grade.REJECT);
        
        authorA007.submitPaper(p33);
        authorA007.submitPaper(p34);
        
        // Calculate acceptance rate
        double acceptanceRate = authorA007.calculateAcceptanceRate();
        
        // Expected Output: 0.50
        assertEquals(0.50, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase3_NoAcceptedPapers() {
        // Setup: Create Author A008 with Papers P35-P37 with REJECT decisions
        authorA008 = new Author();
        
        Paper p35 = new Paper("P35", PaperType.RESEARCH);
        p35.setDecision(Grade.REJECT);
        Paper p36 = new Paper("P36", PaperType.RESEARCH);
        p36.setDecision(Grade.REJECT);
        Paper p37 = new Paper("P37", PaperType.RESEARCH);
        p37.setDecision(Grade.REJECT);
        
        authorA008.submitPaper(p35);
        authorA008.submitPaper(p36);
        authorA008.submitPaper(p37);
        
        // Calculate acceptance rate
        double acceptanceRate = authorA008.calculateAcceptanceRate();
        
        // Expected Output: 0.00
        assertEquals(0.00, acceptanceRate, 0.001);
    }
    
    @Test
    public void testCase4_MixedDecisionsWithOneAcceptance() {
        // Setup: Create Author A009 with Papers P38 (ACCEPT), P39 (REJECT), P40 (REJECT)
        authorA009 = new Author();
        
        Paper p38 = new Paper("P38", PaperType.RESEARCH);
        p38.setDecision(Grade.ACCEPT);
        Paper p39 = new Paper("P39", PaperType.RESEARCH);
        p39.setDecision(Grade.REJECT);
        Paper p40 = new Paper("P40", PaperType.RESEARCH);
        p40.setDecision(Grade.REJECT);
        
        authorA009.submitPaper(p38);
        authorA009.submitPaper(p39);
        authorA009.submitPaper(p40);
        
        // Calculate acceptance rate
        double acceptanceRate = authorA009.calculateAcceptanceRate();
        
        // Expected Output: 0.33 (1 acceptance out of 3 papers)
        assertEquals(0.33, acceptanceRate, 0.01);
    }
    
    @Test
    public void testCase5_SinglePaperAuthor() {
        // Setup: Create Author A010 with Paper P41 with ACCEPT decision
        authorA010 = new Author();
        
        Paper p41 = new Paper("P41", PaperType.RESEARCH);
        p41.setDecision(Grade.ACCEPT);
        
        authorA010.submitPaper(p41);
        
        // Calculate acceptance rate
        double acceptanceRate = authorA010.calculateAcceptanceRate();
        
        // Expected Output: 1.00
        assertEquals(1.00, acceptanceRate, 0.001);
    }
}