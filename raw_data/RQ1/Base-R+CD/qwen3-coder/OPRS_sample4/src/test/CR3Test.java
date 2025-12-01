import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_AuthorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author author = new Author();
        
        // Create 5 papers and add them to the author
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.submitPaper(paper);
        }
        
        // Expected Output: 5
        int result = author.countSubmittedPapers();
        assertEquals("Author with 5 papers should return count 5", 5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        Author author = new Author();
        
        // Expected Output: 0
        int result = author.countSubmittedPapers();
        assertEquals("New author with 0 papers should return count 0", 0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author();
        
        Paper paper = new Paper();
        paper.setTitle("P24");
        author.submitPaper(paper);
        
        // Expected Output: 1
        int result = author.countSubmittedPapers();
        assertEquals("Author with 1 paper should return count 1", 1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles, and Papers P25-P27 linked to U1
        User user = new User();
        user.setName("U1");
        
        // Create Author role and add papers
        Author authorRole = new Author();
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            authorRole.submitPaper(paper);
        }
        
        // Add Author role to user
        user.addRole(authorRole);
        
        // Add Reviewer role to user (though not needed for this test)
        Reviewer reviewerRole = new Reviewer();
        user.addRole(reviewerRole);
        
        // Expected Output: 3 (count from author role)
        int result = authorRole.countSubmittedPapers();
        assertEquals("Author role with 3 papers should return count 3", 3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005 with Paper P28, and Author A006 with Paper P29
        Author authorA005 = new Author();
        Author authorA006 = new Author();
        
        // Create paper for A005
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.submitPaper(paperP28);
        
        // Create paper for A006 (should not be counted for A005)
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.submitPaper(paperP29);
        
        // Expected Output: 1 (only P28 should be counted for A005)
        int result = authorA005.countSubmittedPapers();
        assertEquals("Author A005 should have only 1 paper (P28)", 1, result);
        
        // Verify A006 has 1 paper as well
        int resultA006 = authorA006.countSubmittedPapers();
        assertEquals("Author A006 should have only 1 paper (P29)", 1, resultA006);
    }
}