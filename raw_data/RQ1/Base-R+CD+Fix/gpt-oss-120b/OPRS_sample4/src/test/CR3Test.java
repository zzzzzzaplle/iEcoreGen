import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Author authorA001;
    private Author authorA002;
    private Author authorA003;
    private User userU1;
    private Author authorA004;
    private Author authorA005;
    private Author authorA006;
    
    @Before
    public void setUp() {
        // Initialize authors for test cases
        authorA001 = new Author();
        authorA002 = new Author();
        authorA003 = new Author();
        
        // Setup multi-role user for test case 4
        userU1 = new User("U1");
        authorA004 = new Author();
        userU1.addRole(authorA004);
        
        // Setup authors for test case 5
        authorA005 = new Author();
        authorA006 = new Author();
    }
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Paper p19 = new Paper("Paper 19", true);
        Paper p20 = new Paper("Paper 20", true);
        Paper p21 = new Paper("Paper 21", false);
        Paper p22 = new Paper("Paper 22", true);
        Paper p23 = new Paper("Paper 23", false);
        
        authorA001.submitPaper(p19);
        authorA001.submitPaper(p20);
        authorA001.submitPaper(p21);
        authorA001.submitPaper(p22);
        authorA001.submitPaper(p23);
        
        // Input: Count papers for Author A001
        int result = authorA001.countPapers();
        
        // Expected Output: 5
        assertEquals("Author with 5 papers should return count of 5", 5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Setup: Create Author A002 (no papers)
        // Author A002 is already created in setUp()
        
        // Input: Count papers for Author A002
        int result = authorA002.countPapers();
        
        // Expected Output: 0
        assertEquals("New author with 0 papers should return count of 0", 0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Setup: Create Author A003 and Paper P24 linked to A003
        Paper p24 = new Paper("Paper 24", true);
        authorA003.submitPaper(p24);
        
        // Input: Count papers for Author A003
        int result = authorA003.countPapers();
        
        // Expected Output: 1
        assertEquals("Author with 1 paper should return count of 1", 1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Setup: Create User U1 with Author and Reviewer roles, and Papers P25-P27 linked to U1
        Reviewer reviewerRole = new Reviewer();
        userU1.addRole(reviewerRole);
        
        Paper p25 = new Paper("Paper 25", true);
        Paper p26 = new Paper("Paper 26", false);
        Paper p27 = new Paper("Paper 27", true);
        
        authorA004.submitPaper(p25);
        authorA004.submitPaper(p26);
        authorA004.submitPaper(p27);
        
        // Input: Count papers for Author A004
        int result = authorA004.countPapers();
        
        // Expected Output: 3
        assertEquals("Multi-role user with author role and 3 papers should return count of 3", 3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Setup: Create Author A005 and Paper P28 (A005)
        // Create Author A006 and Paper P29 (A006)
        Paper p28 = new Paper("Paper 28", true);
        Paper p29 = new Paper("Paper 29", false);
        
        authorA005.submitPaper(p28);
        authorA006.submitPaper(p29);
        
        // Input: Count papers for Author A005
        int result = authorA005.countPapers();
        
        // Expected Output: 1
        assertEquals("Author A005 should only count their own paper (P28), not A006's paper", 1, result);
    }
}