import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Author authorA001;
    private Author authorA002;
    private Author authorA003;
    private Author authorA004;
    private Author authorA005;
    private Author authorA006;
    
    @Before
    public void setUp() {
        // Initialize authors for test cases
        authorA001 = new Author();
        authorA002 = new Author();
        authorA003 = new Author();
        authorA004 = new Author();
        authorA005 = new Author();
        authorA006 = new Author();
    }
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Setup: Create Author A001 with 5 papers (P19-P23)
        Paper p19 = new Paper("P19", PaperType.RESEARCH);
        Paper p20 = new Paper("P20", PaperType.RESEARCH);
        Paper p21 = new Paper("P21", PaperType.EXPERIENCE);
        Paper p22 = new Paper("P22", PaperType.RESEARCH);
        Paper p23 = new Paper("P23", PaperType.EXPERIENCE);
        
        authorA001.submitPaper(p19);
        authorA001.submitPaper(p20);
        authorA001.submitPaper(p21);
        authorA001.submitPaper(p22);
        authorA001.submitPaper(p23);
        
        // Input: Count papers for Author A001
        int result = authorA001.countSubmittedPapers();
        
        // Expected Output: 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Setup: Create Author A002 with no papers
        
        // Input: Count papers for Author A002
        int result = authorA002.countSubmittedPapers();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Setup: Create Author A003 with 1 paper (P24)
        Paper p24 = new Paper("P24", PaperType.RESEARCH);
        authorA003.submitPaper(p24);
        
        // Input: Count papers for Author A003
        int result = authorA003.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Setup: Create User U1 with Author and Reviewer roles
        User userU1 = new User("U1");
        Author authorRole = new Author();
        Reviewer reviewerRole = new Reviewer();
        
        // Add both roles to the user
        userU1.addRole(authorRole);
        userU1.addRole(reviewerRole);
        
        // Create Papers P25-P27 linked to U1 (through the author role)
        Paper p25 = new Paper("P25", PaperType.RESEARCH);
        Paper p26 = new Paper("P26", PaperType.EXPERIENCE);
        Paper p27 = new Paper("P27", PaperType.RESEARCH);
        
        authorRole.submitPaper(p25);
        authorRole.submitPaper(p26);
        authorRole.submitPaper(p27);
        
        // Input: Count papers for Author A004 (using the author role)
        int result = authorRole.countSubmittedPapers();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Setup: Create Author A005 with Paper P28
        Paper p28 = new Paper("P28", PaperType.RESEARCH);
        authorA005.submitPaper(p28);
        
        // Setup: Create Author A006 with Paper P29
        Paper p29 = new Paper("P29", PaperType.EXPERIENCE);
        authorA006.submitPaper(p29);
        
        // Input: Count papers for Author A005
        int result = authorA005.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
        
        // Verify that Author A006 has exactly 1 paper (additional validation)
        int resultA006 = authorA006.countSubmittedPapers();
        assertEquals(1, resultA006);
    }
}