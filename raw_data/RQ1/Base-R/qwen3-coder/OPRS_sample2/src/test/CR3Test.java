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
        author.setName("A001");
        
        // Create 5 papers and link them to the author
        for (int i = 19; i <= 23; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.addSubmittedPaper(paper);
        }
        
        // Expected Output: 5
        assertEquals(5, author.countTotalPapers());
    }
    
    @Test
    public void testCase2_newAuthorWith0Papers() {
        // Test Case 2: "New author with 0 papers"
        // Setup: Create Author A002 (no papers)
        Author author = new Author();
        author.setName("A002");
        
        // Expected Output: 0
        assertEquals(0, author.countTotalPapers());
    }
    
    @Test
    public void testCase3_singlePaperAuthor() {
        // Test Case 3: "Single-paper author"
        // Setup: Create Author A003 and Paper P24 linked to A003
        Author author = new Author();
        author.setName("A003");
        
        Paper paper = new Paper();
        paper.setTitle("P24");
        author.addSubmittedPaper(paper);
        
        // Expected Output: 1
        assertEquals(1, author.countTotalPapers());
    }
    
    @Test
    public void testCase4_multiRoleUser() {
        // Test Case 4: "Multi-role user (author+reviewer)"
        // Setup: Create User U1 with Author and Reviewer roles
        // Note: Since Java doesn't support multiple inheritance, we'll create an Author
        // and also demonstrate that being a Reviewer doesn't affect paper count
        Author author = new Author();
        author.setName("U1");
        
        // Create Papers P25-P27 linked to U1 (as author)
        for (int i = 25; i <= 27; i++) {
            Paper paper = new Paper();
            paper.setTitle("P" + i);
            author.addSubmittedPaper(paper);
        }
        
        // Also create Reviewer role (this should not affect paper count)
        Reviewer reviewer = new Reviewer();
        reviewer.setName("U1");
        
        // Expected Output: 3 (only papers submitted as author count)
        assertEquals(3, author.countTotalPapers());
    }
    
    @Test
    public void testCase5_paperOwnershipValidation() {
        // Test Case 5: "Paper ownership validation"
        // Setup: Create Author A005 with Paper P28, and Author A006 with Paper P29
        Author authorA005 = new Author();
        authorA005.setName("A005");
        
        Author authorA006 = new Author();
        authorA006.setName("A006");
        
        // Create Paper P28 for A005
        Paper paperP28 = new Paper();
        paperP28.setTitle("P28");
        authorA005.addSubmittedPaper(paperP28);
        
        // Create Paper P29 for A006
        Paper paperP29 = new Paper();
        paperP29.setTitle("P29");
        authorA006.addSubmittedPaper(paperP29);
        
        // Expected Output: 1 (A005 should only have 1 paper - P28)
        assertEquals(1, authorA005.countTotalPapers());
        
        // Verify A006 has 1 paper as well
        assertEquals(1, authorA006.countTotalPapers());
    }
}