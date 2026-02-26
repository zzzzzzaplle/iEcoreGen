import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Setup: Create Author A001 with 5 papers
        Author author = new Author();
        
        // Create 5 papers (P19-P23)
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.submitPaper(paper);
        }
        
        // Input: Count papers for Author A001
        int result = author.countSubmittedPapers();
        
        // Expected Output: 5
        assertEquals("Author with 5 papers should return count of 5", 5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Setup: Create Author A002 with no papers
        Author author = new Author();
        
        // Input: Count papers for Author A002
        int result = author.countSubmittedPapers();
        
        // Expected Output: 0
        assertEquals("New author with 0 papers should return count of 0", 0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Setup: Create Author A003 with 1 paper
        Author author = new Author();
        
        // Create Paper P24
        Paper paper = new Paper();
        paper.setTitle("P24");
        author.submitPaper(paper);
        
        // Input: Count papers for Author A003
        int result = author.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals("Author with 1 paper should return count of 1", 1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
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
        assertEquals("Author role with 3 papers should return count of 3", 3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Setup: Create Author A005 and Author A006
        Author authorA005 = new Author();
        Author authorA006 = new Author();
        
        // Create Paper P28 for A005
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.submitPaper(paperP28);
        
        // Create Paper P29 for A006
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.submitPaper(paperP29);
        
        // Input: Count papers for Author A005
        int resultA005 = authorA005.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals("Author A005 should have exactly 1 paper", 1, resultA005);
        
        // Verify that Author A006 has 1 paper (additional validation)
        int resultA006 = authorA006.countSubmittedPapers();
        assertEquals("Author A006 should have exactly 1 paper", 1, resultA006);
    }
}