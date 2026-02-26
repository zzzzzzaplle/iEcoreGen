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
        // Setup common test objects
        authorA001 = new Author();
        authorA002 = new Author();
        authorA003 = new Author();
        userU1 = new User();
        authorA004 = new Author();
        authorA005 = new Author();
        authorA006 = new Author();
    }
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        for (int i = 0; i < 5; i++) {
            Paper paper = new Paper();
            authorA001.submitPaper(paper);
        }
        
        // Input: Count papers for Author A001
        int result = authorA001.countSubmittedPapers();
        
        // Expected Output: 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Setup: Author A002 has no papers (default state)
        
        // Input: Count papers for Author A002
        int result = authorA002.countSubmittedPapers();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Setup: Create Author A003 and Paper P24 linked to A003
        Paper paperP24 = new Paper();
        authorA003.submitPaper(paperP24);
        
        // Input: Count papers for Author A003
        int result = authorA003.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Setup: Create User U1 with Author and Reviewer roles
        Author authorRole = new Author();
        Reviewer reviewerRole = new Reviewer();
        userU1.addRole(authorRole);
        userU1.addRole(reviewerRole);
        
        // Setup: Create Papers P25-P27 linked to U1's author role
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper();
            authorRole.submitPaper(paper);
        }
        
        // Input: Count papers for Author A004 (using the author role from U1)
        int result = authorRole.countSubmittedPapers();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Setup: Create Author A005 and Paper P28 (A005)
        Paper paperP28 = new Paper();
        authorA005.submitPaper(paperP28);
        
        // Setup: Create Author A006 and Paper P29 (A006)
        Paper paperP29 = new Paper();
        authorA006.submitPaper(paperP29);
        
        // Input: Count papers for Author A005
        int result = authorA005.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
        
        // Additional validation: Ensure A006 has exactly 1 paper
        assertEquals(1, authorA006.countSubmittedPapers());
    }
}