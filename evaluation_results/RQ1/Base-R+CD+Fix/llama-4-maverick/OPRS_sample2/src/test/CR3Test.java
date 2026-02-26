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
        
        // Create 5 papers and link them to the author
        Paper p19 = new Paper();
        Paper p20 = new Paper();
        Paper p21 = new Paper();
        Paper p22 = new Paper();
        Paper p23 = new Paper();
        
        author.submitPaper(p19);
        author.submitPaper(p20);
        author.submitPaper(p21);
        author.submitPaper(p22);
        author.submitPaper(p23);
        
        // Input: Count papers for Author A001
        int result = author.countSubmittedPapers();
        
        // Expected Output: 5
        assertEquals(5, result);
    }
    
    @Test
    public void testCase2_NewAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        Author author = new Author();
        
        // Input: Count papers for Author A002
        int result = author.countSubmittedPapers();
        
        // Expected Output: 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase3_SinglePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author();
        Paper p24 = new Paper();
        
        author.submitPaper(p24);
        
        // Input: Count papers for Author A003
        int result = author.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase4_MultiRoleUserAuthorReviewer() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles
        User user = new User();
        Author authorRole = new Author();
        Reviewer reviewerRole = new Reviewer();
        
        user.addRole(authorRole);
        user.addRole(reviewerRole);
        
        // Create Papers P25-P27 linked to U1 (via the author role)
        Paper p25 = new Paper();
        Paper p26 = new Paper();
        Paper p27 = new Paper();
        
        authorRole.submitPaper(p25);
        authorRole.submitPaper(p26);
        authorRole.submitPaper(p27);
        
        // Input: Count papers for Author A004 (the author role)
        int result = authorRole.countSubmittedPapers();
        
        // Expected Output: 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase5_PaperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: 
        // 1. Create Author A005
        // 2. Create Papers P28 (A005)
        // 3. Create Author A006  
        // 4. Create Papers P29 (A006)
        Author authorA005 = new Author();
        Author authorA006 = new Author();
        
        Paper p28 = new Paper();
        Paper p29 = new Paper();
        
        authorA005.submitPaper(p28);
        authorA006.submitPaper(p29);
        
        // Input: Count papers for Author A005
        int result = authorA005.countSubmittedPapers();
        
        // Expected Output: 1
        assertEquals(1, result);
    }
}