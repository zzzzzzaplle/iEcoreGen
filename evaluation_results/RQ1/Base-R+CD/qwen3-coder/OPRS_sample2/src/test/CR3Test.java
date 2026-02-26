import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author author = new Author();
        
        // Create 5 papers and link them to the author
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
    public void testCase2_NewAuthorWith0Papers() {
        // Setup: Create Author A002 (no papers)
        Author author = new Author();
        
        // Input: Count papers for Author A002
        int result = author.countSubmittedPapers();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author();
        Paper paper = new Paper();
        paper.setTitle("P24");
        author.submitPaper(paper);
        
        // Input: Count papers for Author A003
        int result = author.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Setup: Create User U1 with Author and Reviewer roles
        User user = new User();
        Author authorRole = new Author();
        Reviewer reviewerRole = new Reviewer();
        
        user.addRole(authorRole);
        user.addRole(reviewerRole);
        
        // Create Papers P25-P27 linked to U1 (via author role)
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            authorRole.submitPaper(paper);
        }
        
        // Input: Count papers for Author A004 (using the author role from user U1)
        int result = authorRole.countSubmittedPapers();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Setup: Create Author A005 and Paper P28 (A005)
        Author authorA005 = new Author();
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.submitPaper(paperP28);
        
        // Create Author A006 and Paper P29 (A006)
        Author authorA006 = new Author();
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.submitPaper(paperP29);
        
        // Input: Count papers for Author A005
        int result = authorA005.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}