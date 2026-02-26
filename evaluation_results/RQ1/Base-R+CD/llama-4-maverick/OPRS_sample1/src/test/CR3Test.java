import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Author author;
    
    @Before
    public void setUp() {
        author = new Author();
    }
    
    @Test
    public void testCase1_authorWith5Papers() {
        // Setup: Create Author A001 with 5 papers (P19-P23)
        author = new Author();
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.submitPaper(paper);
        }
        
        // Input: Count papers for Author A001
        int result = author.countSubmittedPapers();
        
        // Expected Output: 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_newAuthorWith0Papers() {
        // Setup: Create Author A002 (no papers)
        author = new Author();
        
        // Input: Count papers for Author A002
        int result = author.countSubmittedPapers();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_singlePaperAuthor() {
        // Setup: Create Author A003 with 1 paper (P24)
        author = new Author();
        Paper paper = new Paper();
        paper.setTitle("P24");
        author.submitPaper(paper);
        
        // Input: Count papers for Author A003
        int result = author.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_multiRoleUserAuthorReviewer() {
        // Setup: Create User U1 with Author and Reviewer roles
        User user = new User();
        user.setName("U1");
        
        Author authorRole = new Author();
        Reviewer reviewerRole = new Reviewer();
        
        user.addRole(authorRole);
        user.addRole(reviewerRole);
        
        // Create Papers P25-P27 linked to U1 (through author role)
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            authorRole.submitPaper(paper);
        }
        
        // Input: Count papers for Author A004 (using the author role)
        int result = authorRole.countSubmittedPapers();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_paperOwnershipValidation() {
        // Setup: Create Author A005 with Paper P28
        Author authorA005 = new Author();
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.submitPaper(paperP28);
        
        // Create Author A006 with Paper P29
        Author authorA006 = new Author();
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.submitPaper(paperP29);
        
        // Input: Count papers for Author A005
        int result = authorA005.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
        
        // Verify that A006 has exactly 1 paper (additional validation)
        assertEquals(1, authorA006.countSubmittedPapers());
    }
}