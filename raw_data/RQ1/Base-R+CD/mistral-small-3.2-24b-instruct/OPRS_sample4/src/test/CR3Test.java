import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        authorA005 = new Author();
        authorA006 = new Author();
        
        // Initialize user with multiple roles
        userU1 = new User();
        authorA004 = new Author();
        userU1.addRole(authorA004);
        userU1.addRole(new Reviewer());
    }
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Setup: Create Author A001 with Papers P19-P23
        authorA001.setPapers(new ArrayList<>());
        
        // Create 5 papers and add them to author A001
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            authorA001.submitPaper(paper);
        }
        
        // Expected Output: 5
        int result = authorA001.countSubmittedPapers();
        assertEquals("Author with 5 papers should return count 5", 5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Setup: Create Author A002 with no papers
        authorA002.setPapers(new ArrayList<>());
        
        // Expected Output: 0
        int result = authorA002.countSubmittedPapers();
        assertEquals("New author with 0 papers should return count 0", 0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Setup: Create Author A003 with Paper P24
        authorA003.setPapers(new ArrayList<>());
        
        Paper paperP24 = new Paper();
        paperP24.setTitle("P24");
        authorA003.submitPaper(paperP24);
        
        // Expected Output: 1
        int result = authorA003.countSubmittedPapers();
        assertEquals("Author with 1 paper should return count 1", 1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Setup: Create User U1 with Author and Reviewer roles, Papers P25-P27 linked to U1
        authorA004.setPapers(new ArrayList<>());
        
        // Add 3 papers to the author role of user U1
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            authorA004.submitPaper(paper);
        }
        
        // Expected Output: 3
        int result = authorA004.countSubmittedPapers();
        assertEquals("Author role of multi-role user with 3 papers should return count 3", 3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Setup: Create Author A005 with Paper P28, Author A006 with Paper P29
        authorA005.setPapers(new ArrayList<>());
        authorA006.setPapers(new ArrayList<>());
        
        // Create Paper P28 for Author A005
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.submitPaper(paperP28);
        
        // Create Paper P29 for Author A006 (should not be counted for A005)
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.submitPaper(paperP29);
        
        // Expected Output: 1 (only P28 should be counted for A005)
        int result = authorA005.countSubmittedPapers();
        assertEquals("Author A005 should have only 1 paper (P28)", 1, result);
    }
}