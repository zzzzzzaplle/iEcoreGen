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
        
        // Create 5 papers and submit them to the author
        for (int i = 0; i < 5; i++) {
            Paper paper = new Paper();
            author.submitPaper(paper);
        }
        
        // Expected Output: 5
        assertEquals(5, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        Author author = new Author();
        
        // Expected Output: 0
        assertEquals(0, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author();
        Paper paper = new Paper();
        
        author.submitPaper(paper);
        
        // Expected Output: 1
        assertEquals(1, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles
        // Create Papers P25-P27 linked to U1
        User user = new User();
        Author author = new Author();
        Reviewer reviewer = new Reviewer();
        
        // Add both roles to the user
        user.addRole(author);
        user.addRole(reviewer);
        
        // Submit 3 papers through the author role
        for (int i = 0; i < 3; i++) {
            Paper paper = new Paper();
            author.submitPaper(paper);
        }
        
        // Expected Output: 3 (counting papers from author role)
        assertEquals(3, author.countSubmittedPapers());
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005 and Paper P28 (A005)
        // Create Author A006 and Paper P29 (A006)
        Author authorA005 = new Author();
        Author authorA006 = new Author();
        
        Paper paperP28 = new Paper();
        Paper paperP29 = new Paper();
        
        // Submit papers to respective authors
        authorA005.submitPaper(paperP28);
        authorA006.submitPaper(paperP29);
        
        // Expected Output: 1 (only P28 should be counted for A005)
        assertEquals(1, authorA005.countSubmittedPapers());
    }
}