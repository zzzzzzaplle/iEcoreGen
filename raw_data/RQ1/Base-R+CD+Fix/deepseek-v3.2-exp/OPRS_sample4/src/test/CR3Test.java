import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_authorWith5Papers() {
        // Test Case 1: "Author with 5 papers"
        // Setup: Create Author A001 and Papers P19-P23 linked to A001
        Author author = new Author();
        author.submitPaper(new Paper());
        author.submitPaper(new Paper());
        author.submitPaper(new Paper());
        author.submitPaper(new Paper());
        author.submitPaper(new Paper());
        
        // Input: Count papers for Author A001
        int result = author.countSubmittedPapers();
        
        // Expected Output: 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_newAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        Author author = new Author();
        
        // Input: Count papers for Author A002
        int result = author.countSubmittedPapers();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_singlePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author();
        author.submitPaper(new Paper());
        
        // Input: Count papers for Author A003
        int result = author.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_multiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles
        User user = new User();
        Author author = new Author();
        Reviewer reviewer = new Reviewer();
        user.addRole(author);
        user.addRole(reviewer);
        
        // Create Papers P25-P27 linked to U1 (through author role)
        author.submitPaper(new Paper());
        author.submitPaper(new Paper());
        author.submitPaper(new Paper());
        
        // Input: Count papers for Author A004 (the author role of user U1)
        int result = author.countSubmittedPapers();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_paperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005
        Author authorA005 = new Author();
        // Create Paper P28 (A005)
        authorA005.submitPaper(new Paper());
        
        // Create Author A006
        Author authorA006 = new Author();
        // Create Paper P29 (A006)
        authorA006.submitPaper(new Paper());
        
        // Input: Count papers for Author A005
        int result = authorA005.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}